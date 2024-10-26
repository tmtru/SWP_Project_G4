package controller;

import dal.MaintainanceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class editMaintainanceRequestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String idStr = request.getParameter("id");
        String moTa = request.getParameter("moTa");

        if (idStr != null && moTa != null && !moTa.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            
            // Cập nhật yêu cầu bảo trì trong cơ sở dữ liệu
            MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
            maintainanceDAO.updateMaintainanceRequest(id, moTa);

            // Điều hướng về trang danh sách yêu cầu bảo trì
            response.sendRedirect("maintainanceServlet");
        } else {
            // Xử lý lỗi và quay lại trang sửa
            request.setAttribute("error", "Thông tin không hợp lệ.");
            request.getRequestDispatcher("editMaintainance.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet chỉnh sửa yêu cầu bảo trì";
    }
}
