package model;

import java.sql.Date;

public class HopDong {

    private int ID_HopDong;
    private int ID_KhachThue;
    private int ID_Phongtro;
    private Date Ngay_gia_tri;
    private Date Ngay_het_han;
    private int Tien_Coc;

    public HopDong() {
    }

    public HopDong(int ID_HopDong, int ID_KhachThue, int ID_Phongtro, Date Ngay_gia_tri, Date Ngay_het_han, int Tien_Coc) {
        this.ID_HopDong = ID_HopDong;
        this.ID_KhachThue = ID_KhachThue;
        this.ID_Phongtro = ID_Phongtro;
        this.Ngay_gia_tri = Ngay_gia_tri;
        this.Ngay_het_han = Ngay_het_han;
        this.Tien_Coc = Tien_Coc;
    }

    public int getID_HopDong() {
        return ID_HopDong;
    }

    public int getID_KhachThue() {
        return ID_KhachThue;
    }

    public int getID_Phongtro() {
        return ID_Phongtro;
    }

    public Date getNgay_gia_tri() {
        return Ngay_gia_tri;
    }

    public Date getNgay_het_han() {
        return Ngay_het_han;
    }

    public int getTien_Coc() {
        return Tien_Coc;
    }

    public void setID_HopDong(int ID_HopDong) {
        this.ID_HopDong = ID_HopDong;
    }

    public void setID_KhachThue(int ID_KhachThue) {
        this.ID_KhachThue = ID_KhachThue;
    }

    public void setID_Phongtro(int ID_Phongtro) {
        this.ID_Phongtro = ID_Phongtro;
    }

    public void setNgay_gia_tri(Date Ngay_gia_tri) {
        this.Ngay_gia_tri = Ngay_gia_tri;
    }

    public void setNgay_het_han(Date Ngay_het_han) {
        this.Ngay_het_han = Ngay_het_han;
    }

    public void setTien_Coc(int Tien_Coc) {
        this.Tien_Coc = Tien_Coc;
    }

    @Override
    public String toString() {
        return "HopDong{" + "ID_HopDong=" + ID_HopDong + ", ID_KhachThue=" + ID_KhachThue + ", ID_Phongtro=" + ID_Phongtro + ", Ngay_gia_tri=" + Ngay_gia_tri + ", Ngay_het_han=" + Ngay_het_han + ", Tien_Coc=" + Tien_Coc + '}';
    }
}
