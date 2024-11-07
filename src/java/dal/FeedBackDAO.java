package dal;

import model.FeedBack;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedBackDAO extends DBContext {

    public boolean addFeedBack(FeedBack feedback) {
        String sql = "INSERT INTO feedback (ID_KhachThue, ID_Phong, Noi_dung, Danh_gia) VALUES ( ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set parameters for the PreparedStatement
           
            ps.setInt(1, feedback.getID_khachthue());
            ps.setInt(2, feedback.getID_phongtro());
            ps.setString(3, feedback.getNoi_dung());
            ps.setInt(4, feedback.getDanh_gia());

            // Execute the insert and return true if successful
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the SQL exception
            return false; // Return false if an error occurred
        }
    }
  
     public static void main(String[] args) {
        // Khởi tạo FeedbackDAO
        FeedBackDAO feedbackDAO = new FeedBackDAO();
        
        // Tạo đối tượng FeedBack với dữ liệu test
        int idKhachThue = 2; // Giả định ID Khách Thuê là 1
        int idPhong = 5; // Giả định ID Phòng Trọ là 1
        String noiDung = "Phòng rất sạch sẽ và thoải mái!";
        int danhGia = 5;

        FeedBack feedback = new FeedBack(idKhachThue, idPhong, noiDung, danhGia);
        
        // Gọi phương thức addFeedBack để kiểm tra
        boolean result = feedbackDAO.addFeedBack(feedback);
        
        // In kết quả
        if (result) {
            System.out.println("Feedback added successfully!");
        } else {
            System.out.println("Failed to add feedback.");
        }
    }
}
