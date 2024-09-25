/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class DichVu {
    private int ID_DichVu;
    private String TenDichVu;
    private int Don_gia;
    private String Don_vi, Mo_ta;

    public DichVu(int ID_DichVu, String TenDichVu, int Don_gia, String Don_vi, String Mo_ta) {
        this.ID_DichVu = ID_DichVu;
        this.TenDichVu = TenDichVu;
        this.Don_gia = Don_gia;
        this.Don_vi = Don_vi;
        this.Mo_ta = Mo_ta;
    }

    public DichVu() {
    }
    

    public int getID_DichVu() {
        return ID_DichVu;
    }

    public void setID_DichVu(int ID_DichVu) {
        this.ID_DichVu = ID_DichVu;
    }

    public String getTenDichVu() {
        return TenDichVu;
    }

    public void setTenDichVu(String TenDichVu) {
        this.TenDichVu = TenDichVu;
    }

    public int getDon_gia() {
        return Don_gia;
    }

    public void setDon_gia(int Don_gia) {
        this.Don_gia = Don_gia;
    }

    public String getDon_vi() {
        return Don_vi;
    }

    public void setDon_vi(String Don_vi) {
        this.Don_vi = Don_vi;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    @Override
    public String toString() {
        return "DichVu{" + "ID_DichVu=" + ID_DichVu + ", TenDichVu=" + TenDichVu + ", Don_gia=" + Don_gia + ", Don_vi=" + Don_vi + ", Mo_ta=" + Mo_ta + '}';
    }

}
