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
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ThietBi;

/**
 *
 * @author hihihihaha
 */
public class editThietBi extends HttpServlet {

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
            out.println("<title>Servlet editThietBi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editThietBi at " + request.getContextPath() + "</h1>");
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

        ThietBiDAO thietBiDAO = new ThietBiDAO();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String tenThietBi = request.getParameter("tenthietbi").trim();
            int giaTien = Integer.parseInt(request.getParameter("giatien"));
            String moTa = request.getParameter("mota");
            String soLuong = request.getParameter("soluong");

            // Retrieve the existing device to compare quantities
            ThietBi existingThietBi = thietBiDAO.getThietBiById(id);

            // Validate inputs
            if (giaTien <= 0) {
                request.getSession().setAttribute("editThietBiError", "Giá tiền phải lớn hơn 0.");
                response.sendRedirect("loadThietBi");
                return;
            }
            // Validate space khoag trang
            if (tenThietBi.isEmpty()) {
                request.getSession().setAttribute("editThietBiError", "Tên thiết bị không được để trống.");
                response.sendRedirect("loadThietBi");
                return;
            }
            // Check if the new device name already exists in other devices
            List<ThietBi> allThietBi = thietBiDAO.getAllThietBi();
            boolean isDuplicate = allThietBi.stream()
                    .anyMatch(tb -> tb.getID_ThietBi() != id
                    && tb.getTenThietBi().equalsIgnoreCase(tenThietBi));

            if (isDuplicate) {
                request.getSession().setAttribute("editThietBiError", "Tên thiết bị đã tồn tại.");
                response.sendRedirect("loadThietBi");
                return;
            }

            // Compare new quantity with existing quantity
            int oldQuantity = Integer.parseInt(existingThietBi.getSo_luong());
            int newQuantity = Integer.parseInt(soLuong);

            if (newQuantity < oldQuantity) {
                request.getSession().setAttribute("editThietBiError", "Số lượng không được nhỏ hơn số lượng cũ.");
                response.sendRedirect("loadThietBi");
                return;
            }

            // If all validations pass, update the device
            ThietBi thietBi = new ThietBi();
            thietBi.setID_ThietBi(id);
            thietBi.setTenThietBi(tenThietBi);
            thietBi.setGia_tien(giaTien);
            thietBi.setMo_ta(moTa);
            thietBi.setSo_luong(soLuong);

            thietBiDAO.updateThietBi(thietBi);
            response.sendRedirect("loadThietBi");

        } catch (SQLException | NumberFormatException ex) {
            request.getSession().setAttribute("editThietBiError", "Đã có lỗi xảy ra: " + ex.getMessage());
            response.sendRedirect("loadThietBi");
            Logger.getLogger(editThietBi.class.getName()).log(Level.SEVERE, null, ex);
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
