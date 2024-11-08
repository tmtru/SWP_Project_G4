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

        // Check if the equipment is already assigned to the room
        String checkExistenceSql = "SELECT COUNT(*) FROM THIET_BI_PHONG WHERE ID_Phong = ? AND ID_ThietBi = ?";
        try (PreparedStatement checkExistencePs = connection.prepareStatement(checkExistenceSql)) {
            checkExistencePs.setInt(1, idPhong);
            checkExistencePs.setInt(2, idThietBi);
            try (ResultSet rs = checkExistencePs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Thiết bị đã tồn tại trong phòng.");
                }
            }
        }

        // Check the current total quantity and remaining quantity
        String checkSql = "SELECT tb.So_luong, COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "WHERE tb.ID_ThietBi = ? "
                + "GROUP BY tb.So_luong";
        try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
            checkPs.setInt(1, idThietBi);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    int totalQuantity = rs.getInt("So_luong");
                    int quantityAdded = rs.getInt("so_luong_da_them");
                    int currentQuantity = totalQuantity - quantityAdded;
                    if (currentQuantity < soLuong) {
                        return false; // Not enough quantity
                    }
                } else {
                    return false; // ThietBi not found
                }
            }
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

    public boolean updateThietBiInPhong(int idThietBiPhong, int soLuong, String trangThai, String moTa) throws SQLException {
        // Check if the equipment is already assigned to the room
        String checkExistenceSql = "SELECT COUNT(*) FROM THIET_BI_PHONG WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement checkExistencePs = connection.prepareStatement(checkExistenceSql)) {
            checkExistencePs.setInt(1, idThietBiPhong);
            try (ResultSet rs = checkExistencePs.executeQuery()) {
                if (!rs.next() || rs.getInt(1) == 0) {
                    return false; // Equipment not found
                }
            }
        }

        // Check the current total quantity and remaining quantity
        String checkSql = "SELECT tbp.ID_ThietBi, tb.So_luong, COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them "
                + "FROM thiet_bi_phong tbp "
                + "JOIN thiet_bi tb ON tbp.ID_ThietBi = tb.ID_ThietBi "
                + "WHERE tbp.ID_ThietBiPhong = ? "
                + "GROUP BY tbp.ID_ThietBi, tb.So_luong";
        try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
            checkPs.setInt(1, idThietBiPhong);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    int idThietBi = rs.getInt("ID_ThietBi");
                    int totalQuantity = rs.getInt("So_luong");
                    int quantityAdded = rs.getInt("so_luong_da_them");
                    int currentQuantity = totalQuantity - quantityAdded;
                    if (currentQuantity < soLuong) {
                        return false; // Not enough quantity
                    }
                } else {
                    return false; // Equipment not found
                }
            }
        }

        // If quantity check passed, proceed with updating THIET_BI_PHONG
        String sql = "UPDATE THIET_BI_PHONG SET So_luong = ?, Trang_thai = ?, Mo_ta = ? WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setString(2, trangThai);
            ps.setString(3, moTa);
            ps.setInt(4, idThietBiPhong);
            ps.executeUpdate();
        }

        return true;
    }

    public ThietBiPhong getThietBiPhongById(int id) {
        ThietBiPhong device = null;
        String sql = "SELECT tbp.ID_ThietBiPhong, tb.TenThietBi, tbp.Mo_ta, tbp.Trang_thai, tbp.ID_Phong "
                + "FROM thiet_bi_phong tbp "
                + "JOIN thiet_bi tb ON tbp.ID_ThietBi = tb.ID_ThietBi "
                + "WHERE tbp.ID_ThietBiPhong = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                device = new ThietBiPhong();
                device.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                device.setTenThietBi(rs.getString("TenThietBi"));
                device.setMo_ta(rs.getString("Mo_ta"));
                device.setTrang_thai(rs.getString("Trang_thai"));
                device.setID_Phong(rs.getInt("ID_Phong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return device;
    }

    public void updateTrangThaiCSCByIdThietBiPhong(int ID_ThietBiPhong) {
        String sql = "UPDATE thiet_bi_phong SET Trang_thai = 'CSC' WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ID_ThietBiPhong);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTrangThaiTByIdThietBiPhong(int ID_ThietBiPhong) {
        String sql = "UPDATE thiet_bi_phong SET Trang_thai = 'T' WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ID_ThietBiPhong);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDuplicateThietBiInRoom(int idPhong, int idThietBi) {
        String sql = "SELECT COUNT(*) as count FROM thiet_bi_phong tbp "
                + "WHERE tbp.ID_Phong = ? AND tbp.ID_ThietBi = ?";

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

    public boolean updateStatusThietBiToPhong(int id) throws SQLException {
        // Update device status in thiet_bi_phong table
        String updateDeviceSQL = "UPDATE thiet_bi_phong SET Trang_thai = 'T' WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement devicePS = connection.prepareStatement(updateDeviceSQL)) {
            devicePS.setInt(1, id);
            devicePS.executeUpdate();
        }

        // Update maintenance request status in bao_tri table
        String updateMaintenanceSQL = "UPDATE bao_tri SET trang_thai_yeu_cau = 1, trang_thai_chap_thuan = 1 WHERE ID_ThietBiPhong = ? and trang_thai_yeu_cau = 0" ;
        try (PreparedStatement maintenancePS = connection.prepareStatement(updateMaintenanceSQL)) {
            maintenancePS.setInt(1, id);
            maintenancePS.executeUpdate();
        }
        return true;
    }

    public boolean updateStatusThietBiToPhongTuchoi(int id) throws SQLException {
        // Update device status in thiet_bi_phong table
        String updateDeviceSQL = "UPDATE thiet_bi_phong SET Trang_thai = 'BT' WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement devicePS = connection.prepareStatement(updateDeviceSQL)) {
            devicePS.setInt(1, id);
            devicePS.executeUpdate();
        }

        // Update maintenance request status in bao_tri table
        String updateMaintenanceSQL = "UPDATE bao_tri SET trang_thai_yeu_cau = 2, trang_thai_chap_thuan = 2 WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement maintenancePS = connection.prepareStatement(updateMaintenanceSQL)) {
            maintenancePS.setInt(1, id);
            maintenancePS.executeUpdate();
        }
        return true;
    }

    public boolean updateStatusThietBiToPhongChapNhan(int id) throws SQLException {

        // Update maintenance request status in bao_tri table
        String updateMaintenanceSQL = "UPDATE bao_tri SET trang_thai_yeu_cau = 1 WHERE ID_ThietBiPhong = ?";
        try (PreparedStatement maintenancePS = connection.prepareStatement(updateMaintenanceSQL)) {
            maintenancePS.setInt(1, id);
            maintenancePS.executeUpdate();
        }
        return true;
    }

    public static void main(String[] arg) {
        ThietBiPhongDAO tbpd = new ThietBiPhongDAO();

        List<ThietBiPhong> list = tbpd.getAllThietBiPhong();

        for (ThietBiPhong tbp : list) {
            System.out.println(tbp);
        }
    }
}
