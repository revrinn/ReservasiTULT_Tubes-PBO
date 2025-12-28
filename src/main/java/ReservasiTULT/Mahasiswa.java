package ReservasiTULT;

public class Mahasiswa extends Pengguna {
    private String nim;
    private String jurusan;
    private UKM ukm;

    public Mahasiswa(String nama, String nim, String jurusan, UKM ukm) {
        super(nama);
        this.nim = nim;
        this.jurusan = jurusan;
        this.ukm = ukm;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Mahasiswa");
        System.out.println("Nama : " + nama);
        System.out.println("NIM : " + nim);
        System.out.println("Jurusan : " + jurusan);
        if (ukm != null) {
            System.out.println("UKM : " + ukm.getNamaUnit());
        } else {
            System.out.println("UKM : Tidak ada");
        }
    }

    @Override
    public boolean bolehMengajukanReservasi() {
        return ukm != null;
    }

    @Override
    public String getRole() {
        return "MAHASISWA";
    }

    public String getNim() {
        return nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public UKM getUkm() {
        return ukm;
    }

    // METHOD YANG HARUS DITAMBAHKAN
    public void setUkm(UKM ukm) {
        this.ukm = ukm;
    }
}