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
import java.security.Timestamp;
import java.util.List;
import model.Account;
import model.ChuTro;
import model.New;
import model.NhaTro;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManagementNewServlet", urlPatterns = {"/manage-new"})
public class ManagementNewServlet extends HttpServlet {

    final NewDAO newDAO = new NewDAO();
    final NhaTroDAO nhatroDAO = new NhaTroDAO();
    final ChuTroDAO chuTroDAO = new ChuTroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            if (account.getRole().equals("landlord")) {

                String pageParam = request.getParameter("page");
                int page = 1; // Default to the first page
                int pageSize = 6; // Set the desired page size
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                ChuTro chuTro = chuTroDAO.getChuTroByAccountId(account.getID_Account());
                List<NhaTro> listNhaTro = nhatroDAO.getAllNhaTroWithParam(null, chuTro.getId());
                List<New> listNews = newDAO.getAllNews(account.getID_Account());
                List<New> pagingNews = newDAO.Paging(listNews, page, pageSize);

                request.setAttribute("news", pagingNews);
                request.setAttribute("listNhaTro", listNhaTro);

                request.setAttribute("totalPages", listNews.size() % pageSize == 0 ? (listNews.size() / pageSize) : (listNews.size() / pageSize + 1));
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("manage-news.jsp").forward(request, response);

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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null && account.getRole().equals("landlord")) {
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");  
                int nhaTroId = Integer.parseInt(request.getParameter("nhaTro"));

                New newRecord = new New();
                newRecord.setTitle(title);
                newRecord.setContent(content);
                newRecord.setCreator(account);
                NhaTro nhaTro = nhatroDAO.getNhaTroById2(nhaTroId);
                newRecord.setNhaTro(nhaTro);

                newDAO.insertNew(newRecord); // Insert into database
                session.setAttribute("notification", "New record added successfully!");
                response.sendRedirect("manage-new");
            } else if (action.equals("delete")) {

            }

        } else {
            session.setAttribute("errorMessage", "Bạn không có quyền thực hiện hành động này.");
            response.sendRedirect("login.jsp");
        }
    }

}
