package model;

import java.sql.Date;

public class KhachThue {
    private int id;
    //private String name;
    private Date dob;
    private String cccd;
    private String job;
    private String phone;
    private String address;
    private Account account;

    public KhachThue() {
    }

    public KhachThue(int id, Date dob, String cccd, String job, String phone, String address, Account account) {
        this.id = id;
        this.dob = dob;
        this.cccd = cccd;
        this.job = job;
        this.phone = phone;
        this.address = address;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public Date getDob() {
        return dob;
    }

    public String getCccd() {
        return cccd;
    }

    public String getJob() {
        return job;
    }

    public String getPhone() {
        return phone;
    }

    public Account getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "KhachThue{" + "id=" + id + ", dob=" + dob + ", cccd=" + cccd + ", job=" + job + ", phone=" + phone + ", account=" + account + '}';
    }
    
}
