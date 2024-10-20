package controller;

import dal.NhaTroDAO;
import dal.PhongDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.NhaTro;
import model.Phong;

public class searchRoomByName extends HttpServlet {
    private PhongDAO phongDAO;

    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get search parameter
        String tenPhongTro = request.getParameter("tenPhongTro");
        
        // Get account from session
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // If no account logged in, set a default account with role "guest"
        if (account == null) {
            account = new Account();
            account.setRole("guest");
        }

        // Get rooms based on search and account
        List<Phong> rooms = phongDAO.getRoomsByName(tenPhongTro, account);

        // Get available floors
        List<Integer> tangList = phongDAO.getAvailableTang();
        request.setAttribute("tangList", tangList);

        // Get available houses
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        List<NhaTro> nhaTroList = nhaTroDAO.getAvailableNhaTro();
        request.setAttribute("nhaTroList", nhaTroList);

        // Set results and forward
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Search rooms by name servlet";
    }
}