
import java.time.LocalDate;

public abstract class SanPham implements KhuyenMai {
    protected String MaSP;
    protected String TenSP;
    protected double giaGoc;
    protected int tonKho;
    protected double phanTramGiamGia;
public SanPham ( String MaSP, String TenSP, double giaGoc, int tonKho, double phanTramGiamGia ) {
    this.MaSP = MaSP;
    this.TenSP = TenSP;
    this.giaGoc = giaGoc;
    this.tonKho = tonKho;
    this.phanTramGiamGia = phanTramGiamGia;
}
public SanPham {}
@Override 
public long giaBanthucte() {
        return (long) (giaGoc * ( 1 - phanTramGiamGia / 100.0 ));
}
public abstract boolean duocBan();
public String getMaSP() {
    return MaSP;
}
public void setMaSP (String MaSP) {
    this.MaSP = MaSP;
}
public String getTenSP() {
    return TenSP;
}
public void setTenSP (String TenSP) {
    this.TenSP = TenSP;
}
public double getgiaGoc() {
    return giaGoc;
}
public void setgiaGoc (double giaGoc) {
    this.giaGoc = giaGoc;
}
public int gettonKho() {
    return tonKho;
}
public int settonKho(int tonKho) {
    this.tonKho = tonKho;
}
public double getphanTramGiamGia () {
    return phanTramGiamGia;
}
public double setphanTramGiamGia (double phanTramGiamGia) {
    this.phanTramGiamGia = phanTramGiamGia;
}
@Override 
public String toString() {
    return String.format("Mã: %-6s | Tên: %-20s | Giá gốc: %,10.0f VNĐ | Giảm %3.0f%% | Giá bán: %,10.0f VNĐ | Tồn kho: %3d",
                MaSP, TenSP, giaGoc, phanTramGiamGia, giaBanThucTe(), tonKho);
}
}
