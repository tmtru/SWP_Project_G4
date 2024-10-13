/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PhongDAO;
import dal.ThietBiDAO;
import dal.ThietBiPhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Phong;
import model.ThietBi;
import model.ThietBiPhong;

/**
 *
 * @author hihihihaha
 */
public class detailRoomServlet extends HttpServlet {

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
            out.println("<title>Servlet detailRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet detailRoomServlet at " + request.getContextPath() + "</h1>");
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
        String roomIdStr = request.getParameter("id");
        if (roomIdStr != null && !roomIdStr.isEmpty()) {
            try {
                int roomId = Integer.parseInt(roomIdStr);
                PhongDAO phongDAO = new PhongDAO();
                ThietBiDAO thietBiDAO = new ThietBiDAO();
                ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();

                Phong room = phongDAO.getDetailRoom(roomId);
                List<ThietBi> allThietBi = thietBiDAO.getAllThietBi();
                List<ThietBiPhong> thietBiList = thietBiPhongDAO.getThietBiByPhongId(roomId);

                if (room != null) {
                    request.setAttribute("room", room);
                    request.setAttribute("allThietBi", allThietBi);
                    request.setAttribute("thietBiList", thietBiList);

                    HttpSession session = request.getSession();

                    // Xử lý thông báo lỗi từ session
                    String errorMessage = (String) session.getAttribute("errorMessage");
                    if (errorMessage != null) {
                        request.setAttribute("errorMessage", errorMessage);
                        session.removeAttribute("errorMessage");
                    }

                    // Xử lý thông báo lỗi cập nhật từ session
                    String updateErrorMessage = (String) session.getAttribute("updateErrorMessage");
                    if (updateErrorMessage != null) {
                        request.setAttribute("updateErrorMessage", updateErrorMessage);
                        session.removeAttribute("updateErrorMessage");
                    }

                    // Xử lý thông báo thành công từ session
                    String successMessage = (String) session.getAttribute("successMessage");
                    if (successMessage != null) {
                        request.setAttribute("successMessage", successMessage);
                        session.removeAttribute("successMessage");
                    }

                    request.getRequestDispatcher("detailRoom.jsp").forward(request, response);
                } else {
                    response.sendRedirect("room.jsp");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("room.jsp");
            }
        } else {
            response.sendRedirect("home.jsp");
        }
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
