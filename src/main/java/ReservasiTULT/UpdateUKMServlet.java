package ReservasiTULT;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/update-ukm")
public class UpdateUKMServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Pengguna user = (Pengguna) session.getAttribute("user");

        if (user == null || !"MAHASISWA".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String kodeUkm = request.getParameter("kodeUkm");
        String namaUkm = request.getParameter("namaUkm");

        if (kodeUkm == null || namaUkm == null || kodeUkm.trim().isEmpty() || namaUkm.trim().isEmpty()) {
            response.sendRedirect("mahasiswa/dashboard?error=Data UKM tidak lengkap");
            return;
        }

        int idUkm = -1;

        try (Connection conn = DatabaseConfig.getConnection()) {
            // Cek apakah UKM sudah ada
            String checkSql = "SELECT id_ukm FROM ukm WHERE kode_ukm = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, kodeUkm.trim());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    idUkm = rs.getInt("id_ukm");
                }
            }

            // Jika belum ada, buat baru (id_pembina default 1, sesuaikan dengan ID dosen pembina)
            if (idUkm == -1) {
                String insertSql = "INSERT INTO ukm (kode_ukm, nama_ukm, id_pembina) VALUES (?, ?, 1)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, kodeUkm.trim());
                    insertStmt.setString(2, namaUkm.trim());
                    insertStmt.executeUpdate();

                    ResultSet generated = insertStmt.getGeneratedKeys();
                    if (generated.next()) {
                        idUkm = generated.getInt(1);
                    }
                }
            }

            // Update id_ukm di tabel pengguna
            String updateSql = "UPDATE pengguna SET id_ukm = ? WHERE id_pengguna = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, idUkm);
                updateStmt.setInt(2, user.getId());
                updateStmt.executeUpdate();
            }

            // Update objek session
            Mahasiswa mhs = (Mahasiswa) user;
            mhs.setUkm(new UKM(kodeUkm.trim(), namaUkm.trim(), null)); // Pembina null sementara
            session.setAttribute("user", mhs);

            response.sendRedirect("mahasiswa/dashboard?message=UKM berhasil didaftarkan!");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("mahasiswa/dashboard?error=Gagal mendaftar UKM: " + e.getMessage());
        }
    }
}