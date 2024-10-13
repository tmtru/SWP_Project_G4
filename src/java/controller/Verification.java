package controller;

import dal.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Verification extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("confirmverification.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleVerification(request, response);
    }

    /**
     * Handles the verification logic.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleVerification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("verificationCode");
        String enteredCode = request.getParameter("verifyCode");

        if (enteredCode != null && enteredCode.equals(sessionCode)) {
            String verificationType = (String) session.getAttribute("verificationType");

            if ("registration".equals(verificationType)) {
                // Handle registration verification
                handleRegistrationSuccess(request, response, session);
            } else {
                // Handle reset password verification (existing logic)
                response.sendRedirect("resetpassword.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid verification code. Please try again.");
            request.getRequestDispatcher("confirmverification.jsp").forward(request, response);
        }
    }

    /**
     * Handles successful verification for registration.
     *
     * @param request servlet request
     * @param response servlet response
     * @param session the HTTP session
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleRegistrationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session)
            throws ServletException, IOException {
        String username = (String) session.getAttribute("pendingUsername");
        String email = (String) session.getAttribute("pendingEmail");
        String password = (String) session.getAttribute("pendingPassword");

        EncryptPassword ep = new EncryptPassword();
        String encryptedPassword = ep.encryptPassword(password);

        AccountDAO accountDAO = new AccountDAO();
        boolean accountAdded = accountDAO.addAccount(email, username, encryptedPassword);

        if (accountAdded) {
            // Cleanup session attributes
            session.removeAttribute("pendingUsername");
            session.removeAttribute("pendingEmail");
            session.removeAttribute("pendingPassword");
            session.removeAttribute("verificationCode");
            session.removeAttribute("verificationType");

            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to create account. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles user verification for both registration and password reset.";
    }
}
