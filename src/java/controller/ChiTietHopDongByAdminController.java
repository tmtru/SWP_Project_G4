/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ChuTroDAO;
import dal.DichVuDAO;
import dal.HopDongDAO;
import dal.KhachThueDAO;
import dal.KhachThuePhuDAO;
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
import model.KhachThuePhu;
import model.Phong;

/**
 *
 * @author Admin
 */
public class ChiTietHopDongByAdminController extends HttpServlet {

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
            out.println("<title>Servlet ChiTietHopDongByAdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChiTietHopDongByAdminController at " + request.getContextPath() + "</h1>");
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
        HopDongDAO hopDongDao = new HopDongDAO();
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        DichVuDAO dichVuDAO = new DichVuDAO();
        NhaTroDAO nhaTroDao = new NhaTroDAO();
        

        String hopDongIdStr = request.getParameter("hopDongId");
        int hopDongId = Integer.parseInt(hopDongIdStr);
        String action = request.getParameter("action");
        String reason = request.getParameter("reason");

        int phongTroId = hopDongDao.getPhongTroIdByHopDongId(hopDongId);
        int khachThueId = hopDongDao.getKhachThueIdByHopDongId(hopDongId);

        if ("accept".equals(action)) {
            boolean isRoomAvailable = hopDongDao.isRoomAvailableForAccept(phongTroId);

            if (!isRoomAvailable) {
                request.setAttribute("message", "Căn phòng này đã được thuê trong hợp đồng trước. Không thể chấp nhận hợp đồng.");
                loadHopDongDetails(request, hopDongId, khachThueId, phongTroId);
                request.getRequestDispatcher("/ChiTietHopDongDaDangKy_Admin.jsp").forward(request, response);
                return;
            }

            // Chấp nhận hợp đồng nếu phòng trọ chưa có hợp đồng nào đang thuê
            boolean isAccepted = hopDongDao.acceptHopDong(hopDongId, khachThueId, phongTroId);
            request.setAttribute("message", "Hợp đồng đã được chấp nhận thành công.");
            loadHopDongDetails(request, hopDongId, khachThueId, phongTroId);
            request.getRequestDispatcher("/ChiTietHopDongDaDangKy_Admin.jsp").forward(request, response);

        } else if ("reject".equals(action)) {
            boolean success = hopDongDao.rejectHopDong(hopDongId, reason);
            request.setAttribute("message", "Hợp đồng đã bị từ chối thành công.");
            loadHopDongDetails(request, hopDongId, khachThueId, phongTroId);
            request.getRequestDispatcher("/ChiTietHopDongDaDangKy_Admin.jsp").forward(request, response);
        }
    }

    private void loadHopDongDetails(HttpServletRequest request, int hopDongId, int khachThueId, int phongTroId) {
        HopDongDAO hopDongDao = new HopDongDAO();
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        DichVuDAO dichVuDAO = new DichVuDAO();
        NhaTroDAO nhaTroDao = new NhaTroDAO();
        KhachThuePhuDAO khachThuePhuDao = new KhachThuePhuDAO();
        KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
        List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
        Phong roomDetails = nhaTroDao.getRoomDetailsByHopDongId(hopDongId);
        HopDong hopDong = hopDongDao.getHopDongById(hopDongId);
        ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
        List<KhachThuePhu> khachThuePhuList = khachThuePhuDao.getKhachThuePhuByHopDongId(hopDongId);
        request.setAttribute("hopDongId", hopDongId);
        request.setAttribute("chuTro", chuTro);
        request.setAttribute("khachThue", khachThue);
        request.setAttribute("dichVuList", dichVuList);
        request.setAttribute("giaPhong", roomDetails.getGia());
        request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
        request.setAttribute("trangThai", roomDetails.getTrang_thai());
        request.setAttribute("hopDong", hopDong);
        request.setAttribute("khachThuePhuList", khachThuePhuList);
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
