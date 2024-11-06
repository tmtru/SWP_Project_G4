/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import com.google.gson.Gson;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import model.Account;
import model.Phong;

/**
 *
 * @author Admin
 */
public class LoadLikedRooms extends HttpServlet {
   
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
            out.println("<title>Servlet LoadLikedRooms</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadLikedRooms at " + request.getContextPath () + "</h1>");
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
       Cookie[] cookies = request.getCookies();
        String likedRoomsCookie = null;
        int userId = (request.getSession().getAttribute("account") != null) ? 
                ((Account) request.getSession().getAttribute("account")).getID_Account() : 0;
                    response.setContentType("text/html;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoadLikedRooms</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadLikedRooms at " + request.getCookies().length+ "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equalsIgnoreCase("likedRooms_" + userId)) {
                    likedRoomsCookie = cookie.getValue();
                    

                    break;
                }
            }
        }

        List<Phong> likedPhongs = new ArrayList<>();
        if (likedRoomsCookie != null) {
            String[] roomIds = likedRoomsCookie.split(",");
            PhongDAO phongDAO = new PhongDAO();
            for (String id : roomIds) {
                try {
                    Phong phong = phongDAO.getDetailRoom(Integer.parseInt(id.trim()));
                    if (phong != null) {
                        likedPhongs.add(phong);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Cannot convert room ID: " + id + " to an integer.");
                } catch (Exception e) {
                    System.err.println("Error retrieving room details: " + e.getMessage());
                }
            }
        }

        // Chuyển đổi danh sách likedPhongs thành JSON
        Gson gson = new Gson();
        String json = gson.toJson(likedPhongs);

        // Cài đặt kiểu trả về là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Gửi dữ liệu JSON về phía client
        response.getWriter().write(json);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
