/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ChuTroDAO;
import dal.DichVuDAO;
import dal.HopDongDAO;
import dal.KhachThueDAO;
import dal.NhaTroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ChuTro;
import model.DichVu;
import model.HopDong;
import model.KhachThue;
import model.Phong;

/**
 *
 * @author Admin
 */
public class GiaHanHopDongByManagerController extends HttpServlet {
   
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
            out.println("<title>Servlet GiaHanHopDongByManagerController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GiaHanHopDongByManagerController at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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

    String hopDongIdString = request.getParameter("hopDongId");
    String endDateStr = request.getParameter("endDate");

    int hopDongId = Integer.parseInt(hopDongIdString);
    java.sql.Date endDate = java.sql.Date.valueOf(endDateStr); // Converts "yyyy-MM-dd" format

    HopDongDAO hopDongDAO = new HopDongDAO();

    boolean success = hopDongDAO.giaHanHopDong(hopDongId, endDate);

    ChuTroDAO chuTroDAO = new ChuTroDAO(); 
    KhachThueDAO khachThueDAO = new KhachThueDAO();
    DichVuDAO dichVuDAO = new DichVuDAO();
    NhaTroDAO nhaTroDao = new NhaTroDAO();
    
    ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
    KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
    List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
    Phong roomDetails = nhaTroDao.getRoomDetailsByHopDongId(hopDongId);
    HopDong hopDong = hopDongDAO.getHopDongById(hopDongId);
    
    request.setAttribute("hopDongId", hopDongId);
    request.setAttribute("chuTro", chuTro);
    request.setAttribute("khachThue", khachThue);
    request.setAttribute("dichVuList", dichVuList);
    request.setAttribute("giaPhong", roomDetails.getGia());
    request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
    request.setAttribute("trangThai", roomDetails.getTrang_thai());
    request.setAttribute("hopDong", hopDong);

    if (success) {
        request.setAttribute("message", "Gia hạn hợp đồng thành công");
    } else {
        request.setAttribute("message", "Gia hạn hợp đồng thất bại");
    }

    request.getRequestDispatcher("/HopDongThuePhongDaDangKy.jsp").forward(request, response);
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