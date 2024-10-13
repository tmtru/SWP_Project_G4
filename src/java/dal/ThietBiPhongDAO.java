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

    public boolean addThietBiToPhong(int idPhong, int idThietBi, int soLuong, String trangThai, String moTa) throws SQLException {
        ThietBiDAO thietBiDAO = new ThietBiDAO();

        // Check and update quantity in ThietBi table
        if (!thietBiDAO.checkAndUpdateQuantity(idThietBi, soLuong)) {
            return false; // Not enough quantity or ThietBi not found
        }

        // If quantity check passed, proceed with adding to THIET_BI_PHONG
        String sql = "INSERT INTO THIET_BI_PHONG (ID_Phong, ID_ThietBi, So_luong, Trang_thai, Mo_ta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            ps.setInt(2, idThietBi);
            ps.setInt(3, soLuong);
            ps.setString(4, trangThai);
            ps.setString(5, moTa);
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteThietBiFromPhong(int idThietBiPhong) throws SQLException {
        // Get the equipment details
        String getDetailsSql = "SELECT ID_ThietBi, So_luong FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(getDetailsSql)) {
            ps.setInt(1, idThietBiPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idThietBi = rs.getInt("ID_ThietBi");
                    int soLuong = rs.getInt("So_luong");

                    // Update the quantity in THIET_BI table
                    String updateThietBiSql = "UPDATE THIET_BI SET So_luong = So_luong + ? WHERE ID_ThietBi = ?";
                    try (PreparedStatement updatePs = connection.prepareStatement(updateThietBiSql)) {
                        updatePs.setInt(1, soLuong);
                        updatePs.setInt(2, idThietBi);
                        updatePs.executeUpdate();
                    }

                    // Delete from THIET_BI_PHONG
                    String deleteThietBiPhongSql = "DELETE FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
                    try (PreparedStatement deletePs = connection.prepareStatement(deleteThietBiPhongSql)) {
                        deletePs.setInt(1, idThietBiPhong);
                        deletePs.executeUpdate();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateThietBiInPhong(int idThietBiPhong, int newSoLuong, String trangThai, String moTa) throws SQLException {
        // Get current details
        String getDetailsSql = "SELECT ID_ThietBi, So_luong FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(getDetailsSql)) {
            ps.setInt(1, idThietBiPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idThietBi = rs.getInt("ID_ThietBi");
                    int currentSoLuong = rs.getInt("So_luong");
                    int difference = newSoLuong - currentSoLuong;

                    // Check if there's enough quantity in THIET_BI
                    String checkQuantitySql = "SELECT So_luong FROM THIET_BI WHERE ID_ThietBi = ?";
                    try (PreparedStatement checkPs = connection.prepareStatement(checkQuantitySql)) {
                        checkPs.setInt(1, idThietBi);
                        try (ResultSet checkRs = checkPs.executeQuery()) {
                            if (checkRs.next()) {
                                int availableQuantity = checkRs.getInt("So_luong");
                                if (availableQuantity < difference) {
                                    return false; // Not enough quantity
                                }

                                // Update THIET_BI quantity
                                String updateThietBiSql = "UPDATE THIET_BI SET So_luong = So_luong - ? WHERE ID_ThietBi = ?";
                                try (PreparedStatement updateThietBiPs = connection.prepareStatement(updateThietBiSql)) {
                                    updateThietBiPs.setInt(1, difference);
                                    updateThietBiPs.setInt(2, idThietBi);
                                    updateThietBiPs.executeUpdate();
                                }

                                // Update THIET_BI_PHONG
                                String updateThietBiPhongSql = "UPDATE THIET_BI_PHONG SET So_luong = ?, Trang_thai = ?, Mo_ta = ? WHERE ID_ThietBiPhong = ?";
                                try (PreparedStatement updateThietBiPhongPs = connection.prepareStatement(updateThietBiPhongSql)) {
                                    updateThietBiPhongPs.setInt(1, newSoLuong);
                                    updateThietBiPhongPs.setString(2, trangThai);
                                    updateThietBiPhongPs.setString(3, moTa);
                                    updateThietBiPhongPs.setInt(4, idThietBiPhong);
                                    updateThietBiPhongPs.executeUpdate();
                                }

                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
