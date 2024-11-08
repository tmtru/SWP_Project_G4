/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.DichVu;

/**
 *
 * @author Admin
 */
public class addDichVu extends HttpServlet {

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
            out.println("<title>Servlet addDichVu</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addDichVu at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        DichVuDAO dichVuDAO = new DichVuDAO();
        String tenDichVu = request.getParameter("tendichvu").trim();
        int donGia = Integer.parseInt(request.getParameter("donGia"));
        String donVi = request.getParameter("donvi");
        String khac;
        String moTa = request.getParameter("mota").trim();
        List<DichVu> dvs = dichVuDAO.getAll();

        boolean isDuplicate = dvs.stream().anyMatch(dv -> dv.getTenDichVu().equalsIgnoreCase(tenDichVu));

        if (isDuplicate) {
            request.setAttribute("errorMessage", "Tên dịch vụ đã tồn tại. Vui lòng nhập tên khác.");
            request.getRequestDispatcher("loaddichvu").forward(request, response);
            return; 
        }

        DichVu dichVu = new DichVu();
        dichVu.setTenDichVu(tenDichVu);
        dichVu.setDon_gia(donGia);
        dichVu.setIsActive(true);
        if (donVi.equals("Khác")) {
            khac = request.getParameter("donviKhac");
            dichVu.setDon_vi(khac);
        } else {
            dichVu.setDon_vi(donVi);
        }
        dichVu.setMo_ta(moTa);
        dichVuDAO.addDichVu(dichVu);
        response.sendRedirect("loaddichvu");

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
