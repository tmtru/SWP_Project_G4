/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
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
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức cập nhật dịch vụ
    public void updateDichVu(DichVu d) {
        String sql = "UPDATE DICH_VU SET TenDichVu = ?, Don_gia = ?, Don_vi = ?, Mo_ta = ? WHERE ID_DichVu = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, d.getTenDichVu());
            ps.setInt(2, d.getDon_gia());
            ps.setString(3, d.getDon_vi());
            ps.setString(4, d.getMo_ta());
            ps.setInt(5, d.getID_DichVu());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa dịch vụ theo ID
    public void deleteDichVu(int idDichVu) {
        // Xóa các bản ghi liên quan trong bảng HOA_DON_DICH_VU trước
        String deleteHoaDonDichVuSql = "DELETE FROM HOA_DON_DICH_VU WHERE ID_DichVu = ?";
        String deleteDichVuSql = "DELETE FROM DICH_VU WHERE ID_DichVu = ?";

        try (PreparedStatement ps1 = connection.prepareStatement(deleteHoaDonDichVuSql); PreparedStatement ps2 = connection.prepareStatement(deleteDichVuSql)) {

            // Xóa các bản ghi liên quan
            ps1.setInt(1, idDichVu);
            ps1.executeUpdate();

            // Xóa dịch vụ
            ps2.setInt(1, idDichVu);
            ps2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm mới một dịch vụ
    public void addDichVu(DichVu d) {
        String sql = "INSERT INTO DICH_VU (TenDichVu, Don_gia, Don_vi, Mo_ta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, d.getTenDichVu());
            ps.setInt(2, d.getDon_gia());
            ps.setString(3, d.getDon_vi());
            ps.setString(4, d.getMo_ta());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DichVuDAO dao = new DichVuDAO();

        // Thêm một dịch vụ mới
        DichVu dichVuMoi = new DichVu();

        // Lấy tất cả các dịch vụ
        List<DichVu> dichVuList = dao.getAll();
        System.out.println("Danh sách dịch vụ:");
        for (DichVu dv : dichVuList) {
            System.out.println(dv);
        }
//        if (!dichVuList.isEmpty()) {
//            DichVu dichVuCapNhat = dichVuList.get(4);
//            dichVuCapNhat.setTenDichVu("Dịch vụ đã cập nhật");
//            dao.updateDichVu(dichVuCapNhat);
//            System.out.println("Đã cập nhật dịch vụ.");
//        }
        dao.deleteDichVu(1);
                // Lấy tất cả các dịch vụ
        List<DichVu> dichVuList1 = dao.getAll();
        System.out.println("Danh sách dịch vụ:");
        for (DichVu dv : dichVuList1) {
            System.out.println(dv);
        }

    }
}
