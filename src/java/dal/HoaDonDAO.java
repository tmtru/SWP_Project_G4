/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.DichVu;
import model.HoaDon;
import model.Phong;

/**
 *
 * @author Admin
 */
public class HoaDonDAO extends DBContext {

    // Phương thức lấy tất cả hóa đơn theo ID nhà trọ kèm theo dịch vụ
    public List<HoaDon> getAllHoaDonByNhaTroID(int idNhaTro) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.HinhThucThanhToan, "
                + "hd.MaGiaoDich, hd.NguoiTao, hd.TienDaThanhToan, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "INNER JOIN phong_tro pt ON h.ID_PhongTro = pt.ID_Phong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE pt.ID_NhaTro = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idNhaTro);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int idHoaDon = rs.getInt("ID_HoaDon");
                HoaDon hoaDon = findHoaDonById(hoaDons, idHoaDon);
                
                if (hoaDon == null) {
                    hoaDon = new HoaDon();
                    hoaDon.setID_HoaDon(idHoaDon);
                    hoaDon.setID_HopDong(rs.getInt("ID_HopDong"));
                    hoaDon.setNgay(rs.getDate("Ngay"));
                    hoaDon.setTrang_thai(rs.getInt("Trang_thai"));
                    hoaDon.setTong_gia_tien(rs.getInt("Tong_gia_tien"));
                    hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                    hoaDon.setHinhThucChuyenKhoan(rs.getString("HinhThucThanhToan"));
                    hoaDon.setMaGiaoDich(rs.getString("MaGiaoDich"));
                    hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                    hoaDon.setTienDaThanhToan(rs.getInt("TienDaThanhToan"));
                    hoaDon.setDichVus(new ArrayList<>()); // Khởi tạo danh sách dịch vụ
                    hoaDons.add(hoaDon); // Thêm hóa đơn mới vào danh sách
                }

                // Thêm dịch vụ vào danh sách dịch vụ của hóa đơn
                int idDichVu = rs.getInt("ID_DichVu");
                if (idDichVu != 0) { // Nếu có dịch vụ
                    DichVu dichVu = new DichVu();
                    dichVu.setID_DichVu(idDichVu);
                    dichVu.setTenDichVu(rs.getString("TenDichVu"));
                    dichVu.setDon_gia(rs.getInt("Don_gia"));
                    dichVu.setDon_vi(rs.getString("Don_vi"));
                    dichVu.setMo_ta(rs.getString("Mo_ta"));
                    hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về danh sách hóa đơn
        return hoaDons;
    }

    // Phương thức tìm hóa đơn trong danh sách theo ID
    private HoaDon findHoaDonById(List<HoaDon> hoaDons, int idHoaDon) {
        for (HoaDon hoaDon : hoaDons) {
            if (hoaDon.getID_HoaDon() == idHoaDon) {
                return hoaDon; // Trả về hóa đơn nếu tìm thấy
            }
        }
        return null; // Không tìm thấy hóa đơn
    }
    
    public List<DichVu> getChiTietDichVuByHoaDonID(int idHoaDon) {
        List<DichVu> dichVus = new ArrayList<>();
        String sql = "SELECT dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi "
                + "FROM hoa_don_dich_vu hdv "
                + "INNER JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hdv.ID_HoaDon = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idHoaDon);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                DichVu dichVu = new DichVu();
                dichVu.setID_DichVu(rs.getInt("ID_DichVu"));
                dichVu.setTenDichVu(rs.getString("TenDichVu"));
                dichVu.setDon_gia(rs.getInt("Don_gia"));
                dichVu.setDon_vi(rs.getString("Don_vi"));
                dichVu.setMo_ta(rs.getString("Mo_ta"));

                // Lấy các thông tin chi tiết khác
                int chiSoCu = rs.getInt("ChiSo_Cu");
                int chiSoMoi = rs.getInt("ChiSo_Moi");
                int dauNguoi = rs.getInt("DauNguoi");

                // Thêm thông tin chi tiết vào đối tượng dịch vụ
                // (Có thể cần thêm các thuộc tính vào lớp DichVu để lưu thông tin này)
                dichVu.setChiSoCu(chiSoCu);
                dichVu.setChiSoMoi(chiSoMoi);
                dichVu.setDauNguoi(dauNguoi);
                dichVu.setID_HoaDon(idHoaDon);
                
                dichVus.add(dichVu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dichVus;
    }
    public static void main(String[] args) {
        // Tạo đối tượng HoaDonDAO
        HoaDonDAO hoaDonDAO = new HoaDonDAO();

        // Kiểm tra phương thức getAllHoaDonByNhaTroID
        int idNhaTro = 1; // Thay đổi ID nhà trọ theo yêu cầu
        List<HoaDon> hoaDons = hoaDonDAO.getAllHoaDonByNhaTroID(idNhaTro);
        
        System.out.println("Danh sách hóa đơn cho nhà trọ ID: " + idNhaTro);
        for (HoaDon hoaDon : hoaDons) {
            System.out.println("ID Hóa Đơn: " + hoaDon.getID_HoaDon() +
                               ", Ngày: " + hoaDon.getNgay() +
                               ", Trạng Thái: " + hoaDon.getTrang_thai() +
                               ", Tổng Giá Tiền: " + hoaDon.getTong_gia_tien());
        }

        // Kiểm tra phương thức getChiTietDichVuByHoaDonID
        int idHoaDon = 18; // Thay đổi ID hóa đơn theo yêu cầu
        List<DichVu> dichVus = hoaDonDAO.getChiTietDichVuByHoaDonID(idHoaDon);
        
        System.out.println("\nChi tiết dịch vụ cho hóa đơn ID: " + idHoaDon);
        for (DichVu dichVu : dichVus) {
            System.out.println("ID Dịch Vụ: " + dichVu.getID_DichVu() +
                               ", Tên Dịch Vụ: " + dichVu.getTenDichVu() +
                               ", Đơn Giá: " + dichVu.getDon_gia() +
                               ", Đơn Vị: " + dichVu.getDon_vi() +
                               ", Mô Tả: " + dichVu.getMo_ta() +
                               ", Chỉ Số Cũ: " + dichVu.getChiSoCu() +
                               ", Chỉ Số Mới: " + dichVu.getChiSoMoi() +
                               ", Đầu Người: " + dichVu.getDauNguoi());
        }
    }
    // Phương thức lấy thông tin phòng từ hóa đơn theo ID hóa đơn
    public Phong getRoomOfHoaDon(int idHoaDon) {
        Phong phongTro = null;
        String sql = "SELECT pt.ID_Phong, pt.TenPhongTro, pt.Gia, pt.Trang_thai " +
                     "FROM hoa_don hd " +
                     "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong " +
                     "INNER JOIN phong_tro pt ON h.ID_PhongTro = pt.ID_Phong " +
                     "WHERE hd.ID_HoaDon = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setInt(1, idHoaDon);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                phongTro = new Phong();
                phongTro.setID_Phong(rs.getInt("ID_Phong"));
                phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                phongTro.setGia(rs.getInt("Gia"));
                phongTro.setTrang_thai(rs.getString("Trang_thai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phongTro;
    }
    
}
