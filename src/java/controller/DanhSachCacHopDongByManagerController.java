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
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
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
public class DanhSachCacHopDongByManagerController extends HttpServlet {

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
            out.println("<title>Servlet DanhSachCacHopDongByManagerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DanhSachCacHopDongByManagerController at " + request.getContextPath() + "</h1>");
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
        HopDongDAO hopDongDao = new HopDongDAO();
        PhongDAO phongDao = new PhongDAO();
        HttpSession session = request.getSession(false);
        if (session != null) {
            int ID_Account = (int) session.getAttribute("ID_Account");
            List<HopDong> hopDongList = hopDongDao.getHopDongByQuanLyId(ID_Account);
            Date currentDate = new Date();

            for (HopDong hopDong : hopDongList) {
                Date ngayGiaTri = hopDong.getNgay_gia_tri();
                Date ngayHetHan = hopDong.getNgay_het_han();
                int ID_PhongTro = hopDong.getID_Phongtro();
                if (ngayHetHan != null && ngayHetHan.before(currentDate)) {
                    phongDao.updateTrangThaiPhongToT(ID_PhongTro);
                    if (!"expired".equalsIgnoreCase(hopDong.getStatus())) {
                        hopDong.setStatus("expired");
                        hopDongDao.updateHopDongStatus(hopDong.getID_HopDong(), "expired");

                    }
                } else if (ngayGiaTri != null && !ngayGiaTri.after(currentDate)) {
                    long daysUntilExpiration = (ngayHetHan.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);
                    if (daysUntilExpiration >= 20) {
                        phongDao.updateTrangThaiPhongToD(ID_PhongTro);
                        if (!"active".equalsIgnoreCase(hopDong.getStatus())) {
                            hopDongDao.updateHopDongStatus1(hopDong.getID_HopDong(), "active");
                        }
                    }
                }
            }

            request.setAttribute("hopDongList", hopDongList);

            String message = request.getParameter("message");
            if (message != null) {
                request.setAttribute("message", message);
            }

            request.getRequestDispatcher("/DanhSachHopDong_Manager.jsp").forward(request, response);
        } else {
            // Nếu session không tồn tại, chuyển hướng về trang login
            response.sendRedirect("login.jsp");
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
        String hopDongIdStr = request.getParameter("hopDongId");
        int hopDongId = Integer.parseInt(hopDongIdStr);
        String confirm = request.getParameter("confirm");
        HopDongDAO hopDongDao = new HopDongDAO();

        if ("yes".equalsIgnoreCase(confirm)) {
            ChuTroDAO chuTroDAO = new ChuTroDAO();
            KhachThueDAO khachThueDAO = new KhachThueDAO();
            DichVuDAO dichVuDAO = new DichVuDAO();
            NhaTroDAO nhaTroDao = new NhaTroDAO();

            ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
            KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
            List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
            Phong roomDetails = nhaTroDao.getRoomDetailsByHopDongId(hopDongId);
            HopDong hopDong = hopDongDao.getHopDongById(hopDongId);

            request.setAttribute("hopDongId", hopDongId);
            request.setAttribute("chuTro", chuTro);
            request.setAttribute("khachThue", khachThue);
            request.setAttribute("dichVuList", dichVuList);
            request.setAttribute("giaPhong", roomDetails.getGia());
            request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
            request.setAttribute("trangThai", roomDetails.getTrang_thai());
            request.setAttribute("hopDong", hopDong);
            request.getRequestDispatcher("/GiaHanHopDong_Manager.jsp").forward(request, response);
            return;
        } else if ("no".equalsIgnoreCase(confirm)) {
            boolean result = hopDongDao.updateTrangThaiPhongByIdHopDong(hopDongId);
            String message = result ? "Cập nhật trạng thái phòng thành công." : "Cập nhật trạng thái phòng thất bại.";

            response.sendRedirect("DanhSachCacHopDongByManager?message=" + URLEncoder.encode(message, "UTF-8"));
            return;
        }

        String message = "Cập nhật trạng thái phòng đã bị hủy.";
        response.sendRedirect("DanhSachCacHopDongByManager?message=" + URLEncoder.encode(message, "UTF-8"));
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
