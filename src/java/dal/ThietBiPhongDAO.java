/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ThietBiPhong;
import java.sql.SQLException;

/**
 *
 * @author hihihihaha
 */
public class ThietBiPhongDAO extends DBContext {

    //Lay tat ca cac thiet bi
    public List<ThietBiPhong> getAllThietBiPhong() {
        List<ThietBiPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM thiet_bi_phong";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThietBiPhong tbp = new ThietBiPhong();
                tbp.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                tbp.setTrang_thai(rs.getString("Trang_thai"));
                tbp.setMo_ta(rs.getString("Mo_ta"));
                tbp.setSo_luong(rs.getString("So_luong"));
                tbp.setID_Phong(rs.getInt("ID_Phong"));
                tbp.setID_ThietBi(rs.getInt("ID_ThietBi"));
                list.add(tbp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThietBiPhong> getThietBiByPhongId(int idPhong) {
        List<ThietBiPhong> list = new ArrayList<>();
        String sql = "SELECT tbp.*, tb.TenThietBi FROM THIET_BI_PHONG tbp "
                + "JOIN THIET_BI tb ON tbp.ID_ThietBi = tb.ID_ThietBi "
                + "WHERE tbp.ID_Phong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThietBiPhong tbp = new ThietBiPhong();
                    tbp.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                    tbp.setTrang_thai(rs.getString("Trang_thai"));
                    tbp.setMo_ta(rs.getString("Mo_ta"));
                    tbp.setSo_luong(rs.getString("So_luong"));
                    tbp.setID_Phong(rs.getInt("ID_Phong"));
                    tbp.setID_ThietBi(rs.getInt("ID_ThietBi"));
                    tbp.setTenThietBi(rs.getString("TenThietBi")); // Assuming you add this field to ThietBiPhong
                    list.add(tbp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addThietBiToPhong(int idPhong, int idThietBi, String soLuong, String trangThai, String moTa) throws SQLException {
        String sql = "INSERT INTO THIET_BI_PHONG (ID_Phong, ID_ThietBi, So_luong, Trang_thai, Mo_ta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            ps.setInt(2, idThietBi);
            ps.setString(3, soLuong);
            ps.setString(4, trangThai);
            ps.setString(5, moTa);
            ps.executeUpdate();
        }
    }

    public void deleteThietBiFromPhong(int idThietBiPhong) throws SQLException {
        String sql = "DELETE FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idThietBiPhong);
            ps.executeUpdate();
        }
    }

    public void updateThietBiInPhong(int idThietBiPhong, String soLuong, String trangThai, String moTa) throws SQLException {
        String sql = "UPDATE THIET_BI_PHONG SET So_luong = ?, Trang_thai = ?, Mo_ta = ? WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, soLuong);
            ps.setString(2, trangThai);
            ps.setString(3, moTa);
            ps.setInt(4, idThietBiPhong);
            ps.executeUpdate();
        }
    }
    
}
