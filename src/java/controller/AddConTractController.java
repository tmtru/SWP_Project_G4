/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.KhachThueDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class AddConTractController extends HttpServlet {

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
        AccountDAO accountDao = new AccountDAO();
        // Khởi tạo biến
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        String Hovaten = request.getParameter("name");
        String Cmnd = request.getParameter("cmnd");
        String Gioitinh = request.getParameter("gender");
        String Ngaycap = request.getParameter("issuedate");
        String Dienthoai = request.getParameter("phone1");
        String Email = request.getParameter("email");
        String DiaChi = request.getParameter("address");
        String ThanhPho = request.getParameter("city");
        String NgaySinh = request.getParameter("dob");  // Ngày sinh
        String GhiChu = request.getParameter("note");
        String MatTruoc = request.getParameter("imgMatTruoc");
        String MatSau = request.getParameter("imgMatSau");
        String SoPhong = request.getParameter("tenPhongTro");
        String Nghenghiep = request.getParameter("nghenghiep");
        int IdAcount = khachThueDAO.getIdAccountByEmail(Email);
        int roomId = khachThueDAO.getRoomIdByRoomName(SoPhong);
        request.setAttribute("Hovaten", Hovaten);
        request.setAttribute("Cmnd", Cmnd);
        request.setAttribute("Gioitinh", Gioitinh);
        request.setAttribute("Ngaycap", Ngaycap);  // Ngày cấp
        request.setAttribute("Dienthoai", Dienthoai);
        request.setAttribute("Email", Email);
        request.setAttribute("DiaChi", DiaChi);
        request.setAttribute("ThanhPho", ThanhPho);
        request.setAttribute("NgaySinh", NgaySinh);  // Ngày sinh
        request.setAttribute("GhiChu", GhiChu);
        request.setAttribute("MatTruoc", MatTruoc);
        request.setAttribute("MatSau", MatSau);
        request.setAttribute("SoPhong", SoPhong);
        request.setAttribute("Nghenghiep", Nghenghiep);
        List<String> emails = accountDao.getEmailsByRole();

        request.setAttribute("emails", emails);
        // Kiểm tra tính hợp lệ của ngày sinh và ngày cấp
        boolean isValid = true; // Biến để kiểm tra tính hợp lệ

        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // Kiểm tra ngày sinh
        LocalDate birthDate = LocalDate.parse(NgaySinh);
        if (birthDate.isAfter(today)) {
            isValid = false;
            request.setAttribute("message", "Ngày sinh không được sau ngày hiện tại.");
        }

        // Kiểm tra ngày cấp
        LocalDate issueDate = LocalDate.parse(Ngaycap);
        if (issueDate.isAfter(today)) {
            isValid = false;
            request.setAttribute("message", "Ngày cấp không được sau ngày hiện tại.");
        }
        if (issueDate.isBefore(birthDate)) {
            isValid = false;
            request.setAttribute("message", "Ngày cấp phải sau ngày sinh.");
        }

        // Nếu không có lỗi, thực hiện chèn vào cơ sở dữ liệu
        if (isValid) {
            // Chèn thông tin khách thuê
            boolean success = khachThueDAO.insertKhachThue(Hovaten, Cmnd, Gioitinh, Ngaycap, Dienthoai, Email, DiaChi, ThanhPho, NgaySinh, SoPhong, GhiChu, MatTruoc, MatSau, IdAcount, roomId, Nghenghiep);

            // Hiển thị thông báo thành công hoặc thất bại
            if (success) {
                request.setAttribute("message", "Thêm khách thành công!");
                // Thiết lập các thuộc tính để hiển thị lại thông tin nếu cần

                request.getRequestDispatcher("ThemKhachThuePhong.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Thêm khách thất bại!");
            }
        } else {
            // Nếu có lỗi, chuyển hướng về trang và hiển thị thông báo lỗi
            request.getRequestDispatcher("ThemKhachThuePhong.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String IdPhongTro = request.getParameter("roomId");
        int IDPhongTro = Integer.parseInt(IdPhongTro);
        AccountDAO accountDao = new AccountDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();

        String tenPhongTro = khachThueDAO.getTenPhongTroById(IDPhongTro);
        List<String> emails = accountDao.getEmailsByRole();

        request.setAttribute("SoPhong", tenPhongTro);
        request.setAttribute("emails", emails); // Set emails list as request attribute

        // Chuyển tiếp request và response sang trang JSP
        request.getRequestDispatcher("ThemKhachThuePhong.jsp").forward(request, response);
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
