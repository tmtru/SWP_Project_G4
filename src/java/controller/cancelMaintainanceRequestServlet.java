package controller;

import dal.MaintainanceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class cancelMaintainanceRequestServlet extends HttpServlet {
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy ID yêu cầu bảo trì từ request
        String idStr = request.getParameter("id");
        
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);

                // Gọi DAO để cập nhật trạng thái yêu cầu bảo trì thành "Đã hủy" (2)
                MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
                maintainanceDAO.cancelMaintainanceRequest(id);

                // Sau khi cập nhật thành công, chuyển hướng về trang quản lý
                response.sendRedirect("maintainanceServlet");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().write("Invalid ID format");
            }
        } else {
            response.getWriter().write("ID is missing");
        }
    }
}