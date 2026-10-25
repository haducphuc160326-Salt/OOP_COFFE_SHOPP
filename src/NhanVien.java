public class NhanVien extends Nguoi{
    public NhanVien(String Ma, String hoTen, String soDienThoai){
        super(Ma,hoTen,soDienThoai);
    }

    @Override public String getVaiTro(){
        return "Nhân Viên";
    }
}