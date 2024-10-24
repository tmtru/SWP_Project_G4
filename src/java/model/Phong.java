package model;

import java.util.List;

public class Phong {

    private int ID_Phong;
    private int ID_LoaiPhong;
    private String TenPhongTro;
    private int ID_NhaTro;
    private String TenLoaiPhong;
    private String TenNhaTro;
    private int Tang;
    private String Trang_thai;
    private float Dien_tich;
    private String URL_AnhPhongTro;
    private int Gia;
    private String Mo_ta;
    private List<String> images;
    private String diaChiPhongTro;

    public Phong(String Trang_thai, int Gia, String diaChiPhongTro) {
        this.Trang_thai = Trang_thai;
        this.Gia = Gia;
        this.diaChiPhongTro = diaChiPhongTro;
    }

    

    public String getDiaChiPhongTro() {
        return diaChiPhongTro;
    }

    public void setDiaChiPhongTro(String diaChiPhongTro) {
        this.diaChiPhongTro = diaChiPhongTro;
    }

    public Phong() {
    }

    public Phong(int ID_Phong, int ID_LoaiPhong, String TenPhongTro, int ID_NhaTro, String TenLoaiPhong, String TenNhaTro, int Tang, String Trang_thai, float Dien_tich, String URL_AnhPhongTro, int Gia, String Mo_ta) {
        this.ID_Phong = ID_Phong;
        this.ID_LoaiPhong = ID_LoaiPhong;
        this.TenPhongTro = TenPhongTro;
        this.ID_NhaTro = ID_NhaTro;
        this.TenLoaiPhong = TenLoaiPhong;
        this.TenNhaTro = TenNhaTro;
        this.Tang = Tang;
        this.Trang_thai = Trang_thai;
        this.Dien_tich = Dien_tich;
        this.URL_AnhPhongTro = URL_AnhPhongTro;
        this.Gia = Gia;
        this.Mo_ta = Mo_ta;
    }

    public Phong(int ID_LoaiPhong, String TenPhongTro, int ID_NhaTro, int Tang, String Trang_thai, float Dien_tich, int Gia, List<String> images) {
        this.ID_LoaiPhong = ID_LoaiPhong;
        this.TenPhongTro = TenPhongTro;
        this.ID_NhaTro = ID_NhaTro;
        this.Tang = Tang;
        this.Trang_thai = Trang_thai;
        this.Dien_tich = Dien_tich;
        this.Gia = Gia;
        this.images = images;
    }

    public Phong(int ID_Phong, int ID_LoaiPhong, String TenPhongTro, int ID_NhaTro, int Tang, String Trang_thai, float Dien_tich, int Gia) {
        this.ID_LoaiPhong = ID_LoaiPhong;
        this.TenPhongTro = TenPhongTro;
        this.ID_NhaTro = ID_NhaTro;
        this.Tang = Tang;
        this.Trang_thai = Trang_thai;
        this.Dien_tich = Dien_tich;
        this.Gia = Gia;
        this.ID_Phong = ID_Phong;
    }

    public int getID_Phong() {
        return ID_Phong;
    }

    public void setID_Phong(int ID_Phong) {
        this.ID_Phong = ID_Phong;
    }

    public int getID_LoaiPhong() {
        return ID_LoaiPhong;
    }

    public void setID_LoaiPhong(int ID_LoaiPhong) {
        this.ID_LoaiPhong = ID_LoaiPhong;
    }

    public String getTenPhongTro() {
        return TenPhongTro;
    }

    public void setTenPhongTro(String TenPhongTro) {
        this.TenPhongTro = TenPhongTro;
    }

    public int getID_NhaTro() {
        return ID_NhaTro;
    }

    public void setID_NhaTro(int ID_NhaTro) {
        this.ID_NhaTro = ID_NhaTro;
    }

    public String getTenLoaiPhong() {
        return TenLoaiPhong;
    }

    public void setTenLoaiPhong(String TenLoaiPhong) {
        this.TenLoaiPhong = TenLoaiPhong;
    }

    public String getTenNhaTro() {
        return TenNhaTro;
    }

    public void setTenNhaTro(String TenNhaTro) {
        this.TenNhaTro = TenNhaTro;
    }

    public int getTang() {
        return Tang;
    }

    public void setTang(int Tang) {
        this.Tang = Tang;
    }

    public String getTrang_thai() {
        return Trang_thai;
    }

    public void setTrang_thai(String Trang_thai) {
        this.Trang_thai = Trang_thai;
    }

    public float getDien_tich() {
        return Dien_tich;
    }

    public void setDien_tich(float Dien_tich) {
        this.Dien_tich = Dien_tich;
    }

    public String getURL_AnhPhongTro() {
        return URL_AnhPhongTro;
    }

    public void setURL_AnhPhongTro(String URL_AnhPhongTro) {
        this.URL_AnhPhongTro = URL_AnhPhongTro;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int Gia) {
        this.Gia = Gia;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

//    @Override
//    public String toString() {
//        return "Room{" + "ID_Phong=" + ID_Phong + ", ID_LoaiPhong=" + ID_LoaiPhong + ", TenPhongTro=" + TenPhongTro + ", ID_NhaTro=" + ID_NhaTro + ", TenLoaiPhong=" + TenLoaiPhong + ", TenNhaTro=" + TenNhaTro + ", Tang=" + Tang + ", Trang_thai=" + Trang_thai + ", Dien_tich=" + Dien_tich + ", URL_AnhPhongTro=" + URL_AnhPhongTro + ", Gia=" + Gia + ", Mo_ta=" + Mo_ta + '}';
//    }

    @Override
    public String toString() {
        return "Phong{" + "ID_Phong=" + ID_Phong + ", ID_LoaiPhong=" + ID_LoaiPhong + ", TenPhongTro=" + TenPhongTro + ", ID_NhaTro=" + ID_NhaTro + ", TenLoaiPhong=" + TenLoaiPhong + ", TenNhaTro=" + TenNhaTro + ", Tang=" + Tang + ", Trang_thai=" + Trang_thai + ", Dien_tich=" + Dien_tich + ", URL_AnhPhongTro=" + URL_AnhPhongTro + ", Gia=" + Gia + ", Mo_ta=" + Mo_ta + ", images=" + images + ", diaChiPhongTro=" + diaChiPhongTro + '}';
    }

}
