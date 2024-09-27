package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Verification extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy mã xác thực từ session
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("verificationCode");

        // Lấy mã người dùng nhập vào từ request
        String enteredCode = request.getParameter("verifyCode");
        
        if (enteredCode != null && enteredCode.equals(sessionCode)) {
            // Nếu mã khớp, chuyển đến trang đặt lại mật khẩu
            response.sendRedirect("resetpassword.jsp");
        } else {
            // Nếu mã không khớp, thông báo lỗi
            request.setAttribute("errorMessage", "Invalid verification code. Please try again.");
            request.getRequestDispatcher("verification.jsp").forward(request, response);
        }
    }
}

