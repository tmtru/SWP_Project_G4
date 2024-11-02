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
}
