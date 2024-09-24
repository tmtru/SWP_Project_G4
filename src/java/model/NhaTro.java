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

    int ID_NhaTro;
    String TenNhaTro;
    String ID_ChuTro;
    String Dia_chi;
    String Mo_ta;
    String URL_AnhNhaTro;

    public NhaTro(int ID_NhaTro, String TenNhaTro, String ID_ChuTro, String Dia_chi, String Mo_ta, String URL_AnhNhaTro) {
        this.ID_NhaTro = ID_NhaTro;
        this.TenNhaTro = TenNhaTro;
        this.ID_ChuTro = ID_ChuTro;
        this.Dia_chi = Dia_chi;
        this.Mo_ta=Mo_ta;
        this.URL_AnhNhaTro=URL_AnhNhaTro;
    }

    public NhaTro() {
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

    public String getID_ChuTro() {
        return ID_ChuTro;
    }

    public void setID_ChuTro(String ID_ChuTro) {
        this.ID_ChuTro = ID_ChuTro;
    }

    public String getDia_chi() {
        return Dia_chi;
    }

    public void setDia_chi(String Dia_chi) {
        this.Dia_chi = Dia_chi;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    public String getURL_AnhNhaTro() {
        return URL_AnhNhaTro;
    }

    public void setURL_AnhNhaTro(String URL_AnhNhaTro) {
        this.URL_AnhNhaTro = URL_AnhNhaTro;
    }
    

    @Override
    public String toString() {
        return "NhaTro{" + "ID_NhaTro=" + ID_NhaTro + ", TenNhaTro=" + TenNhaTro + ", ID_ChuTro=" + ID_ChuTro + ", Dia_chi=" + Dia_chi + ", Mo_ta=" + Mo_ta + '}';
    }    
}
