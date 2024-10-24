/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.LoaiPhongDAO;
import dal.NhaTroDAO;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.LoaiPhong;
import model.NhaTro;
import model.Phong;

/**
 *
 * @author hihihihaha
 */
public class loadPhongTro extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet loadPhongTro</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadPhongTro at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        PhongDAO pdao = new PhongDAO();
        NhaTroDAO ntdao = new NhaTroDAO();
        LoaiPhongDAO lpdao = new LoaiPhongDAO();
        
        // House tuong ung voi role
        List<NhaTro> houses = (List<NhaTro>) session.getAttribute("housesByRole");
        
        // Initialize selected house ID
        int choseHouse = -1;
        
        // Get selected house from request parameter or session
        if (request.getParameter("nhaTro") != null && !request.getParameter("nhaTro").equals("")) {
            choseHouse = Integer.parseInt(request.getParameter("nhaTro"));
        } else if (session.getAttribute("currentHouse") != null) {
            choseHouse = (int) session.getAttribute("currentHouse");
        }

        // Get rooms and related data
        List<Phong> rooms = null;
        List<LoaiPhong> loaiPhongList = null;
        List<Integer> tangList = null;

        if (houses != null && !houses.isEmpty()) {
            // If no house selected, use the first one
            if (choseHouse == -1) {
                choseHouse = houses.get(0).getID_NhaTro();
                session.setAttribute("currentHouse", choseHouse);
            } else {
                session.setAttribute("currentHouse", choseHouse);
            }

            // Get room list based on filters
            String searchText = request.getParameter("search");
            String selectedFloor = request.getParameter("tang");
            
            if (searchText != null && !searchText.trim().isEmpty()) {
                // If there's a search query, use search function
                rooms = pdao.searchRooms(searchText.trim(), choseHouse);
            } else if (selectedFloor != null && !selectedFloor.isEmpty()) {
                // If there's a floor filter but no search, use floor filter
                rooms = pdao.getRoomsByFloorAndNhaTro(Integer.parseInt(selectedFloor), choseHouse);
            } else {
                // If no search or floor filter, get all rooms
                rooms = pdao.getRoomsByNhaTro(choseHouse);
            }

            // Get list of floors for the selected house
            tangList = pdao.getDistinctFloorsByNhaTro(choseHouse);
            
            // Get room types
            loaiPhongList = lpdao.getAllLoaiPhong();
        }

        // Create status map for room status filter
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("T", "Trống");
        statusMap.put("D", "Đang thuê");
        statusMap.put("DS", "Đang sửa");

        // Set attributes for the JSP
        request.setAttribute("rooms", rooms);
        request.setAttribute("nhaTroList", houses);
        request.setAttribute("loaiPhongList", loaiPhongList);
        request.setAttribute("tangList", tangList);
        request.setAttribute("statusMap", statusMap);
        request.setAttribute("searchValue", request.getParameter("search")); // For maintaining search text in input

        // Handle pagination
        int page = 1;
        int recordsPerPage = 8;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (rooms != null) {
            int totalRecords = rooms.size();
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
            
            int start = (page - 1) * recordsPerPage;
            int end = Math.min(start + recordsPerPage, totalRecords);
            
            List<Phong> paginatedRooms = rooms.subList(start, end);
            
            request.setAttribute("rooms", paginatedRooms);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
        }

        // Forward to the JSP page
        request.getRequestDispatcher("room.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
