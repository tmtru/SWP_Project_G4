/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NhaTroDAO;
import dal.QuanLiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.NhaTro;
import model.QuanLi;

/**
 *
 * @author Admin
 */
public class LoadDashboardByRole extends HttpServlet {

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
            out.println("<title>Servlet LoadDashboardByRole</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadDashboardByRole at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        NhaTroDAO housesDao = new NhaTroDAO();
        //list nha tro ma account duoc quyen truy cap
        List<NhaTro> houses = null;
        if (acc != null) {
            if (acc.getRole().equals("landlord")) {
                houses = housesDao.getAll();
            } else if (acc.getRole().equals("manager")) {
                QuanLiDAO qlDao = new QuanLiDAO();
                QuanLi ql= qlDao.getQuanLiByIDAccount(acc.getID_Account());
                houses = qlDao.getNhaTroByManagerId(ql.getID_QuanLy());
            }
            if (houses == null) {
                boolean erroMess = true;
                session.setAttribute("errorMessByRole", erroMess);
                
            } else {
                //Nha tro ma dc phep truy cap bang role
                session.setAttribute("housesByRole", houses);
            }
            //role cua tai khoan dg truy cap
            session.setAttribute("role", acc.getRole());
            response.sendRedirect("room");
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
