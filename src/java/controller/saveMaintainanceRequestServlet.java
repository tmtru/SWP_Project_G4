package controller;

import dal.MaintainanceDAO;
import dal.ThietBiPhongDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class saveMaintainanceRequestServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters
            String moTa = request.getParameter("moTa");
            int idThietBiPhong = Integer.parseInt(request.getParameter("idThietBiPhong"));
            int idPhong = Integer.parseInt(request.getParameter("idPhong"));
            
            // Validate the input description
            if (!validateMoTa(moTa)) {
                request.setAttribute("errorMessage", "Mô tả không hợp lệ. Không được chỉ chứa khoảng trắng và không quá 255 chữ cái.");
                request.getRequestDispatcher("requestMaintenance.jsp").forward(request, response);
                return;
            }

            // Save maintenance request
            MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
            maintainanceDAO.addMaintainanceRequest(moTa, idThietBiPhong, idPhong);
            
            ThietBiPhongDAO tbpd = new ThietBiPhongDAO();
            tbpd.updateTrangThaiCSCByIdThietBiPhong(idThietBiPhong);

            // Redirect back to maintenance page with the selected room
            response.sendRedirect("maintainanceServlet?selectedRoom=" + idPhong);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("maintainanceServlet");
        }
    }

    // Validate the maintenance description
    private boolean validateMoTa(String moTa) {
        // Trim leading and trailing spaces and check if the description is empty
        if (moTa == null || moTa.trim().isEmpty()) {
            return false;
        }

        // Remove multiple spaces between words and count the number of words
        String[] words = moTa.trim().split("\\s+");
        
        // Check if the number of words is less than or equal to 255
        return words.length <= 255;
    }
}
