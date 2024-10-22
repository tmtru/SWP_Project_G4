/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ActionHistoryDAO;
import dal.NhaTroDAO;
import dal.PhongDAO;
import dal.QuanLyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.ActionHistory;
import model.NhaTro;
import model.Phong;
import model.QuanLy;

/**
 *
 * @author hihihihaha
 */
public class deleteRoomServlet extends HttpServlet {

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
            out.println("<title>Servlet deleteRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteRoomServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        if (roomIdStr != null) {
            try {
                int roomId = Integer.parseInt(roomIdStr);
                PhongDAO dao = new PhongDAO();

                if (dao.isRoomDeletable(roomId)) {

                    Phong phong = dao.getDetailRoom(roomId);
                    NhaTroDAO ntdao = new NhaTroDAO();
                    NhaTro nhaTro = ntdao.getNhaTroByPhongTroId(roomId);
                    boolean success = dao.deleteRoomById(roomId);
                    if (success) {
                        
                         // for action history
                         
                         
                        HttpSession session = request.getSession();
                        Account account = (Account) session.getAttribute("account");
                        if (account.getRole().equals("manager")) {
                            ActionHistoryDAO ahdao = new ActionHistoryDAO();
                            ActionHistory history = new ActionHistory();

                            history.setNhaTro(nhaTro);
                            QuanLyDAO qldao = new QuanLyDAO();
                            QuanLy quanLy = qldao.getChuTroByAccountId(account.getID_Account());
                            history.setQuanLy(quanLy);
                            history.setTitle("Xóa phòng");
                            history.setContent("Đã xóa phòng " + phong.getTenPhongTro() + " của " + nhaTro.getTenNhaTro());
                            ahdao.insertActionHistory(history);
                        }
                        response.sendRedirect("room?deleteSuccess=true");
                    } else {
                        response.sendRedirect("room?deleteError=true");
                    }
                } else {
                    response.sendRedirect("room?deleteError=true");
                }
            } catch (NumberFormatException | SQLException e) {
                response.sendRedirect("room?deleteError=true");
            }
        } else {
            response.sendRedirect("room?deleteError=true");
        }
    }

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
