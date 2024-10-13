package model;

import java.sql.Date;

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

    public KhachThue(int id, Date dob, String cccd, String job, String phone, String hk_thuong_tru, Account account) {
        this.id = id;
        this.dob = dob;
        this.cccd = cccd;
        this.job = job;
        this.phone = phone;
        this.hk_thuong_tru = hk_thuong_tru;
        this.account = account;
    }

    public KhachThue(int id, String name, String cccd, Date dob, String phone, String hk_thuong_tru, Account account, String roomName, String job) {
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

    public String getName() {
        return name;
    }

    public String getCccd() {
        return cccd;
    }

    public Date getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getHk_thuong_tru() {
        return hk_thuong_tru;
    }

    public Account getAccount() {
        return account;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getJob() {
        return job;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHk_thuong_tru(String hk_thuong_tru) {
        this.hk_thuong_tru = hk_thuong_tru;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "KhachThue{" + "id=" + id + ", name=" + name + ", cccd=" + cccd + ", dob=" + dob + ", phone=" + phone + ", hk_thuong_tru=" + hk_thuong_tru + ", account=" + account + ", roomName=" + roomName + ", job=" + job + '}';
    }


}
