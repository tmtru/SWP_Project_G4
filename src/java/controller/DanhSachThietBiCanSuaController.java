/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.HopDongDAO;
import dal.PhongDAO;
import dal.ThietBiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HopDong;
import model.NhaTro;
import model.ThietBi;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DanhSachThietBiCanSuaController", urlPatterns = {"/thiet-bi"})
public class DanhSachThietBiCanSuaController extends HttpServlet {

    private static final int ACCOUNTS_PER_PAGE = 10;
    private final AccountDAO accountDAO = new AccountDAO();
    private final ThietBiDAO thietBiDAO = new ThietBiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
            currentHouseId = ((NhaTro) housesByRole.get(0)).getID_NhaTro();
        }

        // Store the selected house ID in session
        request.getSession().setAttribute("currentHouse", currentHouseId);

        // Calculate dashboard statistics
        try {
            // Get total equipment count
            int totalEquipment = thietBiDAO.getTotalEquipmentCount(currentHouseId);

            // Get equipment counts by status
            int goodEquipment = thietBiDAO.getEquipmentCountByStatus(currentHouseId, "T");
            int normalEquipment = thietBiDAO.getEquipmentCountByStatus(currentHouseId, "BT");
            int brokenEquipment = thietBiDAO.getEquipmentCountByStatus(currentHouseId, "CSC");

            // Set attributes for the dashboard
            request.setAttribute("totalEquipment", totalEquipment);            
            request.setAttribute("goodEquipment", goodEquipment);
            request.setAttribute("normalEquipment", normalEquipment);
            request.setAttribute("brokenEquipment", brokenEquipment);
        } catch (SQLException e) {
            // Log the error and set default values
            e.printStackTrace();
            request.setAttribute("totalEquipment", 0);           
            request.setAttribute("goodEquipment", 0);
            request.setAttribute("normalEquipment", 0);
            request.setAttribute("brokenEquipment", 0);
        }

        // Handle pagination
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm == null) {
            searchTerm = "";
        }

        int start = (page - 1) * ACCOUNTS_PER_PAGE;
        List<ThietBi> thietBis = thietBiDAO.getThietBiCanSuaByNhaTro(currentHouseId, searchTerm, start, ACCOUNTS_PER_PAGE);
        int total = thietBiDAO.getThietBiCanSuaByNhaTro(currentHouseId, searchTerm, null, ACCOUNTS_PER_PAGE).size();
        int totalPages = (int) Math.ceil((double) total / ACCOUNTS_PER_PAGE);

        request.setAttribute("thietBis", thietBis);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);

        request.getRequestDispatcher("danh-sach-thiet-bi-can-sua.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
