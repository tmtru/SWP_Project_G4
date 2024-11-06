/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
import dal.HoaDonDAO;
import dal.HopDongDAO;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.DichVu;
import model.HoaDon;
import model.Phong;

/**
 *
 * @author Admin
 */
public class addFastInvoices extends HttpServlet {

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
            out.println("<title>Servlet addFastInvoices</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addFastInvoices at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String step = request.getParameter("step");
        PhongDAO pd = new PhongDAO();
        HttpSession session = request.getSession();
        if (step.equals("1")) {
            String[] selectedRooms = request.getParameterValues("selectedRooms");

            if (selectedRooms != null) {
                List<Phong> room = new ArrayList<>();
                for (String roomId : selectedRooms) {
                    room.add(pd.getDetailRoom(Integer.parseInt(roomId)));
                }

                session.setAttribute("selectedRooms", room); // Save selected rooms in session

                request.setAttribute("selectedRooms", room);
                request.getRequestDispatcher("Step2AddFastInvoices.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Xin vui lòng chọn ít nhất một phòng để tạo hóa đơn!");
                request.getRequestDispatcher("hoadon").forward(request, response);
            }
        } else if (step.equals("2")) {

            List<Phong> selectedRooms = (List<Phong>) session.getAttribute("selectedRooms");
            List<HoaDon> invoices = new ArrayList<>();

            if (selectedRooms == null) {
                response.sendRedirect("errorPage.jsp");

                return;
            }
            String includeRoomRentParam = request.getParameter("includeRoomRent");
            boolean includeRoomRent = (includeRoomRentParam != null);
            DichVuDAO dvdao = new DichVuDAO();
            HoaDonDAO hdao = new HoaDonDAO();
            List<DichVu> ldv = dvdao.getAll();
            int giadien = 0;
            for (DichVu dv : ldv) {
                if (dv.getTenDichVu().equalsIgnoreCase("Điện")) {
                    giadien = dv.getDon_gia();
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            List<Map<HoaDon, List<DichVu>>> invoicesWithServices = new ArrayList<>();

            for (Phong room : selectedRooms) {
                int roomId = room.getID_Phong();
                HoaDon hoaDon = new HoaDon();
                int idHopDong = hdao.getHopDongHienTaiOfRentedRoom(roomId).get(0);
                hoaDon.setID_HopDong(idHopDong);
                hoaDon.setNgay(today);
                hoaDon.setMoTa("Tính tiền phòng tháng " + new SimpleDateFormat("MM").format(today)+" Phòng "+room.getTenPhongTro());

                List<DichVu> allDichVu = hdao.getAllActiveServicesByContractId(idHopDong);
                List<DichVu> danhSachDichVu = new ArrayList<>();

                for (DichVu dichVu : allDichVu) {
                    if (!"Tháng".equals(dichVu.getDon_vi())) {
                        int chiSoCu = Integer.parseInt(request.getParameter("chiSoCu_dien_" + roomId));
                        int chiSoMoi = Integer.parseInt(request.getParameter("chiSoMoi_dien_" + roomId));
                        dichVu.setChiSoCu(chiSoCu);
                        dichVu.setChiSoMoi(chiSoMoi);
                    } else {
                        int dauNguoi = hdao.getSoLuongNguoiByContractId(idHopDong);
                        dichVu.setDauNguoi(dauNguoi);
                    }
                    dichVu.setOldPrice(dichVu.getDon_gia());
                    danhSachDichVu.add(dichVu);
                }

                int tongGiaTien = calculateTotalPrice(danhSachDichVu);
                if (includeRoomRent) {
                    tongGiaTien += room.getGia();
                }
                hoaDon.setTong_gia_tien(tongGiaTien);
                Map<HoaDon, List<DichVu>> hoaDonWithServices = new HashMap<>();
                hoaDonWithServices.put(hoaDon, danhSachDichVu);

                invoicesWithServices.add(hoaDonWithServices);
                invoices.add(hoaDon);
//                hdao.addHoaDon(hoaDon, danhSachDichVu, acc.getUsername()); 
            }
            session.setAttribute("invoicesToConfirm", invoicesWithServices);

            // Chuyển hướng đến trang xác nhận
            request.getRequestDispatcher("confirmfastInvoices.jsp").forward(request, response);

        } else if (step.equals("3")) {
            HoaDonDAO hdao = new HoaDonDAO();
            List<Map<HoaDon, List<DichVu>>> invoicesWithServices
                    = (List<Map<HoaDon, List<DichVu>>>) session.getAttribute("invoicesToConfirm");
            Account acc = (Account) session.getAttribute("account");

            if (invoicesWithServices != null) {

                for (Map<HoaDon, List<DichVu>> invoiceEntry : invoicesWithServices) {
                    for (Map.Entry<HoaDon, List<DichVu>> entry : invoiceEntry.entrySet()) {
                        HoaDon hoaDon = entry.getKey();
                        List<DichVu> services = entry.getValue();
                        hdao.addHoaDon(hoaDon, services, acc.getUsername());

                    }
                }
                session.setAttribute("invoicesWithServices", null);
                response.sendRedirect("hoadon");
            }
            
            

        }

    }

    public int calculateTotalPrice(List<DichVu> danhSachDichVu) {
        int totalPrice = 0;

        for (DichVu dichVu : danhSachDichVu) {
            int oldPrice = dichVu.getOldPrice();

            if ("Tháng".equals(dichVu.getDon_vi())) {
                int dauNguoi = dichVu.getDauNguoi();
                totalPrice += oldPrice * dauNguoi;
            } else {
                int chiSoCu = dichVu.getChiSoCu();
                int chiSoMoi = dichVu.getChiSoMoi();
                if (chiSoMoi >= chiSoCu) {
                    int usage = chiSoMoi - chiSoCu;
                    totalPrice += oldPrice * usage;
                }
            }
        }
        return totalPrice;
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
