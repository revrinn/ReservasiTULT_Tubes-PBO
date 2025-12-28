package ReservasiTULT;

public class Admin {

    public void tampilkanInfo() {
        System.out.println("Admin sistem reservasi TULT");
    }

    public void setujuiReservasi(Reservasi_Kelas reservasi) {
        if (reservasi.getStatus() == StatusPeminjaman.MENUNGGU) {
            reservasi.setStatus(StatusPeminjaman.DISETUJUI);
            reservasi.getRuangan().setTersedia(false);
            System.out.println("Reservasi " + reservasi.getKodeReservasi() + " disetujui");
        }
    }

    public void tolakReservasi(Reservasi_Kelas reservasi) {
        if (reservasi.getStatus() == StatusPeminjaman.MENUNGGU) {
            reservasi.setStatus(StatusPeminjaman.DITOLAK);
            reservasi.getRuangan().setTersedia(true);
            System.out.println("Reservasi " + reservasi.getKodeReservasi() + " ditolak");
        }
    }

    public void batalkanReservasi(Reservasi_Kelas reservasi) {
        if (reservasi.getStatus() != StatusPeminjaman.DIBATALKAN) {
            reservasi.setStatus(StatusPeminjaman.DIBATALKAN);
            reservasi.getRuangan().setTersedia(true);
            System.out.println("Reservasi " + reservasi.getKodeReservasi() + " dibatalkan");
        }
    }

    public void selesaikanReservasi(Reservasi_Kelas reservasi) {
        if (reservasi.getStatus() == StatusPeminjaman.DISETUJUI) {
            reservasi.setStatus(StatusPeminjaman.SELESAI);
            reservasi.getRuangan().setTersedia(true);
            System.out.println("Reservasi " + reservasi.getKodeReservasi() + " selesai");
        }
    }
}