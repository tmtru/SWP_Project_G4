package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Maintainance;
import java.sql.ResultSet;

public class MaintainanceDAO extends DBContext {

    public void addMaintainanceRequest(String moTa, int idThietBiPhong, int idPhong) {
        String sql = "INSERT INTO bao_tri (mo_ta, trang_thai_chap_thuan, trang_thai_yeu_cau, thoi_gian, ID_ThietBiPhong, ID_Phong) "
                + "VALUES (?, 0, 0, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, moTa);
            stmt.setDate(2, Date.valueOf(LocalDate.now())); // Lưu ngày hiện tại
            stmt.setInt(3, idThietBiPhong);
            stmt.setInt(4, idPhong);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMaintainanceRequest(int id, String newDescription) {
        String query = "UPDATE bao_tri SET mo_ta = ? WHERE id_bao_tri = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newDescription);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelMaintainanceRequest(int id) {
        String query = "UPDATE bao_tri SET trang_thai_yeu_cau = 2 WHERE id_bao_tri = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {;
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readMaintainanceRequest(int id) {
        String query = "UPDATE bao_tri SET trang_thai_yeu_cau = 1 WHERE id_bao_tri = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {;
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void approveMaintainanceRequest(int id) {
        String query = "UPDATE bao_tri SET trang_thai_chap_thuan = 1 WHERE id_bao_tri = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {;
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void declineMaintainanceRequest(int id) {
        String query = "UPDATE bao_tri SET trang_thai_chap_thuan = 2 WHERE id_bao_tri = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {;
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Maintainance> getMaintainanceRequests() {
        List<Maintainance> maintainanceList = new ArrayList<>();
        String sql = "SELECT * FROM bao_tri";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Maintainance maintainance = new Maintainance();
                maintainance.setId_bao_tri(rs.getInt("id_bao_tri"));
                maintainance.setMo_ta(rs.getString("mo_ta"));
                maintainance.setTrang_thai_chap_thuan(rs.getByte("trang_thai_chap_thuan"));
                maintainance.setTrang_thai_yeu_cau(rs.getByte("trang_thai_yeu_cau"));
                maintainance.setThoi_gian(rs.getDate("thoi_gian"));
                maintainance.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                maintainance.setID_Phong(rs.getInt("ID_Phong"));

                maintainanceList.add(maintainance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintainanceList;
    }

    public Maintainance getMaintainanceById(int id_bao_tri) {
        Maintainance maintainance = new Maintainance();
        String sql = "SELECT * FROM bao_tri WHERE id_bao_tri = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_bao_tri);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    maintainance.setId_bao_tri(rs.getInt("id_bao_tri"));
                    maintainance.setMo_ta(rs.getString("mo_ta"));
                    maintainance.setTrang_thai_chap_thuan(rs.getByte("trang_thai_chap_thuan"));
                    maintainance.setTrang_thai_yeu_cau(rs.getByte("trang_thai_yeu_cau"));
                    maintainance.setThoi_gian(rs.getDate("thoi_gian"));
                    maintainance.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                    maintainance.setID_Phong(rs.getInt("ID_Phong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintainance;
    }

    public Maintainance getMaintainanceByIdThietBiPhong(int id_thiet_bi_phong) {
        Maintainance maintainance = new Maintainance();
        String sql = "SELECT * FROM bao_tri WHERE ID_ThietBiPhong = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_thiet_bi_phong);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    maintainance.setId_bao_tri(rs.getInt("id_bao_tri"));
                    maintainance.setMo_ta(rs.getString("mo_ta"));
                    maintainance.setTrang_thai_chap_thuan(rs.getByte("trang_thai_chap_thuan"));
                    maintainance.setTrang_thai_yeu_cau(rs.getByte("trang_thai_yeu_cau"));
                    maintainance.setThoi_gian(rs.getDate("thoi_gian"));
                    maintainance.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                    maintainance.setID_Phong(rs.getInt("ID_Phong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maintainance;
    }

    public static void main(String[] args) {
        MaintainanceDAO mtDAO = new MaintainanceDAO();

        System.out.println(mtDAO.getMaintainanceById(1));
    }

}
