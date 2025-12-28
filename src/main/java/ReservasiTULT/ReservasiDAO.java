package ReservasiTULT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasiDAO {

    public void save(Reservasi_Kelas reservasi) {
        String sql = "INSERT INTO reservasi (kode_reservasi, id_pemohon, id_ruangan, " +
                     "tanggal, jam_mulai, jam_selesai, keperluan, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservasi.getKodeReservasi());
            stmt.setInt(2, reservasi.getPemohon().getId());
            stmt.setInt(3, reservasi.getRuangan().getId());
            stmt.setDate(4, Date.valueOf(reservasi.getTanggal()));
            stmt.setInt(5, reservasi.getJamMulai());
            stmt.setInt(6, reservasi.getJamSelesai());
            stmt.setString(7, reservasi.getKeperluan());
            stmt.setString(8, reservasi.getStatus().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(String kodeReservasi, StatusPeminjaman status, String alasanTolak) {
        String sql = "UPDATE reservasi SET status = ?, alasan_tolak = ? WHERE kode_reservasi = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setString(2, alasanTolak);
            stmt.setString(3, kodeReservasi);

            stmt.executeUpdate();

            if (status == StatusPeminjaman.SELESAI || status == StatusPeminjaman.DIBATALKAN || status == StatusPeminjaman.DITOLAK) {
                updateTanggalDiselesaikan(kodeReservasi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(String kodeReservasi, StatusPeminjaman status) {
        updateStatus(kodeReservasi, status, null);
    }

    private void updateTanggalDiselesaikan(String kodeReservasi) {
        String sql = "UPDATE reservasi SET tanggal_diselesaikan = CURRENT_TIMESTAMP " +
                     "WHERE kode_reservasi = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeReservasi);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reservasi_Kelas findByKode(String kodeReservasi) {
        String sql = "SELECT r.*, p.nama as nama_pemohon, p.role, p.nim, p.nip, " +
                     "ru.kode_ruangan, ru.lantai, ru.kapasitas, ru.tersedia, r.alasan_tolak " +
                     "FROM reservasi r " +
                     "JOIN pengguna p ON r.id_pemohon = p.id_pengguna " +
                     "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                     "WHERE r.kode_reservasi = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeReservasi);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapToReservasi(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reservasi_Kelas> findByPengguna(Pengguna pengguna) {
        List<Reservasi_Kelas> reservasiList = new ArrayList<>();
        String sql = "SELECT r.*, p.nama as nama_pemohon, p.role, p.nim, p.nip, " +
                     "ru.kode_ruangan, ru.lantai, ru.kapasitas, ru.tersedia, r.alasan_tolak " +
                     "FROM reservasi r " +
                     "JOIN pengguna p ON r.id_pemohon = p.id_pengguna " +
                     "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                     "WHERE r.id_pemohon = ? " +
                     "ORDER BY r.tanggal DESC, r.jam_mulai DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pengguna.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservasiList.add(mapToReservasi(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasiList;
    }

    public List<Reservasi_Kelas> findAll() {
        List<Reservasi_Kelas> reservasiList = new ArrayList<>();
        String sql = "SELECT r.*, p.nama as nama_pemohon, p.role, p.nim, p.nip, " +
                     "ru.kode_ruangan, ru.lantai, ru.kapasitas, ru.tersedia, r.alasan_tolak " +
                     "FROM reservasi r " +
                     "JOIN pengguna p ON r.id_pemohon = p.id_pengguna " +
                     "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                     "ORDER BY r.tanggal DESC, r.jam_mulai DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservasiList.add(mapToReservasi(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasiList;
    }

    public List<Reservasi_Kelas> findByStatus(StatusPeminjaman status) {
        List<Reservasi_Kelas> reservasiList = new ArrayList<>();
        String sql = "SELECT r.*, p.nama as nama_pemohon, p.role, p.nim, p.nip, " +
                     "ru.kode_ruangan, ru.lantai, ru.kapasitas, ru.tersedia, r.alasan_tolak " +
                     "FROM reservasi r " +
                     "JOIN pengguna p ON r.id_pemohon = p.id_pengguna " +
                     "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                     "WHERE r.status = ? " +
                     "ORDER BY r.tanggal DESC, r.jam_mulai DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservasiList.add(mapToReservasi(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasiList;
    }

    public boolean cekBentrok(String tanggal, int jamMulai, int jamSelesai, String kodeRuangan) {
        String sql = "SELECT COUNT(*) as count FROM reservasi r " +
                     "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                     "WHERE r.tanggal = ? AND ru.kode_ruangan = ? " +
                     "AND r.status IN ('DISETUJUI', 'MENUNGGU') " +
                     "AND ((? < r.jam_selesai AND ? > r.jam_mulai))";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(tanggal));
            stmt.setString(2, kodeRuangan);
            stmt.setInt(3, jamMulai);
            stmt.setInt(4, jamSelesai);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Reservasi_Kelas mapToReservasi(ResultSet rs) throws SQLException {
        Pengguna pemohon;
        String role = rs.getString("role");

        if ("DOSEN".equals(role)) {
            pemohon = new Dosen(
                rs.getString("nama_pemohon"),
                rs.getString("nip"),
                ""
            );
        } else {
            pemohon = new Mahasiswa(
                rs.getString("nama_pemohon"),
                rs.getString("nim"),
                "",
                null
            );
        }
        pemohon.setId(rs.getInt("id_pemohon"));

        Ruangan_Kelas ruangan = new Ruangan_Kelas(
            rs.getString("kode_ruangan"),
            rs.getInt("lantai"),
            rs.getInt("kapasitas")
        );
        ruangan.setId(rs.getInt("id_ruangan"));
        ruangan.setTersedia(rs.getBoolean("tersedia"));

        Reservasi_Kelas reservasi = new Reservasi_Kelas(
            pemohon,
            ruangan,
            rs.getDate("tanggal").toString(),
            rs.getInt("jam_mulai"),
            rs.getInt("jam_selesai"),
            rs.getString("keperluan")
        );
        reservasi.setKodeReservasi(rs.getString("kode_reservasi"));
        reservasi.setStatus(StatusPeminjaman.valueOf(rs.getString("status")));
        reservasi.setAlasanTolak(rs.getString("alasan_tolak")); // FITUR BARU

        return reservasi;
    }
}