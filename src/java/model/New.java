/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import  java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class New {
    private int id;
    private String title;
    private String content;
    private Account creator;
    private Timestamp created_in;
    private NhaTro nhaTro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Timestamp getCreated_in() {
        return created_in;
    }

    public void setCreated_in(Timestamp created_in) {
        this.created_in = created_in;
    }

    public NhaTro getNhaTro() {
        return nhaTro;
    }

    public void setNhaTro(NhaTro nhaTro) {
        this.nhaTro = nhaTro;
    }

    @Override
    public String toString() {
        return "New{" + "id=" + id + ", title=" + title + ", content=" + content + ", creator=" + creator + ", created_in=" + created_in + ", nhaTro=" + nhaTro + '}';
    }
    
    
}
