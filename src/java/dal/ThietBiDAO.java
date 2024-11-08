//he
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
import model.Phong;
import model.Maintainance;

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
        String checkSql = "SELECT tb.So_luong, COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "WHERE tb.ID_ThietBi = ? "
                + "GROUP BY tb.So_luong";
        String updateSql = "UPDATE thiet_bi SET So_luong = ? WHERE ID_ThietBi = ?";
        connection.setAutoCommit(false);
        try {
            // Check current available quantity
            try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
                checkPs.setInt(1, idThietBi);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int totalQuantity = rs.getInt("So_luong");
                        int quantityAdded = rs.getInt("so_luong_da_them");
                        int currentQuantity = totalQuantity - quantityAdded;
                        if (currentQuantity < requestedQuantity) {
                            return false; // Not enough quantity
                        }
                        // Update the total quantity
                        int newQuantity = totalQuantity - requestedQuantity;
                        try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                            updatePs.setInt(1, newQuantity);
                            updatePs.setInt(2, idThietBi);
                            updatePs.executeUpdate();
                        }
                    } else {
                        return false; // ThietBi not found
                    }
                }
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

    // Lấy tất cả các thiết bị với phân trang
    public List<ThietBi> getAllThietBiWithDetailsPaging(String searchTerm, int page, int recordsPerPage) {
        List<ThietBi> list = new ArrayList<>();
        int start = (page - 1) * recordsPerPage;

        // Chuẩn hóa chuỗi tìm kiếm bằng cách loại bỏ khoảng trắng
        String normalizedSearch = searchTerm.replaceAll("\\s+", "");

        String sql = "SELECT tb.*, "
                + "COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them, "
                + "tb.So_luong - COALESCE(SUM(tbp.So_luong), 0) as so_luong_con_lai "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "WHERE REPLACE(LOWER(tb.TenThietBi), ' ', '') LIKE LOWER(?) "
                + "GROUP BY tb.ID_ThietBi, tb.TenThietBi, tb.Gia_tien, tb.Mo_ta, tb.So_luong "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + normalizedSearch + "%");
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, start);

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
        String sql = "SELECT * "
                + "FROM khach_thue kt "
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN thiet_bi_phong tbp ON pt.ID_Phong = tbp.ID_Phong "
                + "JOIN thiet_bi tb ON tbp.ID_ThietBi = tb.ID_ThietBi "
                + "WHERE kt.ID_KhachThue = ?";

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
        String sql = "SELECT tb.* FROM thiet_bi tb "
                + "JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "WHERE tbp.ID_Phong = ?";

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

    public boolean checkDuplicateThietBi(String tenThietBi) {
        boolean duplicate = false;
        String sql = "SELECT COUNT(*) as count_duplicate FROM thiet_bi WHERE TenThietBi = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tenThietBi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count_duplicate");
                    duplicate = (count > 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return duplicate;
    }

    public List<ThietBi> getThietBiCanSuaByNhaTro(int nhatroId, String search, Integer start, Integer recordPerPage) {
        List<ThietBi> list = new ArrayList<>();
        String sql = "select tb.*, pt.TenPhongTro, tbp.So_luong as sl, tbp.ID_ThietBiPhong, "
                + "nt.TenNhaTro, bt.thoi_gian, bt.mo_ta as mo_ta_bao_tri "
                + "from phong_tro pt "
                + "left join thiet_bi_phong tbp on pt.ID_Phong = tbp.ID_Phong "
                + "left join thiet_bi tb on tb.ID_ThietBi = tbp.ID_ThietBi "
                + "left join nha_tro nt on nt.ID_NhaTro = pt.ID_NhaTro "
                + "left join bao_tri bt on bt.ID_ThietBiPhong = tbp.ID_ThietBiPhong "
                + "where pt.ID_NhaTro = ? and tbp.Trang_thai = 'CSC' and (? = '' or tb.TenThietBi like ?) and bt.trang_thai_yeu_cau = 0";
        if (start != null && recordPerPage != null) {
            sql += " LIMIT ?, ?";
        }
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, nhatroId);
            stmt.setString(2, search);
            stmt.setString(3, "%" + search + "%");
            if (start != null && recordPerPage != null) {
                stmt.setInt(4, start);
                stmt.setInt(5, recordPerPage);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("sl"));
                tb.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                tb.setTenNhaTro(rs.getString("TenNhaTro"));
                // Thêm thông tin từ bảng bao_tri
                tb.setThoiGianBaoTri(rs.getDate("thoi_gian"));
                tb.setMoTaBaoTri(rs.getString("mo_ta_bao_tri"));

                Phong phong = new Phong();
                phong.setTenPhongTro(rs.getString("TenPhongTro"));
                tb.setPhong(phong);
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThietBi> getAllThietBiCanSua() {
        List<ThietBi> list = new ArrayList<>();
        String sql = "select tb.*, pt.TenPhongTro, tbp.So_luong as sl, tbp.ID_ThietBiPhong, "
                + "nt.TenNhaTro, bt.thoi_gian, bt.mo_ta as mo_ta_bao_tri "
                + "from phong_tro pt "
                + "left join thiet_bi_phong tbp on pt.ID_Phong = tbp.ID_Phong "
                + "left join thiet_bi tb on tb.ID_ThietBi = tbp.ID_ThietBi "
                + "left join nha_tro nt on nt.ID_NhaTro = pt.ID_NhaTro "
                + "left join bao_tri bt on bt.ID_ThietBiPhong = tbp.ID_ThietBiPhong "
                + "where tbp.Trang_thai = 'CSC'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThietBi tb = new ThietBi();

                tb.setID_ThietBi(rs.getInt("ID_ThietBi"));
                tb.setTenThietBi(rs.getString("TenThietBi"));
                tb.setGia_tien(rs.getInt("Gia_tien"));
                tb.setMo_ta(rs.getString("Mo_ta"));
                tb.setSo_luong(rs.getString("sl"));
                tb.setID_ThietBiPhong(rs.getInt("ID_ThietBiPhong"));
                tb.setTenNhaTro(rs.getString("TenNhaTro"));

                // Thêm thông tin từ bảng bao_tri
                tb.setThoiGianBaoTri(rs.getDate("thoi_gian"));
                tb.setMoTaBaoTri(rs.getString("mo_ta_bao_tri"));

                Phong phong = new Phong();
                phong.setTenPhongTro(rs.getString("TenPhongTro"));
                tb.setPhong(phong);
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThietBi> searchThietBiWithoutSpaces(String searchTerm, int page, int recordsPerPage) {
        List<ThietBi> list = new ArrayList<>();
        int start = (page - 1) * recordsPerPage;

        // Remove all spaces from the search term
        String normalizedSearch = searchTerm.replaceAll("\\s+", "");

        String sql = "SELECT tb.*, "
                + "COALESCE(SUM(tbp.So_luong), 0) as so_luong_da_them, "
                + "tb.So_luong - COALESCE(SUM(tbp.So_luong), 0) as so_luong_con_lai "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "WHERE REPLACE(LOWER(tb.TenThietBi), ' ', '') LIKE LOWER(?) "
                + "GROUP BY tb.ID_ThietBi, tb.TenThietBi, tb.Gia_tien, tb.Mo_ta, tb.So_luong "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + normalizedSearch + "%");
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, start);

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

    public int getTotalThietBiConLai() {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT tb.ID_ThietBi, "
                + "tb.So_luong - COALESCE(SUM(tbp.So_luong), 0) as so_luong_con_lai "
                + "FROM thiet_bi tb "
                + "LEFT JOIN thiet_bi_phong tbp ON tb.ID_ThietBi = tbp.ID_ThietBi "
                + "GROUP BY tb.ID_ThietBi, tb.So_luong "
                + "HAVING so_luong_con_lai > 0"
                + ") as subquery";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalThietBiBySearch(String searchTerm) {
        // Remove all spaces from the search term
        String normalizedSearch = searchTerm.replaceAll("\\s+", "");

        String sql = "SELECT COUNT(*) FROM thiet_bi "
                + "WHERE REPLACE(LOWER(TenThietBi), ' ', '') LIKE LOWER(?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + normalizedSearch + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getEquipmentCountByStatus(int nhaTroId, String status) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM thiet_bi_phong "
                + "WHERE ID_Phong IN (SELECT ID_Phong FROM phong_tro WHERE ID_NhaTro = ?) "
                + "AND Trang_thai = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nhaTroId);
            stmt.setString(2, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }

    public int getTotalEquipmentCount(int nhaTroId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM thiet_bi_phong "
                + "WHERE ID_Phong IN (SELECT ID_Phong FROM phong_tro WHERE ID_NhaTro = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nhaTroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }

    // Lấy tổng số thiết bị dựa trên chuỗi tìm kiếm
    public int getTotalThietBi(String searchTerm) {
        // Chuẩn hóa chuỗi tìm kiếm bằng cách loại bỏ khoảng trắng
        String normalizedSearch = searchTerm.replaceAll("\\s+", "");

        String sql = "SELECT COUNT(*) FROM thiet_bi "
                + "WHERE REPLACE(LOWER(TenThietBi), ' ', '') LIKE LOWER(?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + normalizedSearch + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
