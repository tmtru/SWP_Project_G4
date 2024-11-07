/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.MessageDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "GetUserListForMessageServlet", value = "/getUserList")
public class GetUserListForMessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null || !"landlord".equalsIgnoreCase(account.getRole())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        MessageDAO messageDAO = new MessageDAO();
        List<Account> tenants = messageDAO.getTenantsWithUnreadCounts(account.getID_Account());

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(tenants);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
