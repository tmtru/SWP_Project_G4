/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Phong;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hihihihaha
 */
@MultipartConfig
public class importRoomExcel extends HttpServlet {
   
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
            out.println("<title>Servlet importRoomExcel</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet importRoomExcel at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        try {
            Part filePart = request.getPart("file");
            InputStream fileInputStream = filePart.getInputStream();
            
            List<Phong> importedRooms = readExcel(fileInputStream);
            
            PhongDAO phongDAO = new PhongDAO();
            for (Phong phong : importedRooms) {
                int newRoomId = phongDAO.insertRoom(phong);
                
            }
            
            response.sendRedirect("room.jsp?importSuccess=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("room.jsp?importError=true");
        }
    }

    private List<Phong> readExcel(InputStream inputStream) throws IOException {
        List<Phong> rooms = new ArrayList<>();
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        
        // Bỏ qua hàng tiêu đề
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }
        
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Phong phong = new Phong();
            
            phong.setID_NhaTro((int) row.getCell(10).getNumericCellValue());
            phong.setTenNhaTro(row.getCell(1).getStringCellValue());
            phong.setTenPhongTro(row.getCell(2).getStringCellValue());
            phong.setID_LoaiPhong((int) row.getCell(3).getNumericCellValue());
            phong.setTang((int) row.getCell(4).getNumericCellValue());
            phong.setDien_tich((float) row.getCell(5).getNumericCellValue());
            phong.setTenLoaiPhong(row.getCell(6).getStringCellValue());
            phong.setGia((int) row.getCell(7).getNumericCellValue());
            phong.setMo_ta(row.getCell(8).getStringCellValue());
            phong.setTrang_thai(row.getCell(9).getStringCellValue());
            
            // Xử lý ảnh nếu có
            // Giả sử ảnh được lưu dưới dạng URL trong một cột riêng
            Cell imageCell = row.getCell(11);  // Thay đổi index nếu cần
            if (imageCell != null) {
                List<String> images = new ArrayList<>();
                images.add(imageCell.getStringCellValue());
                phong.setImages(images);
            }
            
            rooms.add(phong);
        }
        
        workbook.close();
        return rooms;
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
