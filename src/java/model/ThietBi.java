package model;

import java.sql.Date;

public class ThietBi {
    private int ID_ThietBi;
    private String TenThietBi;
    private int Gia_tien;
    private String Mo_ta;
    private String So_luong;
    private int so_luong_con_lai;
    private int so_luong_da_them;
    private Date thoiGianBaoTri;
    private String moTaBaoTri;
    private Phong phong;
    private int ID_ThietBiPhong;
    private String tenNhaTro;

    public ThietBi() {
    }

    public ThietBi(int ID_ThietBi, String TenThietBi, int Gia_tien, String Mo_ta, String So_luong) {
        this.ID_ThietBi = ID_ThietBi;
        this.TenThietBi = TenThietBi;
        this.Gia_tien = Gia_tien;
        this.Mo_ta = Mo_ta;
        this.So_luong = So_luong;
    }

    public Phong getPhong() {
        return phong;
    }

    public int getID_ThietBiPhong() {
        return ID_ThietBiPhong;
    }

    public String getTenNhaTro() {
        return tenNhaTro;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public void setID_ThietBiPhong(int ID_ThietBiPhong) {
        this.ID_ThietBiPhong = ID_ThietBiPhong;
    }

    public void setTenNhaTro(String tenNhaTro) {
        this.tenNhaTro = tenNhaTro;
    }

    public int getID_ThietBi() {
        return ID_ThietBi;
    }

    public void setID_ThietBi(int ID_ThietBi) {
        this.ID_ThietBi = ID_ThietBi;
    }

    public String getTenThietBi() {
        return TenThietBi;
    }

    public void setTenThietBi(String TenThietBi) {
        this.TenThietBi = TenThietBi;
    }

    public int getGia_tien() {
        return Gia_tien;
    }

    public void setGia_tien(int Gia_tien) {
        this.Gia_tien = Gia_tien;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    public String getSo_luong() {
        return So_luong;
    }

    public void setSo_luong(String So_luong) {
        this.So_luong = So_luong;
    }

    public int getSo_luong_con_lai() {
        return so_luong_con_lai;
    }

    public void setSo_luong_con_lai(int so_luong_con_lai) {
        this.so_luong_con_lai = so_luong_con_lai;
    }

    public int getSo_luong_da_them() {
        return so_luong_da_them;
    }

    public void setSo_luong_da_them(int so_luong_da_them) {
        this.so_luong_da_them = so_luong_da_them;
    }

    public Date getThoiGianBaoTri() {
        return thoiGianBaoTri;
    }

    public void setThoiGianBaoTri(Date thoiGianBaoTri) {
        this.thoiGianBaoTri = thoiGianBaoTri;
    }

    public String getMoTaBaoTri() {
        return moTaBaoTri;
    }

    public void setMoTaBaoTri(String moTaBaoTri) {
        this.moTaBaoTri = moTaBaoTri;
    }

    @Override
    public String toString() {
        return "ThietBi{" + "ID_ThietBi=" + ID_ThietBi + ", TenThietBi=" + TenThietBi + ", Gia_tien=" + Gia_tien + ", Mo_ta=" + Mo_ta + ", So_luong=" + So_luong + ", thoiGianBaoTri=" + thoiGianBaoTri + ", moTaBaoTri=" + moTaBaoTri + '}';
    }
}