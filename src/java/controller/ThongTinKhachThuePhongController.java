/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.KhachThueDAO;
import dal.KhachThuePhuDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class ThongTinKhachThuePhongController extends HttpServlet {

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
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        KhachThuePhuDAO khachThuePhuDAO = new KhachThuePhuDAO();
        String Hovaten = request.getParameter("name");
        String Cmnd = request.getParameter("cmnd");
        String Gioitinh = request.getParameter("gender");
        int gioiTinhInt = "male".equals(Gioitinh) ? 1 : "female".equals(Gioitinh) ? 0 : -1; // -1 cho trường hợp không hợp lệ
        String Dienthoai = request.getParameter("phone1");
        String Email = request.getParameter("email");
        String DiaChi = request.getParameter("address");
        String NgaySinh = request.getParameter("dob");  // Ngày sinh
        String hopDongIdStr = request.getParameter("hopDongId");
        int hopDongId = Integer.parseInt(hopDongIdStr);
        int IdAcount = khachThueDAO.getIdAccountByEmail(Email);
        request.setAttribute("Hovaten", Hovaten);
        request.setAttribute("Cmnd", Cmnd);
        request.setAttribute("Gioitinh", Gioitinh);
        request.setAttribute("Dienthoai", Dienthoai);
        request.setAttribute("Email", Email);
        request.setAttribute("DiaChi", DiaChi);
        request.setAttribute("NgaySinh", NgaySinh);  // Ngày sinh
        
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

        

        // Nếu không có lỗi, thực hiện chèn vào cơ sở dữ liệu
        if (isValid) {
            // Chèn thông tin khách thuê
            boolean success = khachThuePhuDAO.insertKhachThuePhu(hopDongId, NgaySinh, Cmnd, Email, Dienthoai, DiaChi, Hovaten, gioiTinhInt);

            // Hiển thị thông báo thành công hoặc thất bại
            if (success) {
                request.setAttribute("message", "Lưu thông tin khách thuê phòng thành công!");
                // Thiết lập các thuộc tính để hiển thị lại thông tin nếu cần

                request.getRequestDispatcher("ThongTinKhachThuePhong.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Lưu thông tin khách thuê phòng thất bại!");
            }
        } else {
            // Nếu có lỗi, chuyển hướng về trang và hiển thị thông báo lỗi
            request.getRequestDispatcher("ThongTinKhachThuePhong.jsp").forward(request, response);
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
    }// </editor-fold>

}
