/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class KhachThuePhu {
    private int idHopDong;
    private Date ngaySinh;
    private String cccd;
    private String email;
    private String ngheNghiep;
    private String sdt;
    private String hkThuongTru;
    private String noiCap;
    private Date ngayCap;
    private String urlMatTruoc;
    private String urlMatSau;
    private String tenKhach;
    private int gioiTinh;
    private int isActive;
    private int IdKhachThuePhu;

    public KhachThuePhu() {
    }

    public KhachThuePhu(int idHopDong, Date ngaySinh, String cccd, String email, String ngheNghiep, String sdt, String hkThuongTru, String noiCap, Date ngayCap, String urlMatTruoc, String urlMatSau, String tenKhach, int gioiTinh, int isActive) {
        this.idHopDong = idHopDong;
        this.ngaySinh = ngaySinh;
        this.cccd = cccd;
        this.email = email;
        this.ngheNghiep = ngheNghiep;
        this.sdt = sdt;
        this.hkThuongTru = hkThuongTru;
        this.noiCap = noiCap;
        this.ngayCap = ngayCap;
        this.urlMatTruoc = urlMatTruoc;
        this.urlMatSau = urlMatSau;
        this.tenKhach = tenKhach;
        this.gioiTinh = gioiTinh;
        this.isActive = isActive;
    }

    public int getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(int idHopDong) {
        this.idHopDong = idHopDong;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHkThuongTru() {
        return hkThuongTru;
    }

    public void setHkThuongTru(String hkThuongTru) {
        this.hkThuongTru = hkThuongTru;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getUrlMatTruoc() {
        return urlMatTruoc;
    }

    public void setUrlMatTruoc(String urlMatTruoc) {
        this.urlMatTruoc = urlMatTruoc;
    }

    public String getUrlMatSau() {
        return urlMatSau;
    }

    public void setUrlMatSau(String urlMatSau) {
        this.urlMatSau = urlMatSau;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIdKhachThuePhu() {
        return IdKhachThuePhu;
    }

    public void setIdKhachThuePhu(int IdKhachThuePhu) {
        this.IdKhachThuePhu = IdKhachThuePhu;
    }
    
    
}
