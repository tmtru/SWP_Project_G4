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

    public boolean checkDuplicateThietBiInRoom(int idPhong, int idThietBi) {
        String sql = "SELECT COUNT(*) as count FROM thiet_bi_phong tbp " +
                    "WHERE tbp.ID_Phong = ? AND tbp.ID_ThietBi = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            ps.setInt(2, idThietBi);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addThietBiToPhong(int idPhong, int idThietBi, int soLuong, String trangThai, String moTa) {
        // First check if the equipment already exists in the room
        if (checkDuplicateThietBiInRoom(idPhong, idThietBi)) {
            throw new IllegalArgumentException("Thiết bị này đã tồn tại trong phòng!");
        }

        // Check if there's enough quantity available
        ThietBiDAO thietBiDAO = new ThietBiDAO();
        try {
            boolean hasEnoughQuantity = thietBiDAO.checkAndUpdateQuantity(idThietBi, soLuong);
            if (!hasEnoughQuantity) {
                return false;
            }

            // If we have enough quantity, proceed with adding to the room
            String sql = "INSERT INTO thiet_bi_phong (ID_Phong, ID_ThietBi, So_luong, Trang_thai, Mo_ta) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, idPhong);
                ps.setInt(2, idThietBi);
                ps.setInt(3, soLuong);
                ps.setString(4, trangThai);
                ps.setString(5, moTa);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean updateThietBiInPhong(int idThietBiPhong, int newSoLuong, String trangThai, String moTa) {
        // Get current details first
        String getDetailsSql = "SELECT ID_ThietBi, So_luong FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(getDetailsSql)) {
            ps.setInt(1, idThietBiPhong);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idThietBi = rs.getInt("ID_ThietBi");
                    int currentSoLuong = rs.getInt("So_luong");
                    int difference = newSoLuong - currentSoLuong;

                    // If we need more items, check if we have enough available
                    if (difference > 0) {
                        ThietBiDAO thietBiDAO = new ThietBiDAO();
                        boolean hasEnoughQuantity = thietBiDAO.checkAndUpdateQuantity(idThietBi, difference);
                        
                        if (!hasEnoughQuantity) {
                            return false; // Not enough quantity available
                        }
                    } else if (difference < 0) {
                        // If we're reducing the quantity, return the difference to inventory
                        String updateThietBiSql = "UPDATE THIET_BI SET So_luong = So_luong + ? WHERE ID_ThietBi = ?";
                        try (PreparedStatement updatePs = connection.prepareStatement(updateThietBiSql)) {
                            updatePs.setInt(1, -difference); // Convert negative difference to positive
                            updatePs.setInt(2, idThietBi);
                            updatePs.executeUpdate();
                        }
                    }

                    // Update the equipment in the room
                    String updateSql = "UPDATE THIET_BI_PHONG SET So_luong = ?, Trang_thai = ?, Mo_ta = ? WHERE ID_ThietBiPhong = ?";
                    try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                        updatePs.setInt(1, newSoLuong);
                        updatePs.setString(2, trangThai);
                        updatePs.setString(3, moTa);
                        updatePs.setInt(4, idThietBiPhong);
                        updatePs.executeUpdate();
                        return true;
                    }
                } else {
                    throw new IllegalArgumentException("Không tìm thấy thiết bị trong phòng!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Lỗi khi cập nhật thiết bị: " + e.getMessage());
        }
    }


    public boolean updateStatusThietBiToPhong(int id) throws SQLException {

        String sql = "UPDATE thiet_bi_phong set Trang_thai = 'BT' where ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        return true;
    }

}
