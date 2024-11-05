/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
import dal.KhachThueDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.DichVu;

/**
 *
 * @author Admin
 */
public class DangKyDichVuPhongTroController extends HttpServlet {

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
        DichVuDAO dichvuDao = new DichVuDAO();
        List<DichVu> dichVuList = dichvuDao.getAll();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        String Cmnd = request.getParameter("Cmnd");
        int khachThueId = khachThueDAO.getKhachThueIdByCCCD(Cmnd);
        int hopDongId = khachThueDAO.getHopDongIdByKhachThueId(khachThueId);
        request.setAttribute("hopDongId", hopDongId);
        request.setAttribute("dichVuList", dichVuList);
        request.getRequestDispatcher("DichVuThuePhong.jsp").forward(request, response);
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
        int soLuong = Integer.parseInt(request.getParameter("people-count"));
        int idHopDong = Integer.parseInt(request.getParameter("hopDongId"));
        String[] serviceSelected = request.getParameterValues("service-selected");

        DichVuDAO dichVuDAO = new DichVuDAO();

        // Cập nhật số lượng người vào hợp đồng
        boolean isUpdated = dichVuDAO.insertSoLuongNguoi(idHopDong, soLuong);
        if (!isUpdated) {
            // Xử lý khi cập nhật không thành công
            request.setAttribute("errorMessage", "Cập nhật số lượng người không thành công.");
            request.getRequestDispatcher("DichVuThuePhong.jsp").forward(request, response);
            return;
        }

        // Chèn các dịch vụ vào dịch vụ hợp đồng
        if (serviceSelected != null) { // Kiểm tra xem có dịch vụ nào được chọn hay không
            for (String idDichVu : serviceSelected) {
                boolean isInserted = dichVuDAO.insertDichVuHopDong(idHopDong, Integer.parseInt(idDichVu));
                if (!isInserted) {
                    // Xử lý nếu chèn dịch vụ không thành công
                    request.setAttribute("errorMessage", "Có lỗi xảy ra khi chèn dịch vụ với ID: " + idDichVu);
                    request.getRequestDispatcher("DichVuThuePhong.jsp").forward(request, response);
                    return;
                }
            }
        }

        DichVuDAO dichvuDao = new DichVuDAO();
        List<DichVu> dichVuList = dichvuDao.getAll();
        request.setAttribute("dichVuList", dichVuList);

        // Đặt lại các thuộc tính để hiển thị trong JSP
        request.setAttribute("peopleCount", soLuong);
        request.setAttribute("selectedServices", serviceSelected);
        request.setAttribute("hopDongId", idHopDong);
        request.setAttribute("Message", "Cập nhật dịch vụ thành công.");

        if (soLuong > 1) {
            request.getRequestDispatcher("ThongTinKhachThuePhong.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("DichVuThuePhong.jsp").forward(request, response);
        }
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
