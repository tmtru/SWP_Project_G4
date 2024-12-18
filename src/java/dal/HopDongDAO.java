package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HopDong;
import java.sql.Date;
import model.Account;
import model.KhachThue;
import model.Phong;

public class HopDongDAO extends DBContext {

    public List<HopDong> getHopDongsByKhachThueID(int ID_KhachThue) {
        List<HopDong> hopDongs = new ArrayList<>();
        String sql = "SELECT \n"
                + "    hd.ID_hopDong,             \n"
                + "    kt.Ten_khach,              \n"
                + "    pt.TenPhongTro,           \n"
                + "    hd.Ngay_gia_tri,          \n"
                + "    hd.Ngay_het_han,          \n"
                + "    hd.Tien_Coc,              \n"
                + "    f.Noi_dung,               \n"
                + "    f.Danh_gia,               \n" // Thêm dấu phẩy ở đây
                + "    kt.ID_KhachThue,          \n" // Thêm dấu phẩy ở đây
                + "    pt.ID_Phong               \n" // Thêm dấu phẩy ở đây
                + "FROM \n"
                + "    hop_dong hd \n"
                + "JOIN \n"
                + "    khach_thue kt ON hd.ID_KhachThue = kt.ID_KhachThue  \n"
                + "JOIN \n"
                + "    phong_tro pt ON hd.ID_Phongtro = pt.ID_Phong       \n"
                + "LEFT JOIN \n"
                + "    feedback f ON kt.ID_KhachThue = f.ID_KhachThue AND pt.ID_Phong = f.ID_Phong \n"
                + "WHERE \n"
                + "    hd.ID_KhachThue = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ID_KhachThue);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HopDong hopDong = new HopDong();
                hopDong.setID_HopDong(rs.getInt("ID_hopDong"));
                hopDong.setKhach_thue(rs.getString("Ten_khach"));
                hopDong.setTen_phong(rs.getString("TenPhongTro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_Coc"));
                hopDong.setNoi_dung(rs.getString("Noi_dung"));
                hopDong.setDanh_gia(rs.getString("Danh_gia"));
                hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                hopDong.setID_Phongtro(rs.getInt("ID_Phong"));

                hopDongs.add(hopDong);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Xử lý lỗi
        }

        return hopDongs;
    }

    public List<HopDong> findAll(int offset, int noOfRecords) {
        List<HopDong> hopDongList = new ArrayList<>();
        String sql = "SELECT * FROM hop_dong LIMIT ?, ?";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, noOfRecords);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HopDong hopDong = mapResultSetToHopDong(rs);
                    hopDongList.add(hopDong);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the exception or throwing a custom exception
        }

        return hopDongList;
    }

    public int getNoOfRecords() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM hop_dong";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the exception or throwing a custom exception
        }

        return count;
    }

    private HopDong mapResultSetToHopDong(ResultSet rs) throws SQLException {
        return new HopDong(
                rs.getInt("ID_HopDong"),
                rs.getInt("ID_KhachThue"),
                rs.getInt("ID_PhongTro"),
                rs.getDate("Ngay_gia_tri"),
                rs.getDate("Ngay_het_han"),
                rs.getInt("Tien_Coc")
        );
    }

    public boolean addHopDong(HopDong hopDong) {
        String sql = "INSERT INTO hop_dong (ID_KhachThue, ID_PhongTro, Ngay_gia_tri, Ngay_het_han, Tien_Coc) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hopDong.getID_KhachThue());
            ps.setInt(2, hopDong.getID_Phongtro());
            ps.setDate(3, hopDong.getNgay_gia_tri());
            ps.setDate(4, hopDong.getNgay_het_han());
            ps.setInt(5, hopDong.getTien_Coc());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public HopDong getHopDongById(int id) {
        String sql = "SELECT * FROM hop_dong WHERE ID_HopDong = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HopDong(
                        rs.getInt("ID_HopDong"),
                        rs.getInt("ID_KhachThue"),
                        rs.getInt("ID_PhongTro"),
                        rs.getDate("Ngay_gia_tri"),
                        rs.getDate("Ngay_het_han"),
                        rs.getInt("Tien_Coc")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateHopDong(HopDong hopDong) {
        String sql = "UPDATE hop_dong SET ID_KhachThue=?, ID_PhongTro=?, Ngay_gia_tri=?, Ngay_het_han=?, Tien_Coc=? WHERE ID_HopDong=?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hopDong.getID_KhachThue());
            ps.setInt(2, hopDong.getID_Phongtro());
            ps.setDate(3, hopDong.getNgay_gia_tri());
            ps.setDate(4, hopDong.getNgay_het_han());
            ps.setInt(5, hopDong.getTien_Coc());
            ps.setInt(6, hopDong.getID_HopDong());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteHopDong(int id) {
        String sql = "DELETE FROM hop_dong WHERE ID_HopDong=?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean insertHopDong(int idHopDong, String ngayGiaTri, String ngayHetHan, int tienCoc, String ghiChu) {
        String sql = "UPDATE hop_dong SET Ngay_gia_tri = ?, Ngay_het_han = ?, Tien_coc = ?, Ghi_chu = ? WHERE ID_HopDong = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(ngayGiaTri));
            statement.setDate(2, java.sql.Date.valueOf(ngayHetHan));
            statement.setInt(3, tienCoc);
            statement.setString(4, ghiChu);// Set ID hợp đồng
            statement.setInt(5, idHopDong);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHopDongStatusToPending(int hopDongId) {
        String query = "UPDATE hop_dong SET Trang_thai = 'pending' WHERE ID_HopDong = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, hopDongId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    

    public boolean acceptHopDong(int hopDongId, int khachThueId, int phongTroId) {
        String updateContractSql = "UPDATE hop_dong SET Trang_thai = 'accept' WHERE ID_HopDong = ?";
        String updateAccountRoleSql = "UPDATE account SET Role = 'tenant' WHERE ID_Account = (SELECT ID_Account FROM khach_thue WHERE ID_KhachThue = ?)";
        String updateRoomStatusSql = "UPDATE phong_tro SET Trang_thai = 'D' WHERE ID_Phong = ?";

        try {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Cập nhật trạng thái hợp đồng
            try (PreparedStatement contractPs = connection.prepareStatement(updateContractSql)) {
                contractPs.setInt(1, hopDongId);
                contractPs.executeUpdate();
            }

            // Cập nhật role tài khoản khách thuê
            try (PreparedStatement accountPs = connection.prepareStatement(updateAccountRoleSql)) {
                accountPs.setInt(1, khachThueId);
                accountPs.executeUpdate();
            }

            // Cập nhật trạng thái phòng trọ
            try (PreparedStatement roomPs = connection.prepareStatement(updateRoomStatusSql)) {
                roomPs.setInt(1, phongTroId);
                roomPs.executeUpdate();
            }

            // Commit giao dịch
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    public int getKhachThueIdByHopDongId(int hopDongId) {
        String sql = "SELECT ID_KhachThue FROM hop_dong WHERE ID_HopDong = ?";
        int khachThueId = -1; // Giá trị mặc định nếu không tìm thấy

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, hopDongId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                khachThueId = rs.getInt("ID_KhachThue");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi
        }

        return khachThueId;
    }

    public int getPhongTroIdByHopDongId(int hopDongId) {
        String sql = "SELECT ID_PhongTro FROM hop_dong WHERE ID_HopDong = ?";
        int phongTroId = -1; // Giá trị mặc định nếu không tìm thấy

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, hopDongId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                phongTroId = rs.getInt("ID_PhongTro");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi
        }

        return phongTroId;
    }

    public boolean rejectHopDong(int idHopDong, String reason) {
        String sql = "UPDATE hop_dong SET Trang_thai = ?, Li_do_tu_choi = ?, isActive = 0 WHERE ID_HopDong = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "reject"); // Cập nhật trạng thái hợp đồng
            ps.setString(2, reason); // Chèn lý do từ chối
            ps.setInt(3, idHopDong); // ID hợp đồng

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi
            return false;
        }

    }

    public List<HopDong> getHopDongWithPhongDetails() {
        List<HopDong> hopDongList = new ArrayList<>();
        String query = "SELECT hd.ID_HopDong, hd.Ngay_gia_tri, hd.Ngay_het_han, hd.Tien_coc, hd.Trang_thai AS HopDong_TrangThai, hd.Li_do_tu_choi, "
                + "pt.TenPhongTro, pt.Gia, pt.Trang_thai AS PhongTro_TrangThai "
                + "FROM hop_dong hd "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HopDong hopDong = new HopDong();

                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_coc"));
                hopDong.setStatus(rs.getString("HopDong_TrangThai"));  // Contract status
                hopDong.setLy_do(rs.getString("Li_do_tu_choi"));

                // Create a new PhongTro object
                Phong phongTro = new Phong();
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));
                phongTro.setTrang_thai(rs.getString("PhongTro_TrangThai"));  // Room status

                // Set PhongTro in HopDong
                hopDong.setPhongTro(phongTro);

                hopDongList.add(hopDong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hopDongList;
    }

    public boolean updateTrangThaiPhongByIdHopDong(int idHopDong) {
        String sql = "UPDATE phong_tro "
                + "SET Trang_thai = 'DT' "
                + "WHERE ID_Phong IN (SELECT ID_PhongTro FROM hop_dong WHERE ID_HopDong = ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.setInt(1, idHopDong);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi nếu cần thiết
            return false;
        }
    }
    public List<HopDong> getHopDongByChuTro(int idChuTro) {
        List<HopDong> hopDongList = new ArrayList<>();
        String sql = "SELECT h.ID_HopDong, h.ID_PhongTro, p.TenPhongTro, h.Ngay_gia_tri, h.Ngay_het_han, h.Trang_thai, kt.Ten_khach\n"
                + "FROM hop_dong h\n"
                + "LEFT JOIN phong_tro p ON h.ID_PhongTro = p.ID_Phong\n"
                + "LEFT JOIN nha_tro nt ON p.ID_NhaTro = nt.ID_NhaTro\n"
                + "LEFT JOIN khach_thue kt ON h.ID_KhachThue = kt.ID_KhachThue\n"
                + "WHERE nt.ID_ChuTro = ? \n"
                + "AND h.Trang_thai IN ('pending', 'accept', 'active', 'reject', 'expired')\n"
                + "ORDER BY h.ID_HopDong DESC;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idChuTro);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HopDong hopDong = new HopDong();
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setID_Phongtro(rs.getInt("ID_PhongTro"));
                hopDong.setTenPhongTro(rs.getString("TenPhongTro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setStatus(rs.getString("Trang_thai"));
                hopDong.setTen_khach(rs.getString("Ten_khach"));
                hopDongList.add(hopDong);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi
        }
        return hopDongList;
    }

    public void updateHopDongStatus(int hopDongId, String status) {
        String sql = "UPDATE hop_dong SET Trang_thai = ?, isActive = 0 WHERE ID_HopDong = ? and isActive = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, hopDongId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateHopDongStatus1(int hopDongId, String status) {
        String sql = "UPDATE hop_dong SET Trang_thai = 'active' WHERE ID_HopDong = ? AND Trang_thai = 'accept' and isActive = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, hopDongId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean giaHanHopDong(int hopDongId, Date ngayHetHan) {
        String query = "UPDATE hop_dong SET Ngay_het_han = ? WHERE ID_HopDong = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, ngayHetHan);
            preparedStatement.setInt(2, hopDongId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HopDong getActiveOrAcceptedContractByRoomId(int roomId) {
        HopDong hopDong = null;
        String sql = "SELECT * FROM hop_dong WHERE ID_PhongTro = ? AND (Trang_thai = 'accept' OR Trang_thai = 'active') ORDER BY Ngay_het_han DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, roomId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hopDong = new HopDong();
                    hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                    hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                    hopDong.setID_Phongtro(rs.getInt("ID_PhongTro"));
                    hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                    hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                    hopDong.setTien_Coc(rs.getInt("Tien_coc"));
                    hopDong.setSo_nguoi(rs.getInt("So_nguoi"));
                    hopDong.setTien_phong(rs.getInt("Tien_phong"));
                    hopDong.setIsActive(rs.getString("isActive"));
                    hopDong.setStatus(rs.getString("Trang_thai"));
                    hopDong.setGhi_chu(rs.getString("Ghi_chu"));
                    hopDong.setGhi_chu(rs.getString("Li_do_tu_choi"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Bạn có thể thay bằng logger nếu cần
        }

        return hopDong;
    }

    public boolean ketThucHopDongSom(int hopDongId, String ngayHetHan) {
        String sql = "UPDATE hop_dong SET isActive = 0, Trang_thai = 'expired', Ngay_het_han = ? WHERE ID_HopDong = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ngayHetHan);      
            ps.setInt(2, hopDongId);         

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;          

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isRoomAvailableForAccept(int phongTroId) {
        String query = "SELECT COUNT(*) FROM hop_dong WHERE ID_PhongTro = ? AND (Trang_thai = 'accept' OR Trang_thai = 'active')";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, phongTroId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Room is already rented in another contract
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Room is available for accepting the contract
    }

    public List<HopDong> getHopDongKhachCocByApartment(int nhatroId, String search, Integer start, Integer recordPerPage) {
        List<HopDong> hopDongList = new ArrayList<>();
        String query = "select * from hop_dong hd\n"
                + "left join khach_thue a on a.ID_KhachThue = hd.ID_KhachThue\n"
                + "left join phong_tro pt on pt.ID_Phong = hd.ID_PhongTro\n"
                + "where (hd.Trang_thai = 'accept' and pt.ID_NhaTro = ?) and (? = '' or a.Ten_khach like ?) ";

        if (start != null && recordPerPage != null) {
            query += "LIMIT ?, ?";
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, nhatroId);
            stmt.setString(2, search);

            stmt.setString(3, "%" + search + "%");
            if (start != null && recordPerPage != null) {
                stmt.setInt(4, start);
                stmt.setInt(5, recordPerPage);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HopDong hopDong = new HopDong();

                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_coc"));

                KhachThue account = new KhachThue();
                account.setName(rs.getString("Ten_khach"));
                account.setEmail(rs.getString("email"));

                hopDong.setKhachThue(account);

                // Create a new PhongTro object
                Phong phongTro = new Phong();
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));

                // Set PhongTro in HopDong
                hopDong.setPhongTro(phongTro);

                hopDongList.add(hopDong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hopDongList;
    }
    //tinh theo ngay het han thoi

    public List<HopDong> getHopDongKhachSapHetHanByApartment(int nhatroId, String search, Integer start, Integer recordPerPage) {
        List<HopDong> hopDongList = new ArrayList<>();
        String query = "select * from hop_dong hd\n"
                + "left join khach_thue a on a.ID_KhachThue = hd.ID_KhachThue\n"
                + "left join phong_tro pt on pt.ID_Phong = hd.ID_PhongTro\n"
                + "where ( pt.ID_NhaTro = ?) and (? = '' or a.Ten_khach like ?) "
                // Thêm điều kiện để lấy hợp đồng sắp hết hạn trong vòng 20 ngày
                + "and (hd.Ngay_het_han BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 20 DAY)) ";

        if (start != null && recordPerPage != null) {
            query += "LIMIT ?, ?";
        }
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, nhatroId);
            stmt.setString(2, search);
            stmt.setString(3, "%" + search + "%");
            if (start != null && recordPerPage != null) {
                stmt.setInt(4, start);
                stmt.setInt(5, recordPerPage);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HopDong hopDong = new HopDong();
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_coc"));
                KhachThue account = new KhachThue();
                account.setName(rs.getString("Ten_khach"));
                account.setEmail(rs.getString("email"));
                hopDong.setKhachThue(account);
                // Create a new PhongTro object
                Phong phongTro = new Phong();
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));
                // Set PhongTro in HopDong
                hopDong.setPhongTro(phongTro);
                hopDongList.add(hopDong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hopDongList;

    }

    public List<HopDong> getHopDongByQuanLyId(int ID_Account) {
        List<HopDong> danhSachHopDong = new ArrayList<>();

        String query = "SELECT hd.*, pt.TenPhongTro, pt.Gia, pt.Trang_thai AS PhongTro_TrangThai "
                + "FROM hop_dong hd "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE ql.ID_Account = ? AND hd.Ngay_het_han IS NOT NULL AND hd.Ngay_gia_tri IS NOT NULL AND hd.isActive = 1 "
                + "ORDER BY hd.ID_HopDong DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, ID_Account);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HopDong hopDong = new HopDong();

                // Mapping dữ liệu từ ResultSet sang đối tượng HopDong
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                hopDong.setID_Phongtro(rs.getInt("ID_PhongTro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_coc"));
                hopDong.setSo_nguoi(rs.getInt("So_nguoi"));
                hopDong.setTien_phong(rs.getInt("Tien_phong"));
                hopDong.setIsActive(rs.getString("isActive"));
                hopDong.setStatus(rs.getString("Trang_thai"));
                hopDong.setGhi_chu(rs.getString("Ghi_chu"));
                hopDong.setLy_do(rs.getString("Li_do_tu_choi"));

                Phong phongTro = new Phong();
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));
                phongTro.setTrang_thai(rs.getString("PhongTro_TrangThai"));  // Room status

                hopDong.setPhongTro(phongTro);

                danhSachHopDong.add(hopDong);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachHopDong;
    }

    public List<HopDong> getHopDongByQuanLyIdAndStatus(int ID_Account, Date selectedDate) {
        List<HopDong> danhSachHopDong = new ArrayList<>();

        String query = "SELECT hd.*, pt.TenPhongTro, pt.Gia, pt.Trang_thai AS PhongTro_TrangThai "
                + "FROM hop_dong hd "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE ql.ID_Account = ? AND hd.Ngay_gia_tri < ? AND hd.Ngay_het_han > ? AND hd.Ngay_het_han IS NOT NULL AND hd.Ngay_gia_tri IS NOT NULL AND hd.isActive = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, ID_Account);
            stmt.setDate(2, selectedDate);
            stmt.setDate(3, selectedDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HopDong hopDong = new HopDong();

                // Mapping dữ liệu từ ResultSet sang đối tượng HopDong
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                hopDong.setID_Phongtro(rs.getInt("ID_PhongTro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_coc"));
                hopDong.setSo_nguoi(rs.getInt("So_nguoi"));
                hopDong.setIsActive(rs.getString("isActive"));
                hopDong.setStatus(rs.getString("Trang_thai"));
                hopDong.setGhi_chu(rs.getString("Ghi_chu"));
                hopDong.setLy_do(rs.getString("Li_do_tu_choi"));

                Phong phongTro = new Phong();
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));
                phongTro.setTrang_thai(rs.getString("PhongTro_TrangThai"));  // Room status

                hopDong.setPhongTro(phongTro);

                danhSachHopDong.add(hopDong);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachHopDong;
    }

    public List<Phong> getPhongTroByAccountId(int idAccount) {
        List<Phong> danhSachPhongTro = new ArrayList<>();

        String query = "SELECT pt.* "
                + "FROM phong_tro pt "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE pt.Trang_thai = 'T' "
                + "AND ql.ID_Account = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAccount);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Phong phong = new Phong();
                phong.setID_Phong(rs.getInt("ID_Phong"));
                phong.setTenPhongTro(rs.getString("TenPhongTro"));
                phong.setID_NhaTro(rs.getInt("ID_NhaTro"));
                phong.setTrang_thai(rs.getString("Trang_thai"));
                phong.setGia(rs.getInt("Gia"));
                phong.setTang(rs.getInt("Tang"));
                phong.setDien_tich(rs.getFloat("Dien_tich"));
                phong.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));

                danhSachPhongTro.add(phong);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachPhongTro;
    }

    public static void main(String[] args) {
        HopDongDAO hopDongDAO = new HopDongDAO();

        // Số phòng cần kiểm tra
        int roomId = 1; // Thay số này bằng ID phòng thực tế bạn muốn kiểm tra

        // Gọi phương thức để lấy hợp đồng
        HopDong hopDong = hopDongDAO.getActiveOrAcceptedContractByRoomId(roomId);

        // Kiểm tra kết quả và in ra thông tin
        if (hopDong != null) {
            System.out.println("Hợp đồng tìm thấy:");
            System.out.println("ID Hợp Đồng: " + hopDong.getID_HopDong());
            System.out.println("ID Khách Thuê: " + hopDong.getID_KhachThue());
            System.out.println("ID Phòng Trọ: " + hopDong.getID_Phongtro());
            System.out.println("Ngày Giá Trị: " + hopDong.getNgay_gia_tri());
            System.out.println("Ngày Hết Hạn: " + hopDong.getNgay_het_han());
            System.out.println("Tiền Cọc: " + hopDong.getTien_Coc());
            System.out.println("Số Người: " + hopDong.getSo_nguoi());
            System.out.println("Tiền Phòng: " + hopDong.getTien_phong());
            System.out.println("Trạng Thái: " + hopDong.getStatus());
            System.out.println("Ghi Chú: " + hopDong.getGhi_chu());
        } else {
            System.out.println("Không tìm thấy hợp đồng nào cho phòng ID: " + roomId);
        }
    }
}
