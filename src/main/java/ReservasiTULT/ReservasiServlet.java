package ReservasiTULT;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reservasi")
public class ReservasiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Pengguna user = (Pengguna) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        SistemReservasiTULT sistem = (SistemReservasiTULT)
            getServletContext().getAttribute("sistem");

        if (sistem == null) {
            sistem = new SistemReservasiTULT();
            getServletContext().setAttribute("sistem", sistem);
        }

        String action = request.getParameter("action");
        String message = "";
        String error = "";

        if ("ajukan".equals(action)) {
            try {
                String kodeRuangan = request.getParameter("kodeRuangan");
                String tanggal = request.getParameter("tanggal");
                String jamMulaiStr = request.getParameter("jamMulai");
                String jamSelesaiStr = request.getParameter("jamSelesai");
                String keperluan = request.getParameter("keperluan");

                if (kodeRuangan == null || kodeRuangan.isEmpty() ||
                    tanggal == null || tanggal.isEmpty() ||
                    jamMulaiStr == null || jamMulaiStr.isEmpty() ||
                    jamSelesaiStr == null || jamSelesaiStr.isEmpty() ||
                    keperluan == null || keperluan.trim().isEmpty()) {
                    error = "Semua field harus diisi!";
                } else {
                    int jamMulai = Integer.parseInt(jamMulaiStr.substring(0, 2));
                    int menitMulai = jamMulaiStr.endsWith("30") ? 30 : 0;
                    int jamSelesai = Integer.parseInt(jamSelesaiStr.substring(0, 2));
                    int menitSelesai = jamSelesaiStr.endsWith("30") ? 30 : 0;

                    int totalMenitMulai = jamMulai * 60 + menitMulai;
                    int totalMenitSelesai = jamSelesai * 60 + menitSelesai;

                    if (totalMenitSelesai <= totalMenitMulai) {
                        error = "Jam selesai harus lebih besar dari jam mulai!";
                    } else {
                        Ruangan_Kelas ruangan = sistem.cariRuangan(kodeRuangan);

                        if (ruangan != null) {
                            Reservasi_Kelas reservasi = sistem.buatReservasi(
                                user, ruangan, tanggal, jamMulai, jamSelesai, keperluan
                            );

                            if (reservasi != null) {
                                message = "Berhasil mengajukan reservasi: " + reservasi.getKodeReservasi();
                            } else {
                                error = "Gagal mengajukan reservasi. Ruangan tidak tersedia atau jadwal bentrok.";
                            }
                        } else {
                            error = "Ruangan tidak ditemukan";
                        }
                    }
                }
            } catch (Exception e) {
                error = "Terjadi kesalahan saat mengajukan reservasi.";
                e.printStackTrace();
            }

        } else if ("batalkan".equals(action)) {
            String kodeReservasi = request.getParameter("kode");
            Reservasi_Kelas reservasi = sistem.findReservasiByKode(kodeReservasi);

            if (reservasi != null && reservasi.getPemohon().getId() == user.getId()) {
                reservasi.batalkanReservasi();
                sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.DIBATALKAN);
                message = "Reservasi " + kodeReservasi + " berhasil dibatalkan";
            } else {
                error = "Reservasi tidak ditemukan atau Anda tidak memiliki akses";
            }

        } else if ("selesaikan".equals(action)) {
            String kodeReservasi = request.getParameter("kode");
            Reservasi_Kelas reservasi = sistem.findReservasiByKode(kodeReservasi);

            if (reservasi != null && reservasi.getPemohon().getId() == user.getId() &&
                reservasi.getStatus() == StatusPeminjaman.DISETUJUI) {
                reservasi.setStatus(StatusPeminjaman.SELESAI);
                sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.SELESAI);
                message = "Reservasi " + kodeReservasi + " berhasil diselesaikan";
            } else {
                error = "Anda tidak dapat menyelesaikan reservasi ini";
            }
        }

        if (!message.isEmpty()) {
            request.setAttribute("message", message);
        }
        if (!error.isEmpty()) {
            request.setAttribute("error", error);
        }

        String redirectPath = user.getRole().equals("DOSEN")
            ? "/dosen/dashboard"
            : "/mahasiswa/dashboard";

        response.sendRedirect(request.getContextPath() + redirectPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}