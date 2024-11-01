package model;

import java.sql.Date;

public class HopDong {

    private int ID_HopDong;
    private int ID_KhachThue;
    private int ID_Phongtro;
    private Date Ngay_gia_tri;
    private Date Ngay_het_han;
    private int Tien_Coc;
    private int ID_Dichvu;
    private int so_nguoi;
    private int Tien_phong;
    private String isActive;
    private String status;
    private String Ghi_chu;
    private String Ly_do;
    private String tenPhongTro;
    private Phong phongTro;

    
    private KhachThue khachThue;


    public Phong getPhongTro() {
        return phongTro;
    }

    public void setPhongTro(Phong phongTro) {
        this.phongTro = phongTro;
    }
    

    public String getTenPhongTro() {
        return tenPhongTro;
    }

    public void setTenPhongTro(String tenPhongTro) {
        this.tenPhongTro = tenPhongTro;
    }

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

    public HopDong(int ID_HopDong, int ID_KhachThue, int ID_Phongtro, Date Ngay_gia_tri, Date Ngay_het_han, int Tien_Coc, int ID_Dichvu, int so_nguoi, int Tien_phong, String isActive, String status, String Ghi_chu, String Ly_do) {
        this.ID_HopDong = ID_HopDong;
        this.ID_KhachThue = ID_KhachThue;
        this.ID_Phongtro = ID_Phongtro;
        this.Ngay_gia_tri = Ngay_gia_tri;
        this.Ngay_het_han = Ngay_het_han;
        this.Tien_Coc = Tien_Coc;
        this.ID_Dichvu = ID_Dichvu;
        this.so_nguoi = so_nguoi;
        this.Tien_phong = Tien_phong;
        this.isActive = isActive;
        this.status = status;
        this.Ghi_chu = Ghi_chu;
        this.Ly_do = Ly_do;
    }

    public int getID_Dichvu() {
        return ID_Dichvu;
    }

    public void setID_Dichvu(int ID_Dichvu) {
        this.ID_Dichvu = ID_Dichvu;
    }

    public int getSo_nguoi() {
        return so_nguoi;
    }

    public void setSo_nguoi(int so_nguoi) {
        this.so_nguoi = so_nguoi;
    }

    public int getTien_phong() {
        return Tien_phong;
    }

    public void setTien_phong(int Tien_phong) {
        this.Tien_phong = Tien_phong;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGhi_chu() {
        return Ghi_chu;
    }

    public void setGhi_chu(String Ghi_chu) {
        this.Ghi_chu = Ghi_chu;
    }

    public String getLy_do() {
        return Ly_do;
    }

    public void setLy_do(String Ly_do) {
        this.Ly_do = Ly_do;
    }


    public KhachThue getKhachThue() {
        return khachThue;
    }

    public void setKhachThue(KhachThue khachThue) {
        this.khachThue = khachThue;
    }


    @Override
    public String toString() {
        return "HopDong{" + "ID_HopDong=" + ID_HopDong + ", ID_KhachThue=" + ID_KhachThue + ", ID_Phongtro=" + ID_Phongtro + ", Ngay_gia_tri=" + Ngay_gia_tri + ", Ngay_het_han=" + Ngay_het_han + ", Tien_Coc=" + Tien_Coc + ", ID_Dichvu=" + ID_Dichvu + ", so_nguoi=" + so_nguoi + ", Tien_phong=" + Tien_phong + ", isActive=" + isActive + ", status=" + status + ", Ghi_chu=" + Ghi_chu + ", Ly_do=" + Ly_do + ", tenPhongTro=" + tenPhongTro + '}';
    }

    
    
}
