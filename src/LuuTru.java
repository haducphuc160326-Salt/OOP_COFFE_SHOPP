import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LuuTru {

    // 1. Đọc từ file .txt lên mảng (ArrayList) khi mở chương trình
    public static List<SanPham> docTuFile(String tenFile) {
        List<SanPham> danhSach = new ArrayList<>();
        File file = new File(tenFile);
        
        if (!file.exists()) {
            return danhSach; // Nếu file chưa tồn tại thì trả về mảng rỗng
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Định dạng dòng trong file txt: 
                // DOUONG,DU01,Ca phe sua,25000,50,M,true
                // THUCPHAM,TP01,Banh mi,20000,15,30/10/2026
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String loai = parts[0];
                String maSP = parts[1];
                String tenSP = parts[2];
                double giaBan = Double.parseDouble(parts[3]);
                int soLuongTon = Integer.parseInt(parts[4]);

                if (loai.equals("DOUONG")) {
                    String size = parts[5];
                    boolean coDa = Boolean.parseBoolean(parts[6]);
                    danhSach.add(new DoUong(maSP, tenSP, giaBan, soLuongTon, size, coDa));
                } else if (loai.equals("THUCPHAM")) {
                    String hanSuDung = parts[5];
                    danhSach.add(new ThucPham(maSP, tenSP, giaBan, soLuongTon, hanSuDung));
                }
            }
            System.out.println("📂 Đã nạp dữ liệu từ file txt lên mảng thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
        return danhSach;
    }

    // 2. Ghi toàn bộ mảng đè xuống file .txt khi kết thúc chương trình
    public static void ghiVaoFile(String tenFile, List<SanPham> danhSach) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(tenFile))) {
            for (SanPham sp : danhSach) {
                if (sp instanceof DoUong) {
                    DoUong du = (DoUong) sp;
                    pw.println("DOUONG," + du.getMaSP() + "," + du.getTenSP() + "," + 
                               du.getGiaBan() + "," + du.getSoLuongTon() + "," + 
                               du.getSize() + "," + du.isCoDa());
                } else if (sp instanceof ThucPham) {
                    ThucPham tp = (ThucPham) sp;
                    pw.println("THUCPHAM," + tp.getMaSP() + "," + tp.getTenSP() + "," + 
                               tp.getGiaBan() + "," + tp.getSoLuongTon() + "," + 
                               tp.getHanSuDung());
                }
            }
            System.out.println("💾 Đã ghi đè dữ liệu từ mảng xuống file txt thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }
}
