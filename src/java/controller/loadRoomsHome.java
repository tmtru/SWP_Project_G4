/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NhaTroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.NhaTro;
import model.Phong;

/**
 *
 * @author Admin
 */
public class loadRoomsHome extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        /*Load house and all rooms of the house*/
        Integer idNhaTro = Integer.parseInt(request.getParameter("id"));
        NhaTroDAO daont = new NhaTroDAO();
        NhaTro nt = daont.getNhaTroById(idNhaTro);
        ArrayList<Phong> rooms = daont.getAllPhongTro(idNhaTro);
        float allPrice = 0;
        for (int i = 0; i < rooms.size(); i++) {
            allPrice += rooms.get(i).getGia();
        }
        float avePrice = allPrice / rooms.size();

        // Làm tròn giá trị avePrice đến 2 chữ số thập phân
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAvePrice = df.format(avePrice / 1000000);
        HttpSession session = request.getSession();

        // Set các giá trị vào request để truyền sang phần hiển thị
        session.setAttribute("avePrice", formattedAvePrice);
        session.setAttribute("allrooms", rooms);
        request.setAttribute("rooms", rooms);
        session.setAttribute("currenthouse", nt);
        request.getRequestDispatcher("detailHouseHome.jsp").forward(request, response);

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
