/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import org.apache.poi.ss.usermodel.Sheet;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import model.Phong;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hihihihaha
 */
public class addRoomExcel extends HttpServlet {

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
            out.println("<title>Servlet addRoomExcel</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addRoomExcel at " + request.getContextPath() + "</h1>");
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

    public boolean exportRoomToExcel(List<Phong> listPhong) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn tệp để lưu"); //Tiêu đề hộp thoại
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Lựa chọn chế độ chỉ chọn thư mục

        int userSelection = fileChooser.showSaveDialog(null);
        //check xem ng dung nhap dung ko
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return false; //dong hop thoai
        }
        File directionToSave = fileChooser.getSelectedFile();
        if (directionToSave == null) {
            return false;
        }
        // workbook la file excel dc luu tru 
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Room data");
        // chua danh sach tieu de
        String[] headerTilte = {"ID_Phong", "TenNhaTro", "TenPhongTro", "ID_LoaiPhong", "Tang", "Dien_Tich", "TenLoaiPhong", "Gia", "Mo_ta", "Trang_thai", "ID_NhaTro",};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTilte.length; i++) {
            headerRow.createCell(i).setCellValue(headerTilte[i]);
        }
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
        String fileName = "roomData.xlsx";
        File exceFile = new File(directionToSave, fileName);
        try (FileOutputStream fileOut = new FileOutputStream(exceFile)) {
            workbook.write(fileOut);
            System.out.println("success export");
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
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
