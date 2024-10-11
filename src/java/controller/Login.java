package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

public class Login extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy cookies từ request
        Cookie[] cookies = request.getCookies();
        String savedUsername = null;
        String savedPassword = null;

        // Duyệt qua cookies để lấy username và password
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    savedUsername = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    savedPassword = cookie.getValue();
                }
            }
        }

        // Nếu cookies tồn tại, set chúng vào request để hiển thị trên form
        if (savedUsername != null && savedPassword != null) {
            request.setAttribute("savedUsername", savedUsername);
            request.setAttribute("savedPassword", savedPassword);
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidInput(username, password)) {
            handleLogin(request, response, username, password);
        } else {
            sendErrorMessage(request, response, "Invalid email or password");
        }
    }

    /**
     * Check if user input is valid method.
     *
     * @param username
     * @param password
     * @return
     */
    private boolean isValidInput(String username, String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    /**
     * Handling login operation logic method
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @throws IOException
     * @throws ServletException
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response, String username, String password)
            throws IOException, ServletException {
        EncryptPassword ep = new EncryptPassword();
        String encryptedPassword = ep.encryptPassword(password);
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccount(username, encryptedPassword);

        if (account != null) {
            createSessionAndRedirect(request, response, account);
        } else {
            sendErrorMessage(request, response, "Invalid email or password");
        }
    }

    /**
     * Initialize session and redirect user to home page method.
     *
     * @param request
     * @param response
     * @param account
     * @throws IOException
     */
    private void createSessionAndRedirect(HttpServletRequest request, HttpServletResponse response, Account account)
            throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        session.setAttribute("ID_Account", account.getID_Account());
        response.sendRedirect("home.jsp");
    }

    /**
     * Send error message if account not exists method.
     *
     * @param request
     * @param response
     * @param message
     * @throws ServletException
     * @throws IOException
     */
    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Controller Servlet";
    }
}
