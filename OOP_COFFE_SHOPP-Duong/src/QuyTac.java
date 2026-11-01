public class QuyTac {
    // 1. Hằng số hệ thống
    public static final String TEN_QUAN = "Quán Cà Phê OOP";
    public static final double GIA_TOI_DA = 100_000_000;
    public static final int TON_KHO_TOI_DA = 100_000;

    // 2. Chuẩn hóa tên (viết hoa chữ cái đầu mỗi từ: "cà phê sữa" -> "Cà Phê Sữa")
    public static String chuanHoaTen(String s) {
        if (s == null || s.trim().isEmpty()) return "";
        
        String[] cacTu = s.trim().split("\\s+");
        String ketQua = "";
        
        for (String tu : cacTu) {
            ketQua += Character.toUpperCase(tu.charAt(0)) + tu.substring(1).toLowerCase() + " ";
        }
        return ketQua.trim();
    }

    // 3. Chuẩn hóa mã (xóa khoảng trắng thừa và in hoa: " sp01 " -> "SP01")
    public static String chuanHoaMa(String ma) {
        return (ma == null) ? "" : ma.trim().toUpperCase();
    }

    // 4. Kiểm tra số điện thoại: đúng 10 số và bắt đầu bằng số 0
    public static boolean kiemTraSDT(String sdt) {
        return sdt != null && sdt.matches("^0\\d{9}$");
    }

    // 5. Kiểm tra giá và số lượng hợp lệ
    public static boolean kiemTraGia(double gia) {
        return gia >= 0 && gia <= GIA_TOI_DA;
    }

    public static boolean kiemTraTonKho(int tonKho) {
        return tonKho >= 0 && tonKho <= TON_KHO_TOI_DA;
    }

    // 6. Định dạng tiền tệ đơn giản (ví dụ: 25000 -> "25,000 VND")
    public static String dinhDangTien(double soTien) {
        return String.format("%,.0f VND", soTien);
    }
}