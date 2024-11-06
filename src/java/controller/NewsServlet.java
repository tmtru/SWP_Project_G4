/*
 */
package controller;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.ChuTro;
import model.KhachThue;
import model.New;
import model.NhaTro;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "NewsServlet", urlPatterns = {"/new"})
public class NewsServlet extends HttpServlet {

    final NewDAO newDAO = new NewDAO();
    final NhaTroDAO nhatroDAO = new NhaTroDAO();
    final KhachThueDAO khachThueDAO = new KhachThueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            if (account.getRole().equals("tenant")) {
                String pageParam = request.getParameter("page");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                KhachThue khachThue = khachThueDAO.getKhachThueByAccountId(account.getID_Account());
                NhaTro nt = nhatroDAO.getNhaTroOfKhach(khachThue.getId());
                List<New> list = newDAO.getNewByNhaTro(nt.getID_NhaTro());

                List<New> pagingNews = newDAO.Paging(list, page, pageSize);
                request.setAttribute("news", pagingNews);
                request.setAttribute("totalPages", list.size() % pageSize == 0 ? (list.size() / pageSize) : (list.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("news.jsp").forward(request, response);

            } else {
                session.setAttribute("errorMessage", "Access Denined!");
                response.sendRedirect("login.jsp");
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
