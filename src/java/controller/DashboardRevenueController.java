/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.NhaTro;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DashboardRevenueController", urlPatterns = {"/statistic-revenue"})
public class DashboardRevenueController extends HttpServlet {

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
        PhongDAO roomDAO = new PhongDAO();
        String idHouseStr = request.getParameter("idHouse");

        // Get houses by role from session
        @SuppressWarnings("unchecked")
        List<NhaTro> housesByRole = (List<NhaTro>) request.getSession().getAttribute("housesByRole");

        // Handle house selection with automatic selection of first house
        int currentHouseId = -1;

        if (idHouseStr != null && !idHouseStr.isEmpty()) {
            try {
                currentHouseId = Integer.parseInt(idHouseStr);
            } catch (NumberFormatException e) {
                // Invalid ID, will fall back to first house
            }
        } else {
            Object sessionHouse = request.getSession().getAttribute("currentHouse");
            if (sessionHouse != null) {
                currentHouseId = (Integer) sessionHouse;
            }
        }

        // If no house is selected and there are available houses, select the first one
        if (currentHouseId == -1 && housesByRole != null && !housesByRole.isEmpty()) {
            currentHouseId = housesByRole.get(0).getID_NhaTro();
        }

        // Store the selected house ID in session
        request.getSession().setAttribute("currentHouse", currentHouseId);
        List<Integer> statisticRevenue = roomDAO.getStatisticRevenueByApartment(currentHouseId);
        request.setAttribute("statisticRevenue", statisticRevenue);

        // Forward to JSP
        request.getRequestDispatcher("statistic-revenue-dashboard.jsp").forward(request, response);
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
