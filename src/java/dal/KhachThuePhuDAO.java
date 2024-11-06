package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhachThuePhu;

public class KhachThuePhuDAO extends DBContext {

    private static final String INSERT_KHACHTHUEPHU_SQL = "INSERT INTO khach_thue_phu "
            + "(ID_HopDong, Ngay_sinh, CCCD, email, SDT, HK_thuong_tru, "
            + "Ten_khach, Gioi_tinh, isActive) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";

    public boolean insertKhachThuePhu(int idHopDong, String ngaySinh, String cccd, String email,
                                         String sdt, String hkThuongTru,
                                         String tenKhach, int gioiTinh) {

        try (Connection conn = this.connection; // Kế thừa kết nối từ DBContext
             PreparedStatement pstmt = conn.prepareStatement(INSERT_KHACHTHUEPHU_SQL)) {
            
            pstmt.setInt(1, idHopDong);
            pstmt.setDate(2, java.sql.Date.valueOf(ngaySinh));
            pstmt.setString(3, cccd);
            pstmt.setString(4, email);
            pstmt.setString(5, sdt);
            pstmt.setString(6, hkThuongTru);
            pstmt.setString(7, tenKhach);
            pstmt.setInt(8, gioiTinh);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<KhachThuePhu> getKhachThuePhuByHopDongId(int idHopDong) {
        List<KhachThuePhu> khachThuePhuList = new ArrayList<>();
        String query = "SELECT * FROM khach_thue_phu WHERE ID_HopDong = ? AND isActive = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idHopDong);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                KhachThuePhu khachThuePhu = new KhachThuePhu();
                khachThuePhu.setIdKhachThuePhu(rs.getInt("ID_KhachThuePhu"));
                khachThuePhu.setIdHopDong(rs.getInt("ID_HopDong"));
                khachThuePhu.setNgaySinh(rs.getDate("Ngay_sinh"));
                khachThuePhu.setCccd(rs.getString("CCCD"));
                khachThuePhu.setEmail(rs.getString("email"));
                khachThuePhu.setSdt(rs.getString("SDT"));
                khachThuePhu.setHkThuongTru(rs.getString("HK_thuong_tru"));
                khachThuePhu.setTenKhach(rs.getString("Ten_khach"));
                khachThuePhu.setGioiTinh(rs.getInt("Gioi_tinh"));
                khachThuePhu.setIsActive(rs.getInt("isActive"));

                khachThuePhuList.add(khachThuePhu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachThuePhuList;
    }
    
    public List<KhachThuePhu> getKhachThuePhuByKhachThueId(int khachThueId) {
        List<KhachThuePhu> khachThuePhuList = new ArrayList<>();
        
        String query = """
                SELECT ktp.ID_KhachThuePhu, ktp.ID_HopDong, ktp.Ngay_sinh, ktp.CCCD, 
                       ktp.email, ktp.SDT, ktp.HK_thuong_tru, ktp.Ten_khach, 
                       ktp.Gioi_tinh, ktp.isActive
                FROM khach_thue_phu ktp
                JOIN hop_dong hd ON ktp.ID_HopDong = hd.ID_HopDong
                WHERE hd.ID_KhachThue = ?;
                """;
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setInt(1, khachThueId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                KhachThuePhu khachThuePhu = new KhachThuePhu();
                khachThuePhu.setIdKhachThuePhu(resultSet.getInt("ID_KhachThuePhu"));
                khachThuePhu.setIdHopDong(resultSet.getInt("ID_HopDong"));
                khachThuePhu.setNgaySinh(resultSet.getDate("Ngay_sinh"));
                khachThuePhu.setCccd(resultSet.getString("CCCD"));
                khachThuePhu.setEmail(resultSet.getString("email"));
                khachThuePhu.setSdt(resultSet.getString("SDT"));
                khachThuePhu.setHkThuongTru(resultSet.getString("HK_thuong_tru"));
                khachThuePhu.setTenKhach(resultSet.getString("Ten_khach"));
                khachThuePhu.setGioiTinh(resultSet.getInt("Gioi_tinh"));
                khachThuePhu.setIsActive(resultSet.getInt("isActive"));
                
                khachThuePhuList.add(khachThuePhu);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return khachThuePhuList;
    }
}
