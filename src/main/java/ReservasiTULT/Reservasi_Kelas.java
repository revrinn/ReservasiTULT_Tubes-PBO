package ReservasiTULT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservasi_Kelas implements LangkahReservasi {
    private String kodeReservasi;
    private Pengguna pemohon;
    private Ruangan_Kelas ruangan;
    private String tanggal;
    private int jamMulai;
    private int jamSelesai;
    private String keperluan;
    private StatusPeminjaman status;
    private String tanggalPengajuan;
    private String alasanTolak; // FITUR BARU: Alasan penolakan oleh admin

    public Reservasi_Kelas(Pengguna pemohon, Ruangan_Kelas ruangan,
                           String tanggal, int jamMulai, int jamSelesai,
                           String keperluan) {
        this.kodeReservasi = generateKodeReservasi();
        this.pemohon = pemohon;
        this.ruangan = ruangan;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.keperluan = keperluan;
        this.status = StatusPeminjaman.MENUNGGU;
        this.tanggalPengajuan = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.alasanTolak = null;
    }

    private String generateKodeReservasi() {
        LocalDate now = LocalDate.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timePart = String.format("%06d", (int)(Math.random() * 1000000));
        return "RES-" + datePart + "-" + timePart;
    }

    @Override
    public boolean cekKetersediaan() {
        return ruangan.isTersedia();
    }

    @Override
    public void ajukanReservasi() {
        System.out.println("Reservasi diajukan: " + kodeReservasi);
    }

    @Override
    public void batalkanReservasi() {
        status = StatusPeminjaman.DIBATALKAN;
        ruangan.setTersedia(true);
    }

    @Override
    public String cetakStruk() {
        return "===== STRUK RESERVASI =====\n" +
               "Kode : " + kodeReservasi + "\n" +
               "Pemohon : " + pemohon.getNama() + "\n" +
               "Ruangan : " + ruangan.getKode() + "\n" +
               "Tanggal : " + tanggal + "\n" +
               "Waktu : " + String.format("%02d", jamMulai) + " - " +
                              String.format("%02d", jamSelesai) + "\n" +
               "Keperluan : " + keperluan + "\n" +
               "Status : " + status + "\n" +
               (alasanTolak != null ? "Alasan Tolak : " + alasanTolak + "\n" : "") +
               "==========================";
    }

    @Override
    public void tampilkanDetail() {
        System.out.println(cetakStruk());
    }

    public StatusPeminjaman getStatus() {
        return status;
    }

    public void setStatus(StatusPeminjaman status) {
        this.status = status;
    }

    public Ruangan_Kelas getRuangan() {
        return ruangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getJamMulai() {
        return jamMulai;
    }

    public int getJamSelesai() {
        return jamSelesai;
    }

    public String getKodeReservasi() {
        return kodeReservasi;
    }

    public void setKodeReservasi(String kodeReservasi) {
        this.kodeReservasi = kodeReservasi;
    }

    public Pengguna getPemohon() {
        return pemohon;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public String getAlasanTolak() {
        return alasanTolak;
    }

    public void setAlasanTolak(String alasanTolak) {
        this.alasanTolak = alasanTolak;
    }
}