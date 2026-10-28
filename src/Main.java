import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_SAN_PHAM = "sanpham.txt";
    private static final String FILE_NHAN_VIEN = "nhanvien.txt";
    
    private static DanhSachSanPham dssP = new DanhSachSanPham();
    private static DanhSachNhanVien dssNV = new DanhSachNhanVien();

    public static void main(String[] args) {
        // Nạp dữ liệu từ file lên mảng khi khởi động
        dssP.setDanhSach(LuuTru.docTuFile(FILE_SAN_PHAM));
        dssNV.setDanhSach(DanhSachNhanVien.docTuFile(FILE_NHAN_VIEN));

        Scanner scanner = new Scanner(System.in);
        int chon = 0;

        do {
            System.out.println("\n================ PHẦN MỀM QUẢN LÝ QUÁN CAFE ================");
            System.out.println("--- QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm đồ uống mới");
            System.out.println("3. Thêm thực phẩm mới");
            System.out.println("4. Tìm kiếm / Xóa sản phẩm theo mã");
            System.out.println("--- QUẢN LÝ NHÂN VIÊN ---");
            System.out.println("5. Xem danh sách nhân viên");
            System.out.println("6. Thêm nhân viên mới");
            System.out.println("7. Xóa nhân viên theo mã");
            System.out.println("----------------------------------------------------------");
            System.out.println("8. Lưu dữ liệu xuống file & Thoát chương trình");
            System.out.println("==========================================================");
            chon = NhapLieu.nhapSoNguyen(" Lựa chọn của bạn (1-8): ");

            switch (chon) {
                case 1:
                    dssP.hienThiDanhSach();
                    break;
                case 2:
                    System.out.println("\n--- THÊM ĐỒ UỐNG ---");
                    String maDU = NhapLieu.nhapChuoi("Nhập mã đồ uống: ");
                    String tenDU = NhapLieu.nhapChuoi("Nhập tên đồ uống: ");
                    double giaDU = NhapLieu.nhapSoThuc("Nhập giá bán: ");
                    int tonDU = NhapLieu.nhapSoNguyenDuong("Nhập số lượng tồn: ");
                    String size = NhapLieu.nhapChuoi("Nhập size (S/M/L): ");
                    System.out.print("Có đá không? (true/false): ");
                    boolean coDa = Boolean.parseBoolean(scanner.nextLine().trim());
                    dssP.themSanPham(new DoUong(maDU, tenDU, giaDU, tonDU, size, coDa));
                    System.out.println(" Đã thêm đồ uống vào mảng!");
                    break;
                case 3:
                    System.out.println("\n--- THÊM THỰC PHẨM ---");
                    String maTP = NhapLieu.nhapChuoi("Nhập mã thực phẩm: ");
                    String tenTP = NhapLieu.nhapChuoi("Nhập tên thực phẩm: ");
                    double giaTP = NhapLieu.nhapSoThuc("Nhập giá bán: ");
                    int tonTP = NhapLieu.nhapSoNguyenDuong("Nhập số lượng tồn: ");
                    String hsd = NhapLieu.nhapChuoi("Nhập hạn sử dụng (dd/MM/yyyy): ");
                    dssP.themSanPham(new ThucPham(maTP, tenTP, giaTP, tonTP, hsd));
                    System.out.println(" Đã thêm thực phẩm vào mảng!");
                    break;
                case 4:
                    String timMa = NhapLieu.nhapChuoi("Nhập mã sản phẩm cần tìm/xóa: ");
                    SanPham sp = dssP.timKiemTheoMa(timMa);
                    if (sp != null) {
                        System.out.println(" Tìm thấy:");
                        sp.hienThiThongTin();
                        String xacNhan = NhapLieu.nhapChuoi("Bạn có muốn xóa sản phẩm này không? (y/n): ");
                        if (xacNhan.equalsIgnoreCase("y")) {
                            dssP.xoaSanPham(timMa);
                            System.out.println(" Đã xóa thành công!");
                        }
                    } else {
                        System.out.println(" Không tìm thấy sản phẩm có mã: " + timMa);
                    }
                    break;
                case 5:
                    dssNV.hienThiDanhSach();
                    break;
                case 6:
                    System.out.println("\n--- THÊM NHÂN VIÊN MỚI ---");
                    String maNV = NhapLieu.nhapChuoi("Nhập mã nhân viên: ");
                    String tenNV = QuyTac.chuanHoaTen(NhapLieu.nhapChuoi("Nhập họ tên nhân viên: "));
                    String sdtNV = NhapLieu.nhapSoDienThoai("Nhập số điện thoại: ");
                    String chucVu = NhapLieu.nhapChuoi("Nhập chức vụ (Pha chế/Thu ngân/...): ");
                    double luong = NhapLieu.nhapSoThuc("Nhập lương cơ bản: ");
                    
                    dssNV.themNhanVien(new NhanVien(maNV, tenNV, sdtNV, chucVu, luong));
                    System.out.println(" Đã thêm nhân viên vào mảng thành công!");
                    break;
                case 7:
                    String xoaID = NhapLieu.nhapChuoi("Nhập mã nhân viên cần xóa: ");
                    if (dssNV.xoaNhanVien(xoaID)) {
                        System.out.println(" Xóa nhân viên thành công!");
                    } else {
                        System.out.println(" Không tìm thấy mã nhân viên cần xóa!");
                    }
                    break;
                case 8:
                    // Ghi đè toàn bộ mảng xuống file khi thoát
                    LuuTru.ghiVaoFile(FILE_SAN_PHAM, dssP.getDanhSach());
                    DanhSachNhanVien.ghiVaoFile(FILE_NHAN_VIEN, dssNV.getDanhSach());
                    System.out.println(" Đã lưu dữ liệu xuống file thành công. Tạm biệt!");
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ! Vui lòng chọn từ 1 đến 8.");
            }
        } while (chon != 8);
        scanner.close();
    }
}
