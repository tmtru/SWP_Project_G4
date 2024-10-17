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
            if (account.getRole().equals("Quản lý")) {
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

        if (account != null && account.getRole().equals("Chủ trọ")) {
            String action = request.getParameter("action");
            if (action.equals("add")) {
                try {
                    // Retrieve form data
                    int accountId = Integer.parseInt(request.getParameter("account"));
                    int idNhaTro = Integer.parseInt(request.getParameter("id"));

                    // Check if the selected account already exists as a manager (QuanLy)
                    QuanLy existingQuanLy = quanLyDAO.getChuTroByAccountId(accountId);
                    System.out.println(existingQuanLy);
                    if (existingQuanLy != null) {
                        // If the account is already a QuanLy
                        if (existingQuanLy.getId_nhaTro() == idNhaTro) {
                            // Already managing the current NhaTro, nothing to do, return success
                            session.setAttribute("notificationErr", "Người dùng đã là quản lý của nhà trọ này.");
                            response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        } else if (existingQuanLy.getId_nhaTro() == 0) {
                            System.out.println(existingQuanLy);
                            QuanLy quanLy = new QuanLy();
                            quanLy.setId_nhaTro(idNhaTro);
                            quanLy.setId(existingQuanLy.getId());
                            boolean isUpdate = quanLyDAO.updateQuanLy(quanLy);
                            if (isUpdate) {
                                Account account2 = accountDAO.getAccountById2(accountId);
                                account2.setRole("Quản lý");
                                accountDAO.updateAccount(account2);
                                session.setAttribute("notification", "Quản lý mới đã được thêm thành công.");
                            }
                            response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        } else {
                            // Managing a different NhaTro, return error
                            session.setAttribute("notificationErr", "Người dùng này đang quản lý nhà trọ khác.");
                            response.sendRedirect("quanly-account-nha-tro?id=" + idNhaTro);
                        }
                    } else {
                        String name = request.getParameter("name");
                        String sdt = request.getParameter("phone");
                        String cccd = request.getParameter("cccd");

                        Date dob = Date.valueOf(request.getParameter("dob"));
                        // Account is not a QuanLy yet, insert new QuanLy entry
                        QuanLy newQuanLy = new QuanLy();
                        Account account2 = accountDAO.getAccountById2(accountId);
                        newQuanLy.setAccount(account2);
                        newQuanLy.setId_nhaTro(idNhaTro);
                        newQuanLy.setName(name);
                        newQuanLy.setPhone(sdt);
                        newQuanLy.setCccd(cccd);
                        newQuanLy.setDob(dob);
                        account2.setRole("Quản lý");
                        quanLyDAO.insertQuanLy(newQuanLy);
                        accountDAO.updateAccount(account2);
                        session.setAttribute("notification", "Quản lý mới đã được thêm thành công.");
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
