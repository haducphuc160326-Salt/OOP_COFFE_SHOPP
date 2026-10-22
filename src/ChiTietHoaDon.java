import java.util.Locale;

/** Bản chụp thông tin sản phẩm tại lúc bán, không giữ tham chiếu SanPham. */
public final class ChiTietHoaDon {
    private final String maSanPham;
    private final String tenSanPham;
    private final int soLuong;
    private final double donGia;

    public ChiTietHoaDon(SanPham sanPham, int soLuong) {
        this(sanPham.getMa(), sanPham.getTen(), soLuong, sanPham.giaBanThucTe());
    }

    // Constructor này cũng dùng để đọc lại hóa đơn từ file text.
    public ChiTietHoaDon(String maSanPham, String tenSanPham, int soLuong, double donGia) {
        if (maSanPham == null || maSanPham.trim().isEmpty()
                || tenSanPham == null || tenSanPham.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã và tên sản phẩm không được trống.");
        }
        if (soLuong <= 0 || !Double.isFinite(donGia) || donGia < 0
                || !Double.isFinite(donGia * soLuong)) {
            throw new IllegalArgumentException("Số lượng hoặc đơn giá không hợp lệ.");
        }
        this.maSanPham = maSanPham.trim().toUpperCase(Locale.ROOT);
        this.tenSanPham = tenSanPham.trim();
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaSanPham() { return maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }

    public double thanhTien() {
        return donGia * soLuong;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | SL: %d | Đơn giá: %,.0f | Thành tiền: %,.0f VND",
                maSanPham, tenSanPham, soLuong, donGia, thanhTien());
    }
}
