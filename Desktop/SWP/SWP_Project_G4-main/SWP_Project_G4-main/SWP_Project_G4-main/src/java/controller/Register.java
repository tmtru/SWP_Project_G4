package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Register extends HttpServlet {

    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes key for AES

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        HttpSession session = request.getSession();
        session.setAttribute("email", email);

        // Kiểm tra xem hai mật khẩu có khớp nhau không
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        AccountDAO userdao = new AccountDAO();

        if (!userdao.isEmailExist(email)) {
            String encryptedPassword = encryptPassword(password);
            if (encryptedPassword != null) { // Kiểm tra nếu mã hóa thành công
                boolean accountAdded = userdao.addAccount(email, username, encryptedPassword);
                if (accountAdded) {
                    response.sendRedirect("login.jsp");
                } else {
                    request.setAttribute("errorMessage", "Username already exists!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Encryption failed!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Account already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private String encryptPassword(String password) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return null; // Trả về null nếu có lỗi
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
