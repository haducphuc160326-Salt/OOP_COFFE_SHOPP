import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Logic nghiệp vụ; Main phụ trách menu và bắt lỗi để thông báo cho người dùng. */
public class CuaHang {
    private final List<SanPham> sanPham;
    private final List<KhachHang> khachHang = new ArrayList<>();
    private final List<NhanVien> nhanVien = new ArrayList<>();
    private final List<HoaDon> hoaDon = new ArrayList<>();
    private final Map<String, Integer> gioHang = new LinkedHashMap<>();
    private int soHoaDonTiepTheo = 1;

    /** Nhận danh sách dùng chung với phần quản lý thực đơn của Tấn Viên. */
    public CuaHang(List<SanPham> sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được null.");
        }
        this.sanPham = sanPham;
    }

    private static String chuanHoaMa(String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã không được trống.");
        }
        return ma.trim().toUpperCase(Locale.ROOT);
    }

    private static void kiemTraSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
    }

    private SanPham timSanPham(String ma) {
        String maChuan = chuanHoaMa(ma);
        for (SanPham sp : sanPham) {
            if (chuanHoaMa(sp.getMa()).equals(maChuan)) {
                return sp;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy sản phẩm: " + maChuan);
    }

    public void nhapHang(String maSanPham, int soLuong) {
        kiemTraSoLuong(soLuong);
        SanPham sp = timSanPham(maSanPham);
        sp.setTonKho(Math.addExact(sp.getTonKho(), soLuong));
    }

    // Nhân viên / khách hàng: đối tượng mới đã được constructor của lớp đó kiểm tra.
    public KhachHang timKhachHang(String ma) {
        String maChuan = chuanHoaMa(ma);
        for (KhachHang kh : khachHang) {
            if (chuanHoaMa(kh.getMa()).equals(maChuan)) {
                return kh;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy khách hàng: " + maChuan);
    }

    public NhanVien timNhanVien(String ma) {
        String maChuan = chuanHoaMa(ma);
        for (NhanVien nv : nhanVien) {
            if (chuanHoaMa(nv.getMa()).equals(maChuan)) {
                return nv;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy nhân viên: " + maChuan);
    }

    public void themKhachHang(KhachHang moi) {
        if (moi == null) {
            throw new IllegalArgumentException("Khách hàng không được null.");
        }
        String ma = chuanHoaMa(moi.getMa());
        for (KhachHang kh : khachHang) {
            if (chuanHoaMa(kh.getMa()).equals(ma)) {
                throw new IllegalArgumentException("Trùng mã khách hàng: " + ma);
            }
        }
        khachHang.add(moi);
    }

    public void suaKhachHang(KhachHang moi) {
        if (moi == null) {
            throw new IllegalArgumentException("Khách hàng không được null.");
        }
        KhachHang cu = timKhachHang(moi.getMa());
        khachHang.set(khachHang.indexOf(cu), moi);
    }

    public void xoaKhachHang(String ma) {
        khachHang.remove(timKhachHang(ma));
    }

    public void themNhanVien(NhanVien moi) {
        if (moi == null) {
            throw new IllegalArgumentException("Nhân viên không được null.");
        }
        String ma = chuanHoaMa(moi.getMa());
        for (NhanVien nv : nhanVien) {
            if (chuanHoaMa(nv.getMa()).equals(ma)) {
                throw new IllegalArgumentException("Trùng mã nhân viên: " + ma);
            }
        }
        nhanVien.add(moi);
    }

    public void suaNhanVien(NhanVien moi) {
        if (moi == null) {
            throw new IllegalArgumentException("Nhân viên không được null.");
        }
        NhanVien cu = timNhanVien(moi.getMa());
        nhanVien.set(nhanVien.indexOf(cu), moi);
    }

    public void xoaNhanVien(String ma) {
        nhanVien.remove(timNhanVien(ma));
    }

    public List<KhachHang> getKhachHang() {
        return Collections.unmodifiableList(khachHang);
    }

    public List<NhanVien> getNhanVien() {
        return Collections.unmodifiableList(nhanVien);
    }

    private void kiemTraBanDuoc(SanPham sp, int soLuong, LocalDate ngay) {
        kiemTraSoLuong(soLuong);
        if (!sp.duocBan(ngay)) {
            throw new IllegalArgumentException("Sản phẩm hết hạn hoặc không được bán: " + sp.getMa());
        }
        if (soLuong > sp.getTonKho()) {
            throw new IllegalArgumentException("Không đủ tồn kho: " + sp.getMa());
        }
    }

    public void themVaoGio(String maSanPham, int soLuong) {
        kiemTraSoLuong(soLuong);
        String ma = chuanHoaMa(maSanPham);
        int tongSoLuong = Math.addExact(gioHang.getOrDefault(ma, 0), soLuong);
        suaSoLuongTrongGio(ma, tongSoLuong);
    }

    public void suaSoLuongTrongGio(String maSanPham, int soLuong) {
        String ma = chuanHoaMa(maSanPham);
        kiemTraBanDuoc(timSanPham(ma), soLuong, LocalDate.now());
        gioHang.put(ma, soLuong);
    }

    public void xoaKhoiGio(String maSanPham) {
        if (gioHang.remove(chuanHoaMa(maSanPham)) == null) {
            throw new IllegalArgumentException("Sản phẩm không có trong giỏ.");
        }
    }

    public void xoaGioHang() {
        gioHang.clear();
    }

    public Map<String, Integer> getGioHang() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(gioHang));
    }

    public List<ChiTietHoaDon> xemGioHang() {
        return Collections.unmodifiableList(lapChiTiet(LocalDate.now()));
    }

    private List<ChiTietHoaDon> lapChiTiet(LocalDate ngay) {
        List<ChiTietHoaDon> ketQua = new ArrayList<>();
        for (Map.Entry<String, Integer> dong : gioHang.entrySet()) {
            SanPham sp = timSanPham(dong.getKey());
            kiemTraBanDuoc(sp, dong.getValue(), ngay);
            ketQua.add(new ChiTietHoaDon(sp, dong.getValue()));
        }
        return ketQua;
    }

    /** Chỉ trừ kho sau khi đã kiểm tra toàn bộ giỏ hàng và tiền thanh toán. */
    public HoaDon banHang(String maKhachHang, String maNhanVien, double tienKhachDua) {
        KhachHang kh = timKhachHang(maKhachHang);
        NhanVien nv = timNhanVien(maNhanVien);
        LocalDate ngay = LocalDate.now();
        List<ChiTietHoaDon> chiTiet = lapChiTiet(ngay);
        HoaDon hd = new HoaDon(String.format(Locale.ROOT, "HD%04d", soHoaDonTiepTheo),
                ngay, kh.getTen(), nv.getTen(), chiTiet, tienKhachDua);
        for (ChiTietHoaDon dong : chiTiet) {
            SanPham sp = timSanPham(dong.getMaSanPham());
            sp.setTonKho(sp.getTonKho() - dong.getSoLuong());
        }
        hoaDon.add(hd);
        soHoaDonTiepTheo++;
        gioHang.clear();
        return hd;
    }

    public HoaDon timHoaDon(String ma) {
        String maChuan = chuanHoaMa(ma);
        for (HoaDon hd : hoaDon) {
            if (hd.getMaHoaDon().equals(maChuan)) {
                return hd;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy hóa đơn: " + maChuan);
    }

    public List<HoaDon> getHoaDon() {
        return Collections.unmodifiableList(hoaDon);
    }

    /** Hủy một lần; kiểm tra toàn bộ trước để tránh hoàn kho dở dang. */
    public void huyHoaDon(String maHoaDon) {
        HoaDon hd = timHoaDon(maHoaDon);
        if (hd.isDaHuy()) {
            throw new IllegalStateException("Hóa đơn đã hủy trước đó.");
        }
        Map<SanPham, Integer> tonKhoMoi = new LinkedHashMap<>();
        for (ChiTietHoaDon dong : hd.getChiTiet()) {
            SanPham sp = timSanPham(dong.getMaSanPham());
            int ton = tonKhoMoi.getOrDefault(sp, sp.getTonKho());
            tonKhoMoi.put(sp, Math.addExact(ton, dong.getSoLuong()));
        }
        for (Map.Entry<SanPham, Integer> dong : tonKhoMoi.entrySet()) {
            dong.getKey().setTonKho(dong.getValue());
        }
        hd.danhDauHuy();
    }

    public double thongKeDoanhThu() {
        double tong = 0;
        for (HoaDon hd : hoaDon) {
            if (!hd.isDaHuy()) {
                tong += hd.tongTien();
            }
        }
        return tong;
    }

    public double thongKeDoanhThu(LocalDate tuNgay, LocalDate denNgay) {
        if (tuNgay == null || denNgay == null || tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Khoảng ngày không hợp lệ.");
        }
        double tong = 0;
        for (HoaDon hd : hoaDon) {
            if (!hd.isDaHuy() && !hd.getNgay().isBefore(tuNgay)
                    && !hd.getNgay().isAfter(denNgay)) {
                tong += hd.tongTien();
            }
        }
        return tong;
    }
}
