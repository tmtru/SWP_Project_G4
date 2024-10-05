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
        PhongDAO dao = new PhongDAO();
        List<Phong> list = dao.getAllRooms();
        for (Phong r : list) {
            System.out.println(r);
        }
    }
//check xem room co trang thai dang thue-> false

    public boolean isRoomDeletable(int roomId) throws SQLException {
        String sql = "SELECT trang_thai FROM phong_tro WHERE ID_Phong = ?";
        try (PreparedStatement st1 = connection.prepareStatement(sql)) {
            st1.setInt(1, roomId);
            try (ResultSet rs = st1.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("trang_thai");
                    return "T".equals(status);
                }
            }
        }
        return false; // Room not found or error occurred
    }

    //delete room by id
    public boolean deleteRoomById(int roomId) throws SQLException {
        if (!isRoomDeletable(roomId)) {
            return false; // Room is not deletable
        }

        // delete relate in the ANH_PHONG_TRO table
        String deleteImagesSql = "DELETE FROM anh_phong_tro WHERE ID_Phong = ?";
        try (PreparedStatement st1 = connection.prepareStatement(deleteImagesSql)) {
            st1.setInt(1, roomId);
            st1.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.deleteRoomById (delete images): " + e.getMessage());
            return false; // or handle as needed
        }

        // delete the room
        String deleteRoomSql = "DELETE FROM phong_tro WHERE ID_Phong = ?";
        try (PreparedStatement st2 = connection.prepareStatement(deleteRoomSql)) {
            st2.setInt(1, roomId);
            int rowsAffected = st2.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.deleteRoomById (delete room): " + e.getMessage());
            return false;
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
    public boolean insertRoom(Phong room) {
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

                // Kiá»ƒm tra náº¿u khÃ´ng cÃ³ dÃ²ng nÃ o bá»‹ áº£nh hÆ°á»Ÿng
                if (affectedRows == 0) {
                    throw new SQLException("Creating room failed, no rows affected.");
                }

                // Láº¥y ID sinh tá»± Ä‘á»™ng náº¿u cÃ³
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int roomId = generatedKeys.getInt(1);

                        // ChÃ¨n cÃ¡c hÃ¬nh áº£nh náº¿u cÃ³
                        if (room.getImages() != null && !room.getImages().isEmpty()) {
                            for (String image : room.getImages()) {
                                insertRoomImage(roomId, image);
                            }
                        }

                    } else {
                        throw new SQLException("Creating room failed, no ID obtained.");
                    }
                }

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in PhongDAO.insertRoom: " + e.getMessage());
            return false;
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
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
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

    try (PreparedStatement st = connection.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {

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
}
