package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import dal.AccountDAO;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "ChangePassword", urlPatterns = {"/changepassword"})
public class ChangePassword extends HttpServlet {

    private String validatePassword(String password) {
        // Kiểm tra mật khẩu có chứa khoảng trắng hay không
        if (password.contains(" ")) {
            return "Mật khẩu không được chứa khoảng trắng.";
        }

        // Kiểm tra mật khẩu có chứa ký tự đặc biệt không hợp lệ
        String regex = "^[a-zA-Z0-9]*$";
        if (!password.matches(regex)) {
            return "Mật khẩu chỉ được chứa chữ cái và số.";
        }

        return null; // Mật khẩu hợp lệ
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("ID_Account");

        if (accountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountById(accountId);

        if (account == null) {
            request.setAttribute("error", "Không thể tải dữ liệu tài khoản.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Validate mật khẩu mới và mật khẩu hiện tại
        String passwordValidationMessage = validatePassword(newPassword);
        if (passwordValidationMessage != null) {
            request.setAttribute("error", passwordValidationMessage);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu hiện tại để so sánh
        EncryptPassword encryptor = new EncryptPassword();
        String encryptedCurrentPassword = encryptor.encryptPassword(currentPassword);

        // So sánh mật khẩu cũ
        if (!encryptedCurrentPassword.equals(account.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu mới
        String encryptedNewPassword = encryptor.encryptPassword(newPassword);

        // Cập nhật mật khẩu mới trong cơ sở dữ liệu
        boolean updated = accountDAO.updatePassword(account.getEmail(), encryptedNewPassword);

        if (updated) {
            request.setAttribute("success", "Mật khẩu đã được thay đổi thành công.");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật mật khẩu.");
        }

        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Change Password Servlet";
    }
}