import java.util.Scanner;

public class NhapLieu {
    private static Scanner sc = new Scanner(System.in);

    // 1. Nhập chuỗi (bắt buộc nhập, không được để trống)
    public static String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String str = sc.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            }
            System.out.println("(!) Không được để trống. Vui lòng nhập lại!");
        }
    }

    // 2. Nhập số nguyên an toàn (chống crash khi người dùng gõ chữ)
    public static int nhapSoNguyen(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("(!) Phải nhập số nguyên. Vui lòng nhập lại!");
            }
        }
    }

    // 3. Nhập số lượng tồn hoặc số lượng mua (phải > 0)
    public static int nhapSoNguyenDuong(String thongBao) {
        while (true) {
            int so = nhapSoNguyen(thongBao);
            if (so > 0) {
                return so;
            }
            System.out.println("(!) Số lượng phải lớn hơn 0!");
        }
    }

    // 4. Nhập số thực (giá bán/lương, tự nhận diện cả dấu phẩy và chấm)
    public static double nhapSoThuc(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                String s = sc.nextLine().trim().replace(',', '.');
                double giaTri = Double.parseDouble(s);
                if (giaTri >= 0) {
                    return giaTri;
                }
                System.out.println("(!) Số tiền không được âm!");
            } catch (Exception e) {
                System.out.println("(!) Phải nhập số thực hợp lệ!");
            }
        }
    }

    // 5. Nhập số điện thoại (kiểm tra chuẩn bằng hàm của QuyTac)
    public static String nhapSoDienThoai(String thongBao) {
        while (true) {
            String sdt = nhapChuoi(thongBao);
            if (QuyTac.kiemTraSDT(sdt)) {
                return sdt;
            }
            System.out.println("(!) Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0!");
        }
    }
}