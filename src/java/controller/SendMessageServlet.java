package controller;

import dal.MessageDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.Account;
import model.Message;

@WebServlet(name = "SendMessageServlet", value = "/sendMessage")
public class SendMessageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int senderId = account.getID_Account();
        int receiverId;
        String messageContent = request.getParameter("messageContent");
        int houseId = Integer.parseInt(request.getParameter("houseId"));

        if ("landlord".equalsIgnoreCase(account.getRole())) {
            receiverId = Integer.parseInt(request.getParameter("selectedUserId"));
        } else {
            receiverId = Integer.parseInt(request.getParameter("receiverId"));
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(messageContent);
        message.setNhatroID(houseId);

        MessageDAO messageDAO = new MessageDAO();
        messageDAO.insertMessage(message);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
