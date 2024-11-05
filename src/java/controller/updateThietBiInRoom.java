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
        HttpSession session = request.getSession();
        
        try {
            // Validate input parameters
            String idThietBiPhongStr = request.getParameter("idThietBiPhong");
            String idPhongStr = request.getParameter("idPhong");
            String soLuongStr = request.getParameter("soLuong");
            String trangThai = request.getParameter("trangThai");
            String moTa = request.getParameter("moTa");

            // Check for null or empty values
            if (idThietBiPhongStr == null || idPhongStr == null || soLuongStr == null
                    || idThietBiPhongStr.isEmpty() || idPhongStr.isEmpty() || soLuongStr.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            // Parse numeric values
            int idThietBiPhong = Integer.parseInt(idThietBiPhongStr);
            int idPhong = Integer.parseInt(idPhongStr);
            int soLuong = Integer.parseInt(soLuongStr);

            // Validate numeric values
            if (soLuong <= 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
            }

            ThietBiPhongDAO dao = new ThietBiPhongDAO();
            boolean success = dao.updateThietBiInPhong(idThietBiPhong, soLuong, trangThai, moTa);
            
            if (success) {
                session.setAttribute("successMessage", "Cập nhật thiết bị thành công!");
            } else {
                session.setAttribute("updateErrorMessage", "Không đủ số lượng thiết bị để cập nhật.");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("updateErrorMessage", "Lỗi: Dữ liệu số không hợp lệ.");
        } catch (IllegalArgumentException e) {
            session.setAttribute("updateErrorMessage", e.getMessage());
       
        } catch (Exception e) {
            session.setAttribute("updateErrorMessage", "Đã xảy ra lỗi không mong muốn: " + e.getMessage());
        }
        
        // Get idPhong for redirect, with error handling
        String idPhong = "0";
        try {
            idPhong = request.getParameter("idPhong");
        } catch (Exception e) {
            session.setAttribute("updateErrorMessage", "Lỗi khi xử lý ID phòng.");
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
