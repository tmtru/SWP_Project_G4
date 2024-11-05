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
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.text.SimpleDateFormat;
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
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.MoTa, "
                + "hd.NguoiTao,  "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "INNER JOIN phong_tro pt ON h.ID_PhongTro = pt.ID_Phong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE pt.ID_NhaTro = ? AND hd.isActive=1 ORDER BY hd.Ngay DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idNhaTro);
            try (ResultSet rs = ps.executeQuery()) { // Sử dụng try-with-resources cho ResultSet
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
                        hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                        hoaDon.setMoTa(rs.getNString("MoTa"));
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
                        dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                        dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                        dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                        dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                        hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    public HoaDon getHoaDonById(int idHoaDon) {
        HoaDon hoaDon = null;
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.MoTa, "
                + "hd.NguoiTao, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hd.ID_HoaDon = ? AND hd.isActive = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                List<DichVu> dichVus = new ArrayList<>();

                while (rs.next()) {
                    if (hoaDon == null) {
                        hoaDon = new HoaDon();
                        hoaDon.setID_HoaDon(rs.getInt("ID_HoaDon"));
                        hoaDon.setID_HopDong(rs.getInt("ID_HopDong"));
                        hoaDon.setNgay(rs.getDate("Ngay"));
                        hoaDon.setTrang_thai(rs.getInt("Trang_thai"));
                        hoaDon.setTong_gia_tien(rs.getInt("Tong_gia_tien"));
                        hoaDon.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                        hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                        hoaDon.setMoTa(rs.getNString("MoTa"));
                        hoaDon.setDichVus(dichVus);
                    }

                    int idDichVu = rs.getInt("ID_DichVu");
                    if (idDichVu != 0) {
                        DichVu dichVu = new DichVu();
                        dichVu.setID_DichVu(idDichVu);
                        dichVu.setTenDichVu(rs.getString("TenDichVu"));
                        dichVu.setDon_gia(rs.getInt("Don_gia"));
                        dichVu.setDon_vi(rs.getString("Don_vi"));
                        dichVu.setMo_ta(rs.getString("Mo_ta"));
                        dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                        dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                        dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                        dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                        dichVus.add(dichVu);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDon; // Return HoaDon object, which may be null if not found
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
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi , hdv.CurrentPrice "
                + "FROM hoa_don_dich_vu hdv "
                + "INNER JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hdv.ID_HoaDon = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) { // Sử dụng try-with-resources cho ResultSet
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
                    dichVu.setOldPrice(rs.getInt("CurrentPrice"));

                    dichVus.add(dichVu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dichVus;
    }

// Phương thức để vô hiệu hóa hóa đơn (isActive = 0)
    public void deActiveHoaDon(int idHoaDon) {
        String sql = "UPDATE hoa_don SET isActive = 0 WHERE ID_HoaDon = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHoaDon);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Hóa đơn ID: " + idHoaDon + " đã được vô hiệu hóa.");
            } else {
                System.out.println("Không tìm thấy hóa đơn với ID: " + idHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Phương thức lấy thông tin phòng từ hóa đơn theo ID hóa đơn
    public Phong getRoomOfHoaDon(int idHoaDon) {
        Phong phongTro = null;
        String sql = "SELECT pt.ID_Phong, pt.TenPhongTro, pt.Gia, pt.Trang_thai "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "INNER JOIN phong_tro pt ON h.ID_PhongTro = pt.ID_Phong "
                + "WHERE hd.ID_HoaDon = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) { // Sử dụng try-with-resources cho ResultSet
                if (rs.next()) {
                    phongTro = new Phong();
                    phongTro.setID_Phong(rs.getInt("ID_Phong"));
                    phongTro.setTenPhongTro(rs.getString("TenPhongTro"));
                    phongTro.setGia(rs.getInt("Gia"));
                    phongTro.setTrang_thai(rs.getString("Trang_thai"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phongTro;
    }

    //get Hoa don thoe ngay thang nam voi id cua nha tro
    public List<HoaDon> getHoaDonByDateRange(LocalDate startDate, LocalDate endDate, int idNhaTro) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.MoTa, "
                + "hd.NguoiTao,  "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "INNER JOIN phong_tro pt ON h.ID_PhongTro = pt.ID_Phong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hd.Ngay BETWEEN ? AND ? AND pt.ID_NhaTro = ? AND hd.isActive = 1 ORDER BY hd.ID_HoaDon DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ps.setInt(3, idNhaTro);

            try (ResultSet rs = ps.executeQuery()) {
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
                        hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                        hoaDon.setMoTa(rs.getNString("MoTa"));
                        hoaDon.setDichVus(new ArrayList<>());
                        hoaDons.add(hoaDon);
                    }

                    int idDichVu = rs.getInt("ID_DichVu");
                    if (idDichVu != 0) {
                        DichVu dichVu = new DichVu();
                        dichVu.setID_DichVu(idDichVu);
                        dichVu.setTenDichVu(rs.getString("TenDichVu"));
                        dichVu.setDon_gia(rs.getInt("Don_gia"));
                        dichVu.setDon_vi(rs.getString("Don_vi"));
                        dichVu.setMo_ta(rs.getString("Mo_ta"));
                        dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                        dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                        dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                        dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                        hoaDon.getDichVus().add(dichVu);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    //get hoa don tu ngay nao den nagy nao cua phong nao
    public List<HoaDon> getHoaDonByRoomId(int roomId) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.MoTa, "
                + "hd.NguoiTao, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE h.ID_PhongTro = ? AND hd.isActive = 1 ORDER BY hd.ID_HoaDon DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);

            try (ResultSet rs = ps.executeQuery()) {
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
                        hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                        hoaDon.setMoTa(rs.getNString("MoTa"));
                        hoaDon.setDichVus(new ArrayList<>()); // Khởi tạo danh sách dịch vụ
                        hoaDons.add(hoaDon);
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
                        dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                        dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                        dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                        dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                        hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    //get hoa don tu ngay nao den nagy nao cua phong nao
    public List<HoaDon> getHoaDonByRoomId(int roomId, LocalDate startDate, LocalDate endDate) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.MoTa,"
                + "hd.NguoiTao, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "INNER JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE h.ID_PhongTro = ? AND hd.Ngay BETWEEN ? AND ? AND hd.isActive = 1 ORDER BY hd.ID_HoaDon DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setDate(2, Date.valueOf(startDate)); // Thêm tham số startDate
            ps.setDate(3, Date.valueOf(endDate));   // Thêm tham số endDate

            try (ResultSet rs = ps.executeQuery()) {
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
                        hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                        hoaDon.setMoTa(rs.getNString("MoTa"));
                        hoaDon.setDichVus(new ArrayList<>()); // Khởi tạo danh sách dịch vụ
                        hoaDons.add(hoaDon);
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
                        dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                        dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                        dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                        dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                        hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    public List<HoaDon> getHoaDonByHopDong(int idHopDong) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.NguoiTao, hd.MoTa, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hd.ID_HopDong = ? AND hd.isActive=1 ORDER BY hd.ID_HoaDon DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHopDong); // Đặt ID hợp đồng vào tham số truy vấn
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
                    hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                    hoaDon.setMoTa(rs.getNString("MoTa"));
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
                    dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                    dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                    dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                    dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                    hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }
//lay hoa dơn theo thnags hiện tại

    public List<HoaDon> getHoaDonByCurrentMonthAndByIdHopDong(int idHopDong) {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sql = "SELECT hd.ID_HoaDon, hd.ID_HopDong, hd.Ngay, hd.Trang_thai, "
                + "hd.Tong_gia_tien, hd.NgayThanhToan, hd.NguoiTao, hd.MoTa, "
                + "dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi, dv.Mo_ta, "
                + "hdv.ChiSo_Cu, hdv.ChiSo_Moi, hdv.DauNguoi, hdv.CurrentPrice "
                + "FROM hoa_don hd "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "LEFT JOIN dich_vu dv ON hdv.ID_DichVu = dv.ID_DichVu "
                + "WHERE hd.ID_HopDong = ? AND hd.isActive=1 "
                + "AND MONTH(hd.Ngay) = MONTH(CURRENT_DATE()) "
                + "AND YEAR(hd.Ngay) = YEAR(CURRENT_DATE()) "
                + "ORDER BY hd.ID_HoaDon DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHopDong); // Đặt ID hợp đồng vào tham số truy vấn
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
                    hoaDon.setNguoiTao(rs.getString("NguoiTao"));
                    hoaDon.setMoTa(rs.getNString("MoTa"));
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
                    dichVu.setChiSoCu(rs.getInt("ChiSo_Cu"));
                    dichVu.setChiSoMoi(rs.getInt("ChiSo_Moi"));
                    dichVu.setDauNguoi(rs.getInt("DauNguoi"));
                    dichVu.setOldPrice(rs.getInt("CurrentPrice"));
                    hoaDon.getDichVus().add(dichVu); // Thêm dịch vụ vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoaDons;
    }

    //method  lấy các ID hợp đồng mà có roomId là idRoom 
    public List<Integer> getHopDongOfRentedRoom(int idRoom) {
        List<Integer> hopDongIds = new ArrayList<>();
        String sql = "SELECT h.ID_HopDong "
                + "FROM hop_dong h "
                + "WHERE h.ID_PhongTro = ? ";
//               + "AND h.isActive = 1"; // Chỉ lấy các hợp đồng đang hoạt động

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRoom); // Đặt giá trị idRoom vào tham số truy vấn
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idHopDong = rs.getInt("ID_HopDong");
                hopDongIds.add(idHopDong); // Thêm id hợp đồng vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hopDongIds;
    }

    public List<Integer> getHopDongHienTaiOfRentedRoom(int idRoom) {
        List<Integer> hopDongIds = new ArrayList<>();
        String sql = "SELECT h.ID_HopDong "
                + "FROM hop_dong h "
                + "WHERE h.ID_PhongTro = ? "
                + "AND h.Ngay_gia_tri <= CURDATE() "
                + "AND h.Ngay_het_han >= CURDATE()"; // Kiểm tra hợp đồng có hiệu lực hiện tại

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRoom); // Đặt giá trị idRoom vào truy vấn
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idHopDong = rs.getInt("ID_HopDong");
                hopDongIds.add(idHopDong); // Thêm ID hợp đồng vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hopDongIds;
    }

    // Phương thức thêm một hóa đơn mới cùng với các dịch vụ
    public void addHoaDon(HoaDon hoaDon, List<DichVu> danhSachDichVu, String nguoiTao) {
        String sqlHoaDon = "INSERT INTO hoa_don (ID_HopDong, Ngay, Trang_thai, Tong_gia_tien,  NguoiTao,MoTa, isActive) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, hoaDon.getID_HopDong());
            ps.setDate(2, new Date(hoaDon.getNgay().getTime()));
            ps.setInt(3, 0);
            ps.setInt(4, hoaDon.getTong_gia_tien());
            ps.setString(5, nguoiTao);
            ps.setNString(6, hoaDon.getMoTa());
            ps.setInt(7, 1);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                // Lấy ID của hóa đơn vừa được thêm
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long idHoaDon = generatedKeys.getLong(1);
                        System.out.println("Hóa đơn đã được thêm thành công với ID: " + idHoaDon);

                        // Thêm các dịch vụ vào bảng hoa_don_dich_vu
                        String sqlDichVu = "INSERT INTO hoa_don_dich_vu (ID_HoaDon, ID_DichVu, ChiSo_Cu, ChiSo_Moi, DauNguoi, CurrentPrice) VALUES (?, ?, ?, ?, ?,?)";
                        try (PreparedStatement psDichVu = connection.prepareStatement(sqlDichVu)) {
                            for (DichVu dichVu : danhSachDichVu) {
                                psDichVu.setLong(1, idHoaDon);
                                psDichVu.setInt(2, dichVu.getID_DichVu());
                                psDichVu.setInt(3, dichVu.getChiSoCu());
                                psDichVu.setInt(4, dichVu.getChiSoMoi());
                                psDichVu.setInt(5, dichVu.getDauNguoi());
                                psDichVu.setInt(6, dichVu.getOldPrice());

                                psDichVu.executeUpdate();
                            }
                        }
                    }
                }
            } else {
                System.out.println("Không thể thêm hóa đơn.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //lấy chỉ số điện mới cho từng phong
    public int getLatestElectricityReadingByPhongID(int idPhong) {
        Integer latestReading = null; // Initialize to null to represent no reading found
        String sql = "SELECT MAX(hdv.ChiSo_Moi) AS ChiSo_Moi "
                + "FROM phong_tro pt "
                + "LEFT JOIN hop_dong h ON pt.ID_Phong = h.ID_PhongTro "
                + "LEFT JOIN hoa_don hd ON h.ID_HopDong = hd.ID_HopDong "
                + "LEFT JOIN hoa_don_dich_vu hdv ON hd.ID_HoaDon = hdv.ID_HoaDon "
                + "WHERE pt.ID_Phong = ? AND hd.isActive = 1 "
                + "AND hdv.ID_DichVu = (SELECT ID_DichVu FROM dich_vu WHERE TenDichVu = 'Điện')";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    latestReading = rs.getInt("ChiSo_Moi"); // Get the maximum reading
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return latestReading; // Return the latest reading or null if not found
    }

    public List<DichVu> getAllActiveServicesByContractId(int contractId) {
        List<DichVu> activeServices = new ArrayList<>();
        String sql = "SELECT dv.ID_DichVu, dv.TenDichVu, dv.Don_gia, dv.Don_vi "
                + "FROM dich_vu dv "
                + "JOIN dich_vu_hop_dong dvhd ON dv.ID_DichVu = dvhd.ID_DichVu "
                + "JOIN hop_dong hd ON dvhd.ID_HopDong = hd.ID_HopDong "
                + "WHERE hd.ID_HopDong = ? AND dv.isActive = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contractId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DichVu service = new DichVu();
                    service.setID_DichVu(rs.getInt("ID_DichVu"));
                    service.setTenDichVu(rs.getString("TenDichVu"));
                    service.setDon_gia(rs.getInt("Don_gia"));
                    service.setDon_vi(rs.getString("Don_vi"));
                    activeServices.add(service);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return activeServices;
    }

    public int getSoLuongNguoiByContractId(int contractId) {
        int soNguoi = 0; // Default value if not found
        String sql = "SELECT So_nguoi FROM hop_dong WHERE ID_HopDong = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contractId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    soNguoi = rs.getInt("So_nguoi");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return soNguoi; // Returns 0 if not found
    }

    public static void main(String[] args) {
        // Create an instance of HoaDonDAO
        HoaDonDAO hoaDonDAO = new HoaDonDAO();

        // Specify the ID of the room you want to check
        // Replace with a valid contract ID to test
        int contractId = 3;

        // Call the method to get all active services for the contract
        List<DichVu> activeServices = hoaDonDAO.getAllActiveServicesByContractId(contractId);

        // Print the results
        if (activeServices != null && !activeServices.isEmpty()) {
            for (DichVu service : activeServices) {
                System.out.println("Service ID: " + service.getID_DichVu()
                        + ", Name: " + service.getTenDichVu()
                        + ", Price: " + service.getDon_gia()
                        + ", Unit: " + service.getDon_vi());
            }
        }
        System.out.println(hoaDonDAO.getHoaDonById(4).getDichVus().size());

    }

}
