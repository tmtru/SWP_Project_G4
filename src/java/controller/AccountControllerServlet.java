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
import model.Account;

public class AccountControllerServlet extends HttpServlet {

    private static final int ACCOUNTS_PER_PAGE = 10; // Sá»‘ tÃ i khoáº£n trÃªn má»—i trang
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

    private void addAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        Account newAccount = new Account(0, email, username, password, role, isActive);
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

        List<Account> accounts;
        int totalAccounts;

        if (searchTerm.isEmpty()) {
            accounts = accountDAO.getAccountsPaginated(start, ACCOUNTS_PER_PAGE);
            totalAccounts = accountDAO.getTotalAccounts();
        } else {
            accounts = accountDAO.searchAccountsByName(searchTerm, start, ACCOUNTS_PER_PAGE);
            totalAccounts = accountDAO.getTotalAccountsBySearch(searchTerm);
        }

        int totalPages = (int) Math.ceil((double) totalAccounts / ACCOUNTS_PER_PAGE);

        request.setAttribute("accounts", accounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);

        request.getRequestDispatcher("adminUserManagement/account-management.jsp").forward(request, response);
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
