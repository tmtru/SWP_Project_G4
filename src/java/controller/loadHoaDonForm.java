/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
import dal.HoaDonDAO;
import dal.PhongDAO;
import dal.TransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import model.DichVu;
import model.HopDong;
import model.Phong;

/**
 *
 * @author Admin
 */
public class loadHoaDonForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet loadHoaDonForm</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadHoaDonForm at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
        String roomIdParam = request.getParameter("roomId");
        PhongDAO pd = new PhongDAO();

        Integer idCurrentHouse=Integer.parseInt(session.getAttribute("currentHouse").toString());

        List<Phong> pList = pd.getRoomsByNhaTro(idCurrentHouse);
        List<Phong> rentedPList = new ArrayList<>();

        for (Phong p : pList) { 
            if (pd.isRentedRoom(p.getID_Phong())) { 
                rentedPList.add(p); 
            }
        }
        int roomId = -1;  

        if (roomIdParam != null) {
            try {
                roomId = Integer.parseInt(roomIdParam);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu roomId không phải là số
                request.setAttribute("errorMessage", "ID phòng không hợp lệ.");
            }
        }
        List<DichVu> dvListOfHopDong = null;

        if (roomId > -1) {
            PhongDAO pdao = new PhongDAO();
            HoaDonDAO hddao= new HoaDonDAO();
            Phong room = pdao.getDetailRoom(roomId); 
//            HopDong hopDong= hddao.getHopDongHienTaiOfRentedRoom(roomID);
            if (room != null) {
                int amount = room.getGia(); 
                request.setAttribute("room", room);
                request.setAttribute("amount", amount);
                if (hddao.getHopDongHienTaiOfRentedRoom(room.getID_Phong()).isEmpty()){
                    request.setAttribute("errorMessage", "Phòng không đang hoạt động.");
                            request.setAttribute("invoices", session.getAttribute("listInvoicesOfCurrentRoom"));
        request.setAttribute("currentRoomOfHoaDon", room);
                    request.getRequestDispatcher("HoaDonEachRoom.jsp").forward(request, response);
                    return;
                }
                int hdong= hddao.getHopDongHienTaiOfRentedRoom(room.getID_Phong()).get(0);
                int dauNguoi = hddao.getSoLuongNguoiByContractId(hdong);
                request.setAttribute("nofpeople", dauNguoi);
                dvListOfHopDong= hddao.getAllActiveServicesByContractId(hdong);
            } else {
                request.setAttribute("errorMessage", "Không tìm thấy thông tin phòng.");
            }
        } else {
            request.setAttribute("errorMessage", "ID phòng không hợp lệ.");
        }
        //load all dich vu
        DichVuDAO dvdao = new DichVuDAO();
        if (dvListOfHopDong!=null) request.setAttribute("dvListHopDong", dvListOfHopDong);
        List<DichVu> dvlist = dvdao.getAll();
        request.setAttribute("dvList", dvlist);
        request.setAttribute("rentedrooms", rentedPList);
        request.getRequestDispatcher("hoaDonForm.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
