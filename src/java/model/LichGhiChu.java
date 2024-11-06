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
public class LichGhiChu {
    private int ID_GhiChu;
    private String GhiChu;
    private Date Ngay;
    private int ID_QuanLy;
    private int Status;

    public LichGhiChu() {
    }

    public LichGhiChu(int ID_GhiChu, String GhiChu, Date Ngay, int ID_QuanLy, int Status) {
        this.ID_GhiChu = ID_GhiChu;
        this.GhiChu = GhiChu;
        this.Ngay = Ngay;
        this.ID_QuanLy = ID_QuanLy;
        this.Status = Status;
    }

    public int getID_GhiChu() {
        return ID_GhiChu;
    }

    public void setID_GhiChu(int ID_GhiChu) {
        this.ID_GhiChu = ID_GhiChu;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public int getID_QuanLy() {
        return ID_QuanLy;
    }

    public void setID_QuanLy(int ID_QuanLy) {
        this.ID_QuanLy = ID_QuanLy;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
    
}
