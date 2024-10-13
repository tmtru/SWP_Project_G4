/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class QuanLy {

    private int id;
    private String name;
    private Date dob;
    private String phone;
    private String cccd;
    private Account account;
    private int id_nhaTro;
    public QuanLy() {
    }

    @Override
    public String toString() {
        return "ChuTro{" + "id=" + id + ", name=" + name + ", dob=" + dob + ", phone=" + phone + ", cccd=" + cccd + ", account=" + account + '}';
    }

    public QuanLy(int id, String name, Date dob, String phone, String cccd, Account account) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.cccd = cccd;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public QuanLy(int id, String name, Date dob, String phone, String cccd, Account account, int id_nhaTro) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.cccd = cccd;
        this.account = account;
        this.id_nhaTro = id_nhaTro;
    }

    public int getId_nhaTro() {
        return id_nhaTro;
    }

    public void setId_nhaTro(int id_nhaTro) {
        this.id_nhaTro = id_nhaTro;
    }
    

}
