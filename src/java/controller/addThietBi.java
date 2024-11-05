/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ThietBiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ThietBi;

/**
 *
 * @author hihihihaha
 */
public class addThietBi extends HttpServlet {

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
            out.println("<title>Servlet addThietBi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addThietBi at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Get parameters
        String tenThietBi = request.getParameter("tenthietbi");
        String giaTienStr = request.getParameter("giatien");
        String moTa = request.getParameter("mota");
        String soLuongStr = request.getParameter("soluong");

        // Create DAO
        ThietBiDAO thietBiDAO = new ThietBiDAO();

        // Validation variables
        boolean hasError = false;
        String errorMessage = "";

        // Validate device name
        if (tenThietBi == null || tenThietBi.trim().isEmpty()) {
            hasError = true;
            errorMessage += "Tên thiết bị không được để trống. ";
        } else if (thietBiDAO.checkDuplicateThietBi(tenThietBi.trim())) {
            hasError = true;
            errorMessage += "Tên thiết bị đã tồn tại. ";
        }

        // Validate price
        int giaTien = 0;
        try {
            giaTien = Integer.parseInt(giaTienStr);
            if (giaTien <= 0) {
                hasError = true;
                errorMessage += "Giá tiền phải lớn hơn 0. ";
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMessage += "Giá tiền không hợp lệ. ";
        }

        // Validate quantity
        int soLuong = 0;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                hasError = true;
                errorMessage += "Số lượng phải lớn hơn 0. ";
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMessage += "Số lượng không hợp lệ. ";
        }

        // If there are errors, redirect back with error message
        if (hasError) {
            HttpSession session = request.getSession();
            session.setAttribute("addThietBiError", errorMessage);
            session.setAttribute("tenThietBi", tenThietBi);
            session.setAttribute("giaTien", giaTienStr);
            session.setAttribute("moTa", moTa);
            session.setAttribute("soLuong", soLuongStr);
            response.sendRedirect("loadThietBi");
            return;
        }

        // Create ThietBi object
        ThietBi thietBi = new ThietBi();
        thietBi.setTenThietBi(tenThietBi.trim());
        thietBi.setGia_tien(giaTien);
        thietBi.setMo_ta(moTa);
        thietBi.setSo_luong(String.valueOf(soLuong));

        try {
            // Add device
            thietBiDAO.addThietBi(thietBi);
            response.sendRedirect("loadThietBi");
        } catch (SQLException ex) {
            ex.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("addThietBiError", "Có lỗi xảy ra khi thêm thiết bị.");
            response.sendRedirect("loadThietBi");
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
