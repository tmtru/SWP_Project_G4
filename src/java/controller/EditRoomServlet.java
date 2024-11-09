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
    
    // Define allowed image types
    private static final String[] ALLOWED_IMAGE_TYPES = {
        "image/jpeg",
        "image/png",
        "image/gif",
        "image/jpg"
    };

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

            // Add image format validation
            Collection<Part> fileParts = request.getParts();
            boolean hasImages = false;

            // Validate image formats if any images are uploaded
            for (Part filePart : fileParts) {
                if (filePart.getName().equals("urlPhongTro") && filePart.getSize() > 0) {
                    hasImages = true;
                    String contentType = filePart.getContentType();
                    boolean isValidFormat = false;
                    
                    // Check if the file type is allowed
                    for (String allowedType : ALLOWED_IMAGE_TYPES) {
                        if (contentType.toLowerCase().equals(allowedType.toLowerCase())) {
                            isValidFormat = true;
                            break;
                        }
                    }
                    
                    if (!isValidFormat) {
                        hasError = true;
                        errorMessage += "Định dạng ảnh không hợp lệ. Chỉ chấp nhận các định dạng: JPEG, JPG, PNG, GIF. ";
                        break;
                    }
                }
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

            // Handle image uploads if there are any new images
            List<String> imageFiles = new ArrayList<>();
            if (hasImages) {
                String applicationPath = getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + UPLOAD_IMAGES_DIR;

                // Create upload directory if it doesn't exist
                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                // Process each uploaded file
                for (Part filePart : fileParts) {
                    if (filePart.getName().equals("urlPhongTro") && filePart.getSize() > 0) {
                        String fileName = extractFileName(filePart);
                        String imageFilePath = uploadFilePath + File.separator + fileName;
                        
                        // Save the file
                        try (InputStream fileContent = filePart.getInputStream()) {
                            Path destinationPath = Paths.get(imageFilePath);
                            if (!Files.exists(destinationPath)) {
                                Files.copy(fileContent, new File(imageFilePath).toPath());
                            }
                        }
                        
                        // Add the file path to the list
                        imageFiles.add(UPLOAD_IMAGES_DIR + File.separator + fileName);
                    }
                }

                // Update the room's images in the database
                if (!imageFiles.isEmpty()) {
                    // Delete old images
                    pdao.deleteImageByPhong(Integer.parseInt(id));
                    
                    // Insert new images
                    for (String image : imageFiles) {
                        pdao.insertRoomImage(Integer.parseInt(id), image);
                    }
                }
            }

            // Create updated room object
            Phong updatedRoom = new Phong(
                    Integer.parseInt(id),
                    Integer.parseInt(loaiPhongId),
                    tenPhongTro,
                    Integer.parseInt(nhaTroId),
                    Integer.parseInt(tang),
                    oldRoom.getTrang_thai(), // Keep the original status
                    Float.parseFloat(dienTich),
                    Integer.parseInt(gia)
            );

            // Update room in database
            pdao.updateRoom(updatedRoom);

            // Create log of changes for action history
            StringBuilder changesLog = new StringBuilder("Cập nhật chi tiết phòng trọ: \n");

            // Log name changes
            if (!oldRoom.getTenPhongTro().equals(tenPhongTro)) {
                changesLog.append("Tên phòng trọ đổi từ '")
                        .append(oldRoom.getTenPhongTro())
                        .append("' thành '")
                        .append(tenPhongTro)
                        .append("'.\n");
            }

            // Log floor changes
            if (oldRoom.getTang() != Integer.parseInt(tang)) {
                changesLog.append("Tầng đổi từ '")
                        .append(oldRoom.getTang())
                        .append("' thành '")
                        .append(tang)
                        .append("'.\n");
            }

            // Log room type changes
            if (oldRoom.getID_LoaiPhong() != Integer.parseInt(loaiPhongId)) {
                changesLog.append("Đã thay đổi kiểu phòng.\n");
            }

            // Log area changes
            if (oldRoom.getDien_tich() != Float.parseFloat(dienTich)) {
                changesLog.append("Diện tích đã đổi từ '")
                        .append(oldRoom.getDien_tich())
                        .append(" m²' thành '")
                        .append(dienTich)
                        .append(" m²'.\n");
            }

            // Log price changes
            if (oldRoom.getGia() != Integer.parseInt(gia)) {
                changesLog.append("Giá đã đổi từ '")
                        .append(oldRoom.getGia())
                        .append("' thành '")
                        .append(gia)
                        .append("'.\n");
            }

            // Log image changes
            if (!imageFiles.isEmpty()) {
                changesLog.append("Ảnh phòng đã cập nhật.\n");
            }

            // Record action history if user is a manager
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account.getRole().equals("manager")) {
                ActionHistoryDAO ahdao = new ActionHistoryDAO();
                ActionHistory history = new ActionHistory();

                // Get NhaTro information
                NhaTroDAO ntdao = new NhaTroDAO();
                NhaTro nhaTro = ntdao.getNhaTroByPhongTroId(updatedRoom.getID_Phong());
                history.setNhaTro(nhaTro);

                // Get QuanLy information
                QuanLyDAO qldao = new QuanLyDAO();
                QuanLy quanLy = qldao.getChuTroByAccountId(account.getID_Account());
                history.setQuanLy(quanLy);

                // Set history details
                history.setTitle("Room Update");
                history.setContent(changesLog.toString());

                // Save action history
                ahdao.insertActionHistory(history);
            }

            // Redirect back to room page
            response.sendRedirect("room");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("room"); // Redirect on error
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