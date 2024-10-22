package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DichVu;

public class DichVuDAO extends DBContext {

    // Phương thức lấy tất cả các dịch vụ
    public List<DichVu> getAll() {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM DICH_VU";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setID_DichVu(rs.getInt("ID_DichVu"));
                dv.setTenDichVu(rs.getString("TenDichVu"));
                dv.setDon_gia(rs.getInt("Don_gia"));
                dv.setDon_vi(rs.getString("Don_vi"));
                dv.setMo_ta(rs.getString("Mo_ta"));
                dv.setIsActive(rs.getBoolean("isActive")); // Lấy thông tin từ cột isActive
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public DichVu getDichVuById(int idDichVu) {
        DichVu dv = null; // Khởi tạo đối tượng DichVu
        String sql = "SELECT * FROM DICH_VU WHERE ID_DichVu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDichVu); // Gán giá trị cho tham số trong câu truy vấn
            ResultSet rs = ps.executeQuery(); // Thực thi truy vấn
            if (rs.next()) { // Nếu có kết quả
                dv = new DichVu();
                dv.setID_DichVu(rs.getInt("ID_DichVu"));
                dv.setTenDichVu(rs.getString("TenDichVu"));
                dv.setDon_gia(rs.getInt("Don_gia"));
                dv.setDon_vi(rs.getString("Don_vi"));
                dv.setMo_ta(rs.getString("Mo_ta"));
                dv.setIsActive(rs.getBoolean("isActive")); // Lấy thông tin từ cột isActive
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dv; // Trả về dịch vụ hoặc null nếu không tìm thấy
    }

    // Phương thức cập nhật dịch vụ
    public void updateDichVu(DichVu d) {
        String sql = "UPDATE DICH_VU SET TenDichVu = ?, Don_gia = ?, Don_vi = ?, Mo_ta = ?, isActive = ? WHERE ID_DichVu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, d.getTenDichVu());
            ps.setInt(2, d.getDon_gia());
            ps.setString(3, d.getDon_vi());
            ps.setString(4, d.getMo_ta());
            ps.setBoolean(5, d.getIsActive()); // Cập nhật isActive
            ps.setInt(6, d.getID_DichVu());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa dịch vụ theo ID
    public void deactivateDichVu(int idDichVu) {
        String sql = "UPDATE DICH_VU SET isActive = 0 WHERE ID_DichVu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDichVu);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateDichVu(int idDichVu) {
        String sql = "UPDATE DICH_VU SET isActive = 1 WHERE ID_DichVu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDichVu);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm mới một dịch vụ
    public void addDichVu(DichVu d) {
        String sql = "INSERT INTO DICH_VU (TenDichVu, Don_gia, Don_vi, Mo_ta, isActive) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, d.getTenDichVu());
            ps.setInt(2, d.getDon_gia());
            ps.setString(3, d.getDon_vi());
            ps.setString(4, d.getMo_ta());
            ps.setBoolean(5, d.getIsActive()); // Thêm isActive
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean insertSoLuongNguoi(int idHopDong, int soLuong) {
        String sql = "UPDATE hop_dong SET So_nguoi = ? WHERE ID_HopDong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, idHopDong);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertDichVuHopDong(int idHopDong, int idDichVu) {
        String sql = "INSERT INTO dich_vu_hop_dong (ID_DichVu, ID_HopDong, So_luong) VALUES (?, ?, 1)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDichVu);
            ps.setInt(2, idHopDong);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DichVu> getDichVuByHopDongId(int hopDongId) {
        List<DichVu> dichVuList = new ArrayList<>();
        String query = "SELECT dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, dv.isActive " +
                       "FROM dich_vu dv " +
                       "JOIN dich_vu_hop_dong dvhd ON dv.ID_DichVu = dvhd.ID_DichVu " +
                       "WHERE dvhd.ID_HopDong = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, hopDongId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DichVu dichVu = new DichVu();
                    dichVu.setID_DichVu(rs.getInt("ID_DichVu"));
                    dichVu.setTenDichVu(rs.getString("TenDichVu"));
                    dichVu.setDon_gia(rs.getInt("Don_gia"));
                    dichVu.setDon_vi(rs.getString("Don_vi"));
                    dichVu.setMo_ta(rs.getString("Mo_ta"));
                    dichVu.setIsActive(rs.getBoolean("isActive"));
                    dichVuList.add(dichVu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception
        }

        return dichVuList;
    }
    
    public static void main(String[] args) {
        DichVuDAO dao = new DichVuDAO();
        // Lấy tất cả các dịch vụ
        List<DichVu> dichVuList = dao.getAll();
        System.out.println("Danh sách dịch vụ:");
        for (DichVu dv : dichVuList) {
            System.out.println(dv);
        }

        List<DichVu> dichVuList1 = dao.getAll();

    }
}
