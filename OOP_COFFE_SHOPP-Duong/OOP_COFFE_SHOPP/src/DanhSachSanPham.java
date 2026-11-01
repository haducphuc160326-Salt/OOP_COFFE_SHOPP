import java.util.ArrayList;
import java.util.List;

public class DanhSachSanPham {
    private List<SanPham> danhSach;
    public DanhSachSanPham() {
        this.danhSach = new ArrayList<>();
    }
// Them 1 sp moi ( trong trung ma sp )
public boolean themSanPham (SanPham sp) {
    if (timkiemTheoMa(sp.getMaSP()) != null) {
        System.out.println ("Mã sản phẩm không tồn tại.");
        return false;
    }
    danhSach.add(sp);
    return true;
}
// Tim kiem theo ma sp.
public SanPham timkiemTheoMa (String MaSP) {
    for ( SanPham sp : danhSach ) {
        if ( sp.getMaSP().equalsIgnoreCase(MaSP)) {
            return sp;
        }
    }
    return null;
}
//  Tim kiem danh sach sp theo ten.
public List<SanPham> timkiemTheoTen ( String ten ) {
    List<SanPham> ketqua = new ArrayList<>();
    for ( SanPham sp : danhsach ) {
        if ( sp.getTenSP().toLowerCase().contains(ten.toLowerCase())) {
            ketqua.add(sp);
        }
    }
    return ketqua;
}
// Sua thong tin sp theo ma.
public boolean capNhatSanPham (String MaSP, SanPham spMoi) {
    SanPham spCu = timkiemTheoMa(MaSP);
    if ( spCu != null ) {
        spCu.setTenSP(spMoi.getTenSP());
        spCu.setgiaGoc(spMoi.getgiaGoc());
        spCu.settonKho(spMoi.gettonKho());
        spCu.setphanTramGiamGia(spMoi.getphanTramGiamGia());
        return true;
    }
    return false;
}
// Xoa sp theo ma.
public boolean xoaSanPham (String MaSP) {
    SanPham sp = timkiemTheoMa(MaSP);
    if ( sp != null ) {
        danhSach.remove(sp);
        return true;
    }
    return false;
}
// Loc lay danh sach cac san pham du dieu kien ban.
public List<SanPham> locSanPhamDuocBan() {
    List<SanPham> ketqua = new ArrayList<>();
    for ( SanPham sp : danhSach ) {
        if (sp.duocBan()) {
            ketqua.add(sp);
        }
    } 
    return ketqua;
}
// Hien thi toan bo danh sach san pham.
public void hienthiDanhSach () {
    if ( danhSach.isEmpty()) {
        System.out.println ("Danh sách SẢN PHẨM TRỐNG!");
        return;
    }
    System.out.println("==== DANH SÁCH SẢN PHẨM ====");
    for (SanPham sp : danhSach) {
        System.out.println(sp);
    }
}
public List<SanPham> getdanhSach() {
    return danhSach;    
}
} 