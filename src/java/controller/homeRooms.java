/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NhaTroDAO;
import dal.PhongDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.NhaTro;
import model.Phong;

/**
 *
 * @author Admin
 */
public class homeRooms extends HttpServlet {

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
            out.println("<title>Servlet homeRooms</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet homeRooms at " + request.getContextPath() + "</h1>");
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
        List<NhaTro> nhatros = (List<NhaTro>) session.getAttribute("nhatros");

        if (nhatros == null) {
            // Nếu chưa có dữ liệu, truy xuất từ DAO

            NhaTroDAO nhaTroDAO = new NhaTroDAO();
            ArrayList<NhaTro> danhSachNhaTro = nhaTroDAO.getAll();
            nhatros = danhSachNhaTro;

            session.setAttribute("nhatros", danhSachNhaTro);
            // Lưu vào session để sử dụng sau
        }

        // Lấy tham số pageNumber từ request
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = 2; // Hiển thị 2 mục mỗi lần

        // Tính toán vị trí bắt đầu và kết thúc
        int start = (pageNumber - 1) * pageSize;

        int end = Math.min(start + pageSize, nhatros.size());
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) nhatros.size() / pageSize);
        // Lấy 2 mục từ danh sách
        List<NhaTro> paginatedList = new ArrayList<>(nhatros.subList(start, end));

        session.setAttribute("nhatros", nhatros);
        request.setAttribute("page", pageNumber);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("nhatrosP", paginatedList);
        request.setAttribute("totalPages", totalPages);

        // Chuyển tiếp đến JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("listNhaTroHome.jsp");
        dispatcher.include(request, response);
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
