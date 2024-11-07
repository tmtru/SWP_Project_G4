package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FeedBack; // Import the FeedBack model
import dal.FeedBackDAO; // Import the FeedbackDAO

public class FeedBackController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý yêu cầu theo logic của bạn
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the request
        String idKhachThueStr = request.getParameter("idKhachThue");
        String idPhongStr = request.getParameter("idPhong");
        String noiDung = request.getParameter("noiDung");
        int danhGia = Integer.parseInt(request.getParameter("danhGia"));

        int idKhachThue = 0;
        int idPhong = 0;

        try {
            idKhachThue = Integer.parseInt(idKhachThueStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid KhachThue ID format.");
            request.getRequestDispatcher("viewUserContracts.jsp").forward(request, response);
            return;
        }

        try {
            idPhong = Integer.parseInt(idPhongStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Phong ID format.");
            request.getRequestDispatcher("viewUserContracts.jsp").forward(request, response);
            return;
        }

        FeedBack feedback = new FeedBack(idKhachThue, idPhong, noiDung, danhGia);

        FeedBackDAO feedbackDAO = new FeedBackDAO();

        boolean success = feedbackDAO.addFeedBack(feedback);
        if (success) {
            request.setAttribute("message", "Feedback submitted successfully.");
            request.getRequestDispatcher("viewUserContracts.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Failed to submit feedback.");
            request.getRequestDispatcher("viewUserContracts.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
