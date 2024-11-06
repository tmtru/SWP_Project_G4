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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Account;
import model.DichVu;
import model.HoaDon;
import model.Phong;
import model.Transaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class actionHoaDon extends HttpServlet {

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
            out.println("<title>Servlet actionHoaDon</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet actionHoaDon at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        if (role.equals("landlord")) {
            if ("dele".equals(action)) {
                int idHoaDon = Integer.parseInt(request.getParameter("id"));
                HoaDonDAO hddao = new HoaDonDAO();
                TransactionDAO transactionDao = new TransactionDAO();
                List<Transaction> transactions = transactionDao.getTransactionsByIdHoaDon(idHoaDon);

                if (transactions.isEmpty()) {
                    hddao.deActiveHoaDon(idHoaDon);
                    request.setAttribute("notification", "Hóa đơn đã được xóa thành công.");
                } else {
                    request.setAttribute("errorMessage", "Không thể vô hiệu hóa hóa đơn có giao dịch.");
                }

                if (request.getParameter("exit").equals("home")) {
                    request.getRequestDispatcher("hoadon").forward(request, response);
                } else {

                    request.getRequestDispatcher("hoadonroom").forward(request, response);
                }
            }
        } else {
            response.sendRedirect("hoadon");
        }
        if ("exportExcel".equals(action)) {
            PhongDAO phongDAO = new PhongDAO();
            List<Phong> listPhong = phongDAO.getAllRooms();

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Room data");

            // Create header row
            String[] headerTitles = {"ID_Phong", "TenNhaTro", "TenPhongTro", "ID_LoaiPhong", "Tang", "Dien_Tich", "TenLoaiPhong", "Gia", "Mo_ta", "Trang_thai", "ID_NhaTro"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headerTitles.length; i++) {
                headerRow.createCell(i).setCellValue(headerTitles[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (Phong phong : listPhong) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(phong.getID_Phong());
                row.createCell(1).setCellValue(phong.getTenNhaTro());
                row.createCell(2).setCellValue(phong.getTenPhongTro());
                row.createCell(3).setCellValue(phong.getID_LoaiPhong());
                row.createCell(4).setCellValue(phong.getTang());
                row.createCell(5).setCellValue(phong.getDien_tich());
                row.createCell(6).setCellValue(phong.getTenLoaiPhong());
                row.createCell(7).setCellValue(phong.getGia());
                row.createCell(8).setCellValue(phong.getMo_ta());
                row.createCell(9).setCellValue(phong.getTrang_thai());
                row.createCell(10).setCellValue(phong.getID_NhaTro());
            }

            // Set response headers
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=roomData.xlsx");

            // Write to response output stream
            try (OutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            } finally {
                workbook.close();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private DichVuDAO dichVuDAO = new DichVuDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addHoaDon(request, response);
        }
    }

    private void addHoaDon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy thông tin từ form
            int idPhong = Integer.parseInt(request.getParameter("idPhong"));
            String ngayHoaDonStr = request.getParameter("ngayHoaDon");
            String moTa = request.getParameter("moTa");
            HoaDonDAO hddao = new HoaDonDAO();

            // Chuyển đổi định dạng ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayHoaDon = new Date(sdf.parse(ngayHoaDonStr).getTime());

            // Tạo đối tượng HoaDon
            HoaDon hoaDon = new HoaDon();
            int idHopDong = hddao.getHopDongHienTaiOfRentedRoom(idPhong).get(0);
            hoaDon.setID_HopDong(idHopDong);  // Lấy id hợp đồng từ id Phòng
            hoaDon.setNgay(ngayHoaDon);
            hoaDon.setMoTa(moTa);
            String giaPhong = request.getParameter("tinhTienPhong");
            int amount = -1;
            try {
                amount = Integer.parseInt(giaPhong);
            } catch (Exception e) {
                
            }

            // Xử lý danh sách dịch vụ từ form
            List<DichVu> danhSachDichVu = new ArrayList<>();
            String[] dichVuIds = request.getParameterValues("dichVuId");

            if (dichVuIds != null) {
                for (String dichVuIdStr : dichVuIds) {
                    int dichVuId = Integer.parseInt(dichVuIdStr);
                    DichVu dichVu = dichVuDAO.getDichVuById(dichVuId);

                    // Xử lý các dịch vụ có chỉ số cũ và mới (Điện/Nước)
                    if (!"Tháng".equals(dichVu.getDon_vi())) {
                        int chiSoCu = Integer.parseInt(request.getParameter("chiSoCu_dichVu_" + dichVuId));
                        int chiSoMoi = Integer.parseInt(request.getParameter("chiSoMoi_dichVu_" + dichVuId));
                        dichVu.setChiSoCu(chiSoCu);
                        dichVu.setChiSoMoi(chiSoMoi);
                    } else {
                        // Dịch vụ đầu người
                        int dauNguoi = Integer.parseInt(request.getParameter("dauNguoiInput_dichVu_" + dichVuId));
                        dichVu.setDauNguoi(dauNguoi);
                    }
                    dichVu.setOldPrice(dichVu.getDon_gia());

                    // Thêm vào danh sách dịch vụ
                    danhSachDichVu.add(dichVu);
                }
            }

            // Tính tổng giá tiền dựa trên các dịch vụ đã chọn
            int tongGiaTien = calculateTotalPrice(danhSachDichVu, amount);
            hoaDon.setTong_gia_tien(tongGiaTien);

            // Gọi hàm addHoaDon từ DAO
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("account");
            hoaDonDAO.addHoaDon(hoaDon, danhSachDichVu, acc.getUsername());

            // Chuyển hướng về trang danh sách hóa đơn hoặc hiển thị thành công
            session.setAttribute("currentRoom", idPhong);
            response.sendRedirect("hoadonroom");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e);
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title></title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet actionHoaDon at " + e + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private int calculateTotalPrice(List<DichVu> danhSachDichVu, int amount) {
        int totalPrice = 0;
        for (DichVu dichVu : danhSachDichVu) {
            if (!"Tháng".equals(dichVu.getDon_vi())) {
                // Tính tiền cho dịch vụ dựa trên chênh lệch chỉ số cũ và mới
                int chiSoCu = dichVu.getChiSoCu();
                int chiSoMoi = dichVu.getChiSoMoi();
                totalPrice += (chiSoMoi - chiSoCu) * dichVu.getDon_gia();
            } else {
                // Tính tiền dựa trên số đầu người
                totalPrice += dichVu.getDauNguoi() * dichVu.getDon_gia();
            }
        }
        if (amount != -1) {
            totalPrice += amount;
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
