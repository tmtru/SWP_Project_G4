package controller;

import dal.HopDongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import model.HopDong;
import model.Phong;

public class CalendarByManagerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HopDongDAO hopDongDao = new HopDongDAO();
        HttpSession session = request.getSession(false);

        if (session != null) {
            int ID_Account = (int) session.getAttribute("ID_Account");
            List<HopDong> hopDongList = hopDongDao.getHopDongByQuanLyId(ID_Account);
            List<Phong> danhSachPhongTro = hopDongDao.getPhongTroByAccountId(ID_Account);
            // Lấy ngày tháng năm hiện tại
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11

            // Lấy tham số năm và tháng từ request nếu có
            String yearParam = request.getParameter("year");
            String monthParam = request.getParameter("month");

            if (yearParam != null && monthParam != null) {
                currentYear = Integer.parseInt(yearParam);
                currentMonth = Integer.parseInt(monthParam);
            }

            calendar.set(currentYear, currentMonth - 1, 1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            // Gán thuộc tính cho request để truyền sang JSP
            request.setAttribute("currentYear", currentYear);
            request.setAttribute("currentMonth", currentMonth);
            request.setAttribute("daysInMonth", daysInMonth);
            request.setAttribute("hopDongList", hopDongList);
            request.setAttribute("danhSachPhongTroTrong", danhSachPhongTro);

            request.getRequestDispatcher("/Calendarbymanager.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp"); // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
