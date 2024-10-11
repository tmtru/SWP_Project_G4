/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.HoaDonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.HoaDon;
import model.NhaTro;

/**
 *
 * @author Admin
 */
public class loadHoaDonNhaTro extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loadHoaDonNhaTro</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadHoaDonNhaTro at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        List<NhaTro> houses = (List<NhaTro>) session.getAttribute("housesByRole");
        int choseHouse=-1;
        if (request.getParameter("id")!=null){
            choseHouse=Integer.parseInt(request.getParameter("id"));
        };
        if (houses!=null && !houses.isEmpty()){
            //neu chua duoc chon bat ki nha tro nào sẽ tự động lấy nahf trọ đầu tiên
            if (choseHouse==-1){
                choseHouse=houses.get(0).getID_NhaTro();
                session.setAttribute("currentHouse", choseHouse);
                setListHoaDonOfHouse(choseHouse, session);
            } else {
                session.setAttribute("currentHouse", choseHouse);
                setListHoaDonOfHouse(choseHouse, session);
            }
        }
        request.getRequestDispatcher("hoaDonManagement.jsp").forward(request, response);
    } 
    //load list of invoices cho nha tro
    private static void setListHoaDonOfHouse(int idNhaTro, HttpSession session){
        HoaDonDAO hddao= new HoaDonDAO();
        List<HoaDon> listHoaDon= hddao.getAllHoaDonByNhaTroID(idNhaTro);
        session.setAttribute("invoices", listHoaDon);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
