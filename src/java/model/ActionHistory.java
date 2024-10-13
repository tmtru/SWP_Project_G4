/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
/**
 *
 * @author ADMIN
 */
public class ActionHistory {
    private int id;
    private QuanLy quanLy;
    private NhaTro nhaTro;
    private String title;
    private String content;
    private Timestamp createdDate;

    public ActionHistory() {
    }

    public ActionHistory(int id, QuanLy quanLy, NhaTro nhaTro, String title, String content, Timestamp createdDate) {
        this.id = id;
        this.quanLy = quanLy;
        this.nhaTro = nhaTro;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuanLy getQuanLy() {
        return quanLy;
    }

    public void setQuanLy(QuanLy quanLy) {
        this.quanLy = quanLy;
    }

    public NhaTro getNhaTro() {
        return nhaTro;
    }

    public void setNhaTro(NhaTro nhaTro) {
        this.nhaTro = nhaTro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ActionHistory{" + "id=" + id + ", quanLy=" + quanLy + ", nhaTro=" + nhaTro + ", title=" + title + ", content=" + content + ", createdDate=" + createdDate + '}';
    }
    
    
}
