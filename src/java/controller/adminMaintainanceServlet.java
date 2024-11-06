package controller;

import dal.MaintainanceDAO;
import model.Maintainance;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class adminMaintainanceServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
        List<Maintainance> maintainanceList = maintainanceDAO.getMaintainanceRequests();

        request.setAttribute("maintainanceList", maintainanceList);

        request.getRequestDispatcher("viewMaintainanceRequest.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        int id = Integer.parseInt(idParam);
        MaintainanceDAO maintainanceDAO = new MaintainanceDAO();

        if ("approve".equals(action)) {
            maintainanceDAO.readMaintainanceRequest(id);  // Update request status
            maintainanceDAO.approveMaintainanceRequest(id);  // Set approval status
        } else if ("deny".equals(action)) {
            maintainanceDAO.readMaintainanceRequest(id);  // Update request status
            maintainanceDAO.declineMaintainanceRequest(id);
        }

        // Redirect or forward back to the list after processing
        List<Maintainance> maintainanceList = maintainanceDAO.getMaintainanceRequests();
        request.setAttribute("maintainanceList", maintainanceList);
        request.getRequestDispatcher("viewMaintainanceRequest.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}