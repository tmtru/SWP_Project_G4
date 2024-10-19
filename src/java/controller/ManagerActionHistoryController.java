/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.ActionHistoryDAO;
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
import java.util.List;
import model.Account;
import model.ActionHistory;
import model.KhachThue;
import model.NhaTro;
import model.QuanLy;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManagerActionHistoryController", urlPatterns = {"/manager-action"})
public class ManagerActionHistoryController extends HttpServlet {

    private final NhaTroDAO nhaTroDAO = new NhaTroDAO();
    private final ActionHistoryDAO actionHistoryDAO = new ActionHistoryDAO();

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
                String searchParam = request.getParameter("search");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                int idNhaTro = Integer.parseInt(request.getParameter("id"));
                NhaTro nhatro = nhaTroDAO.getNhaTroById(idNhaTro);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("nhatro", nhatro);
                List<ActionHistory> listActionHistory = actionHistoryDAO.getAllActionWithParam(searchParam, idNhaTro, null);
                List<ActionHistory> pagingKhachThue = actionHistoryDAO.Paging(listActionHistory, page, pageSize);
                request.setAttribute("list", pagingKhachThue);
                request.setAttribute("totalPages", listActionHistory.size() % pageSize == 0 ? (listActionHistory.size() / pageSize) : (listActionHistory.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.setAttribute("id", idNhaTro);
                request.getRequestDispatcher("manager-action.jsp").forward(request, response);
            } else if (account.getRole().equals("manager")) {
                isOwner = false;
                String pageParam = request.getParameter("page");
                String searchParam = request.getParameter("search");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                QuanLyDAO qldao = new QuanLyDAO();
               QuanLy quanLy = qldao.getChuTroByAccountId(account.getID_Account());
                int idNhaTro = Integer.parseInt(request.getParameter("id"));
                NhaTro nhatro = nhaTroDAO.getNhaTroById(idNhaTro);
                request.setAttribute("isOwner", isOwner);
                request.setAttribute("nhatro", nhatro);
                List<ActionHistory> listActionHistory = actionHistoryDAO.getAllActionWithParam(searchParam, idNhaTro, quanLy.getId());
                List<ActionHistory> pagingKhachThue = actionHistoryDAO.Paging(listActionHistory, page, pageSize);
                request.setAttribute("list", pagingKhachThue);
                request.setAttribute("totalPages", listActionHistory.size() % pageSize == 0 ? (listActionHistory.size() / pageSize) : (listActionHistory.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.setAttribute("id", idNhaTro);
                request.getRequestDispatcher("manager-action.jsp").forward(request, response);
            }
        } else {
            session.setAttribute("errorMessage", "You must login first!");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
