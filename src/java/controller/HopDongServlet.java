/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dal.HopDongDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import model.HopDong;

@WebServlet(name = "HopDongServlet", urlPatterns = {"/hop-dong"})
public class HopDongServlet extends HttpServlet {

    @Override
   
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action != null && action.equals("add")) {
        // Hiển thị form thêm mới
        request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
    } else {
        // Xử lý phân trang như cũ
        HopDongDAO hopDongDAO = new HopDongDAO();

        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<HopDong> listHopDong = hopDongDAO.findAll((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = hopDongDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("listHopDong", listHopDong);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("active", "hopdong");
        request.getRequestDispatcher("hopDongDashboard.jsp").forward(request, response);
    }
}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            // Xử lý thêm mới hợp đồng
            addHopDong(request, response);
        } else {
            // Xử lý các action khác nếu cần
            response.sendRedirect("hop-dong");
        }
    }

    private void addHopDong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        int ID_KhachThue = Integer.parseInt(request.getParameter("ID_KhachThue"));
        int ID_PhongTro = Integer.parseInt(request.getParameter("ID_PhongTro"));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Chuyển đổi từ String sang java.util.Date, sau đó sang java.sql.Date
        java.util.Date utilNgayGiaTri = sdf.parse(request.getParameter("Ngay_gia_tri"));
        java.util.Date utilNgayHetHan = sdf.parse(request.getParameter("Ngay_het_han"));
        
        Date Ngay_gia_tri = new Date(utilNgayGiaTri.getTime());
        Date Ngay_het_han = new Date(utilNgayHetHan.getTime());
        
        int Tien_Coc = Integer.parseInt(request.getParameter("Tien_Coc"));

        HopDong newHopDong = new HopDong(0, ID_KhachThue, ID_PhongTro, Ngay_gia_tri, Ngay_het_han, Tien_Coc);
        HopDongDAO hopDongDAO = new HopDongDAO();
        
        boolean success = hopDongDAO.addHopDong(newHopDong);
        if (success) {
            response.sendRedirect("hop-dong?addSuccess=true");
        } else {
            request.setAttribute("error", "Không thể thêm hợp đồng. Vui lòng thử lại.");
            request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
        }
    } catch (NumberFormatException | ParseException e) {
        request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
        request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
    }
    }
}
