/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDon {
    private int ID_HoaDon;
    private int ID_HopDong;
    private Date Ngay, NgayThanhToan;
    private int Trang_thai;
    private int Tong_gia_tien;
    private String HinhThucChuyenKhoan, MaGiaoDich, NguoiTao;
    private int TienDaThanhToan;
    private List<DichVu> DichVus;

    public HoaDon() {
    }

    public HoaDon(int ID_HoaDon, int ID_HopDong, Date Ngay, Date NgayThanhToan, int Trang_thai, int Tong_gia_tien, String HinhThucChuyenKhoan, String MaGiaoDich, String NguoiTao, int TienDaThanhToan, List<DichVu> DichVus) {
        this.ID_HoaDon = ID_HoaDon;
        this.ID_HopDong = ID_HopDong;
        this.Ngay = Ngay;
        this.NgayThanhToan = NgayThanhToan;
        this.Trang_thai = Trang_thai;
        this.Tong_gia_tien = Tong_gia_tien;
        this.HinhThucChuyenKhoan = HinhThucChuyenKhoan;
        this.MaGiaoDich = MaGiaoDich;
        this.NguoiTao = NguoiTao;
        this.TienDaThanhToan = TienDaThanhToan;
        this.DichVus = DichVus;
    }

    public int getID_HoaDon() {
        return ID_HoaDon;
    }

    public void setID_HoaDon(int ID_HoaDon) {
        this.ID_HoaDon = ID_HoaDon;
    }

    public int getID_HopDong() {
        return ID_HopDong;
    }

    public void setID_HopDong(int ID_HopDong) {
        this.ID_HopDong = ID_HopDong;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public Date getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(Date NgayThanhToan) {
        this.NgayThanhToan = NgayThanhToan;
    }

    public int getTrang_thai() {
        return Trang_thai;
    }

    public void setTrang_thai(int Trang_thai) {
        this.Trang_thai = Trang_thai;
    }

    public int getTong_gia_tien() {
        return Tong_gia_tien;
    }

    public void setTong_gia_tien(int Tong_gia_tien) {
        this.Tong_gia_tien = Tong_gia_tien;
    }

    public String getHinhThucChuyenKhoan() {
        return HinhThucChuyenKhoan;
    }

    public void setHinhThucChuyenKhoan(String HinhThucChuyenKhoan) {
        this.HinhThucChuyenKhoan = HinhThucChuyenKhoan;
    }

    public String getMaGiaoDich() {
        return MaGiaoDich;
    }

    public void setMaGiaoDich(String MaGiaoDich) {
        this.MaGiaoDich = MaGiaoDich;
    }

    public String getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(String NguoiTao) {
        this.NguoiTao = NguoiTao;
    }

    public int getTienDaThanhToan() {
        return TienDaThanhToan;
    }

    public void setTienDaThanhToan(int TienDaThanhToan) {
        this.TienDaThanhToan = TienDaThanhToan;
    }

    public List<DichVu> getDichVus() {
        return DichVus;
    }

    public void setDichVus(List<DichVu> DichVus) {
        this.DichVus = DichVus;
    }
    
}
