/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hihihihaha
 */
public class AnhPhongTro {
    private int ID_AnhPhong;
    private int ID_Phong;
    private String URL_AnhPhongTro;

    public AnhPhongTro() {
    }

    public AnhPhongTro(int ID_AnhPhong, int ID_Phong, String URL_AnhPhongTro) {
        this.ID_AnhPhong = ID_AnhPhong;
        this.ID_Phong = ID_Phong;
        this.URL_AnhPhongTro = URL_AnhPhongTro;
    }

    public int getID_AnhPhong() {
        return ID_AnhPhong;
    }

    public void setID_AnhPhong(int ID_AnhPhong) {
        this.ID_AnhPhong = ID_AnhPhong;
    }

    public int getID_Phong() {
        return ID_Phong;
    }

    public void setID_Phong(int ID_Phong) {
        this.ID_Phong = ID_Phong;
    }

    public String getURL_AnhPhongTro() {
        return URL_AnhPhongTro;
    }

    public void setURL_AnhPhongTro(String URL_AnhPhongTro) {
        this.URL_AnhPhongTro = URL_AnhPhongTro;
    }
    
}
