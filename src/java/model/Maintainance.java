package model;

import java.util.Date;

public class Maintainance {
    private int id_bao_tri;
    private String mo_ta;
    private byte trang_thai_chap_thuan;
    private byte trang_thai_yeu_cau;
    private Date thoi_gian;
    private int ID_ThietBiPhong;
    private int ID_Phong;

    public Maintainance() {
    }

    public Maintainance(int id_bao_tri, String mo_ta, byte trang_thai_chap_thuan, byte trang_thai_yeu_cau, Date thoi_gian) {
        this.id_bao_tri = id_bao_tri;
        this.mo_ta = mo_ta;
        this.trang_thai_chap_thuan = trang_thai_chap_thuan;
        this.trang_thai_yeu_cau = trang_thai_yeu_cau;
        this.thoi_gian = thoi_gian;
    }

    public Maintainance(int id_bao_tri, String mo_ta, byte trang_thai_chap_thuan, byte trang_thai_yeu_cau, Date thoi_gian, int ID_ThietBiPhong, int ID_Phong) {
        this.id_bao_tri = id_bao_tri;
        this.mo_ta = mo_ta;
        this.trang_thai_chap_thuan = trang_thai_chap_thuan;
        this.trang_thai_yeu_cau = trang_thai_yeu_cau;
        this.thoi_gian = thoi_gian;
        this.ID_ThietBiPhong = ID_ThietBiPhong;
        this.ID_Phong = ID_Phong;
    }

    public int getId_bao_tri() {
        return id_bao_tri;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public byte getTrang_thai_chap_thuan() {
        return trang_thai_chap_thuan;
    }

    public byte getTrang_thai_yeu_cau() {
        return trang_thai_yeu_cau;
    }

    public Date getThoi_gian() {
        return thoi_gian;
    }

    public int getID_ThietBiPhong() {
        return ID_ThietBiPhong;
    }

    public int getID_Phong() {
        return ID_Phong;
    }

    public void setId_bao_tri(int id_bao_tri) {
        this.id_bao_tri = id_bao_tri;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public void setTrang_thai_chap_thuan(byte trang_thai_chap_thuan) {
        this.trang_thai_chap_thuan = trang_thai_chap_thuan;
    }

    public void setTrang_thai_yeu_cau(byte trang_thai_yeu_cau) {
        this.trang_thai_yeu_cau = trang_thai_yeu_cau;
    }

    public void setThoi_gian(Date thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public void setID_ThietBiPhong(int ID_ThietBiPhong) {
        this.ID_ThietBiPhong = ID_ThietBiPhong;
    }

    public void setID_Phong(int ID_Phong) {
        this.ID_Phong = ID_Phong;
    }

    @Override
    public String toString() {
        return "Maintainance{" + "id_bao_tri=" + id_bao_tri + ", mo_ta=" + mo_ta + ", trang_thai_chap_thuan=" + trang_thai_chap_thuan + ", trang_thai_yeu_cau=" + trang_thai_yeu_cau + '}';
    }
    
}