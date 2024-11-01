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
import java.util.List;
import model.ThietBi;

/**
 *
 * @author hihihihaha
 */
public class searchThietBi extends HttpServlet {

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
            out.println("<title>Servlet searchThietBi</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet searchThietBi at " + request.getContextPath() + "</h1>");
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
    private static final int RECORDS_PER_PAGE = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get search term from request
        String searchTerm = request.getParameter("search");

        // If search term is null, empty, or contains only whitespace, redirect to the main page
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            response.sendRedirect("loadThietBi");
            return;
        }

        // Get current page number
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        // Initialize DAO
        ThietBiDAO thietBiDAO = new ThietBiDAO();

        // Get filtered list of devices
        List<ThietBi> thietBiList = thietBiDAO.searchThietBiWithoutSpaces(searchTerm, currentPage, RECORDS_PER_PAGE);

        // Get total number of records for pagination
        int totalRecords = thietBiDAO.getTotalThietBiBySearch(searchTerm);
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

        // Set attributes for the JSP
        request.setAttribute("thietBiList", thietBiList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);

        // Forward to the same JSP used for listing devices
        request.getRequestDispatcher("equipment.jsp").forward(request, response);
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
