import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class HoaDon {
    private final String maHoaDon;
    private final LocalDate ngay;
    private final String tenKhachHang;
    private final String tenNhanVien;
    private final List<ChiTietHoaDon> chiTiet;
    private final double tienKhachDua;
    private boolean daHuy;

    public HoaDon(String maHoaDon, LocalDate ngay, String tenKhachHang,
                  String tenNhanVien, List<ChiTietHoaDon> chiTiet, double tienKhachDua) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty() || ngay == null
                || tenKhachHang == null || tenKhachHang.trim().isEmpty()
                || tenNhanVien == null || tenNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Thông tin hóa đơn không được trống.");
        }
        if (chiTiet == null || chiTiet.isEmpty() || chiTiet.contains(null)) {
            throw new IllegalArgumentException("Hóa đơn phải có chi tiết hợp lệ.");
        }
        List<ChiTietHoaDon> banSao = new ArrayList<>(chiTiet);
        double tong = 0;
        for (ChiTietHoaDon dong : banSao) {
            tong += dong.thanhTien();
        }
        if (!Double.isFinite(tong) || !Double.isFinite(tienKhachDua) || tienKhachDua < tong) {
            throw new IllegalArgumentException("Tiền khách đưa không hợp lệ hoặc chưa đủ.");
        }
        this.maHoaDon = maHoaDon.trim().toUpperCase(Locale.ROOT);
        this.ngay = ngay;
        this.tenKhachHang = tenKhachHang.trim();
        this.tenNhanVien = tenNhanVien.trim();
        this.chiTiet = Collections.unmodifiableList(banSao);
        this.tienKhachDua = tienKhachDua;
    }

    public String getMaHoaDon() { return maHoaDon; }
    public LocalDate getNgay() { return ngay; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getTenNhanVien() { return tenNhanVien; }
    public List<ChiTietHoaDon> getChiTiet() { return chiTiet; }
    public double getTienKhachDua() { return tienKhachDua; }
    public boolean isDaHuy() { return daHuy; }

    public double tongTien() {
        double tong = 0;
        for (ChiTietHoaDon dong : chiTiet) {
            tong += dong.thanhTien();
        }
        return tong;
    }

    public double tienThua() {
        return tienKhachDua - tongTien();
    }

    public String getTrangThai() {
        return daHuy ? "ĐÃ HỦY" : "ĐÃ THANH TOÁN";
    }

    // CuaHang chỉ gọi sau khi đã kiểm tra và hoàn kho thành công.
    void danhDauHuy() {
        if (daHuy) {
            throw new IllegalStateException("Hóa đơn đã hủy trước đó.");
        }
        daHuy = true;
    }

    @Override
    public String toString() {
        StringBuilder noiDung = new StringBuilder();
        noiDung.append("HÓA ĐƠN ").append(maHoaDon).append(" | ").append(ngay)
                .append("\nKhách: ").append(tenKhachHang)
                .append(" | Nhân viên: ").append(tenNhanVien).append('\n');
        for (ChiTietHoaDon dong : chiTiet) {
            noiDung.append(dong).append('\n');
        }
        noiDung.append(String.format("Tổng: %,.0f | Khách đưa: %,.0f | Tiền thừa: %,.0f VND\n%s",
                tongTien(), tienKhachDua, tienThua(), getTrangThai()));
        return noiDung.toString();
    }
}
