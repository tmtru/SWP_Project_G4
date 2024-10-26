/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.ThietBi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hihihihaha
 */
public class ThietBiDAO extends DBContext {

    //Lay tat ca cac thiet bi
    public List<ThietBi> getAllThietBi() {
        List<ThietBi> list = new ArrayList<>();
        String sql = "SELECT * FROM thiet_bi";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("So_luong"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ThietBi getThietBiById(int idThietBi) {
        ThietBi tb = null;
        String sql = "SELECT * FROM thiet_bi WHERE ID_ThietBi = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idThietBi); // Gán giá trị cho tham số trong câu truy vấn
            ResultSet rs = ps.executeQuery(); // Thực thi truy vấn
            if (rs.next()) { // Nếu có kết quả
                tb = new ThietBi();
                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("So_luong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public void addThietBi(ThietBi tb) throws SQLException {
        String sql = "INSERT INTO thiet_bi (TenThietBi, Gia_tien, Mo_ta, So_luong) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tb.getTenThietBi());
            ps.setInt(2, tb.getGia_tien());
            ps.setString(3, tb.getMo_ta());
            ps.setString(4, tb.getSo_luong());
            ps.executeUpdate();
        }
    }

    public void updateThietBi(ThietBi tb) throws SQLException {
        String sql = "UPDATE thiet_bi SET TenThietBi = ?, Gia_tien = ?, Mo_ta = ?, So_luong = ? WHERE ID_ThietBi = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tb.getTenThietBi());
            ps.setInt(2, tb.getGia_tien());
            ps.setString(3, tb.getMo_ta());
            ps.setString(4, tb.getSo_luong());
            ps.setInt(5, tb.getID_ThietBi());
            ps.executeUpdate();
        }
    }

    public boolean checkAndUpdateQuantity(int idThietBi, int requestedQuantity) throws SQLException {
        String checkSql = "SELECT So_luong FROM thiet_bi WHERE ID_ThietBi = ?";
        String updateSql = "UPDATE thiet_bi SET So_luong = So_luong - ? WHERE ID_ThietBi = ?";

        connection.setAutoCommit(false);
        try {
            // Check current quantity
            try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
                checkPs.setInt(1, idThietBi);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int currentQuantity = Integer.parseInt(rs.getString("So_luong"));
                        if (currentQuantity < requestedQuantity) {
                            return false; // Not enough quantity
                        }
                    } else {
                        return false; // ThietBi not found
                    }
                }
            }

            // Update quantity
            try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                updatePs.setInt(1, requestedQuantity);
                updatePs.setInt(2, idThietBi);
                updatePs.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<ThietBi> getAllThietBiWithDetails() {
        List<ThietBi> list = new ArrayList<>();
        String sql = "SELECT tb.*, "
                + "COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them, "
                + "tb.So_luong - COALESCE(SUM(tbp.So_luong), 0) as so_luong_con_lai "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "GROUP BY tb.ID_ThietBi, tb.TenThietBi, tb.Gia_tien, tb.Mo_ta, tb.So_luong";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("So_luong"));
                tb.setSo_luong_da_them(rs.getInt("so_luong_da_them"));
                tb.setSo_luong_con_lai(rs.getInt("so_luong_con_lai"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Add new method to get total number of records
    public int getTotalThietBi() {
        String sql = "SELECT COUNT(*) FROM thiet_bi";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Modified method to support pagination
    public List<ThietBi> getAllThietBiWithDetailsPaging(int page, int recordsPerPage) {
        List<ThietBi> list = new ArrayList<>();
        int start = (page - 1) * recordsPerPage;
        String sql = "SELECT tb.*, "
                + "COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them, "
                + "tb.So_luong - COALESCE(SUM(tbp.So_luong), 0) as so_luong_con_lai "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "GROUP BY tb.ID_ThietBi, tb.TenThietBi, tb.Gia_tien, tb.Mo_ta, tb.So_luong "
                + "LIMIT ? OFFSET ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, start);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("So_luong"));
                tb.setSo_luong_da_them(rs.getInt("so_luong_da_them"));
                tb.setSo_luong_con_lai(rs.getInt("so_luong_con_lai"));
                list.add(tb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ThietBi> getThietBiByKhachThue(int ID_KhachThue) {
        List<ThietBi> listThietBi = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM khach_thue kt " +
                     "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue " +
                     "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong " +
                     "JOIN thiet_bi_phong tbp ON pt.ID_Phong = tbp.ID_Phong " +
                     "JOIN thiet_bi tb ON tbp.ID_ThietBi = tb.ID_ThietBi " +
                     "WHERE kt.ID_KhachThue = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // Gán giá trị cho tham số
            ps.setInt(1, ID_KhachThue);

            // Thực thi truy vấn và xử lý kết quả
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThietBi thietBi = new ThietBi();
                    thietBi.setID_ThietBi(rs.getInt("ID_ThietBi"));
                    thietBi.setTenThietBi(rs.getString("TenThietBi"));
                    thietBi.setMo_ta(rs.getString("Mo_ta"));
                    
                    listThietBi.add(thietBi);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listThietBi;
    }
    
    public List<ThietBi> getThietBiByIdPhong(int idPhong) {
        List<ThietBi> listThietBi = new ArrayList<>();
        String sql = "SELECT tb.* FROM thiet_bi tb " +
                     "JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi " +
                     "WHERE tbp.ID_Phong = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setInt(1, idPhong); // Gán giá trị cho tham số
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThietBi thietBi = new ThietBi();
                thietBi.setID_ThietBi(rs.getInt("ID_ThietBi"));
                thietBi.setTenThietBi(rs.getString("TenThietBi"));
                thietBi.setMo_ta(rs.getString("Mo_ta"));
                
                listThietBi.add(thietBi);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
        }
        return listThietBi; // Trả về danh sách thiết bị
    }
    
    public static void main(String[] args) {
        ThietBiDAO tb = new ThietBiDAO();
        System.out.println(tb.getThietBiByIdPhong(1));
    }
}
