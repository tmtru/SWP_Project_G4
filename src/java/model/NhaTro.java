/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class NhaTro {
    private int ID_NhaTro;
    private String TenNhaTro;
    private String Dia_chi;
    private int ID_ChuTro;
    private String Mo_ta;

    public NhaTro() {
    }

    public NhaTro(int ID_NhaTro, String TenNhaTro, String Dia_chi, int ID_ChuTro, String Mo_ta) {
        this.ID_NhaTro = ID_NhaTro;
        this.TenNhaTro = TenNhaTro;
        this.Dia_chi = Dia_chi;
        this.ID_ChuTro = ID_ChuTro;
        this.Mo_ta=Mo_ta;
    }

    public int getID_NhaTro() {
        return ID_NhaTro;
    }

    public void setID_NhaTro(int ID_NhaTro) {
        this.ID_NhaTro = ID_NhaTro;
    }

    public String getTenNhaTro() {
        return TenNhaTro;
    }

    public void setTenNhaTro(String TenNhaTro) {
        this.TenNhaTro = TenNhaTro;
    }

    public String getDia_chi() {
        return Dia_chi;
    }

    public void setDia_chi(String Dia_chi) {
        this.Dia_chi = Dia_chi;
    }

    public int getID_ChuTro() {
        return ID_ChuTro;
    }

    public void setID_ChuTro(int ID_ChuTro) {
        this.ID_ChuTro = ID_ChuTro;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }
    

    @Override
    public String toString() {
        return "NhaTro{" + "ID_NhaTro=" + ID_NhaTro + ", TenNhaTro=" + TenNhaTro + ", Dia_chi=" + Dia_chi + ", ID_ChuTro=" + ID_ChuTro + '}';
    }
    
}

