import java.time.LocalDate;
import java.time.LocalDateTime;

public class ThucPham extends SanPham {
    private LocalDate Hansudung;
public ThucPham (String MaSP, String TenSP, double giaGoc, int tonKho, double phanTramGiamGia, LocalDate Hansudung) {
    super (MaSP, TenSP, giaGoc, tonKho, phanTramGiamGia);
    this.Hansudung = Hansudung;
}
public LocalDate getHansudung() {
    return Hansudung;
}
public void setHansudung(LocalDate Hansudung) {
    this.Hansudung = Hansudung;
}
@Override 
public boolean duocBan() {
    // Tồn kho > 0 và hạn sử dụng trùng với ngày hiện tại. ( trùng hoặc hsd >= ngày hiện tại )
    LocalDate ngayHientai = LocalDate.now();
    return tonKho > 0 && ( Hansudung.equals(ngayHientai)|| Hansudung.isAfter(ngayHientai));
}
@Override  
public String toString() {
return super.toString() + String.format(" | HSD: %s | Loại: Thực phẩm", HanSuDung.toString());
    }
}


