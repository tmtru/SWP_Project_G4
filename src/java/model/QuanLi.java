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
public class QuanLi {
    private int ID_QuanLy;
    private String TenQuanLy;
    private Date Ngay_sinh;
    private String SDT,CCCD;
    private int ID_NhaTro;
    private Account account;

    public QuanLi() {
    }

    public QuanLi(int ID_QuanLy, String TenQuanLy, Date Ngay_sinh, String SDT, String CCCD, int ID_NhaTro, Account account) {
        this.ID_QuanLy = ID_QuanLy;
        this.TenQuanLy = TenQuanLy;
        this.Ngay_sinh = Ngay_sinh;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.ID_NhaTro = ID_NhaTro;
        this.account = account;
    }
    

    public QuanLi(int ID_QuanLy, String TenQuanLy, Date Ngay_sinh, String SDT, String CCCD, Account account) {
        this.ID_QuanLy = ID_QuanLy;
        this.TenQuanLy = TenQuanLy;
        this.Ngay_sinh = Ngay_sinh;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.account = account;
    }
    

    public int getID_QuanLy() {
        return ID_QuanLy;
    }

    public void setID_QuanLy(int ID_QuanLy) {
        this.ID_QuanLy = ID_QuanLy;
    }

    public String getTenQuanLy() {
        return TenQuanLy;
    }

    public void setTenQuanLy(String TenQuanLy) {
        this.TenQuanLy = TenQuanLy;
    }

    public Date getNgay_sinh() {
        return Ngay_sinh;
    }

    public void setNgay_sinh(Date Ngay_sinh) {
        this.Ngay_sinh = Ngay_sinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
    
}
