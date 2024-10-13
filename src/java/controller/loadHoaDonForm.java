/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
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
        List<Phong> pList = pd.getAllRooms();
        List<Phong> rentedPList = new ArrayList<>();

        for (Phong p : pList) { 
            if (pd.isRentedRoom(p.getID_Phong())) { 
                rentedPList.add(p); 
            }
        }
        int roomId = -1;  // Giá trị mặc định nếu không chuyển đổi được

        if (roomIdParam != null) {
            try {
                roomId = Integer.parseInt(roomIdParam); // Chuyển đổi chuỗi thành int
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu roomId không phải là số
                request.setAttribute("errorMessage", "ID phòng không hợp lệ.");
            }
        }

        if (roomId > -1) {
            // Tạo đối tượng PhongDAO
            PhongDAO pdao = new PhongDAO();

            // Tìm kiếm thông tin phòng
            Phong room = pdao.getDetailRoom(roomId); // Phương thức cần được định nghĩa trong PhongDAO

            if (room != null) {
                // Lấy giá của phòng
                int amount = room.getGia(); // Phương thức để lấy giá phòng

                // Lưu thông tin phòng và số tiền vào request
                request.setAttribute("room", room);
                request.setAttribute("amount", amount);
            } else {
                // Xử lý trường hợp không tìm thấy phòng
                request.setAttribute("errorMessage", "Không tìm thấy thông tin phòng.");
            }
        } else {
            // Xử lý trường hợp ID phòng không hợp lệ
            request.setAttribute("errorMessage", "ID phòng không hợp lệ.");
        }
        //load all dich vu
        DichVuDAO dvdao = new DichVuDAO();
        List<DichVu> dvlist = dvdao.getAll();
        request.setAttribute("dvList", dvlist);
        request.setAttribute("rentedrooms", rentedPList);

        //load all rooms of nha tro (session Rooms)
        // Chuyển tiếp đến trang hiển thị hóa đơn
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
