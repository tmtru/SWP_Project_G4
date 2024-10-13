/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import model.AnhPhongTro;
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
public class AddRoomServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRoomServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            //get value from form insert
            String nhaTroId = request.getParameter("tenNhaTro");
            String loaiPhongId = request.getParameter("tenLoaiPhong");
            String tenPhongTro = request.getParameter("tenPhongTro");
            String tang = request.getParameter("tang");
            String dienTich = request.getParameter("dienTich");
            String gia = request.getParameter("gia");

            // Xử lý upload nhiều file image
            List<String> imageFiles = new ArrayList<>();
            Collection<Part> fileParts = request.getParts(); // Lấy tất cả các file được gửi

            String applicationPath = getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + UPLOAD_IMAGES_DIR;

            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(uploadFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            for (Part filePart : fileParts) {
                // Kiểm tra nếu là một file (vì request.getParts() có thể trả về cả các part không phải file)
                if (filePart.getName().equals("urlPhongTro") && filePart.getSize() > 0) {
                    String fileName = extractFileName(filePart);

                    // Lưu file hình ảnh
                    String imageFilePath = uploadFilePath + File.separator + fileName;
                    try (InputStream fileContent = filePart.getInputStream()) {
                        // Nếu file chưa tồn tại thì lưu ảnh
                        Path destinationPath = Paths.get(imageFilePath);
                        if (!Files.exists(destinationPath)) {
                            Files.copy(fileContent, new File(imageFilePath).toPath());
                        }
                    }
                    // Thêm đường dẫn file vào danh sách
                    String imageFile = UPLOAD_IMAGES_DIR + File.separator + fileName;
                    imageFiles.add(imageFile);
                }
            }

            //insert room
            Phong room = new Phong(Integer.parseInt(loaiPhongId),
                    tenPhongTro, Integer.parseInt(nhaTroId),
                    Integer.parseInt(tang),
                    "T",
                    Float.parseFloat(dienTich),
                    Integer.parseInt(gia),
                    imageFiles);
            PhongDAO pdao = new PhongDAO();
            pdao.insertRoom(room);
            NhaTroDAO ntdao = new NhaTroDAO();
            Phong phong = pdao.getLatestPhong();
            NhaTro nhaTro = ntdao.getNhaTroByPhongTroId(phong.getID_Phong());
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account.getRole().equals("Quản lý")) {
                ActionHistoryDAO ahdao = new ActionHistoryDAO();
                ActionHistory history = new ActionHistory();

                history.setNhaTro(nhaTro);

                QuanLyDAO qldao = new QuanLyDAO();
                QuanLy quanLy = qldao.getChuTroByAccountId(account.getID_Account());
                history.setQuanLy(quanLy);
                history.setTitle("Thêm phòng mới");
                history.setContent("Thêm phòng " + phong.getTenPhongTro() + " của " + nhaTro.getTenNhaTro());
                ahdao.insertActionHistory(history);
            }
            response.sendRedirect("room");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
