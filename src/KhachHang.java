public class KhachHang extends Nguoi {
    public KhachHang(String Ma, String hoTen, String soDienThoai){
        super(Ma,hoTen,soDienThoai);
    }

    @Override public String getRole(){
        return "Khách Hàng";
    }
}