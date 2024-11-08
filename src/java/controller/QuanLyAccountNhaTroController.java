/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.KhachThueDAO;
import dal.NhaTroDAO;
import dal.QuanLyDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import model.Account;
import model.KhachThue;
import model.NhaTro;
import model.QuanLy;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "QuanLyAccountNhaTroController", urlPatterns = {"/quanly-account-nha-tro"})
public class QuanLyAccountNhaTroController extends HttpServlet {

    private final NhaTroDAO nhaTroDAO = new NhaTroDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final QuanLyDAO quanLyDAO = new QuanLyDAO();
    private final KhachThueDAO khachThueDAO = new KhachThueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            boolean isOwner;
            if (account.getRole().equals("landlord")) {
                isOwner = true;
                String pageParam = request.getParameter("page");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                Account currentAccount = accountDAO.getAccountById2(account.getID_Account());
                int idNhaTro = Integer.parseInt(request.getParameter("id"));
                NhaTro nhatro = nhaTroDAO.getNhaTroById(idNhaTro);
                List<Account> accounts = accountDAO.getAllAccountsForNhaTro();
                List<KhachThue> listkKhachThue = khachThueDAO.getKhachThueByNhaTro(idNhaTro);

                List<KhachThue> pagingKhachThue = khachThueDAO.Paging(listkKhachThue, page, pageSize);
                List<QuanLy> listQuanLys = quanLyDAO.getQuanLyByNhaTro(idNhaTro);
                request.setAttribute("listkKhachThue", pagingKhachThue);
                request.setAttribute("quanlys", listQuanLys);
                request.setAttribute("totalPages", listkKhachThue.size() % pageSize == 0 ? (listkKhachThue.size() / pageSize) : (listkKhachThue.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.setAttribute("accounts", accounts);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("id", idNhaTro);
                request.setAttribute("nhatro", nhatro);
                String aid = request.getParameter("aid");
                if (aid != null && !aid.isEmpty()) {
                    QuanLy quanLy = quanLyDAO.getChuTroByAccountId(Integer.parseInt(aid));
                    request.setAttribute("quanLy", quanLy);
                }
                request.getRequestDispatcher("quanly-account-nha-tro.jsp").forward(request, response);

            }
            if (account.getRole().equals("manager")) {
                String pageParam = request.getParameter("page");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                int idNhaTro = Integer.parseInt(request.getParameter("id"));
                NhaTro nhatro = nhaTroDAO.getNhaTroById(idNhaTro);
                isOwner = false;
                List<KhachThue> listkKhachThue = khachThueDAO.getKhachThueByNhaTro(idNhaTro);

                List<KhachThue> pagingKhachThue = khachThueDAO.Paging(listkKhachThue, page, pageSize);
                request.setAttribute("listkKhachThue", pagingKhachThue);
                request.setAttribute("isOwner", isOwner);

                request.setAttribute("totalPages", listkKhachThue.size() % pageSize == 0 ? (listkKhachThue.size() / pageSize) : (listkKhachThue.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("id", idNhaTro);
                request.setAttribute("nhatro", nhatro);
                request.getRequestDispatcher("quanly-account-nha-tro.jsp").forward(request, response);

            }
        } else {
            session.setAttribute("errorMessage", "You must login first!");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null && account.getRole().equals("landlord")) {
            String action = request.getParameter("action");
            if (action.equals("add")) {
                try {
                    // Retrieve form data
                    int idNhaTro = Integer.parseInt(request.getParameter("id"));

                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String email = request.getParameter("email");
                    String name = request.getParameter("name");
                    String sdt = request.getParameter("phone");
                    String cccd = request.getParameter("cccd");
                    if (accountDAO.isEmailExist(email)) {
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        session.setAttribute("email", email);
                        session.setAttribute("name", name);
                        session.setAttribute("phone", sdt);
                        session.setAttribute("cccd", cccd);
                        session.setAttribute("notificationErr", "Email đã tồn tại trong hệ thống! Vui lòng lại với email khác");
                        response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        return;
                    }
                    if (accountDAO.isUsernameExist(username)) {
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        session.setAttribute("email", email);
                        session.setAttribute("name", name);
                        session.setAttribute("phone", sdt);
                        session.setAttribute("cccd", cccd);
                        session.setAttribute("notificationErr", "Tên đăng nhập đã tồn tại trong hệ thống! Vui lòng lại với tên khác khác");
                        response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        return;
                    }
                    if (accountDAO.isPhoneExist(sdt)) {
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        session.setAttribute("email", email);
                        session.setAttribute("name", name);
                        session.setAttribute("phone", sdt);
                        session.setAttribute("cccd", cccd);
                        session.setAttribute("notificationErr", "Số điện đã tồn tại trong hệ thống! Vui lòng lại với tên khác khác");
                        response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        return;
                    }
                    if (accountDAO.isCCCDExist(cccd)) {
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        session.setAttribute("email", email);
                        session.setAttribute("name", name);
                        session.setAttribute("phone", sdt);
                        session.setAttribute("cccd", cccd);
                        session.setAttribute("notificationErr", "Số CCCD đã tồn tại trong hệ thống! Vui lòng lại với tên khác khác");
                        response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        return;
                    }
                    Account newAccount = new Account();
                    newAccount.setUsername(username);
                    newAccount.setPassword(password);
                    newAccount.setEmail(email);
                    newAccount.setRole("manager");
                    boolean isAdded = accountDAO.addManager(newAccount);
                    if (isAdded) {
                        Date dob = Date.valueOf(request.getParameter("dob"));

                        QuanLy newQuanLy = new QuanLy();
                        int accountID = accountDAO.getAccountIdByEmail(email);
                        Account newAccount2 = accountDAO.getAccountById2(accountID);
                        newQuanLy.setAccount(newAccount2);
                        newQuanLy.setId_nhaTro(idNhaTro);
                        newQuanLy.setName(name);
                        newQuanLy.setPhone(sdt);
                        newQuanLy.setCccd(cccd);
                        newQuanLy.setDob(dob);

                        boolean isAddQuanLy = quanLyDAO.insertQuanLy(newQuanLy);
                        if (!isAddQuanLy) {
                            session.setAttribute("notificationErr", "Thêm quản lý thất bại.");
                            response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        } else {
                            session.setAttribute("notification", "Quản lý mới đã được thêm thành công.");
                            session.removeAttribute("username");
                            session.removeAttribute("password");
                            session.removeAttribute("email");
                            session.removeAttribute("name");
                            session.removeAttribute("phone");
                            session.removeAttribute("cccd");
                            response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        }

                    } else {
                        session.setAttribute("notificationErr", "Thêm quản lý thất bại.");
                        response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Đã xảy ra lỗi khi thêm quản lý.");
                    response.sendRedirect("quanly-account-nha-tro?id=" + request.getParameter("id"));
                }
            } else if (action.equals("delete")) {
                try {
                    // Get the manager (QuanLy) ID to delete
                    int quanLyId = Integer.parseInt(request.getParameter("quanLyId"));
                    int idNhaTro = Integer.parseInt(request.getParameter("id"));

                    // Call the DAO to delete the manager (QuanLy)
                    boolean isDeleted = quanLyDAO.deleteQuanLy(quanLyId);

                    if (isDeleted) {
                        // Set success notification
                        session.setAttribute("notification", "Quản lý đã được xóa thành công.");
                    } else {
                        // Set error notification
                        session.setAttribute("notificationErr", "Xóa quản lý thất bại. Vui lòng thử lại.");
                    }

                    // Redirect back to the QuanLy list page
                    response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);

                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Đã xảy ra lỗi khi xóa quản lý.");
                    response.sendRedirect("quanly-account-nha-tro?id=" + request.getParameter("id"));
                }
            }

        } else {
            session.setAttribute("errorMessage", "Bạn không có quyền thực hiện hành động này.");
            response.sendRedirect("login.jsp");
        }
    }

}
