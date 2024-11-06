/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DichVuDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.DichVu;

/**
 *
 * @author Admin
 */
public class actionDichvu extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String role = session.getAttribute("role").toString();
        String action = request.getParameter("action");
        int idDichVu = Integer.parseInt(request.getParameter("id"));
        DichVuDAO dvDao = new DichVuDAO();
        String khac;
        if (role.equals("landlord")) {

            switch (action) {
                case "dele":
                    dvDao.deactivateDichVu(idDichVu);
                    break;
                case "edit":
                    String tenDichVu = request.getParameter("tendichvu");
                    int donGia = Integer.parseInt(request.getParameter("donGia"));
                    String donVi = request.getParameter("donvi");
                    String moTa = request.getParameter("mota");
                    DichVu dichVu = dvDao.getDichVuById(idDichVu);
                    dichVu.setTenDichVu(tenDichVu);
                    dichVu.setDon_gia(donGia);
                    if (donVi.equals("Khác")) {
                        khac = request.getParameter("donviKhac");
                        dichVu.setDon_vi(khac);
                    } else {
                        dichVu.setDon_vi(donVi);
                    }
                    dichVu.setMo_ta(moTa);

                    dichVu.setMo_ta(moTa);
                    dichVu.setID_DichVu(idDichVu);
                    List<DichVu> allDichVuList = dvDao.getAll();

                    boolean isDuplicate = false;
                    for (DichVu existingDichVu : allDichVuList) {
                        if (existingDichVu.getTenDichVu().equalsIgnoreCase(tenDichVu) && existingDichVu.getID_DichVu() != idDichVu) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (isDuplicate) {
                        request.setAttribute("errorMessage", "Tên dịch vụ đã tồn tại. Vui lòng nhập tên khác.");
                        request.getRequestDispatcher("loaddichvu").forward(request, response);
                        return; // Stop further processing
                    }
                    dvDao.updateDichVu(dichVu);
                    break;
                case "update":
                    String isActive = request.getParameter("isActive");
                    if ("true".equals(isActive)) { // Kiểm tra nếu isActive là "1"
                        dvDao.activateDichVu(idDichVu); // Kích hoạt dịch vụ
                    } else {
                        dvDao.deactivateDichVu(idDichVu); // Tắt dịch vụ
                    }

                default:
                    break;
            }
        }
        response.sendRedirect("loaddichvu");
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
