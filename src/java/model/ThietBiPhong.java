package model;

public class ThietBiPhong {
    private int ID_ThietBiPhong;
    private String Trang_thai;
    private String Mo_ta;
    private String So_luong;
    private int ID_Phong;
    private int ID_ThietBi;
    private String TenThietBi;

    public ThietBiPhong() {
    }

    public ThietBiPhong(int ID_ThietBiPhong, String Trang_thai, String Mo_ta, String So_luong, int ID_Phong, int ID_ThietBi, String TenThietBi) {
        this.ID_ThietBiPhong = ID_ThietBiPhong;
        this.Trang_thai = Trang_thai;
        this.Mo_ta = Mo_ta;
        this.So_luong = So_luong;
        this.ID_Phong = ID_Phong;
        this.ID_ThietBi = ID_ThietBi;
        this.TenThietBi = TenThietBi;
    }

    public int getID_ThietBiPhong() {
        return ID_ThietBiPhong;
    }

    public void setID_ThietBiPhong(int ID_ThietBiPhong) {
        this.ID_ThietBiPhong = ID_ThietBiPhong;
    }

    public String getTrang_thai() {
        return Trang_thai;
    }

    public void setTrang_thai(String Trang_thai) {
        this.Trang_thai = Trang_thai;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    public String getSo_luong() {
        return So_luong;
    }

    public void setSo_luong(String So_luong) {
        this.So_luong = So_luong;
    }

    public int getID_Phong() {
        return ID_Phong;
    }

    public void setID_Phong(int ID_Phong) {
        this.ID_Phong = ID_Phong;
    }

    public int getID_ThietBi() {
        return ID_ThietBi;
    }

    public void setID_ThietBi(int ID_ThietBi) {
        this.ID_ThietBi = ID_ThietBi;
    }

    public String getTenThietBi() {
        return TenThietBi;
    }

    public void setTenThietBi(String TenThietBi) {
        this.TenThietBi = TenThietBi;
    }

    @Override
    public String toString() {
        return "ThietBiPhong{" + "ID_ThietBiPhong=" + ID_ThietBiPhong + ", Trang_thai=" + Trang_thai + ", Mo_ta=" + Mo_ta + ", So_luong=" + So_luong + ", ID_Phong=" + ID_Phong + ", ID_ThietBi=" + ID_ThietBi + ", TenThietBi=" + TenThietBi + '}';
    }
    
    
}