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
import java.util.List;
import model.ThietBi;

/**
 *
 * @author hihihihaha
 */
public class loadThietBi extends HttpServlet {

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
            out.println("<title>Servlet loadThietBi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadThietBi at " + request.getContextPath() + "</h1>");
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
        ThietBiDAO thietBiDAO = new ThietBiDAO();

        // Get current page and records per page
        int page = 1;
        int recordsPerPage = 8; // You can adjust this number

        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // If invalid page parameter, default to page 1
                page = 1;
            }
        }

        // Get search term and trim it
        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm != null) {
            searchTerm = searchTerm.trim();
        } else {
            searchTerm = "";
        }

        // Get paginated list and total records
        List<ThietBi> thietBiList = thietBiDAO.getAllThietBiWithDetailsPaging(searchTerm, page, recordsPerPage);
        int totalRecords = thietBiDAO.getTotalThietBi(searchTerm);

        // Calculate total pages
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        // Set attributes for JSP
        request.setAttribute("thietBiList", thietBiList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("searchTerm", searchTerm);

        // Forward to JSP page
        request.getRequestDispatcher("equipment.jsp").forward(request, response);

        // In the loadThietBi servlet's doGet method
        HttpSession session = request.getSession();
        session.removeAttribute("addThietBiError");
        session.removeAttribute("editThietBiError");
        session.removeAttribute("tenThietBi");
        session.removeAttribute("giaTien");
        session.removeAttribute("moTa");
        session.removeAttribute("soLuong");
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
