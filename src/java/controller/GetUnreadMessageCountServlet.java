/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MessageDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "GetUnreadMessageCountServlet", value = "/getUnreadMessageCount")
public class GetUnreadMessageCountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        MessageDAO messageDAO = new MessageDAO();
        int unreadCount = messageDAO.getUnreadMessageCount(account.getID_Account());
        System.out.println(unreadCount);
        // Return the count as JSON
        response.setContentType("application/json");
        response.getWriter().write("{\"unreadCount\":" + unreadCount + "}");
    }
}
