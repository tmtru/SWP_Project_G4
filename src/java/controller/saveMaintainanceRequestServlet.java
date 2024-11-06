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
}