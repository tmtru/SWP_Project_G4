package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Account;

public class AccountControllerServlet extends HttpServlet {

    private static final int ACCOUNTS_PER_PAGE = 10; 
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            listAccounts(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addAccount(request, response);
        } else if ("update".equals(action)) {
            updateAccount(request, response);
        } else if ("toggleActive".equals(action)) {
            toggleAccountActive(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserManagement/add-account.jsp");
        dispatcher.forward(request, response);
    }

    //Encrypt here
    private void addAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        EncryptPassword ep = new EncryptPassword();
        String encryptedPassword = ep.encryptPassword(password);
        
        Account newAccount = new Account(0, email, username, encryptedPassword, role, isActive);
        accountDAO.addAccount(newAccount);

        response.sendRedirect("accountController");
    }

    private void listAccounts(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int page = 1;
    String pageStr = request.getParameter("page");
    if (pageStr != null && !pageStr.isEmpty()) {
        page = Integer.parseInt(pageStr);
    }

    String searchTerm = request.getParameter("searchTerm");
    if (searchTerm == null) {
        searchTerm = "";
    }

    int start = (page - 1) * ACCOUNTS_PER_PAGE;

    List<Account> accounts = new ArrayList<>(); // Khởi tạo danh sách
    int totalAccounts = 0; // Khởi tạo biến totalAccounts
    HttpSession session = request.getSession(false);
    if (session != null) {
        int ID_Account = (int) session.getAttribute("ID_Account");
        String role = (String) session.getAttribute("role");
        
        if (role.equals("landlord")) {
            if (searchTerm.isEmpty()) {
                accounts = accountDAO.getAccountsPaginated(start, ACCOUNTS_PER_PAGE);
                totalAccounts = accountDAO.getTotalAccounts();
            } else {
                accounts = accountDAO.searchAccountsByName(searchTerm, start, ACCOUNTS_PER_PAGE);
                totalAccounts = accountDAO.getTotalAccountsBySearch(searchTerm);
            }
        } else if (role.equals("manager")) {
            if (searchTerm.isEmpty()) {
                accounts = accountDAO.getTenantAccountsByManager(ID_Account, start, ACCOUNTS_PER_PAGE);
                totalAccounts = accountDAO.countTenantAccountsByManager(ID_Account);
            } else {
                accounts = accountDAO.searchTenantByUsername(ID_Account, searchTerm, start, ACCOUNTS_PER_PAGE);
                totalAccounts = accountDAO.countTenantsByUsername(ID_Account, searchTerm);
            }
        }

        // Kiểm tra và tính toán số trang
        int totalPages = 0;
        if (totalAccounts > 0 && ACCOUNTS_PER_PAGE > 0) {
            totalPages = (int) Math.ceil((double) totalAccounts / ACCOUNTS_PER_PAGE);
        }

        // Kiểm tra nếu accounts là null và khởi tạo nếu cần
        if (accounts == null) {
            accounts = new ArrayList<>(); // Khởi tạo danh sách rỗng nếu accounts là null
        }

        // Thiết lập các thuộc tính cho request
        request.setAttribute("accounts", accounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);

        request.getRequestDispatcher("adminUserManagement/account-management.jsp").forward(request, response);
    } else {
        // Nếu session không tồn tại, chuyển hướng về trang login
        response.sendRedirect("login.jsp");
    }
}

    private void toggleAccountActive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        
        boolean success = accountDAO.updateAccountStatus(id, isActive);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (success) {
            out.print("{\"status\":\"success\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\"}");
        }
        out.flush();
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Account account = accountDAO.getAccountById(id);
        request.setAttribute("account", account);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserManagement/edit-account.jsp");
        dispatcher.forward(request, response);
    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        Account account = new Account(id, email, username, password, role, isActive);
        accountDAO.updateAccount(account);

        response.sendRedirect("accountController");
    }
}
