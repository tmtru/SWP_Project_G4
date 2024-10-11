package model;

public class DichVu {
    private int ID_DichVu;
    private String TenDichVu;
    private int Don_gia;
    private String Don_vi;
    private String Mo_ta;
    private boolean isActive; // Thêm thuộc tính isActive

    public DichVu(int ID_DichVu, String TenDichVu, int Don_gia, String Don_vi, String Mo_ta, boolean isActive) {
        this.ID_DichVu = ID_DichVu;
        this.TenDichVu = TenDichVu;
        this.Don_gia = Don_gia;
        this.Don_vi = Don_vi;
        this.Mo_ta = Mo_ta;
        this.isActive = isActive;
    }

    public DichVu() {
    }

    public int getID_DichVu() {
        return ID_DichVu;
    }

    public void setID_DichVu(int ID_DichVu) {
        this.ID_DichVu = ID_DichVu;
    }

    public String getTenDichVu() {
        return TenDichVu;
    }

    public void setTenDichVu(String TenDichVu) {
        this.TenDichVu = TenDichVu;
    }

    public int getDon_gia() {
        return Don_gia;
    }

    public void setDon_gia(int Don_gia) {
        this.Don_gia = Don_gia;
    }

    public String getDon_vi() {
        return Don_vi;
    }

    public void setDon_vi(String Don_vi) {
        this.Don_vi = Don_vi;
    }

    public String getMo_ta() {
        return Mo_ta;
    }

    public void setMo_ta(String Mo_ta) {
        this.Mo_ta = Mo_ta;
    }

    public boolean getIsActive() { // Getter cho isActive
        return isActive;
    }

    public void setIsActive(boolean isActive) { // Setter cho isActive
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "DichVu{" +
                "ID_DichVu=" + ID_DichVu +
                ", TenDichVu='" + TenDichVu + '\'' +
                ", Don_gia=" + Don_gia +
                ", Don_vi='" + Don_vi + '\'' +
                ", Mo_ta='" + Mo_ta + '\'' +
                ", isActive=" + isActive + // Thêm isActive vào phương thức toString
                '}';
    }
    
    // Thêm các thuộc tính chi tiết
    private int ID_HoaDon;
    private int ChiSoCu;
    private int ChiSoMoi;
    private int DauNguoi;

    public int getID_HoaDon() {
        return ID_HoaDon;
    }

    public void setID_HoaDon(int ID_HoaDon) {
        this.ID_HoaDon = ID_HoaDon;
    }

    
    public int getChiSoCu() {
        return ChiSoCu;
    }

    public void setChiSoCu(int ChiSoCu) {
        this.ChiSoCu = ChiSoCu;
    }

    public int getChiSoMoi() {
        return ChiSoMoi;
    }

    public void setChiSoMoi(int ChiSoMoi) {
        this.ChiSoMoi = ChiSoMoi;
    }

    public int getDauNguoi() {
        return DauNguoi;
    }

    public void setDauNguoi(int DauNguoi) {
        this.DauNguoi = DauNguoi;
    }
    
}
