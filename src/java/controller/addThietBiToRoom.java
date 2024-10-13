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
                throw new IllegalArgumentException("Các trường bắt buộc không được để trống");
            }

            int idPhong = Integer.parseInt(idPhongStr);
            int idThietBi = Integer.parseInt(idThietBiStr);
            int soLuong = Integer.parseInt(soLuongStr);

            ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();
            boolean success = thietBiPhongDAO.addThietBiToPhong(idPhong, idThietBi, soLuong, trangThai, moTa);

            if (success) {
                // Thiết bị đã được thêm thành công
                response.sendRedirect("detailRoom?id=" + idPhong);
            } else {
                // Không đủ số lượng thiết bị
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Không đủ số lượng thiết bị để thêm vào phòng.");
                response.sendRedirect("detailRoom?id=" + idPhong);
            }
        } catch (NumberFormatException e) {
            String idPhong = request.getParameter("idPhong");
            request.setAttribute("errorMessage", "Lỗi: Dữ liệu số không hợp lệ.");
            request.getRequestDispatcher("detailRoom?id=" + idPhong).forward(request, response);
        } catch (IllegalArgumentException e) {
            String idPhong = request.getParameter("idPhong");
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("detailRoom?id=" + idPhong).forward(request, response);
        } catch (Exception e) {
            String idPhong = request.getParameter("idPhong");
            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi thêm thiết bị: " + e.getMessage());
            request.getRequestDispatcher("detailRoom?id=" + idPhong).forward(request, response);
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
