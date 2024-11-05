/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.HoaDonDAO;
import dal.PhongDAO;
import dal.TransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.HoaDon;
import model.NhaTro;
import model.Phong;
import model.Transaction;

/**
 *
 * @author Admin
 */
public class loadHoaDonByRoom extends HttpServlet {

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
            out.println("<title>Servlet loadHoaDonByRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadHoaDonByRoom at " + request.getContextPath() + "</h1>");
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
        PhongDAO pdao = new PhongDAO();
        TransactionDAO trdao = new TransactionDAO();

// Lấy ID phòng từ session hoặc từ tham số yêu cầu
        int idRoom = (int) (session.getAttribute("currentRoom") != null ? session.getAttribute("currentRoom") : -1);
        int choseRoom = (request.getParameter("roomId") != null) ? Integer.parseInt(request.getParameter("roomId")) : -1;

        Phong room = null;
        List<HoaDon> listhd = null;

// Kiểm tra ID phòng được chọn
        if (choseRoom != -1) {
            session.setAttribute("currentRoom", choseRoom);
            listhd = getListHoaDonOfRoom(choseRoom);
            room = pdao.getDetailRoom(choseRoom);
        } else if (idRoom != -1) {
            listhd = getListHoaDonOfRoom(idRoom);
            room = pdao.getDetailRoom(idRoom);
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy thông tin phòng.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return; 
        }

        request.setAttribute("invoices", listhd);
        request.setAttribute("currentRoomOfHoaDon", room);
        request.getRequestDispatcher("HoaDonEachRoom.jsp").forward(request, response);

    }

    private static List<HoaDon> getListHoaDonOfRoom(int idRoom) {
        HoaDonDAO hddao = new HoaDonDAO();
        List<HoaDon> listHoaDon = hddao.getHoaDonByRoomId(idRoom);
        return listHoaDon;
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
