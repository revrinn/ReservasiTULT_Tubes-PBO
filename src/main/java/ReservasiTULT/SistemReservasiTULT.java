package ReservasiTULT;

import java.util.ArrayList;
import java.util.List;

public class SistemReservasiTULT {
    private PenggunaDAO penggunaDAO = new PenggunaDAO();
    private RuanganDAO ruanganDAO = new RuanganDAO();
    private ReservasiDAO reservasiDAO = new ReservasiDAO();
    private Admin admin = new Admin();

    public SistemReservasiTULT() {
        System.out.println("Sistem reservasi TULT dimulai...");
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Reservasi_Kelas> getAllReservasi() {
        return reservasiDAO.findAll();
    }

    public List<Reservasi_Kelas> getReservasiByStatus(StatusPeminjaman status) {
        return reservasiDAO.findByStatus(status);
    }

    public Reservasi_Kelas findReservasiByKode(String kodeReservasi) {
        return reservasiDAO.findByKode(kodeReservasi);
    }

    public Ruangan_Kelas cariRuangan(String kode) {
        return ruanganDAO.findByKode(kode);
    }

    public List<Ruangan_Kelas> getAllRuangan() {
        return ruanganDAO.findAll();
    }

    public boolean cekBentrok(String tanggal, int mulai, int selesai, String kode) {
        System.out.println("Cek bentrok untuk tanggal " + tanggal + " jam " + mulai + " - " + selesai);
        return reservasiDAO.cekBentrok(tanggal, mulai, selesai, kode);
    }

    public Reservasi_Kelas buatReservasi(Pengguna p, Ruangan_Kelas r,
        String tanggal, int mulai, int selesai, String keperluan) {
        System.out.println("Mau buat reservasi buat " + p.getNama());
        if (!p.bolehMengajukanReservasi()) {
            System.out.println("Maaf, " + p.getNama() + " tidak boleh reservasi");
            return null;
        }
        if (!r.isTersedia()) {
            System.out.println("Ruangan " + r.getKode() + " lagi dipakai");
            return null;
        }
        if (mulai >= selesai) {
            System.out.println("Jam mulai harus lebih kecil dari jam selesai");
            return null;
        }
        if (cekBentrok(tanggal, mulai, selesai, r.getKode())) {
            System.out.println("Wah, jadwal bentrok nih");
            return null;
        }
        Reservasi_Kelas res = new Reservasi_Kelas(p, r, tanggal, mulai, selesai, keperluan);
        reservasiDAO.save(res);
        ruanganDAO.updateKetersediaan(r.getId(), false);
        System.out.println("Reservasi berhasil dibuat: " + res.getKodeReservasi());
        res.ajukanReservasi();
        return res;
    }

    public List<Reservasi_Kelas> getReservasiByPengguna(Pengguna p) {
        return reservasiDAO.findByPengguna(p);
    }

    public void updateStatusReservasi(String kodeReservasi, StatusPeminjaman status, String alasanTolak) {
        reservasiDAO.updateStatus(kodeReservasi, status, alasanTolak);
        if (status == StatusPeminjaman.SELESAI || status == StatusPeminjaman.DIBATALKAN || status == StatusPeminjaman.DITOLAK) {
            Reservasi_Kelas reservasi = reservasiDAO.findByKode(kodeReservasi);
            if (reservasi != null) {
                ruanganDAO.updateKetersediaan(reservasi.getRuangan().getId(), true);
            }
        }
    }

    public void updateStatusReservasi(String kodeReservasi, StatusPeminjaman status) {
        updateStatusReservasi(kodeReservasi, status, null);
    }

    public int getJumlahReservasi() {
        return reservasiDAO.findAll().size();
    }

    public int getJumlahRuangan() {
        return ruanganDAO.findAll().size();
    }

    public int getJumlahPengguna() {
        return penggunaDAO.findAll().size();
    }
}