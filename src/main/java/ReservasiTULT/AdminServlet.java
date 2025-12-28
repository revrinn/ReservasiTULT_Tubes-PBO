package ReservasiTULT;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Proteksi admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        SistemReservasiTULT sistem = (SistemReservasiTULT)
            getServletContext().getAttribute("sistem");

        if (sistem == null) {
            sistem = new SistemReservasiTULT();
            getServletContext().setAttribute("sistem", sistem);
        }

        Admin admin = sistem.getAdmin();
        String action = request.getParameter("action");
        String kodeReservasi = request.getParameter("kode");

        if (action != null && kodeReservasi != null) {
            Reservasi_Kelas reservasi = sistem.findReservasiByKode(kodeReservasi);

            if (reservasi != null) {
                switch (action) {
                    case "setujui":
                        admin.setujuiReservasi(reservasi);
                        sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.DISETUJUI);
                        request.setAttribute("message", "Reservasi " + kodeReservasi + " telah disetujui");
                        break;
                    case "tolak":
                        String alasan = request.getParameter("alasan");
                        if (alasan == null || alasan.trim().isEmpty()) {
                            request.setAttribute("error", "Alasan penolakan wajib diisi!");
                        } else {
                            admin.tolakReservasi(reservasi);
                            sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.DITOLAK, alasan.trim());
                            request.setAttribute("message", "Reservasi " + kodeReservasi + " telah ditolak dengan alasan: " + alasan.trim());
                        }
                        break;
                    case "batalkan":
                        admin.batalkanReservasi(reservasi);
                        sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.DIBATALKAN);
                        request.setAttribute("message", "Reservasi " + kodeReservasi + " telah dibatalkan");
                        break;
                    case "selesaikan":
                        admin.selesaikanReservasi(reservasi);
                        sistem.updateStatusReservasi(kodeReservasi, StatusPeminjaman.SELESAI);
                        request.setAttribute("message", "Reservasi " + kodeReservasi + " telah selesai");
                        break;
                    default:
                        request.setAttribute("error", "Aksi tidak dikenali");
                        break;
                }
            } else {
                request.setAttribute("error", "Reservasi tidak ditemukan");
            }
        }

        request.setAttribute("admin", admin);
        request.setAttribute("semuaReservasi", sistem.getAllReservasi());
        request.setAttribute("reservasiMenunggu", sistem.getReservasiByStatus(StatusPeminjaman.MENUNGGU));
        request.setAttribute("reservasiDisetujui", sistem.getReservasiByStatus(StatusPeminjaman.DISETUJUI));
        request.setAttribute("jumlahRuangan", sistem.getJumlahRuangan());
        request.setAttribute("jumlahReservasi", sistem.getJumlahReservasi());
        request.setAttribute("jumlahPengguna", sistem.getJumlahPengguna());

        request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
    }
}