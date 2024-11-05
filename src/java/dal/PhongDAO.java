/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Statement;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.NhaTro;
import model.Phong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.sql.Date;
import model.Account;
import model.AnhPhongTro;
import model.FeedBack;
import model.KhachThue;

public class PhongDAO extends DBContext {

    public List<Phong> getAllRooms() {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta , p.Trang_thai,p.ID_NhaTro  "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong order by p.ID_Phong;";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Phong r = new Phong();
                r.setID_Phong(rs.getInt("ID_Phong"));
                r.setTenNhaTro(rs.getString("TenNhaTro"));
                r.setTenPhongTro(rs.getString("TenPhongTro"));
                r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                r.setTang(rs.getInt("Tang"));
                r.setDien_tich(rs.getFloat("Dien_Tich"));
                r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                r.setGia(rs.getInt("Gia"));
                r.setMo_ta(rs.getString("Mo_ta"));
                r.setTrang_thai(rs.getString("Trang_thai"));
                r.setID_NhaTro(rs.getInt("ID_NhaTro"));

                List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                r.setImages(images);
                rooms.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getAllRooms: " + e.getMessage());
        }
        return rooms;
    }

    //search by name
    public List<Phong> getRoomsByName(String tenPhongTro, Account account) {
        List<Phong> rooms = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong ");

        // Thêm debug
        System.out.println("Account role: " + account.getRole());
        System.out.println("Account ID: " + account.getID_Account());

        if (account.getRole().equalsIgnoreCase("manager")) {
            sql.append("JOIN quan_ly q ON n.ID_QuanLy = q.ID_QuanLy ");
            sql.append("WHERE p.TenPhongTro LIKE ? AND q.ID_Account = ? ");
        } else {
            sql.append("WHERE p.TenPhongTro LIKE ? ");
        }

        sql.append("ORDER BY p.ID_Phong");

        // In ra câu SQL để debug
        System.out.println("SQL Query: " + sql.toString());

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            st.setString(1, "%" + tenPhongTro + "%");
            if (account.getRole().equalsIgnoreCase("manager")) {
                st.setInt(2, account.getID_Account());
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByName: " + e.getMessage());
            e.printStackTrace();
        }

        // In ra số lượng phòng tìm thấy
        System.out.println("Found " + rooms.size() + " rooms");

        return rooms;
    }

    public List<Phong> getRoomsByNhaTro(int idNhaTro) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta , p.Trang_thai,p.ID_NhaTro  "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ? order by p.ID_Phong";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, idNhaTro);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));

                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);

                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getRoomsByNhaTro: " + e.getMessage());
        }
        return rooms;
    }

    public List<Phong> getRoomsByLoaiPhong(int loaiPhongId) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT * FROM phong_tro WHERE ID_LoaiPhong = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, loaiPhongId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong room = new Phong();
                    // Thiáº¿t láº­p cÃ¡c thuá»™c tÃ­nh cá»§a phÃ²ng tá»« ResultSet
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByLoaiPhong: " + e.getMessage());
        }
        return rooms;
    }

//check xem room co trang thai dang thue-> false
    public boolean isRoomDeletable(int roomId) throws SQLException {
        String sql = "SELECT trang_thai FROM phong_tro WHERE ID_Phong = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, roomId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("trang_thai");
                    // Allow deletion if the room is "Trống" (Available)
                    return "T".equals(status);
                }
            }
        }
        return false; // Room not found or error occurred
    }

    public boolean deleteRoomById(int roomId) throws SQLException {
        if (!isRoomDeletable(roomId)) {
            return false; // Room is not deletable
        }

        connection.setAutoCommit(false);
        try {
            // Delete related records in the THIET_BI_PHONG table
            String deleteThietBiPhongSql = "DELETE FROM thiet_bi_phong WHERE ID_Phong = ?";
            try (PreparedStatement st = connection.prepareStatement(deleteThietBiPhongSql)) {
                st.setInt(1, roomId);
                st.executeUpdate();
            }

            // Delete related records in the ANH_PHONG_TRO table
            String deleteImagesSql = "DELETE FROM anh_phong_tro WHERE ID_Phong = ?";
            try (PreparedStatement st = connection.prepareStatement(deleteImagesSql)) {
                st.setInt(1, roomId);
                st.executeUpdate();
            }

            // Delete the room
            String deleteRoomSql = "DELETE FROM phong_tro WHERE ID_Phong = ?";
            try (PreparedStatement st = connection.prepareStatement(deleteRoomSql)) {
                st.setInt(1, roomId);
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Error in PhongDAO.deleteRoomById: " + e.getMessage());
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    //detail room 
    public Phong getDetailRoom(int roomId) {
        Phong room = null;
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_Phong = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, roomId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    room = new Phong();
                    room.setID_Phong(rs.getInt("ID_Phong"));
                    room.setTenNhaTro(rs.getString("TenNhaTro"));
                    room.setTenPhongTro(rs.getString("TenPhongTro"));
                    room.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    room.setTang(rs.getInt("Tang"));
                    room.setDien_tich(rs.getFloat("Dien_Tich"));
                    room.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    room.setGia(rs.getInt("Gia"));
                    room.setMo_ta(rs.getString("Mo_ta"));
                    room.setTrang_thai(rs.getString("Trang_thai"));

                    // Fetch images separately
                    List<String> images = getImagesByPhongId(roomId);
                    room.setImages(images);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getDetailRoom: " + e.getMessage());
        }
        return room;
    }

    //insert room
    public int insertRoom(Phong room) {
        String sql = "INSERT INTO phong_tro (ID_NhaTro, ID_LoaiPhong, TenPhongTro, Tang, Dien_Tich, Gia, Trang_thai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                st.setInt(1, room.getID_NhaTro());
                st.setInt(2, room.getID_LoaiPhong());
                st.setString(3, room.getTenPhongTro());
                st.setInt(4, room.getTang());
                st.setFloat(5, room.getDien_tich());
                st.setInt(6, room.getGia());
                st.setString(7, room.getTrang_thai());

                int affectedRows = st.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating room failed, no rows affected.");
                }

                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int roomId = generatedKeys.getInt(1);

                        if (room.getImages() != null && !room.getImages().isEmpty()) {
                            for (String image : room.getImages()) {
                                insertRoomImage(roomId, image);
                            }
                        }

                        return roomId; // Return the new room ID
                    } else {
                        throw new SQLException("Creating room failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.insertRoom: " + e.getMessage());
            return -1; // Return -1 or throw an exception to indicate failure
        }
    }
    
    public boolean isRoomNameExists(String roomName, int houseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM phong_tro WHERE TenPhongTro = ? AND ID_NhaTro = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomName);
            st.setInt(2, houseId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void insertRoomImage(int roomId, String imageUrl) throws SQLException {
        String sql = "INSERT INTO anh_phong_tro (ID_Phong, URL_AnhPhongTro) VALUES (?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, roomId);
            st.setString(2, imageUrl);
            st.executeUpdate();
        }
    }

    private int getIDNhaTroByName(String tenNhaTro) throws SQLException {
        String sql = "SELECT ID_NhaTro FROM nha_tro WHERE TenNhaTro = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, tenNhaTro);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_NhaTro");
                }
            }
        }
        throw new SQLException("NhaTro not found: " + tenNhaTro);
    }

    private int getIDLoaiPhongByName(String tenLoaiPhong) throws SQLException {
        String sql = "SELECT ID_LoaiPhong FROM loai_phong WHERE TenLoaiPhong = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, tenLoaiPhong);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_LoaiPhong");
                }
            }
        }
        throw new SQLException("LoaiPhong not found: " + tenLoaiPhong);
    }

    //get detail img each phong
    public List<String> getImagesByPhongId(int phongId) {
        List<String> images = new ArrayList<>();
        String sql = "SELECT * FROM anh_phong_tro WHERE ID_Phong = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, phongId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    images.add(rs.getString("URL_AnhPhongTro"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getImagesByPhongId: " + e.getMessage());
        }
        return images;
    }

    public void updateRoom(Phong room) {
        String sql = "UPDATE phong_tro set ID_NhaTro = ?, ID_LoaiPhong  = ?, TenPhongTro  = ?, "
                + " Tang  = ?, Dien_Tich  = ?, Gia  = ?, Trang_thai  = ? where ID_Phong = ? ";
        try {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, room.getID_NhaTro());
                st.setInt(2, room.getID_LoaiPhong());
                st.setString(3, room.getTenPhongTro());
                st.setInt(4, room.getTang());
                st.setFloat(5, room.getDien_tich());
                st.setInt(6, room.getGia());
                st.setString(7, room.getTrang_thai());
                st.setInt(8, room.getID_Phong());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.updateRoom: " + e.getMessage());
        }
    }

    public void deleteImageByPhong(int phongId) {
        String sql = "DELETE FROM anh_phong_tro where ID_Phong = ?";
        try {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, phongId);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.deleteImageByPhong: " + e.getMessage());
        }
    }

    public List<Phong> getRoomsWithPagination(int page, int pageSize) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "ORDER BY p.ID_Phong "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, pageSize);
            st.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));

                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsWithPagination: " + e.getMessage());
        }
        return rooms;
    }

    public int getTotalRooms() {
        String sql = "SELECT COUNT(*) FROM phong_tro";
        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getTotalRooms: " + e.getMessage());
        }
        return 0;
    }

//get tat ca trang thai dang co
    public List<String> getAvailableStatuses() {
        List<String> statusList = new ArrayList<>();
        String sql = "SELECT DISTINCT Trang_thai FROM phong_tro ORDER BY Trang_thai";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String status = rs.getString("Trang_thai");
                if (status != null && !status.trim().isEmpty()) {
                    statusList.add(status);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getAvailableStatuses: " + e.getMessage());
        }

        return statusList;
    }

    public boolean isRentedRoom(int idRoom) {
        LocalDate today = LocalDate.now(); // Lấy ngày hiện tại

        String sql = "SELECT 1 " // Chỉ cần kiểm tra xem có kết quả hay không
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "JOIN hop_dong h ON p.ID_Phong = h.ID_PhongTro "
                + "WHERE h.Ngay_gia_tri <= ? AND h.Ngay_het_han >= ? AND p.ID_Phong = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(today)); // Ngày hiện tại cho Ngay_gia_tri
            st.setDate(2, Date.valueOf(today)); // Ngày hiện tại cho Ngay_het_han
            st.setInt(3, idRoom); // ID của phòng

            try (ResultSet rs = st.executeQuery()) {
                // Nếu có kết quả trả về, nghĩa là phòng đang được thuê
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.isRentedRoom: " + e.getMessage());
        }

        return false; // Mặc định trả về false nếu có lỗi hoặc không có kết quả
    }

    public int insertRooms(Phong phong) {
        String insertRoomSQL = "INSERT INTO phong_tro (TenPhongTro, Tang, ID_LoaiPhong, ID_NhaTro, Dien_Tich, Gia, Trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(insertRoomSQL, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, phong.getTenPhongTro());
            st.setInt(2, phong.getTang());
            st.setInt(3, phong.getID_LoaiPhong());
            st.setInt(4, phong.getID_NhaTro());
            st.setFloat(5, phong.getDien_tich());
            st.setInt(6, phong.getGia());
            st.setString(7, phong.getTrang_thai());

            st.executeUpdate();

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated room ID (ID_Phong)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  // Return -1 if the insertion failed
    }

    public boolean insertThietBiPhong(int roomId, int thietBiId, String trangThai, String moTa, int soLuong) {
        String insertThietBiPhongSQL = "INSERT INTO thiet_bi_phong (ID_Phong, ID_ThietBi, Trang_thai, Mo_ta, So_luong) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(insertThietBiPhongSQL)) {
            st.setInt(1, roomId);
            st.setInt(2, thietBiId);
            st.setString(3, trangThai);
            st.setString(4, moTa);
            st.setInt(5, soLuong);

            st.executeUpdate();  // Execute the SQL statement
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getThietBiID(String name) {
        String sql = "SELECT * FROM thiet_bi WHERE TenThietBi = ? LIMIT 1";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name);  // Set the parameter value before executing the query
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_ThietBi");  // Return the ID if a result is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getThietBiID: " + e.getMessage());
        }

        return -1;  // Return -1 if no result is found or an error occurs
    }

    public boolean roomNameExists(String roomName, int nhaTroId) {
        String sql = "SELECT COUNT(*) FROM phong_tro WHERE TenPhongTro = ? AND ID_NhaTro = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roomName);
            st.setInt(2, nhaTroId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Returns true if the room name already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Default to false if there is no matching room
    }

    public Phong getLatestPhong() {
        Phong room = null;
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "ORDER BY p.ID_Phong DESC";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    room = new Phong();
                    room.setID_Phong(rs.getInt("ID_Phong"));
                    room.setTenNhaTro(rs.getString("TenNhaTro"));
                    room.setTenPhongTro(rs.getString("TenPhongTro"));
                    room.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    room.setTang(rs.getInt("Tang"));
                    room.setDien_tich(rs.getFloat("Dien_Tich"));
                    room.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    room.setGia(rs.getInt("Gia"));
                    room.setMo_ta(rs.getString("Mo_ta"));
                    room.setTrang_thai(rs.getString("Trang_thai"));

                    // Fetch images separately
//                    List<String> images = getImagesByPhongId(roomId);
//                    room.setImages(images);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getDetailRoom: " + e.getMessage());
        }
        return room;
    }

    public List<Phong> getRoomsByFloorAndNhaTro(int tang, int idNhaTro) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ? AND p.Tang = ? "
                + "ORDER BY p.ID_Phong";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, idNhaTro);
            st.setInt(2, tang);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByFloorAndNhaTro: " + e.getMessage());
        }
        return rooms;
    }

    public List<Integer> getDistinctFloorsByNhaTro(int idNhaTro) {
        List<Integer> floors = new ArrayList<>();
        String sql = "SELECT DISTINCT Tang FROM phong_tro WHERE ID_NhaTro = ? ORDER BY Tang";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, idNhaTro);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    floors.add(rs.getInt("Tang"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getDistinctFloorsByNhaTro: " + e.getMessage());
        }
        return floors;
    }

    public List<Phong> getPhongsByNhaTroId(int nhaTroId) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT * FROM phong_tro WHERE ID_NhaTro = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, nhaTroId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong room = new Phong();
                    room.setID_Phong(rs.getInt("ID_Phong"));
                    room.setTenPhongTro(rs.getString("TenPhongTro"));
                    room.setTang(rs.getInt("Tang"));
                    room.setDien_tich(rs.getFloat("Dien_tich"));
                    room.setGia(rs.getInt("Gia"));
                    room.setTrang_thai(rs.getString("Trang_thai"));
                    room.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    room.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getPhongsByNhaTroId: " + e.getMessage());
        }
        return rooms;
    }
    // Phương thức lấy phòng theo tầng

    public List<Phong> getRoomsByTang(int tang) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.Tang = ? order by p.ID_Phong";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, tang);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByTang: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

// Phương thức lấy phòng theo nhà trọ và tầng
    public List<Phong> getRoomsByNhaTroAndTang(int nhaTroId, int tang) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ? AND p.Tang = ? "
                + "ORDER BY p.ID_Phong";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, nhaTroId);
            st.setInt(2, tang);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByNhaTroAndTang: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

// Phương thức lấy danh sách tầng có sẵn
    public List<Integer> getAvailableTang() {
        List<Integer> floors = new ArrayList<>();
        String sql = "SELECT DISTINCT Tang FROM phong_tro ORDER BY Tang";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                floors.add(rs.getInt("Tang"));
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getAvailableTang: " + e.getMessage());
            e.printStackTrace();
        }
        return floors;
    }

    public List<Phong> getRoomsByKhachThueId(int id) {
        List<Phong> danhSachPhong = new ArrayList<>(); // Khởi tạo danh sách phòng
        String sql = "SELECT * "
                + "FROM phong_tro p "
                + "JOIN hop_dong h ON p.ID_Phong = h.ID_PhongTro "
                + "JOIN khach_thue k ON h.ID_KhachThue = k.ID_KhachThue "
                + "WHERE k.ID_KhachThue = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) { // Sử dụng vòng lặp để lấy tất cả các phòng
                    Phong phong = new Phong();
                    phong.setID_Phong(rs.getInt("ID_Phong"));
                    phong.setTenPhongTro(rs.getString("TenPhongTro"));
                    phong.setTang(rs.getInt("Tang"));
                    phong.setDien_tich(rs.getFloat("Dien_Tich"));
                    phong.setGia(rs.getInt("Gia"));
                    phong.setTrang_thai(rs.getString("Trang_thai"));

                    danhSachPhong.add(phong); // Thêm phòng vào danh sách
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomsByKhachThueId: " + e.getMessage());
        }
        return danhSachPhong; // Trả về danh sách phòng hoặc danh sách rỗng nếu không tìm thấy
    }

    public Phong getPhongById(int ID_Phong) {
        String sql = "SELECT * FROM phong_tro WHERE ID_Phong = ?";
        Phong phong = new Phong();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Set the parameter value before executing the query
            st.setInt(1, ID_Phong);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    phong.setID_Phong(rs.getInt("ID_Phong"));
                    phong.setTenPhongTro(rs.getString("TenPhongTro"));
                    phong.setTang(rs.getInt("Tang"));
                    phong.setDien_tich(rs.getFloat("Dien_Tich"));
                    phong.setGia(rs.getInt("Gia"));
                    phong.setTrang_thai(rs.getString("Trang_thai"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error here: " + e.getMessage());
            e.printStackTrace();
        }
        return phong;
    }

    public List<Integer> getStatisticRoomByApartment(int nhaTroId) {
        List<Integer> data = new ArrayList<>();
        String sql = "    SELECT \n"
                + "    status.Trang_thai,\n"
                + "    COALESCE(p.SoLuong, 0) AS SoLuong\n"
                + "FROM \n"
                + "    (SELECT \"T\" AS Trang_thai\n"
                + "     UNION ALL\n"
                + "     SELECT \"D\" AS Trang_thai) AS status\n"
                + "LEFT JOIN \n"
                + "    (SELECT Trang_thai, COUNT(*) AS SoLuong\n"
                + "     FROM phong_tro\n"
                + "     WHERE ID_NhaTro = ?\n"
                + "     GROUP BY Trang_thai) AS p\n"
                + "ON status.Trang_thai = p.Trang_thai\n"
                + "ORDER BY \n"
                + "    status.Trang_thai DESC;";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, nhaTroId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {

                    data.add(rs.getInt("SoLuong"));

                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getStatisticRoomByApartment: " + e.getMessage());
        }
        return data;
    }

    public List<Integer> getStatisticRevenueByApartment(int nhaTroId, int year) {
        List<Integer> data = new ArrayList<>();
        String sql = "SELECT \n"
                + "    months.month AS Thang,\n"
                + "    COALESCE(SUM(h.Tong_gia_tien), 0) AS DoanhThu\n"
                + "FROM \n"
                + "    (SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL \n"
                + "     SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL \n"
                + "     SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL \n"
                + "     SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) AS months\n"
                + "LEFT JOIN \n"
                + "    hoa_don h ON MONTH(h.NgayThanhToan) = months.month \n"
                + "                AND YEAR(h.NgayThanhToan) = ?\n"
                + "                AND h.Trang_thai = 1\n"
                + "LEFT JOIN \n"
                + "    hop_dong hd ON hd.ID_HopDong = h.ID_HopDong \n"
                + "LEFT JOIN \n"
                + "    phong_tro pt ON pt.ID_Phong = hd.ID_PhongTro \n"
                + "WHERE \n"
                + "    pt.ID_NhaTro = ? OR h.ID_HopDong IS NULL\n"
                + "GROUP BY \n"
                + "    months.month\n"
                + "ORDER BY \n"
                + "    months.month;";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, year);
            st.setInt(2, nhaTroId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    data.add(rs.getInt("DoanhThu"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getStatisticRevenueByApartment: " + e.getMessage());
        }
        return data;
    }

// Add a method to get available years
    public List<Integer> getAvailableYears(int nhaTroId) {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(h.NgayThanhToan) as year\n"
                + "FROM hoa_don h\n"
                + "JOIN hop_dong hd ON hd.ID_HopDong = h.ID_HopDong\n"
                + "JOIN phong_tro pt ON pt.ID_Phong = hd.ID_PhongTro\n"
                + "WHERE pt.ID_NhaTro = ?\n"
                + "ORDER BY year DESC";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, nhaTroId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    years.add(rs.getInt("year"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getAvailableYears: " + e.getMessage());
        }
        return years;
    }

    public List<Phong> searchRooms(String searchText, int idNhaTro) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ? AND p.TenPhongTro LIKE ? "
                + "ORDER BY p.ID_Phong";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, idNhaTro);
            st.setString(2, "%" + searchText + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.searchRooms: " + e.getMessage());
        }
        return rooms;
    }

    public List<Phong> getRoomByStatus(String status) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.Trang_thai = ? "
                + "ORDER BY p.ID_Phong";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomByStatus: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

// Phương thức lấy phòng theo tầng và trạng thái
    public List<Phong> getRoomByFloorAndStatus(int tang, String status) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.Tang = ? AND p.Trang_thai = ? "
                + "ORDER BY p.ID_Phong";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, tang);
            st.setString(2, status);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));
                    r.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    List<String> images = getImagesByPhongId(rs.getInt("ID_Phong"));
                    r.setImages(images);
                    rooms.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.getRoomByFloorAndStatus: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

public Phong getRoomById(int idPhong) {
    String sql = "SELECT p.ID_Phong, p.TenPhongTro, p.Tang, p.Trang_thai, " +
                 "p.Dien_tich, p.Gia, n.TenNhaTro, n.Dia_chi AS diaChiPhongTro, " +
                 "l.TenLoaiPhong, a.URL_AnhPhongTro, " +
                 "tb.Trang_thai AS trangThaiThietBi, tb.Mo_ta AS moTaThietBi, tb.So_luong AS soLuongThietBi " +
                 "FROM phong_tro p " +
                 "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro " +
                 "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong " +
                 "LEFT JOIN anh_phong_tro a ON p.ID_Phong = a.ID_Phong " +
                 "LEFT JOIN thiet_bi_phong tb ON p.ID_Phong = tb.ID_Phong " +
                 "WHERE p.ID_Phong = ?;";

    Phong room = null;
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idPhong);
        try (ResultSet rs = stmt.executeQuery()) {
            List<String> deviceDescriptions = new ArrayList<>();
            List<Integer> deviceQuantities = new ArrayList<>();
            List<String> deviceStatuses = new ArrayList<>();

            while (rs.next()) {
                if (room == null) {
                    room = new Phong();
                    room.setID_Phong(rs.getInt("ID_Phong"));
                    room.setTenPhongTro(rs.getString("TenPhongTro"));
                    room.setTang(rs.getInt("Tang"));
                    room.setTrang_thai(rs.getString("Trang_thai"));
                    room.setDien_tich(rs.getFloat("Dien_tich"));
                    room.setGia(rs.getInt("Gia"));
                    room.setTenNhaTro(rs.getString("TenNhaTro"));
                    room.setDiaChiPhongTro(rs.getString("diaChiPhongTro"));
                    room.setTenLoaiPhong(rs.getString("TenLoaiPhong"));

                    // Fetch images separately for each room
                    List<String> images = getImagesByPhongId(idPhong);
                    room.setImages(images);
                }

                // Add device details to lists
                deviceStatuses.add(rs.getString("trangThaiThietBi"));
                deviceDescriptions.add(rs.getString("moTaThietBi"));
                deviceQuantities.add(rs.getInt("soLuongThietBi"));
            }

            if (room != null) {
                room.setTrangthaithietbi(deviceStatuses);
                room.setMotathietbi(deviceDescriptions);
                room.setSoluongthietbi(deviceQuantities);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error in RoomDAO.getRoomById: " + e.getMessage());
    }
    return room;
}


        public List<FeedBack> getFeedbackByPhongId(int idPhong) {
        List<FeedBack> feedbackList = new ArrayList<>();
        String sql = "SELECT f.Noi_dung, f.Danh_gia, k.Ten_khach " +
                     "FROM feedback f " +
                     "JOIN khach_thue k ON f.ID_KhachThue = k.ID_KhachThue " +
                     "WHERE f.ID_Phong = ? " +
                     "ORDER BY f.ID_FeedBack";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPhong);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FeedBack feedback = new FeedBack();
                    feedback.setNoi_dung(rs.getString("Noi_dung"));
                    feedback.setDanh_gia(rs.getInt("Danh_gia"));
                    feedback.setTen_khach(rs.getString("Ten_khach"));

                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getFeedbackByPhongId: " + e.getMessage());
        }
        return feedbackList;
    
    }

}

