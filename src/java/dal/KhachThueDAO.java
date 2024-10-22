package dal;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import model.Account;
import model.KhachThue;

public class KhachThueDAO extends DBContext {

    public KhachThue getKhachThueByAccountId(int id) {
        KhachThue khachThue = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from khach_thue where ID_Account = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khachThue = new KhachThue();
                khachThue.setId(rs.getInt("ID_KhachThue"));
                //khachThue.setName(rs.getNString("TenKhachThue"));
                khachThue.setDob(rs.getDate("Ngay_sinh"));
                khachThue.setPhone(rs.getNString("SDT"));
                khachThue.setCccd(rs.getNString("CCCD"));
                khachThue.setJob(rs.getNString("Nghe_nghiep"));
                khachThue.setHk_thuong_tru(rs.getNString("Hk_thuong_tru"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                khachThue.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachThue;
    }

    public List<KhachThue> getKhachThueByNhaTro(int id) {
        KhachThue khachThue = null;
        List<KhachThue> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        String sql = "SELECT \n"
                + "   \n"
                + "   \n"
                + "   khach_thue.*,\n"
                + "    phong_tro.TenPhongTro AS RoomName,\n"
                + "    nha_tro.TenNhaTro AS BuildingName\n"
                + "FROM phong_tro\n"
                + "\n"
                + "JOIN nha_tro ON phong_tro.ID_NhaTro = nha_tro.ID_NhaTro\n"
                + "\n"
                + "\n"
                + " JOIN hop_dong ON hop_dong.ID_PhongTro = phong_tro.ID_Phong\n"
                + " JOIN khach_thue ON khach_thue.ID_KhachThue = hop_dong.ID_KhachThue\n"
                + "WHERE nha_tro.ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                khachThue = new KhachThue();
                khachThue.setId(rs.getInt("ID_KhachThue"));
                khachThue.setName(rs.getNString("ten_khach"));
                khachThue.setDob(rs.getDate("Ngay_sinh"));
                khachThue.setPhone(rs.getNString("SDT"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                khachThue.setCccd(rs.getNString("CCCD"));
                khachThue.setHk_thuong_tru(rs.getNString("HK_thuong_tru"));
                khachThue.setJob(rs.getNString("Nghe_nghiep"));
                khachThue.setRoomName(rs.getNString("RoomName"));
                khachThue.setAccount(account);
                list.add(khachThue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhachThue> Paging(List<KhachThue> khachThues, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, khachThues.size());

        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }

        return khachThues.subList(fromIndex, toIndex);
    }

    public boolean insertKhachThue(String hovaten, String cmnd, String gioitinh, String ngaycap,
            String dienthoai, String email, String diachi, String ThanhPho, String ngaysinh, String sophong, String GhiChu, String MatTruoc, String MatSau, int IdAcount, int roomId, String Nghenghiep) {
        Connection conn = null;
        PreparedStatement stmtKhachThue = null;
        PreparedStatement stmtHopDong = null;

        try {
            conn = this.connection; // Giả sử lớp DBContext đã cung cấp kết nối
            conn.setAutoCommit(false); // Tạo một giao dịch

            // Convert gender string to integer
            int gender = "male".equalsIgnoreCase(gioitinh) ? 0 : 1; // Assuming 0 = male, 1 = female

            // Insert vào bảng khach_thue
            String sqlInsertKhachThue = "INSERT INTO khach_thue (Ten_khach, CCCD, Gioi_tinh, Ngay_cap, SDT, email, HK_thuong_tru, Noi_cap, Ngay_sinh, URL_mat_truoc, URL_mat_sau, ID_Account, Nghe_nghiep) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtKhachThue = conn.prepareStatement(sqlInsertKhachThue, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtKhachThue.setString(1, hovaten);      // Ten_khach
            stmtKhachThue.setString(2, cmnd);         // CCCD
            stmtKhachThue.setInt(3, gender);          // Gioi_tinh (int)
            stmtKhachThue.setString(4, ngaycap);      // Ngay_cap
            stmtKhachThue.setString(5, dienthoai);    // SDT
            stmtKhachThue.setString(6, email);        // Email
            stmtKhachThue.setString(7, diachi);       // HK_thuong_tru
            stmtKhachThue.setString(8, ThanhPho);     // Noi_cap
            stmtKhachThue.setString(9, ngaysinh);     // Ngay_sinh
            stmtKhachThue.setString(10, MatTruoc);    // URL_mat_truoc
            stmtKhachThue.setString(11, MatSau);      // URL_mat_sau
            stmtKhachThue.setInt(12, IdAcount);       // ID_Account
            stmtKhachThue.setString(13, Nghenghiep);
            stmtKhachThue.executeUpdate();

            // Lấy ID của KhachThue vừa được tạo
            ResultSet generatedKeys = stmtKhachThue.getGeneratedKeys();
            int khachThueId = -1;
            if (generatedKeys.next()) {
                khachThueId = generatedKeys.getInt(1);
            }

            // Insert vào bảng hop_dong
            String sqlInsertHopDong = "INSERT INTO hop_dong (ID_KhachThue, ID_PhongTro, Trang_thai) VALUES (?, ?, 'waiting')";
            stmtHopDong = conn.prepareStatement(sqlInsertHopDong);
            stmtHopDong.setInt(1, khachThueId); // ID của khách thuê
            stmtHopDong.setInt(2, roomId);  // ID của phòng thuê
            stmtHopDong.executeUpdate();

            // Commit nếu thành công
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // Đóng kết nối và statement
            try {
                if (stmtKhachThue != null) {
                    stmtKhachThue.close();
                }
                if (stmtHopDong != null) {
                    stmtHopDong.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getIdAccountByEmail(String email) {
        int idAccount = -1;
        String sql = "SELECT ID_Account FROM account WHERE Email = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idAccount = rs.getInt("ID_Account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAccount;
    }

    public String getTenPhongTroById(int idPhong) {
        String tenPhongTro = null;
        String sql = "SELECT TenPhongTro FROM phong_tro WHERE ID_Phong = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenPhongTro = rs.getString("TenPhongTro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenPhongTro;
    }

    public int getRoomIdByRoomName(String roomName) {
        int roomId = -1; // Khởi tạo giá trị mặc định
        String sql = "SELECT ID_Phong FROM phong_tro WHERE TenPhongTro = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, roomName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roomId = rs.getInt("ID_Phong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomId;
    }

    public int getKhachThueIdByCCCD(String Cmnd) {
        String sql = "SELECT ID_KhachThue FROM khach_thue WHERE CCCD = ?";
        int khachThueId = -1; // Default value if not found
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, Cmnd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    khachThueId = rs.getInt("ID_KhachThue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachThueId;
    }

    public int getHopDongIdByKhachThueId(int idKhachThue) {
        String sql = "SELECT MAX(ID_HopDong) AS ID_HopDong \n"
                + "FROM hop_dong \n"
                + "WHERE ID_KhachThue = ?;";
        int hopDongId = -1; // Default value if not found
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idKhachThue);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hopDongId = rs.getInt("ID_HopDong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hopDongId;
    }

    public KhachThue getKhachThueByHopDongId(int hopDongId) {
        KhachThue khachThue = null;
        String sql = "SELECT kt.* "
                + "FROM khach_thue kt "
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue "
                + "WHERE hd.ID_HopDong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, hopDongId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    khachThue = new KhachThue();
                    khachThue.setId(rs.getInt("ID_KhachThue"));
                    khachThue.setDob(rs.getDate("Ngay_sinh"));
                    khachThue.setCccd(rs.getString("CCCD"));
                    khachThue.setEmail(rs.getString("email"));
                    khachThue.setPhone(rs.getString("SDT"));
                    khachThue.setHk_thuong_tru(rs.getString("HK_thuong_tru"));
                    khachThue.setNoi_cap(rs.getString("Noi_cap"));
                    khachThue.setNgay_cap(rs.getString("Ngay_cap"));
                    khachThue.setName(rs.getString("Ten_khach"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu cần
        }
        return khachThue;
    }
}
