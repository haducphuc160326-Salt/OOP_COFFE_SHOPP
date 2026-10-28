import java.util.List;

public class KiemThu {
    public static void main(String[] args) {
        System.out.println("=== BẮT ĐẦU CHẠY KIỂM THỬ (UNIT TEST / DEMO) ===");

        // 1. Kiểm thử QuyTac chuẩn hóa tên
        String tenGoc = "   nguyen   van   A   ";
        String tenChuan = QuyTac.chuanHoaTen(tenGoc);
        System.out.println("Test chuẩn hóa tên: '" + tenGoc + "' -> '" + tenChuan + "'");

        // 2. Kiểm thử định dạng tiền
        double tien = 45000;
        System.out.println("Test định dạng tiền: " + QuyTac.dinhDangTien(tien));

        // 3. Kiểm thử cơ chế Đọc/Ghi mảng với file txt
        String fileTest = "test_sanpham.txt";
        
        DanhSachSanPham danhSachTest = new DanhSachSanPham();
        danhSachTest.themSanPham(new DoUong("DU99", "Bạc sỉu test", 30000, 20, "L", true));
        danhSachTest.themSanPham(new ThucPham("TP99", "Bánh ngọt test", 15000, 10, "31/12/2026"));

        // Ghi mảng xuống file test
        System.out.println("\nĐang thử ghi mảng xuống file: " + fileTest);
        LuuTru.ghiVaoFile(fileTest, danhSachTest.getDanhSach());

        // Đọc file test ngược lại lên mảng mới
        System.out.println("Đang thử đọc file lên mảng mới...");
        List<SanPham> mảngMoi = LuuTru.docTuFile(fileTest);

        System.out.println("\nDanh sách sau khi đọc từ file test lên:");
        for (SanPham sp : mảngMoi) {
            sp.hienThiThongTin();
        }

        System.out.println("=== KIỂM THỬ HOÀN TẤT THÀNH CÔNG ===");
    }
}
