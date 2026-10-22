# Quản lý quán cà phê — phần Đức Phúc

Chỉ hiện thực 4 file theo bản phân công:

| File | Nhiệm vụ |
| --- | --- |
| `src/KhuyenMai.java` | Interface khai báo `giaBanThucTe()` |
| `src/ChiTietHoaDon.java` | Lưu bản chụp mã, tên, số lượng và giá sau giảm tại lúc bán |
| `src/HoaDon.java` | Lưu hóa đơn, tính tổng tiền, tiền thừa, hiển thị trạng thái |
| `src/CuaHang.java` | Nhập kho, quản lý khách/nhân viên, giỏ hàng, bán hàng, hủy và doanh thu |

Java 8 trở lên, console, không cần thư viện ngoài. Các file của thành viên khác đang trống và được giữ nguyên. **Chưa thể chạy cả ứng dụng** bằng `Main` cho đến khi nhóm ghép phần còn lại.

## Quy ước ghép nhóm

Do các lớp liên quan chưa được viết, đây là các chữ ký đề xuất cần thống nhất:

| Lớp phụ thuộc | Phương thức cần có |
| --- | --- |
| `SanPham implements KhuyenMai` | `String getMa()`, `String getTen()`, `int getTonKho()`, `void setTonKho(int soLuong)`, `double giaBanThucTe()`, `boolean duocBan(LocalDate ngay)` |
| `KhachHang` | `String getMa()`, `String getTen()` |
| `NhanVien` | `String getMa()`, `String getTen()` |

- `SanPham` là lớp abstract của Tấn Viên. `ThucPham`/`DoUong` kế thừa và kiểm tra hạn dùng; đề xuất còn bán được trong ngày hết hạn.
- `Nguoi` là lớp abstract của Thủy Tiên; constructor/setter bên đó kiểm tra tên, mã, số điện thoại theo tài liệu.
- Mã không trùng, không thay đổi sau khi thêm vào danh sách. Tra cứu bỏ khoảng trắng hai đầu, không phân biệt hoa/thường.
- `CuaHang` nhận **cùng danh sách sản phẩm** mà `DanhSachSanPham` quản lý qua constructor, để kho và menu đồng bộ.
- `setTonKho()` cần nhận mọi số nguyên từ 0 đến `Integer.MAX_VALUE`, không có tác dụng phụ. Nếu nhóm quy định giới hạn nhỏ hơn, bổ sung kiểm tra giới hạn vào cả `nhapHang()` và `huyHoaDon()` trước khi gọi setter. Hiện tại đã chặn tràn số nguyên.
- Không xóa sản phẩm còn trong hóa đơn có thể cần hủy. Nếu sản phẩm không còn trên menu, thao tác hủy báo lỗi và giữ nguyên kho, trạng thái.
- Ứng dụng console xử lý lần lượt từng thao tác. Giỏ chưa giữ chỗ trong kho; thanh toán luôn kiểm tra lại hạn dùng, tồn kho và giá hiện tại.
- Tiền dùng `double` cho dễ học, nhập theo đồng Việt Nam. `giaBanThucTe()` của sản phẩm chịu trách nhiệm áp dụng phần trăm giảm giá.

## Ví dụ gọi từ Main

Giả sử `danhSachSanPham`, `khachHang`, `nhanVien` đã được tạo bởi các lớp của thành viên phụ trách:

```java
CuaHang cuaHang = new CuaHang(danhSachSanPham);
try {
    cuaHang.themKhachHang(khachHang);
    cuaHang.themNhanVien(nhanVien);
    cuaHang.nhapHang("SP01", 10);
    cuaHang.themVaoGio("SP01", 2);
    for (ChiTietHoaDon dong : cuaHang.xemGioHang()) {
        System.out.println(dong);
    }
    HoaDon hd = cuaHang.banHang(khachHang.getMa(), nhanVien.getMa(), 100000);
    System.out.println(hd);
    System.out.println("Doanh thu: " + cuaHang.thongKeDoanhThu());
    cuaHang.huyHoaDon(hd.getMaHoaDon());
    System.out.println(hd.getTrangThai()); // ĐÃ HỦY, kho đã hoàn lại
} catch (IllegalArgumentException | IllegalStateException | ArithmeticException e) {
    System.out.println(e.getMessage());
}
```

Menu và vòng lặp nhập liệu thuộc `Main`/`NhapLieu`. Đặt `try/catch` bao quanh từng thao tác menu.

- Khách hàng: `getKhachHang`, `timKhachHang`, `themKhachHang`, `suaKhachHang`, `xoaKhachHang`.
- Nhân viên: `getNhanVien`, `timNhanVien`, `themNhanVien`, `suaNhanVien`, `xoaNhanVien`.
- Giỏ hàng: `getGioHang`, `xemGioHang`, `themVaoGio`, `suaSoLuongTrongGio`, `xoaKhoiGio`, `xoaGioHang`. Số lượng phải dương; muốn bỏ mặt hàng thì gọi xóa.
- Hóa đơn: `getHoaDon`, `timHoaDon`, `huyHoaDon`; `System.out.println(hd)` hiển thị chi tiết.
- Thống kê: `thongKeDoanhThu()` hoặc `thongKeDoanhThu(tuNgay, denNgay)`, tính cả hai ngày đầu/cuối, bỏ hóa đơn đã hủy.

Thanh toán lỗi giữ giỏ để sửa; thành công xóa giỏ. Hóa đơn giữ tên người và giá lúc bán dù thông tin gốc thay đổi. Hủy không xóa lịch sử hay thay tổng tiền gốc.

## Các ý OOP để trình bày

- Đóng gói: thuộc tính `private`, kiểm tra dữ liệu qua constructor/phương thức.
- Constructor: cả ba lớp cụ thể đều có hàm thiết lập.
- Interface: `KhuyenMai`; sản phẩm hiện thực `giaBanThucTe()`.
- Đa hình: `CuaHang` xử lý `SanPham`, các lớp con quyết định giá và khả năng bán.
- Danh sách đối tượng: `List` với `ArrayList`; giỏ dùng `LinkedHashMap` gộp số lượng và giữ thứ tự thêm.
- Static: hàm kiểm tra dùng chung trong `CuaHang` là `private static`.
- Bản chụp: `ChiTietHoaDon` bất biến; danh sách chi tiết được sao chép và chặn sửa từ bên ngoài.

Kế thừa, lớp abstract, hằng số hệ thống và đọc/ghi file text thuộc phần khác theo phân công. `LuuTru` cần phối hợp thêm cách khôi phục cửa hàng (đặc biệt danh sách hóa đơn, trạng thái hủy và số hóa đơn tiếp theo); phần này chưa có chức năng nạp trạng thái.

Sau khi ghép đủ lớp, biên dịch/chạy từ thư mục dự án:

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

## Kiểm tra phần Đức Phúc

Đã biên dịch bằng JDK 25 đi kèm VS Code và chạy đạt 45 kiểm tra với các lớp phụ thuộc giả lập tạm thời. Bao gồm: bán hàng và tiền thừa; thiếu tiền, NaN, vô cực; hết hạn, thiếu kho; giá hóa đơn cũ; hủy và chống hủy lặp; hoàn kho thất bại không thay đổi dữ liệu; tràn số; doanh thu theo ngày; thêm/sửa/xóa nhân sự; chống sửa danh sách từ bên ngoài. Các lớp giả lập đã được xóa sau khi kiểm tra. Chưa kiểm tra tích hợp với lớp thật của các thành viên khác.
