package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HopDong;

public class HopDongDAO extends DBContext{
    public List<HopDong> getHopDongsByKhachThueID(int ID_KhachThue) {
        List<HopDong> hopDongs = new ArrayList<>();
        String sql = "SELECT * FROM hop_dong WHERE ID_KhachThue = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
             
            ps.setInt(1, ID_KhachThue);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                HopDong hopDong = new HopDong();
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                hopDong.setID_Phongtro(rs.getInt("ID_Phongtro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_Coc"));
                
                hopDongs.add(hopDong);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Xử lý lỗi
        }
        
        return hopDongs;
    }
    
    public static void main(String[] args) {

            HopDongDAO hopDongDAO = new HopDongDAO();
            List<HopDong> hopDongs = hopDongDAO.getHopDongsByKhachThueID(2); // ID_KhachThue = 2

            // In ra các hợp đồng lấy được
            if (hopDongs.isEmpty()) {
                System.out.println("Không có hợp đồng nào.");
            } else {
                for (HopDong hopDong : hopDongs) {
                    System.out.println(hopDong);
                }
            }
    }
}
