/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ChuTroDAO;
import dal.NhaTroDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ChuTro;
import model.NhaTro;

/**
 *
 * @author hihihihaha
 */
@WebServlet(name = "detailNhaTroServlet", urlPatterns = {"/nhatro-detail"})
public class detailNhaTroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomIdStr = request.getParameter("id");
        if (roomIdStr != null && !roomIdStr.isEmpty()) {
            try {
                int id = Integer.parseInt(roomIdStr);
                NhaTroDAO nhaTroDAO = new NhaTroDAO();
                NhaTro nhaTro = nhaTroDAO.getNhaTroById2(id);

                if (nhaTro != null) {
                    List<String> listImage = nhaTroDAO.getImagesForNhaTro(nhaTro.getID_NhaTro());
                    request.setAttribute("s", nhaTro);
                    System.out.println(nhaTro);
                    ChuTroDAO ctdao = new ChuTroDAO();
                    request.setAttribute("listImage", listImage);
                    ChuTro ct = ctdao.getChuTroById(nhaTro.getID_ChuTro());
                    request.setAttribute("ct", ct);
                    request.getRequestDispatcher("detailNhaTro.jsp").forward(request, response);
                } else {
                    response.sendRedirect("room.jsp");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("room.jsp");
            }
        } else {
            response.sendRedirect("home.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
