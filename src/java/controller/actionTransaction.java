/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.HoaDonDAO;
import dal.HopDongDAO;
import dal.KhachThueDAO;
import dal.TransactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import model.Transaction;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HoaDon;
import model.KhachThue;

/**
 *
 * @author Admin
 */
public class actionTransaction extends HttpServlet {

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
            out.println("<title>Servlet actionTransaction</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet actionTransaction at " + request.getContextPath() + "</h1>");
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
        int id;
        if (role.equals("landlord")) {
            if ("dele".equals(action)) {
                int idTransaction = Integer.parseInt(request.getParameter("id"));
                TransactionDAO tdao = new TransactionDAO();
                tdao.deactivateTransaction(idTransaction);
                request.setAttribute("notification", "Giao dịch được xóa thành công.");
                if (session.getAttribute("currentHouse") != null) {
                    id = (int) session.getAttribute("currentHouse");
                    session.setAttribute("transactions", tdao.getAllTransactionsByNhaTroId(id));
                }
                if (request.getParameter("exit").equals("home")) {

                    request.getRequestDispatcher("TransactionHistory.jsp").forward(request, response);
                } else {
                    response.sendRedirect("hoadonroom");
                }
            }
        } else {
            response.sendRedirect("hoadon");
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
        TransactionDAO transactionDao = new TransactionDAO();
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        HoaDonDAO hdao=new HoaDonDAO();
        HopDongDAO hddao = new HopDongDAO();
        KhachThueDAO ktdao= new KhachThueDAO();
        AccountDAO accdao= new AccountDAO();

        if ("add".equals(action)) {
            String maGiaoDich = request.getParameter("maGiaoDich").trim();
            float amount = Float.parseFloat(request.getParameter("amount"));
            String paymentMethod = request.getParameter("paymentMethod");
            int idHoaDon = Integer.parseInt(request.getParameter("idHoaDon"));
            String transactionDateStr = request.getParameter("transactionDate");
            String mota = request.getParameter("moTa").trim();
            Date transactionDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                transactionDate = new Date(sdf.parse(transactionDateStr).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(actionTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Kiểm tra xem giao dịch đã tồn tại chưa
            if (transactionDao.isTransactionDuplicated(maGiaoDich)) {
                request.setAttribute("id", idHoaDon);
                request.setAttribute("errorMessage", "Giao dịch đã tồn tại.");
                request.getRequestDispatcher("TransactionForm.jsp").forward(request, response);
            } else {
                float totalCurrentTransactionAmount = transactionDao.getTotalMoneyByIdHoaDon(idHoaDon);
                float amountDue = transactionDao.getRequiredAmountByIdHoaDon(idHoaDon);
                // Kiểm tra xem tổng số tiền sau khi thêm giao dịch mới có vượt quá số tiền cần thanh toán không
                if (totalCurrentTransactionAmount + amount > amountDue) {
                    request.setAttribute("id", idHoaDon);
                    request.setAttribute("errorMessage", "Tổng số tiền giao dịch vượt quá số tiền cần thanh toán.");
                    request.getRequestDispatcher("TransactionForm.jsp").forward(request, response);
                } else {
                    Transaction transaction = new Transaction();
                    transaction.setMaGiaoDich(maGiaoDich);
                    transaction.setAmount(amount);
                    transaction.setPaymentMethod(paymentMethod);
                    transaction.setID_HoaDon(idHoaDon);
                    transaction.setTransaction(transactionDate);
                    transaction.setMoTa(mota);
                    HoaDon h1= hdao.getHoaDonById(idHoaDon);
                    KhachThue kt1= ktdao.getKhachThueByHopDongId(h1.getID_HopDong());

                    boolean isAdded = transactionDao.addTransaction(transaction);
                    if (isAdded && kt1.getEmail()!=null) {
                        request.setAttribute("id", idHoaDon);
                        request.setAttribute("successMessage", "Thêm giao dịch thành công.");
                        
                        String to = kt1.getEmail();
                        String subject = "Thông báo Giao dịch mới Nhà Trọ TQAT";
                        StringBuilder message = new StringBuilder();
                        message.append("<html>");
                        message.append("<body style='font-family: Arial, sans-serif; font-size: 14px; color: #333;'>");
                        message.append("<h2 style='color: #4CAF50;'>Chúc mừng bạn!</h2>");
                        message.append("<p>Bạn đã chuyển thành công số tiền <strong style='color: #4CAF50;'>").append(amount).append(" VNĐ</strong>.</p>");
                        message.append("<p><strong>Mã Giao dịch:</strong> ").append(maGiaoDich).append("</p>");
                        message.append("<p><strong>Phương thức thanh toán:</strong> ").append(paymentMethod).append("</p>");
                        message.append("<p><strong>Ngày giao dịch:</strong> ").append(sdf.format(transactionDate)).append("</p>");
                        message.append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>");
                        message.append("</body>");
                        message.append("</html>");

                        // Sending the email
                        IJavaMail emailService = new EmailService();
                        emailService.send(to, subject, message.toString());
                        request.getRequestDispatcher("TransactionForm.jsp").forward(request, response);
                    } else {
                        request.setAttribute("id", idHoaDon);
                        request.setAttribute("errorMessage", "Thêm giao dịch không thành công.");
                        request.getRequestDispatcher("TransactionForm.jsp").forward(request, response);
                    }
                }
            }
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
