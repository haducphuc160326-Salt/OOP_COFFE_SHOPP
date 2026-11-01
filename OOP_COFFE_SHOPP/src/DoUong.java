import java.time.LocalDate;

public class DoUong extends SanPham {
    private LocalDate Hansudung;
public DoUong (String MaSP, String TenSP, double giaGoc, int tonKho, double phanTramGiamGia, LocalDate Hansudung) {
    super (MaSP, TenSP, giaGoc, tonKho, phanTramGiamGia);
    this.Hansudung = Hansudung;
}
public DoUong() {
    super();
}
public LocalDate getHansudung() {
    return Hansudung;
}
public void setHansudung (LocalDate Hansudung) {
    this.Hansudung = Hansudung;
}
// Đồ uống khi được bán thì số lượng kho phải lớn hơn 0.
// Hạn sử dụng phải trùng hoặc lớn hơn ngày hiện tại.
@Override 
public boolean duocBan() {
    if ( Hansudung == null ) {
        return false;
    }
    LocalDate ngayHienTai = LocalDate.now();
    boolean conHSD = Hansudung.isEqual(ngayHienTai) || Hansudung.isAfter(ngayHienTai);
    return tonKho > 0 && conHSD;
}
@Override 
public String toString() {
    String chuoiHansudung;
    if ( Hansudung != null ) {
        chuoiHansudung = Hansudung.toString();
    }
    else {
        chuoiHansudung = "Chưa xác định.";
    }
    return super.toString() + String.format(" | HSD: %s | Loại: Đồ uống", chuoiHanSuDung);
}
}
