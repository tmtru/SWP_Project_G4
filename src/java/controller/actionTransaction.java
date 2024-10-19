/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.HoaDonDAO;
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

        if ("add".equals(action)) {
            String maGiaoDich = request.getParameter("maGiaoDich");
            float amount = Float.parseFloat(request.getParameter("amount"));
            String paymentMethod = request.getParameter("paymentMethod");
            int idHoaDon = Integer.parseInt(request.getParameter("idHoaDon"));
            String transactionDateStr = request.getParameter("transactionDate");
            String mota = request.getParameter("moTa");
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

                    boolean isAdded = transactionDao.addTransaction(transaction);
                    if (isAdded) {
                        request.setAttribute("id", idHoaDon);
                        request.setAttribute("successMessage", "Thêm giao dịch thành công.");
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
