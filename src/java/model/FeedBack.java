/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class FeedBack {
    private int feedback_id;
    private int ID_khachthue;
    private int ID_phongtro;
    private String ten_khach;
     

    private String noi_dung;
       private int danh_gia;

    public FeedBack() {
    }

    public FeedBack(int feedback_id, int ID_khachthue, int ID_phongtro, String noi_dung, int danh_gia) {
        this.feedback_id = feedback_id;
        this.ID_khachthue = ID_khachthue;
        this.ID_phongtro = ID_phongtro;
        this.noi_dung = noi_dung;
        this.danh_gia = danh_gia;
        
    }

    public FeedBack(int ID_khachthue, int ID_phongtro, String noi_dung, int danh_gia) {
        this.ID_khachthue = ID_khachthue;
        this.ID_phongtro = ID_phongtro;
        this.noi_dung = noi_dung;
        this.danh_gia = danh_gia;
    }

    public String getTen_khach() {
        return ten_khach;
    }

    public void setTen_khach(String ten_khach) {
        this.ten_khach = ten_khach;
    }
    

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public int getID_khachthue() {
        return ID_khachthue;
    }

    public void setID_khachthue(int ID_khachthue) {
        this.ID_khachthue = ID_khachthue;
    }

    public int getID_phongtro() {
        return ID_phongtro;
    }

    public void setID_phongtro(int ID_phongtro) {
        this.ID_phongtro = ID_phongtro;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public int getDanh_gia() {
        return danh_gia;
    }

    public void setDanh_gia(int danh_gia) {
        this.danh_gia = danh_gia;
    }

   

    

}