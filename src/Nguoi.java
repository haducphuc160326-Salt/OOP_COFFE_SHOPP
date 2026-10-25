public abstract class Nguoi{
    private final String Ma;
    private String hoTen;
    private String soDienThoai;

    protected Nguoi(String Ma, String hoTen, String soDienThoai){
        this.Ma = QuyTac.ChuanHoaMa(Ma);
        Update(hoTen, soDienThoai);
    }

    public String getMa(){
        return Ma;
    }

    public String getTen() {
        return hoTen;
    }

    public String getSDT() {
        return soDienThoai;
    }

    public abstract String getVaiTro();

    public void capNhat(String tenMoi, String soDienThoaiMoi){
        String Legal_Name = QuyTac.chuanHoaChuoi(tenMoi);
        String Legal_PhoneNumber = QuyTac.kiemtraSoDienThoai(soDienThoaiMoi);
        this.hoTen = Legal_Name;
        this.soDienThoai = Legal_PhoneNumber;
    }
}