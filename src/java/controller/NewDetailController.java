/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.KhachThue;
import model.New;
import model.NhaTro;
import model.QuanLy;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "NewDetailController", urlPatterns = {"/new-detail"})
public class NewDetailController extends HttpServlet {

    NewDAO newDAO = new NewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        boolean isLandLord;
        if (account != null) {
            System.out.println(account.getRole());
            switch (account.getRole()) {
                case "landlord" -> {
                    isLandLord = true;
                    int id = Integer.parseInt(request.getParameter("id"));
                    New news = newDAO.getNewByID(id);
                    request.setAttribute("n", news);
                    request.setAttribute("isLandLord", isLandLord);
                    System.out.println(isLandLord);
                }
                case "tenant" -> {
                    isLandLord = false;
                    int id = Integer.parseInt(request.getParameter("id"));
                    New news = newDAO.getNewByID(id);
                    request.setAttribute("n", news);
                    request.setAttribute("isLandLord", isLandLord);
                }
                default -> {
                    session.setAttribute("errorMessage", "Access Denined!");
                    response.sendRedirect("login.jsp");
                    return;
                }

            }
            request.getRequestDispatcher("new-detail.jsp").forward(request, response);
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
