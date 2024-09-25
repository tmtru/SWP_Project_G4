/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AnhNhaTro {
    int ID_AnhNhaTro;
    ArrayList<String> URL_AnhNhaTro;
    int ID_NhaTro;

    public AnhNhaTro() {
    }

    public AnhNhaTro(int ID_AnhNhaTro, ArrayList<String> URL_AnhNhaTro, int ID_NhaTro) {
        this.ID_AnhNhaTro = ID_AnhNhaTro;
        this.URL_AnhNhaTro = URL_AnhNhaTro;
        this.ID_NhaTro = ID_NhaTro;
    }

    public int getID_AnhNhaTro() {
        return ID_AnhNhaTro;
    }

    public void setID_AnhNhaTro(int ID_AnhNhaTro) {
        this.ID_AnhNhaTro = ID_AnhNhaTro;
    }

    public ArrayList<String> getURL_AnhNhaTro() {
        return URL_AnhNhaTro;
    }

    public void setURL_AnhNhaTro(ArrayList<String> URL_AnhNhaTro) {
        this.URL_AnhNhaTro = URL_AnhNhaTro;
    }

    

    public int getID_NhaTro() {
        return ID_NhaTro;
    }

    public void setID_NhaTro(int ID_NhaTro) {
        this.ID_NhaTro = ID_NhaTro;
    }

    @Override
    public String toString() {
        return "AnhNhaTro{" + "ID_AnhNhaTro=" + ID_AnhNhaTro + ", URL_AnhNhaTro=" + URL_AnhNhaTro + ", ID_NhaTro=" + ID_NhaTro + '}';
    }
    
}
