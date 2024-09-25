package controller;

import dal.AccountDAO;
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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.Base64;

public class Login extends HttpServlet {

    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes key for AES
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

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
        //String remember = request.getParameter("remember");

        AccountDAO userdao = new AccountDAO();
        String encryptedPassword = encryptPassword(password);

        // Thay Ä‘á»•i Ä‘á»ƒ sá»­ dá»¥ng máº­t kháº©u Ä‘Ã£ mÃ£ hÃ³a cho viá»‡c xÃ¡c thá»±c
        Account acc = userdao.getAccount(username, encryptedPassword);

        if (acc != null) {
            int ID_Account = acc.getID_Account();
            HttpSession session = request.getSession();
            session.setAttribute("ID_Account", ID_Account);

            response.sendRedirect("homer");
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
            return null; // Tráº£ vá» null náº¿u cÃ³ lá»—i
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}