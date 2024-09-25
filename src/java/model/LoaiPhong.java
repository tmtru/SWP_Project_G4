/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hihihihaha
 */
public class LoaiPhong {
    private int ID_LoaiPhong;
    private String TenLoaiPhong;
    private String Mo_ta;

    public LoaiPhong() {
    }

    public LoaiPhong(int ID_LoaiPhong, String TenLoaiPhong, String Mo_ta) {
        this.ID_LoaiPhong = ID_LoaiPhong;
        this.TenLoaiPhong = TenLoaiPhong;
        this.Mo_ta = Mo_ta;
    }

    public int getID_LoaiPhong() {
        return ID_LoaiPhong;
    }

    public void setID_LoaiPhong(int ID_LoaiPhong) {
        this.ID_LoaiPhong = ID_LoaiPhong;
    }

    public String getTenLoaiPhong() {
        return TenLoaiPhong;
    }

    public void setTenLoaiPhong(String TenLoaiPhong) {
        this.TenLoaiPhong = TenLoaiPhong;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

}