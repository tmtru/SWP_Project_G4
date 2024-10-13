/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class KhachThue {
    private int id;
    private String name;
    private String cccd;
    private Date dob;
    private String phone;
    private String hk_thuong_tru;
    private Account account;
    private String roomName;
    private String job;

    public KhachThue() {
    }

    public KhachThue(int id, String name, String cccd, Date dob, String phone, String hk_thuong_tru, Account account, String roomName,  String job) {
        this.id = id;
        this.name = name;
        this.cccd = cccd;
        this.dob = dob;
        this.phone = phone;
        this.hk_thuong_tru = hk_thuong_tru;
        this.account = account;
        this.roomName = roomName;
        this.job = job;
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

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
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

    public String getHk_thuong_tru() {
        return hk_thuong_tru;
    }

    public void setHk_thuong_tru(String hk_thuong_tru) {
        this.hk_thuong_tru = hk_thuong_tru;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    
    
}
