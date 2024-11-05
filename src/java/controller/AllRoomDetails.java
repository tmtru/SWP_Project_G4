package controller;

import dal.PhongDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.FeedBack;
import model.Phong;

@WebServlet(name = "AllRoomDetails", urlPatterns = {"/roomdetails"})
public class AllRoomDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idPhongParam = request.getParameter("idPhong"); // Lấy ID phòng từ yêu cầu
            if (idPhongParam == null || idPhongParam.isEmpty()) {
                throw new NumberFormatException("Room ID is required."); // Ném ngoại lệ nếu id không hợp lệ
            }

            int idPhong = Integer.parseInt(idPhongParam); // Chuyển đổi sang số nguyên
            PhongDAO phongDAO = new PhongDAO();
            
            // Lấy thông tin phòng và phản hồi
            Phong room = phongDAO.getRoomById(idPhong);
            List<FeedBack> feedbackList = phongDAO.getFeedbackByPhongId(idPhong);

            // Đặt các thuộc tính vào request để sử dụng trong JSP
            request.setAttribute("room", room);
            request.setAttribute("feedbackList", feedbackList);

            // Chuyển hướng đến JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("AllRoomDetails.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid room ID.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AllRoomDetails.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Xử lý ngoại lệ khác nếu cần
            e.printStackTrace(); // In ra log để xem lỗi
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AllRoomDetails.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Chuyển tiếp mọi yêu cầu POST đến doGet
    }

    @Override
    public String getServletInfo() {
        return "Room details and feedback servlet";
    }
}
