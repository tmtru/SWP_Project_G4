/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ChuTroDAO;
import dal.DichVuDAO;
import dal.HopDongDAO;
import dal.KhachThueDAO;
import dal.KhachThuePhuDAO;
import dal.NhaTroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.ChuTro;
import model.DichVu;
import model.HopDong;
import model.KhachThue;
import model.KhachThuePhu;
import model.Phong;

/**
 *
 * @author Admin
 */
public class HopDongThueTroController extends HttpServlet {

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
            out.println("<title>Servlet HopDongThueTroController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HopDongThueTroController at " + request.getContextPath() + "</h1>");
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
        String hopDongIdStr = request.getParameter("hopDongId");
        int hopDongId = 0;
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        DichVuDAO dichVuDAO = new DichVuDAO();
        KhachThuePhuDAO khachThuePhuDao = new KhachThuePhuDAO();
        try {
            hopDongId = Integer.parseInt(hopDongIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Gọi hàm DAO để lấy thông tin chủ trọ từ ID hợp đồng
        ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
        // Gọi hàm DAO để lấy thông tin khách thuê từ ID hợp đồng
        KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
        // Gọi hàm DAO để lấy danh sách dịch vụ
        List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
        NhaTroDAO nhaTroDao = new NhaTroDAO();
        Phong roomDetails = nhaTroDao.getRoomDetailsByHopDongId(hopDongId);
        List<KhachThuePhu> khachThuePhuList = khachThuePhuDao.getKhachThuePhuByHopDongId(hopDongId);
        if (chuTro != null && khachThue != null) {
            request.setAttribute("chuTro", chuTro);
            request.setAttribute("khachThue", khachThue);
            request.setAttribute("dichVuList", dichVuList);
            request.setAttribute("giaPhong", roomDetails.getGia());
            request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
            request.setAttribute("khachThuePhuList", khachThuePhuList);
            request.getRequestDispatcher("/HopDongThuePhong.jsp").forward(request, response);
        } else {
            response.sendRedirect("errorPage.jsp");
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
        String hopDongIdString = request.getParameter("hopDongId");
        int hopDongId = Integer.parseInt(hopDongIdString);
        int depositAmount = Integer.parseInt(request.getParameter("depositAmount"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String ghiChu = request.getParameter("ghichu");
        request.setAttribute("depositAmount", depositAmount);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("ghichu", ghiChu);

        // Khởi tạo DAO để truy xuất dữ liệu
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        KhachThueDAO khachThueDAO = new KhachThueDAO();
        DichVuDAO dichVuDAO = new DichVuDAO();
        HopDongDAO hopDongDAO = new HopDongDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        KhachThuePhuDAO khachThuePhuDao = new KhachThuePhuDAO();

        try {
           
            HopDong newContract = hopDongDAO.getHopDongById(hopDongId);
            int roomId = newContract.getID_Phongtro();  // Lấy ra ID_PhongTro từ hợp đồng mới
            System.out.println(roomId);
          
            HopDong oldContract = hopDongDAO.getActiveOrAcceptedContractByRoomId(roomId);

          
            LocalDate newContractStartDate = LocalDate.parse(startDate);

           
            if (oldContract != null) {
                LocalDate oldContractEndDate = oldContract.getNgay_het_han().toLocalDate();

               
                if (!newContractStartDate.isAfter(oldContractEndDate)) {
                  
                    request.setAttribute("message", "Lỗi: Hợp đồng mới phải bắt đầu sau khi hợp đồng cũ kết thúc vào ngày " + oldContractEndDate.toString() + ".");

                   
                    ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
                    KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
                    List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
                    Phong roomDetails = nhaTroDAO.getRoomDetailsByHopDongId(hopDongId);
                    List<KhachThuePhu> khachThuePhuList = khachThuePhuDao.getKhachThuePhuByHopDongId(hopDongId);
                    
                    request.setAttribute("chuTro", chuTro);
                    request.setAttribute("khachThue", khachThue);
                    request.setAttribute("dichVuList", dichVuList);
                    request.setAttribute("giaPhong", roomDetails.getGia());
                    request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
                    request.setAttribute("khachThuePhuList", khachThuePhuList);
                    
                    request.getRequestDispatcher("/HopDongThuePhong.jsp").forward(request, response);
                    return; // Dừng xử lý nếu ngày bắt đầu không hợp lệ
                }

            }

          
            boolean isSaved = hopDongDAO.insertHopDong(hopDongId, startDate, endDate, depositAmount, ghiChu);

            if (isSaved) {
                NhaTroDAO nhaTroDao = new NhaTroDAO();
                    ChuTro chuTro = chuTroDAO.getChuTroByHopDongId(hopDongId);
            KhachThue khachThue = khachThueDAO.getKhachThueByHopDongId(hopDongId);
            List<DichVu> dichVuList = dichVuDAO.getDichVuByHopDongId(hopDongId);
            Phong roomDetails = nhaTroDao.getRoomDetailsByHopDongId(hopDongId);
            HopDong hopDong = hopDongDAO.getHopDongById(hopDongId);
            List<KhachThuePhu> khachThuePhuList = khachThuePhuDao.getKhachThuePhuByHopDongId(hopDongId);
            request.setAttribute("hopDongId", hopDongId);
            request.setAttribute("chuTro", chuTro);
            request.setAttribute("khachThue", khachThue);
            request.setAttribute("dichVuList", dichVuList);
            request.setAttribute("giaPhong", roomDetails.getGia());
            request.setAttribute("diaChiPhongTro", roomDetails.getDiaChiPhongTro());
            request.setAttribute("trangThai", roomDetails.getTrang_thai());
            request.setAttribute("hopDong", hopDong);
            request.setAttribute("khachThuePhuList", khachThuePhuList);

               
                request.getRequestDispatcher("/HopDongThuePhongDaDangKy.jsp").forward(request, response);
            } else {
                
                response.getWriter().write("Không thể lưu hợp đồng.");
            }
        } catch (DateTimeParseException e) {
          
            request.setAttribute("message", "Lỗi: Định dạng ngày không hợp lệ.");
            request.getRequestDispatcher("/HopDongThuePhongDaDangKy.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi: " + e.getMessage());
        }
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
