package controller;

import com.google.gson.Gson;
import dal.HopDongDAO;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.HopDong;
import model.Phong;

public class DanhSachPhongTroCalenderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    HttpSession session = request.getSession(false);

    // Kiểm tra xem phiên có hợp lệ không
    if (session == null || session.getAttribute("ID_Account") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int ID_Account = (int) session.getAttribute("ID_Account");
    String yearParam = request.getParameter("year");
    String monthParam = request.getParameter("month");
    String dayParam = request.getParameter("day");

    // Kiểm tra tham số yêu cầu
    if (yearParam == null || monthParam == null || dayParam == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"Missing parameters\"}");
        return;
    }

    try {
        int year = Integer.parseInt(yearParam);
        int month = Integer.parseInt(monthParam);
        int day = Integer.parseInt(dayParam);

        // Tạo ngày đã chọn từ tham số
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date selectedDate = calendar.getTime();

        // Lấy danh sách hợp đồng và phòng
        HopDongDAO hopDongDao = new HopDongDAO();
        List<HopDong> danhSachHopDong = hopDongDao.getHopDongByQuanLyIdAndStatus(ID_Account, new java.sql.Date(selectedDate.getTime()));
        List<Phong> danhSachPhongTro = hopDongDao.getPhongTroByAccountId(ID_Account);
        
        List<HopDong> phongDangThue = new ArrayList<>();
        List<HopDong> phongSapHetHan = new ArrayList<>();
        Date currentDate = new Date();

        // Phân loại hợp đồng
        for (HopDong hopDong : danhSachHopDong) {
            Date ngayHetHan = hopDong.getNgay_het_han();
            Date ngayGiaTri = hopDong.getNgay_gia_tri();
            String trangThai = hopDong.getPhongTro().getTrang_thai();

            long daysRemaining = (ngayHetHan.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);

            if (!currentDate.before(ngayGiaTri) && !currentDate.after(ngayHetHan) && daysRemaining <= 20 && daysRemaining > 0) {
                phongSapHetHan.add(hopDong);
            } else if (!currentDate.before(ngayGiaTri) && daysRemaining > 20) {
                phongDangThue.add(hopDong);
            }
        }

        // Đóng gói dữ liệu thành JSON trong một Map duy nhất
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("phongDangThue", phongDangThue);
        responseData.put("phongSapHetHan", phongSapHetHan);
        responseData.put("phongTrong", danhSachPhongTro);

        // Chuyển đổi map thành JSON
        Gson gson = new Gson();
        String json = gson.toJson(responseData);

        // Cấu hình phản hồi
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"Invalid year, month, or day format\"}");
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"error\":\"An unexpected error occurred\"}");
    }
}




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DanhSachPhongTroCalenderController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DanhSachPhongTroCalenderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing room lists by calendar date.";
    }
}
