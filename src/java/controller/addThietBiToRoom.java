/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ThietBiPhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author hihihihaha
 */
public class addThietBiToRoom extends HttpServlet {

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
            out.println("<title>Servlet addThietBiToRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addThietBiToRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            // Validate input parameters
            String idPhongStr = request.getParameter("idPhong");
            String idThietBiStr = request.getParameter("idThietBi");
            String soLuongStr = request.getParameter("soLuong");
            String trangThai = request.getParameter("trangThai");
            String moTa = request.getParameter("moTa");

            // Check for null or empty values
            if (idPhongStr == null || idThietBiStr == null || soLuongStr == null
                    || idPhongStr.isEmpty() || idThietBiStr.isEmpty() || soLuongStr.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            // Validate số lượng trước khi parse sang int
            try {
                int soLuong = Integer.parseInt(soLuongStr);

                // Kiểm tra số lượng = 0
                if (soLuong == 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", "Số lượng không thể bằng 0");
                    response.sendRedirect("detailRoom?id=" + idPhongStr);
                    return;
                }

                // Kiểm tra số lượng âm
                if (soLuong < 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", "Số lượng không thể là số âm");
                    response.sendRedirect("detailRoom?id=" + idPhongStr);
                    return;
                }

                int idPhong = Integer.parseInt(idPhongStr);
                int idThietBi = Integer.parseInt(idThietBiStr);

                ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();

                try {
                    boolean success = thietBiPhongDAO.addThietBiToPhong(idPhong, idThietBi, soLuong, trangThai, moTa);
                    if (success) {
                        // Thiết bị đã được thêm thành công
                        HttpSession session = request.getSession();
                        session.setAttribute("successMessage", "Thêm thiết bị thành công!");
                        response.sendRedirect("detailRoom?id=" + idPhong);
                    } else {
                        // Không đủ số lượng thiết bị
                        HttpSession session = request.getSession();
                        session.setAttribute("errorMessage", "Không đủ số lượng thiết bị để thêm vào phòng.");
                        response.sendRedirect("detailRoom?id=" + idPhong);
                    }
                } catch (IllegalArgumentException e) {
                    // Bắt lỗi thiết bị đã tồn tại
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", e.getMessage());
                    response.sendRedirect("detailRoom?id=" + idPhong);
                }
            } catch (NumberFormatException e) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Số lượng phải là số nguyên hợp lệ!");
                response.sendRedirect("detailRoom?id=" + idPhongStr);
            }
        } catch (Exception e) {
            String idPhong = request.getParameter("idPhong");
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Đã xảy ra lỗi khi thêm thiết bị: " + e.getMessage());
            response.sendRedirect("detailRoom?id=" + idPhong);
        }
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
