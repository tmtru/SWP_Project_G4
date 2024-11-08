package controller; 

import dal.AccountDAO;
import dal.KhachThueDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import model.KhachThue;

public class Login extends HttpServlet {

    KhachThue khachthue = new KhachThue();
    KhachThueDAO ktdao = new KhachThueDAO();

    private static final String SECRET_KEY = "1234567890123456";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
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
        String password = request.getParameter("password");

        // Validate input before proceeding
        if (!validateInput(username, password)) {
            request.setAttribute("errorMessage", "Invalid username or password format.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        AccountDAO userdao = new AccountDAO();
        String encryptedPassword = encryptPassword(password);
        HttpSession session = request.getSession();

        Account acc = userdao.getAccount(username, encryptedPassword);

        if (acc != null && !acc.isActive()) { 
            int ID_Account = acc.getID_Account();
            khachthue = ktdao.getKhachThueByAccountId(ID_Account);

            session.setAttribute("account", acc);
            session.setAttribute("role", acc.getRole());
            session.setAttribute("ID_Account", ID_Account);

            if ("tenant".equals(acc.getRole())) {
                session.setAttribute("ID_KhachThue", khachthue.getId());
            }
            response.sendRedirect("home.jsp");
        } else { 
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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

    private boolean validateInput(String username, String password) {
        // Validate username (only allows letters, numbers, and spaces)
        if (username == null || username.trim().isEmpty()) {
            return false; // Username is invalid
        }

        // Validate password (only allows letters, numbers, and some special characters)
        if (password == null || password.trim().isEmpty() || !password.matches("[a-zA-Z0-9!@#$%^&*()_+=-]+")) {
            return false; // Password is invalid
        }

        return true; // Input is valid
    }

    @Override
    public String getServletInfo() {
        return "Login servlet that handles user authentication.";
    }
}
