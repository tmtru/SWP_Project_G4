/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.ChuTro;
import model.NhaTro;
import model.Phong;

public class NhaTroDAO extends DBContext {

    public ArrayList<NhaTro> getAll() {
        ArrayList<NhaTro> nhaTroList = new ArrayList<>();
        String sql = "SELECT * FROM NHA_TRO ";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            // Lặp qua các kết quả và thêm vào danh sách
            while (rs.next()) {
                NhaTro nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(rs.getInt("ID_ChuTro"));  // Chuyển sang getInt
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));
                nhaTroList.add(nhaTro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaTroList;
    }
    //mang danh sach theo ten nha tro

    public List<NhaTro> getAvailableNhaTro() {
        List<NhaTro> nhaTroList = new ArrayList<>();
        String sql = "SELECT DISTINCT ID_NhaTro, TenNhaTro FROM  nha_tro ORDER BY TenNhaTro";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                NhaTro n = new NhaTro();
                n.setID_NhaTro(rs.getInt("ID_NhaTro"));
                n.setTenNhaTro(rs.getString("TenNhaTro"));
                n.setID_ChuTro(rs.getInt("ID_ChuTro"));  // Chuyển sang getInt
                n.setDia_chi(rs.getNString("Dia_Chi"));
                n.setMo_ta(rs.getNString("Mo_ta"));
                nhaTroList.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Error in NhaTroDAO.getAvailableNhaTro: " + e.getMessage());
        }

        return nhaTroList;
    }

    // Phương thức lấy tất cả phòng dựa trên ID nhà trọ
    public ArrayList<Phong> getAllPhongTro(int idNhaTro) {
        ArrayList<Phong> phongList = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM PHONG_TRO p "
                + "JOIN NHA_TRO n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN LOAI_PHONG l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "LEFT JOIN ANH_PHONG_TRO a ON p.ID_Phong = a.ID_Phong "
                + "WHERE p.ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idNhaTro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Phong phong = new Phong();
                phong.setID_Phong(rs.getInt("ID_Phong"));
                phong.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                phong.setTenPhongTro(rs.getString("TenPhongTro"));
                phong.setID_NhaTro(rs.getInt("ID_NhaTro"));
                phong.setTenNhaTro(rs.getNString("TenNhaTro"));
                phong.setTang(rs.getInt("Tang"));
                phong.setTrang_thai(rs.getString("Trang_thai"));
                phong.setDien_tich(rs.getFloat("Dien_Tich"));
                phong.setURL_AnhPhongTro(rs.getString("URL_AnhPhongTro"));
                phong.setGia(rs.getInt("Gia"));
                phong.setMo_ta(rs.getString("Mo_ta"));
                phongList.add(phong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phongList;
    }

    //get information of a house by id
    public NhaTro getNhaTroById(int id) {
        NhaTro nhaTro = null;
        String sql = "SELECT * FROM NHA_TRO p "
                + "LEFT JOIN ANH_NHA_TRO a ON p.ID_NhaTro = a.ID_NhaTro "
                + "WHERE p.ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(Integer.parseInt(rs.getString("ID_ChuTro")));
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaTro;
    }

    public NhaTro getNhaTroById2(int id) {
        NhaTro nhaTro = null;
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        String sql = "SELECT * FROM NHA_TRO p "
                + "LEFT JOIN ANH_NHA_TRO a ON p.ID_NhaTro = a.ID_NhaTro "
                + "WHERE p.ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(Integer.parseInt(rs.getString("ID_ChuTro")));
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));
                ChuTro chuTro = chuTroDAO.getChuTroById(rs.getInt("ID_ChuTro"));
                nhaTro.setChuTro(chuTro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaTro;
    }

    // Trả về danh sách các phòng có trạng thái nhập vào
    public ArrayList<Phong> getAllPhongStatus(int idNhaTro, String status) {
        ArrayList<Phong> phongList = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM PHONG_TRO p "
                + "JOIN NHA_TRO n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN LOAI_PHONG l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "LEFT JOIN ANH_PHONG_TRO a ON p.ID_Phong = a.ID_Phong "
                + "WHERE p.ID_NhaTro = ? AND p.Trang_thai = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            // Thiết lập tham số ID_NhaTro và trangThaiKhac cho câu lệnh SQL
            ps.setInt(1, idNhaTro);
            ps.setString(2, status); // Truyền trạng thái loại trừ (ví dụ: "D")

            ResultSet rs = ps.executeQuery();

            // Lặp qua các kết quả và thêm vào danh sách
            while (rs.next()) {
                Phong phong = new Phong();
                phong.setID_Phong(rs.getInt("ID_Phong"));
                phong.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                phong.setTenPhongTro(rs.getString("TenPhongTro"));
                phong.setID_NhaTro(rs.getInt("ID_NhaTro"));
                phong.setTenNhaTro(rs.getNString("TenNhaTro"));
                phong.setTang(rs.getInt("Tang"));
                phong.setTrang_thai(rs.getString("Trang_thai"));
                phong.setDien_tich(rs.getFloat("Dien_Tich"));
                phong.setGia(rs.getInt("Gia"));
                phong.setMo_ta(rs.getString("Mo_ta"));
                phongList.add(phong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phongList; // Trả về danh sách các phòng có trạng thái khác trạng thái truyền vào
    }

    // Method to insert a new NhaTro
    public void insertNhaTro(NhaTro nhaTro) {
        String sql = "INSERT INTO NHA_TRO (TenNhaTro, Dia_chi, Mo_ta, ID_ChuTro) VALUES (?, ?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nhaTro.getTenNhaTro());
            statement.setString(2, nhaTro.getDia_chi());
            statement.setString(3, nhaTro.getMo_ta());
            statement.setInt(4, nhaTro.getID_ChuTro());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing NhaTro
    public void updateNhaTro(NhaTro nhaTro) {
        String sql = "UPDATE NHA_TRO SET TenNhaTro = ?, Dia_chi = ?, Mo_ta = ? WHERE ID_NhaTro = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nhaTro.getTenNhaTro());
            statement.setString(2, nhaTro.getDia_chi());
            statement.setString(3, nhaTro.getMo_ta());
            statement.setInt(4, nhaTro.getID_NhaTro());

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NhaTro> getAllNhaTroWithParam(String searchParam, Integer id_chu_tro) {
        List<NhaTro> nhaTro = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        ChuTroDAO ctdao = new ChuTroDAO();

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT nt.*, COUNT(pt.ID_Phong) AS roomNumber ")
                    .append("FROM nha_tro nt ")
                    .append("LEFT JOIN phong_tro pt ON nt.ID_NhaTro = pt.ID_NhaTro ")
                    .append("WHERE 1 = 1 ");

            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND nt.TenNhaTro LIKE ? ");
                list.add("%" + searchParam + "%");
            }

            if (id_chu_tro != null) {
                query.append(" AND nt.ID_chutro = ? ");
                list.add(id_chu_tro);
            }

            query.append("GROUP BY nt.ID_NhaTro, nt.TenNhaTro, nt.Dia_chi, nt.Mo_ta ")
                    .append("ORDER BY nt.ID_NhaTro ASC");

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    NhaTro nt = new NhaTro();
                    nt.setID_NhaTro(rs.getInt("ID_NhaTro"));
                    nt.setTenNhaTro(rs.getString("TenNhaTro"));
                    nt.setMo_ta(rs.getString("Mo_ta"));
                    nt.setDia_chi(rs.getString("Dia_chi"));
                    nt.setRoomNumber(rs.getInt("roomNumber"));  // Set the room count

                    // Get associated ChuTro details
                    ChuTro chuTro = ctdao.getChuTroById(rs.getInt("ID_chutro"));
                    nt.setChuTro(chuTro);

                    // Fetch images for the current NhaTro
                    nt.setAnhNhaTro(getImagesForNhaTro(nt.getID_NhaTro()));

                    nhaTro.add(nt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaTro;
    }

    public void saveImages(int nhaTroId, List<String> imageUrls) {
        try {
            String query = "INSERT INTO anh_nha_tro (ID_NhaTro, URL_AnhNhaTro) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            for (String imageUrl : imageUrls) {
                ps.setInt(1, nhaTroId);
                ps.setString(2, imageUrl);
                ps.addBatch();
            }

            ps.executeBatch();  // Execute the batch of insert statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImagesForNhaTro(int nhaTroId) {
        List<String> imageUrls = new ArrayList<>();

        try {
            String query = "SELECT URL_AnhNhaTro FROM anh_nha_tro WHERE ID_NhaTro = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, nhaTroId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                imageUrls.add(rs.getString("URL_AnhNhaTro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageUrls;
    }

    public int saveNhaTro(NhaTro nhaTro) {
        try {
            String query = "INSERT INTO nha_tro (TenNhaTro, Dia_chi, Mo_ta, ID_ChuTro) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nhaTro.getTenNhaTro());
            ps.setString(2, nhaTro.getDia_chi());
            ps.setString(3, nhaTro.getMo_ta());
            ps.setInt(4, nhaTro.getID_ChuTro());

            ps.executeUpdate();

            // Retrieve the generated ID with a separate query using a unique field, such as TenNhaTro
            return getNhaTroId(nhaTro.getTenNhaTro(), nhaTro.getDia_chi(), nhaTro.getID_ChuTro());
        } catch (SQLException e) {
            System.out.println("Lỗi add nhà " + e);
        }
        return -1;  // Return -1 if insertion fails
    }

    public int getNhaTroId(String tenNhaTro, String diaChi, int chuTroId) {
        int nhaTroId = -1;  // Default to -1 if no ID is found
        try {
            String query = "SELECT ID_NhaTro FROM nha_tro WHERE TenNhaTro = ? AND Dia_chi = ? AND ID_ChuTro = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tenNhaTro);
            ps.setString(2, diaChi);
            ps.setInt(3, chuTroId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhaTroId = rs.getInt("ID_NhaTro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhaTroId;  // Return the ID if found, otherwise -1
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public List<NhaTro> Paging(List<NhaTro> nahTro, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, nahTro.size());

        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }

        return nahTro.subList(fromIndex, toIndex);
    }

    // Method to search NhaTro by name or address
    public List<NhaTro> searchNhaTro(String searchQuery) {
        List<NhaTro> list = new ArrayList<>();
        NhaTro nhaTro = null;
        String sql = "SELECT * FROM NHA_TRO WHERE TenNhaTro LIKE ? OR Dia_chi LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(Integer.parseInt(rs.getString("ID_ChuTro")));
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));

                list.add(nhaTro);
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateNhaTro2(NhaTro nhaTro) {
        try {
            String query = "UPDATE nha_tro SET TenNhaTro = ?, Dia_chi = ?, Mo_ta = ? WHERE ID_NhaTro = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nhaTro.getTenNhaTro());
            ps.setString(2, nhaTro.getDia_chi());
            ps.setString(3, nhaTro.getMo_ta());
            ps.setInt(4, nhaTro.getID_NhaTro());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteImage(int nhaTroId, String imageUrl) {
        try {
            String query = "DELETE FROM anh_nha_tro WHERE ID_NhaTro = ? AND URL_AnhNhaTro = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, nhaTroId);
            ps.setString(2, imageUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(int nhaTroId, String imageUrl) {
        try {
            String query = "INSERT INTO anh_nha_tro (ID_NhaTro, URL_AnhNhaTro) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, nhaTroId);
            ps.setString(2, imageUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNhaTro(int nhaTroId) {
        String sql = "DELETE FROM nha_tro WHERE ID_NhaTro = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nhaTroId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPhongTro(int nhaTroId) throws SQLException {
        String query = "SELECT COUNT(*) FROM phong_tro WHERE ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, nhaTroId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if there are associated rooms
                }
            }
        }
        return false;
    }

    public void deleteAnhNhaTro(int nhaTroId) throws SQLException {
        String query = "DELETE FROM anh_nha_tro WHERE ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, nhaTroId);
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) {
        NhaTroDAO dAO = new NhaTroDAO();
        List<String> list = dAO.getImagesForNhaTro(2);
        for (String string : list) {
            System.out.println(string);
        }
//        // Kiểm tra phương thức getAll
//        System.out.println("Danh sách nhà trọ:");
//        ArrayList<NhaTro> danhSachNhaTro = nhaTroDAO.getAll();
//        for (NhaTro nhaTro : danhSachNhaTro) {
//            System.out.println("ID: " + nhaTro.getID_NhaTro());
//            System.out.println("Tên nhà trọ: " + nhaTro.getTenNhaTro());
//            System.out.println("ID chủ trọ: " + nhaTro.getID_ChuTro());
//            System.out.println("Địa chỉ: " + nhaTro.getDia_chi());
//            System.out.println("--------------------------");
//        }
//
//        // Kiểm tra phương thức getAllPhongTro với một ID nhà trọ cụ thể (ví dụ: ID = 1)
//        int idNhaTro = 1;
//        System.out.println("Danh sách phòng trong nhà trọ có ID = " + idNhaTro + ":");
//        ArrayList<Phong> danhSachPhong = nhaTroDAO.getAllPhongTro(idNhaTro);
//        for (Phong phong : danhSachPhong) {
//            System.out.println("ID Phòng: " + phong.getID_Phong());
//            System.out.println("Tên phòng: " + phong.getTenPhongTro());
//            System.out.println("ID Nhà trọ: " + phong.getID_NhaTro());
//            System.out.println("Tầng: " + phong.getTang());
//            System.out.println("Trạng thái: " + phong.getTrang_thai());
//            System.out.println("Diện tích: " + phong.getDien_tich());
//            System.out.println("Giá: " + phong.getGia());
//            System.out.println(nhaTroDAO.getNhaTroById(1));
//        }
//        NhaTroDAO dao = new NhaTroDAO();
//
//        // Tạo đối tượng NhaTro
//        NhaTro nhaTro = new NhaTro(1, "Nha Tro A", "123 Đường A", 1, "Phòng thoáng mát, giá rẻ");
//
//        // Gọi phương thức để chèn NhaTro vào cơ sở dữ liệu
//        dao.insertNhaTro(nhaTro);
//
//        System.out.println("Insert completed!");
    }
}
