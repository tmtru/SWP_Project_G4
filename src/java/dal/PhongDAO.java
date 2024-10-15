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
import model.AnhPhongTro;

public class PhongDAO extends DBContext {

    public List<Phong> getAllRooms() {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta , p.Trang_thai,p.ID_NhaTro  "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong ";

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

    public List<Phong> getRoomsByName(String tenPhongTro) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "a.URL_AnhPhongTro, l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "LEFT JOIN anh_phong_tro a ON p.ID_Phong = a.ID_Phong "
                + "WHERE p.TenPhongTro LIKE ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + tenPhongTro + "%");
            System.out.println("Executing query with tenPhongTro: " + tenPhongTro);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Phong r = new Phong();
                    r.setID_Phong(rs.getInt("ID_Phong"));
                    r.setTenNhaTro(rs.getString("TenNhaTro"));
                    r.setTenPhongTro(rs.getString("TenPhongTro"));
                    r.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    r.setTang(rs.getInt("Tang"));
                    r.setDien_tich(rs.getFloat("Dien_Tich"));
                    r.setURL_AnhPhongTro(rs.getString("URL_AnhPhongTro"));
                    r.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                    r.setGia(rs.getInt("Gia"));
                    r.setMo_ta(rs.getString("Mo_ta"));
                    r.setTrang_thai(rs.getString("Trang_thai"));

                    rooms.add(r);
                }
                System.out.println("Rooms retrieved: " + rooms.size());
            }
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getRoomsByName: " + e.getMessage());
            e.printStackTrace();
        }

        return rooms;
    }

    //available floor
    public List<Integer> getAvailableTang() {
        List<Integer> tangList = new ArrayList<>();
        String sql = "SELECT DISTINCT Tang FROM phong_tro ORDER BY Tang";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                tangList.add(rs.getInt("Tang"));
            }
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getAvailableTang: " + e.getMessage());
        }

        return tangList;
    }

    //get room by floor
    public List<Phong> getRoomsByTang(int tang) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.Tang = ?";

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

    public List<Phong> getRoomsByNhaTroAndTang(int idNhaTro, int tang) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ? AND p.Tang = ?";
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
            System.out.println("Error in PhongDAO.getRoomsByNhaTroAndTang: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Phong> getRoomsByNhaTro(int idNhaTro) {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "l.TenLoaiPhong, p.Gia, l.Mo_ta , p.Trang_thai,p.ID_NhaTro  "
                + "FROM phong_tro p "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN loai_phong l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "WHERE p.ID_NhaTro = ?";

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

    public static void main(String[] args) {

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

}
