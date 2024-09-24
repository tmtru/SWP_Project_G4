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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Phong;

public class PhongDAO extends DBContext {

    public List<Phong> getAllRooms() {
        List<Phong> rooms = new ArrayList<>();
        String sql = "SELECT p.ID_Phong, n.TenNhaTro, p.TenPhong, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "a.URL_PhongTro, l.TenLoaiPhong, p.Gia, l.Mo_ta, p.Trang_thai "
                + "FROM PHONG_TRO p "
                + "JOIN NHA_TRO n ON p.ID_NhaTro = n.ID_NhaTro "
                + "JOIN LOAI_PHONG l ON p.ID_LoaiPhong = l.ID_LoaiPhong "
                + "LEFT JOIN ANH_PHONG_TRO a ON p.ID_Phong = a.ID_Phong";


        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

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
        } catch (SQLException e) {
            System.out.println("Error in RoomDAO.getAllRooms: " + e.getMessage());
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
}
