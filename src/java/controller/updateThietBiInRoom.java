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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hihihihaha
 */
public class updateThietBiInRoom extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

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
        int idThietBiPhong = Integer.parseInt(request.getParameter("idThietBiPhong"));
        int idPhong = Integer.parseInt(request.getParameter("idPhong"));
        int soLuong = Integer.parseInt(request.getParameter("soLuong"));
        String trangThai = request.getParameter("trangThai");
        String moTa = request.getParameter("moTa");

        ThietBiPhongDAO dao = new ThietBiPhongDAO();
        HttpSession session = request.getSession();

        try {
            boolean success = dao.updateThietBiInPhong(idThietBiPhong, soLuong, trangThai, moTa);
            if (success) {
                session.setAttribute("successMessage", "Cập nhật thiết bị thành công.");
            } else {
                session.setAttribute("updateErrorMessage", "Không đủ số lượng thiết bị để cập nhật.");
            }
        } catch (SQLException e) {
            session.setAttribute("updateErrorMessage", "Lỗi khi cập nhật thiết bị: " + e.getMessage());
        }

        response.sendRedirect("detailRoom?id=" + idPhong);
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
