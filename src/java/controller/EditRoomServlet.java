package controller;

import static controller.AddRoomServlet.extractFileName;
import dal.ActionHistoryDAO;
import dal.NhaTroDAO;
import dal.PhongDAO;
import dal.QuanLyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Account;
import model.ActionHistory;
import model.NhaTro;
import model.Phong;
import model.QuanLy;

/**
 *
 * @author
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
@WebServlet(name = "EditRoomServlet", urlPatterns = {"/editRoom"})
public class EditRoomServlet extends HttpServlet {

    private static final String UPLOAD_IMAGES_DIR = "images";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PhongDAO pdao = new PhongDAO();
            String nhaTroId = request.getParameter("tenNhaTro");
            String loaiPhongId = request.getParameter("tenLoaiPhong");
            String tenPhongTro = request.getParameter("tenPhongTro");
            String tang = request.getParameter("tang");
            String dienTich = request.getParameter("dienTich");
            String gia = request.getParameter("gia");
            String id = request.getParameter("phongId");

            // Validation variables
            boolean hasError = false;
            String errorMessage = "";

            // Fetch the old room details before updating
            Phong oldRoom = pdao.getDetailRoom(Integer.parseInt(id));

            // Validate room name
            if (tenPhongTro == null || tenPhongTro.trim().isEmpty()) {
                hasError = true;
                errorMessage += "Tên phòng không được để trống. ";
            } else {
                // Check if the room name exists for other rooms in the same nha tro
                if (!tenPhongTro.equals(oldRoom.getTenPhongTro())
                        && pdao.isRoomNameExists(tenPhongTro.trim(), Integer.parseInt(nhaTroId))) {
                    hasError = true;
                    errorMessage += "Tên phòng đã tồn tại trong nhà trọ này. ";
                }
            }

            // Validate floor number
            int tangNumber;
            try {
                tangNumber = Integer.parseInt(tang);
                if (tangNumber <= 0) {
                    hasError = true;
                    errorMessage += "Số tầng phải là số nguyên dương. ";
                }
            } catch (NumberFormatException e) {
                hasError = true;
                errorMessage += "Số tầng không hợp lệ. ";
            }

            // Validate area
            float dienTichValue;
            try {
                dienTichValue = Float.parseFloat(dienTich);
                if (dienTichValue <= 0) {
                    hasError = true;
                    errorMessage += "Diện tích phải lớn hơn 0. ";
                }
            } catch (NumberFormatException e) {
                hasError = true;
                errorMessage += "Diện tích không hợp lệ. ";
            }

            // Validate price
            int giaValue;
            try {
                giaValue = Integer.parseInt(gia);
                if (giaValue <= 0) {
                    hasError = true;
                    errorMessage += "Giá phòng phải lớn hơn 0. ";
                }
            } catch (NumberFormatException e) {
                hasError = true;
                errorMessage += "Giá phòng không hợp lệ. ";
            }

            // If there are errors, redirect back with error message
            if (hasError) {
                HttpSession session = request.getSession();
                session.setAttribute("editRoomError", errorMessage);
                session.setAttribute("tenPhongTro", tenPhongTro);
                session.setAttribute("tang", tang);
                session.setAttribute("dienTich", dienTich);
                session.setAttribute("gia", gia);
                response.sendRedirect("room");
                return;
            }

            // Handling image uploads
            List<String> imageFiles = new ArrayList<>();
            Collection<Part> fileParts = request.getParts();

            String applicationPath = getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + UPLOAD_IMAGES_DIR;

            File uploadDir = new File(uploadFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            for (Part filePart : fileParts) {
                if (filePart.getName().equals("urlPhongTro") && filePart.getSize() > 0) {
                    String fileName = extractFileName(filePart);
                    String imageFilePath = uploadFilePath + File.separator + fileName;
                    try (InputStream fileContent = filePart.getInputStream()) {
                        Path destinationPath = Paths.get(imageFilePath);
                        if (!Files.exists(destinationPath)) {
                            Files.copy(fileContent, new File(imageFilePath).toPath());
                        }
                    }
                    imageFiles.add(UPLOAD_IMAGES_DIR + File.separator + fileName);
                }
            }

            if (imageFiles.size() > 0) {
                // Delete old images and add new ones
                pdao.deleteImageByPhong(Integer.parseInt(id));
                for (String image : imageFiles) {
                    pdao.insertRoomImage(Integer.parseInt(id), image);
                }
            }

            // Update room - keeping the original trangThai
            Phong updatedRoom = new Phong(Integer.parseInt(id), Integer.parseInt(loaiPhongId),
                    tenPhongTro, Integer.parseInt(nhaTroId),
                    Integer.parseInt(tang),
                    oldRoom.getTrang_thai(), // Keep the original status
                    Float.parseFloat(dienTich),
                    Integer.parseInt(gia));

            pdao.updateRoom(updatedRoom);

            // for action history
            // Log the changes
            StringBuilder changesLog = new StringBuilder("Cập nhật chi tiết nhà trọ: \n");

            if (!oldRoom.getTenPhongTro().equals(tenPhongTro)) {
                changesLog.append("Tên phòng trọ đổi từ '")
                        .append(oldRoom.getTenPhongTro())
                        .append("' thành '")
                        .append(tenPhongTro)
                        .append("'.\n");
            }
            if (oldRoom.getTang() != Integer.parseInt(tang)) {
                changesLog.append("Tầng đổi từ '")
                        .append(oldRoom.getTang())
                        .append("' thành '")
                        .append(tang)
                        .append("'.\n");
            }
            if (oldRoom.getID_LoaiPhong() != Integer.parseInt(loaiPhongId)) {
                changesLog.append("Đã thay đổi kiểu phòng.\n");
            }
            if (oldRoom.getDien_tich() != Float.parseFloat(dienTich)) {
                changesLog.append("Diện tích đã đổi từ '")
                        .append(oldRoom.getDien_tich())
                        .append(" m²' thành '")
                        .append(dienTich)
                        .append(" m²'.\n");
            }
            if (oldRoom.getGia() != Integer.parseInt(gia)) {
                changesLog.append("Giá đã đổi từ '")
                        .append(oldRoom.getGia())
                        .append("' thành '")
                        .append(gia)
                        .append("'.\n");
            }

            if (imageFiles.size() > 0) {
                changesLog.append("Ảnh phòng đã cập nhật.\n");
            }

            // Log the action if the user is a manager
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account.getRole().equals("manager")) {
                ActionHistoryDAO ahdao = new ActionHistoryDAO();
                ActionHistory history = new ActionHistory();

                NhaTroDAO ntdao = new NhaTroDAO();
                NhaTro nhaTro = ntdao.getNhaTroByPhongTroId(updatedRoom.getID_Phong());

                history.setNhaTro(nhaTro);

                QuanLyDAO qldao = new QuanLyDAO();
                QuanLy quanLy = qldao.getChuTroByAccountId(account.getID_Account());
                history.setQuanLy(quanLy);
                history.setTitle("Room Update");
                history.setContent(changesLog.toString());

                ahdao.insertActionHistory(history);
            }

            response.sendRedirect("room");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
