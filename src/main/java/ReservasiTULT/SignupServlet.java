package ReservasiTULT;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        String nama = request.getParameter("nama");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (role == null || nama == null || username == null || password == null ||
            nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Semua field wajib diisi!");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        String sql = "INSERT INTO pengguna (username, password, nama, role";
        String valuesPart = ") VALUES (?, ?, ?, ?";

        if ("DOSEN".equals(role)) {
            String unitKerja = request.getParameter("unitKerja");
            if (unitKerja == null || unitKerja.isEmpty()) {
                request.setAttribute("error", "Unit Kerja wajib diisi untuk Dosen!");
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }
            sql += ", nip, unit_kerja" + valuesPart + ", ?, ?)";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, nama);
                stmt.setString(4, role);
                stmt.setString(5, username); // NIP = username
                stmt.setString(6, unitKerja);
                stmt.executeUpdate();
            } catch (SQLException e) {
                request.setAttribute("error", "Gagal mendaftar: " + e.getMessage());
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }
        } else if ("MAHASISWA".equals(role)) {
            String jurusan = request.getParameter("jurusan");
            if (jurusan == null || jurusan.isEmpty()) {
                request.setAttribute("error", "Jurusan wajib diisi untuk Mahasiswa!");
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }
            sql += ", nim, jurusan" + valuesPart + ", ?, ?)";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, nama);
                stmt.setString(4, role);
                stmt.setString(5, username); // NIM = username
                stmt.setString(6, jurusan);
                stmt.executeUpdate();
            } catch (SQLException e) {
                request.setAttribute("error", "Gagal mendaftar: " + e.getMessage());
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Role tidak valid!");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("login.jsp?message=Registrasi berhasil! Silakan login dengan akun baru Anda.");
    }
}