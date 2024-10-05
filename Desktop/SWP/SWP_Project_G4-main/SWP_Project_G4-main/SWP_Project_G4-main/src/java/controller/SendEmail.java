package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import jakarta.servlet.http.HttpSession;

public class SendEmail extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        // Sinh mã xác thực và lưu vào session
        String verificationCode = generateVerificationCode();
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("verificationCode", verificationCode);
        
        EmailService emailService = new EmailService();

        // Gửi email chứa mã xác thực
        boolean emailSent = emailService.send(email, "Your Verification Code", 
            "Your verification code is: " + verificationCode);
        
        if (emailSent) {
            request.setAttribute("successMessage", "Verification code has been sent to your email.");
        } else {
            request.setAttribute("errorMessage", "Failed to send verification email. Please try again.");
        }
        request.getRequestDispatcher("confirmverification.jsp").forward(request, response); // Trang nhập mã xác thực
    }

    // Sinh mã xác thực ngẫu nhiên (6 chữ số)
    private String generateVerificationCode() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }

}
