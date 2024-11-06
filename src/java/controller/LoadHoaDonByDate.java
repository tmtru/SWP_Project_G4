/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.HoaDonDAO;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import model.DichVu;
import model.HoaDon;
import model.Phong;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Admin
 */
public class LoadHoaDonByDate extends HttpServlet {

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
            out.println("<title>Servlet LoadHoaDonByDate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadHoaDonByDate at " + request.getContextPath() + "</h1>");
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
        // Lấy startDate và endDate từ request và chuyển thành LocalDate
        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        String action = request.getParameter("action");

        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);

        HoaDonDAO hdDao = new HoaDonDAO();
        List<HoaDon> listhd = null;
        int id = -1;
        if (session.getAttribute("currentHouse") != null) {
            id = (int) session.getAttribute("currentHouse");
        }
        listhd = hdDao.getHoaDonByDateRange(startDate, endDate, id);
        if (action != null) {

            if (action.equals("exportExcel")) {
                // Create workbook and sheet
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Hợp đồng");

                // Create header row with additional information
                Row titleRow = sheet.createRow(0);
                // Create header row with additional information
                titleRow.createCell(0).setCellValue("Ngày tạo: " + LocalDate.now());
                titleRow.createCell(1).setCellValue(""); // Leave this cell empty for alignment
                titleRow.createCell(2).setCellValue("Ngày hóa đơn: " + startDate + " đến " + endDate);

// Adjust the row height for better visibility (optional)
                sheet.getRow(0).setHeightInPoints(30); // Adjust height as needed

// Create header row for data
                String[] headerTitles = {"Tên phòng trọ", "Tiền phòng", "Chỉ số cũ", "Chỉ số mới", "Tiền điện", "Tiền Nước", "Khác", "Tổng số tiền"};
                Row headerRow = sheet.createRow(1);
                for (int i = 0; i < headerTitles.length; i++) {
                    headerRow.createCell(i).setCellValue(headerTitles[i]);
                }

// Apply gray background to header
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                for (int i = 0; i < headerTitles.length; i++) {
                    headerRow.getCell(i).setCellStyle(headerStyle);
                }

                // Sort the list by room name
                listhd.sort(Comparator.comparing(hoadon -> hdDao.getRoomOfHoaDon(hoadon.getID_HoaDon()).getTenPhongTro()));

                // Populate data rows
                int rowNum = 2; // Start from row 2 as row 0 is for titles and row 1 is for headers
                for (HoaDon hoadon : listhd) {
                    List<DichVu> dvs = hoadon.getDichVus();
                    int OldNumber = 0, NewNumber = 0, TienDien = 0, TienNuoc = 0, totalServiceCharges = 0;
                    StringBuilder otherServices = new StringBuilder();

                    for (DichVu dv : dvs) {
                        if (dv.getTenDichVu().equals("Điện")) {
                            OldNumber = dv.getChiSoCu();
                            NewNumber = dv.getChiSoMoi();
                            TienDien = dv.getOldPrice();
                            totalServiceCharges += (NewNumber - OldNumber) * TienDien;
                        } else if (dv.getTenDichVu().equals("Nước")) {
                            TienNuoc = dv.getDauNguoi()*dv.getDon_gia();
                            totalServiceCharges += TienNuoc;
                        } else {
                            if (otherServices.length() > 0) {
                                otherServices.append("\n");
                            }
                            otherServices.append(dv.getTenDichVu()).append(": ").append(dv.getOldPrice()*dv.getDauNguoi());
                            totalServiceCharges += dv.getOldPrice()*dv.getDauNguoi(); // Add other service charges
                        }
                    }

                    // Calculate room rent as the total amount minus total service charges
                    int tienPhongChinh = hoadon.getTong_gia_tien() - totalServiceCharges;

                    Row row = sheet.createRow(rowNum++);
                    Phong room = hdDao.getRoomOfHoaDon(hoadon.getID_HoaDon());
                    row.createCell(0).setCellValue(room.getTenPhongTro());
                    row.createCell(1).setCellValue(tienPhongChinh); // Set calculated room rent
                    row.createCell(2).setCellValue(OldNumber);
                    row.createCell(3).setCellValue(NewNumber);
                    row.createCell(4).setCellValue((NewNumber - OldNumber) * TienDien);
                    row.createCell(5).setCellValue(TienNuoc);

                    // Create cell for other services and set text wrapping
                    Cell otherServicesCell = row.createCell(6);
                    otherServicesCell.setCellValue(otherServices.toString());
                    CellStyle style = sheet.getWorkbook().createCellStyle();
                    style.setWrapText(true);
                    otherServicesCell.setCellStyle(style);

                    row.createCell(7).setCellValue(hoadon.getTong_gia_tien());
                }

                // Set response headers
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=hoadonData.xlsx");

                // Write to response output stream
                try (OutputStream outputStream = response.getOutputStream()) {
                    workbook.write(outputStream);
                } finally {
                    workbook.close();
                }
            }

        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("invoices", listhd);
        request.getRequestDispatcher("hoaDonManagement.jsp").forward(request, response);
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
