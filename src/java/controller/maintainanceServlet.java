package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.KhachThue;
import model.Phong;
import model.ThietBiPhong;
import dal.AccountDAO;
import dal.KhachThueDAO;
import dal.ThietBiPhongDAO;
import dal.PhongDAO;
import java.util.ArrayList;

public class maintainanceServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        
        // Check for authentication
        Integer accountId = (Integer) session.getAttribute("ID_Account");
        if (accountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Initialize DAOs
        AccountDAO accountDAO = new AccountDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        PhongDAO phongDAO = new PhongDAO();
        ThietBiPhongDAO thietBiDAO = new ThietBiPhongDAO();

        // Get account info
        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get tenant info and check role
        KhachThue khachThue = khachThueDAO.getKhachThueByAccountId(accountId);
        boolean isTenant = "tenant".equalsIgnoreCase(account.getRole());

        // Get rooms for tenant
        List<Phong> listPhong = new ArrayList<>();
        if (khachThue != null) {
            listPhong = phongDAO.getRoomsByKhachThueId(khachThue.getId());
        }

        // Handle room selection
        String selectedRoom = request.getParameter("selectedRoom");
        if (selectedRoom == null || selectedRoom.isEmpty()) {
            // If no room is selected and there are rooms available, select the first one
            if (!listPhong.isEmpty()) {
                selectedRoom = String.valueOf(listPhong.get(0).getID_Phong());
            }
        }

        // Get devices for selected room
        List<ThietBiPhong> listThietBi = new ArrayList<>();
        if (selectedRoom != null && !selectedRoom.isEmpty()) {
            listThietBi = thietBiDAO.getThietBiByPhongId(Integer.parseInt(selectedRoom));
        }

        // Set attributes for JSP
        request.setAttribute("listPhong", listPhong);
        request.setAttribute("listThietBiPhong", listThietBi);
        request.setAttribute("account", account);
        request.setAttribute("isTenant", isTenant);
        request.setAttribute("selectedRoom", selectedRoom);
        
        // Forward to maintenance page
        request.getRequestDispatcher("maintainance.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}