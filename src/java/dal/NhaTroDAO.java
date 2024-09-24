/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.NhaTro;
import model.Phong;

public class NhaTroDAO extends DBContext {

    public ArrayList<NhaTro> getAll() {
        ArrayList<NhaTro> nhaTroList = new ArrayList<>();
        String sql = "SELECT * FROM NHA_TRO p "
                + "LEFT JOIN ANH_NHA_TRO a ON p.ID_NhaTro = a.ID_NhaTro ";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            // Tạo câu lệnh SQL để lấy tất cả nhà trọ

            // Lặp qua các kết quả và thêm vào danh sách
            while (rs.next()) {
                NhaTro nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(rs.getString("ID_ChuTro"));
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));
                nhaTro.setURL_AnhNhaTro(rs.getNString("URL_AnhNhaTro"));
                nhaTroList.add(nhaTro);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                nhaTro.setID_ChuTro(rs.getString("ID_ChuTro"));
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));
                nhaTro.setURL_AnhNhaTro(rs.getNString("URL_AnhNhaTro"));

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
                phong.setURL_AnhPhongTro(rs.getString("URL_AnhPhongTro"));
                phong.setGia(rs.getInt("Gia"));
                phong.setMo_ta(rs.getString("Mo_ta"));
                phongList.add(phong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phongList; // Trả về danh sách các phòng có trạng thái khác trạng thái truyền vào
    }

    public static void main(String[] args) {
        NhaTroDAO nhaTroDAO = new NhaTroDAO();

        // Kiểm tra phương thức getAll
        System.out.println("Danh sách nhà trọ:");
        ArrayList<NhaTro> danhSachNhaTro = nhaTroDAO.getAll();
        for (NhaTro nhaTro : danhSachNhaTro) {
            System.out.println("ID: " + nhaTro.getID_NhaTro());
            System.out.println("Tên nhà trọ: " + nhaTro.getTenNhaTro());
            System.out.println("ID chủ trọ: " + nhaTro.getID_ChuTro());
            System.out.println("Địa chỉ: " + nhaTro.getDia_chi());
            System.out.println("Địa chỉ: " + nhaTro.getURL_AnhNhaTro());
            System.out.println("--------------------------");
        }

        // Kiểm tra phương thức getAllPhongTro với một ID nhà trọ cụ thể (ví dụ: ID = 1)
        int idNhaTro = 1;
        System.out.println("Danh sách phòng trong nhà trọ có ID = " + idNhaTro + ":");
        ArrayList<Phong> danhSachPhong = nhaTroDAO.getAllPhongTro(idNhaTro);
        for (Phong phong : danhSachPhong) {
            System.out.println("ID Phòng: " + phong.getID_Phong());
            System.out.println("Tên phòng: " + phong.getTenPhongTro());
            System.out.println("ID Nhà trọ: " + phong.getID_NhaTro());
            System.out.println("Tầng: " + phong.getTang());
            System.out.println("Trạng thái: " + phong.getTrang_thai());
            System.out.println("Diện tích: " + phong.getDien_tich());
            System.out.println("Giá: " + phong.getGia());
            System.out.println(nhaTroDAO.getNhaTroById(1));
        }
    }
}
