package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AccountDAO;
import dal.QuanLyDAO;
import jakarta.servlet.RequestDispatcher;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Account;
import model.QuanLy;

public class AccountControllerServlet extends HttpServlet {

    private static final int ACCOUNTS_PER_PAGE = 10;
    private final AccountDAO accountDAO = new AccountDAO();
    private final QuanLyDAO quanlyDAO = new QuanLyDAO();

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
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String cccd = request.getParameter("cccd");
        int idNhaTro = Integer.parseInt(request.getParameter("nhaTroId"));

        // Chuyển đổi chuỗi "dob" sang đối tượng Date
        String dobStr = request.getParameter("dob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = null;
        try {
            dob = dateFormat.parse(dobStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EncryptPassword ep = new EncryptPassword();
        String encryptedPassword = ep.encryptPassword(password);

        Account acc = new Account(0, email, username, encryptedPassword, null, isActive);
        boolean checkAdd = accountDAO.addAccount2(email, username, encryptedPassword);

        if (checkAdd) {
            int thisAccountId = accountDAO.getAccountIdByEmail(email);

            quanlyDAO.insertQuanLy(thisAccountId, name, dob, phone, cccd, idNhaTro);
            
            response.sendRedirect("accountController");
        }
    }

    protected void listAccounts(HttpServletRequest request, HttpServletResponse response)
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

        // Get role filter, default to empty string to show all roles
        String roleFilter = request.getParameter("role");
        if (roleFilter == null) {
            roleFilter = "";
        }

        // Get accounts based on filters
        List<Account> accounts;
        int totalAccounts;

        if (searchTerm.isEmpty() && roleFilter.isEmpty()) {
            // No filters applied - get all accounts
            accounts = accountDAO.getAllAccountsPaginated(page, ACCOUNTS_PER_PAGE);
            totalAccounts = accountDAO.getTotalAccounts();
        } else {
            // Apply filters
            accounts = accountDAO.getFilteredAccounts(searchTerm, roleFilter, page, ACCOUNTS_PER_PAGE);
            totalAccounts = accountDAO.getTotalFilteredAccounts(searchTerm, roleFilter);
        }

        int totalPages = (int) Math.ceil((double) totalAccounts / ACCOUNTS_PER_PAGE);

        request.setAttribute("accounts", accounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("roleFilter", roleFilter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserManagement/account-management.jsp");
        dispatcher.forward(request, response);
    }

    private void toggleAccountActive(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("id"));
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        accountDAO.toggleActive(accountId, isActive);

        PrintWriter out = response.getWriter();
        out.print("Status updated successfully");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Account existingAccount = accountDAO.getAccountById(id);
        request.setAttribute("account", existingAccount);
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

        EncryptPassword ep = new EncryptPassword();
        String encryptedPassword = ep.encryptPassword(password);

        Account updatedAccount = new Account(id, email, username, encryptedPassword, role, isActive);
        accountDAO.updateAccount(updatedAccount);

        response.sendRedirect("accountController");
    }
}
