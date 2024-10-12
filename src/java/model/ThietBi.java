/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hihihihaha
 */
public class ThietBi {
    private int ID_ThietBi;
    private String TenThietBi;
    private int Gia_tien;
    private String Mo_ta;
    private String So_luong;

    public ThietBi() {
    }

    public ThietBi(int ID_ThietBi, String TenThietBi, int Gia_tien, String Mo_ta, String So_luong) {
        this.ID_ThietBi = ID_ThietBi;
        this.TenThietBi = TenThietBi;
        this.Gia_tien = Gia_tien;
        this.Mo_ta = Mo_ta;
        this.So_luong = So_luong;
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

    @Override
    public String toString() {
        return "ThietBi{" + "ID_ThietBi=" + ID_ThietBi + ", TenThietBi=" + TenThietBi + ", Gia_tien=" + Gia_tien + ", Mo_ta=" + Mo_ta + ", So_luong=" + So_luong + '}';
    }
    
    
}
