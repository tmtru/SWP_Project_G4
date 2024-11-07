package controller;

import com.google.gson.Gson;
import dal.MessageDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.Message;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetMessagesServlet", value = "/getMessages")
public class GetMessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int receiverId = account.getID_Account();
        int senderId;
        int houseId = Integer.parseInt(request.getParameter("houseId"));

        if ("landlord".equalsIgnoreCase(account.getRole())) {
            senderId = Integer.parseInt(request.getParameter("selectedUserId"));
        } else {
            senderId = Integer.parseInt(request.getParameter("receiverId"));
        }

        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessagesBetween(senderId, receiverId, houseId);

        // Mark messages as read
        messageDAO.markMessagesAsRead(senderId, receiverId, houseId);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(messages);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
