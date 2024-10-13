package controller;
import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
public class Register extends HttpServlet {
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
        forwardToRegisterPage(request, response);
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
        handleRegistration(request, response);
    }
    
    /**
     * Forwards to the registration page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void forwardToRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    /**
     * Handles the registration logic.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!password.equals(confirmPassword)) {
            forwardWithError(request, response, "Passwords do not match!");
            return;
        }
        
        AccountDAO accountDAO = new AccountDAO();
        if (accountDAO.isEmailExist(email)) {
            forwardWithError(request, response, "Account already exists!");
            return;
        }
        
        // Tạo session và lưu thông tin
        HttpSession session = request.getSession();
        session.setAttribute("pendingUsername", username);
        session.setAttribute("pendingEmail", email);
        session.setAttribute("pendingPassword", password);
        session.setAttribute("verificationType", "registration"); // Thêm attribute này
        
        // Gửi mail verification
        sendVerificationEmail(request, response, session, email);
    }
    
    /**
     * Sends verification email and handles the response.
     *
     * @param request servlet request
     * @param response servlet response
     * @param session the HTTP session
     * @param email the recipient email address
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void sendVerificationEmail(HttpServletRequest request, HttpServletResponse response, 
                                      HttpSession session, String email)
            throws ServletException, IOException {
        String verificationCode = generateVerificationCode();
        session.setAttribute("verificationCode", verificationCode);
        
        EmailService emailService = new EmailService();
        boolean emailSent = emailService.send(email, "Verification Code", 
                "Your verification code is: " + verificationCode);
        
        if (emailSent) {
            response.sendRedirect("confirmverification.jsp");
        } else {
            forwardWithError(request, response, "Failed to send verification email!");
        }
    }
    
    /**
     * Generates a verification code.
     *
     * @return the generated verification code
     */
    private String generateVerificationCode() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }
    
    /**
     * Forwards to register page with an error message.
     *
     * @param request servlet request
     * @param response servlet response
     * @param errorMessage the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}