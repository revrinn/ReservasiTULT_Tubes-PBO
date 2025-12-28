package ReservasiTULT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenggunaDAO {
    
    public Pengguna login(String username, String password) {
    String sql = "SELECT p.*, u.id_ukm, u.kode_ukm, u.nama_ukm, " +
                 "pb.nama as nama_pembina, pb.nip as nip_pembina " +
                 "FROM pengguna p " +
                 "LEFT JOIN ukm u ON p.id_ukm = u.id_ukm " +
                 "LEFT JOIN pengguna pb ON u.id_pembina = pb.id_pengguna " +
                 "WHERE p.username = ? AND p.password = ?";

    System.out.println("DAO LOGIN DIPANGGIL");
    System.out.println("Username dicari: '" + username + "'");
    System.out.println("Password dicari: '" + password + "'");
    System.out.println("Query: " + sql);

    try (Connection conn = DatabaseConfig.getConnection()) {
        System.out.println("Koneksi database berhasil untuk login");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            System.out.println("Executing query login...");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("DATA DITEMUKAN! Role: " + rs.getString("role"));
                    Pengguna user = mapToPengguna(rs);
                    if (user != null) {
                        System.out.println("Mapping berhasil: " + user.getNama() + " - " + user.getRole());
                    } else {
                        System.out.println("Mapping gagal (mapToPengguna return null)");
                    }
                    return user;
                } else {
                    System.out.println("TIDAK ADA DATA yang match di database");
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("ERROR SQL saat login: " + e.getMessage());
        e.printStackTrace();
        return null;
    }

    System.out.println("Login DAO return null");
    return null;
}
    
    public Pengguna findById(int id) {
        String sql = "SELECT p.*, u.kode_ukm, u.nama_ukm, pb.nama as nama_pembina " +
                     "FROM pengguna p " +
                     "LEFT JOIN ukm u ON p.id_ukm = u.id_ukm " +
                     "LEFT JOIN pengguna pb ON u.id_pembina = pb.id_pengguna " +
                     "WHERE p.id_pengguna = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapToPengguna(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Pengguna> findAll() {
        List<Pengguna> penggunaList = new ArrayList<>();
        String sql = "SELECT p.*, u.kode_ukm, u.nama_ukm, pb.nama as nama_pembina " +
                     "FROM pengguna p " +
                     "LEFT JOIN ukm u ON p.id_ukm = u.id_ukm " +
                     "LEFT JOIN pengguna pb ON u.id_pembina = pb.id_pengguna";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                penggunaList.add(mapToPengguna(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return penggunaList;
    }
    
    private Pengguna mapToPengguna(ResultSet rs) throws SQLException {
    String role = rs.getString("role");

    if ("DOSEN".equals(role)) {
        Dosen dosen = new Dosen(
            rs.getString("nama"),
            rs.getString("nip"),
            rs.getString("unit_kerja")
        );
        dosen.setId(rs.getInt("id_pengguna"));
        return dosen;

    } else if ("MAHASISWA".equals(role)) {
        UKM ukm = null;
        if (rs.getString("kode_ukm") != null) {
            Dosen pembina = new Dosen(
                rs.getString("nama_pembina"),
                "", ""
            );
            ukm = new UKM(
                rs.getString("kode_ukm"),
                rs.getString("nama_ukm"),
                pembina
            );
        }

        Mahasiswa mhs = new Mahasiswa(
            rs.getString("nama"),
            rs.getString("nim"),
            rs.getString("jurusan"),
            ukm
        );
        mhs.setId(rs.getInt("id_pengguna"));
        return mhs;

    } else if ("ADMIN".equals(role)) {
        // Tambah handling untuk ADMIN
        Pengguna admin = new Pengguna(rs.getString("nama")) {
            @Override
            public void tampilkanInfo() {
                System.out.println("Admin");
            }

            @Override
            public boolean bolehMengajukanReservasi() {
                return true;
            }

            @Override
            public String getRole() {
                return "ADMIN";
            }
        };
        admin.setId(rs.getInt("id_pengguna"));
        return admin;
    }

    // Kalau role tidak dikenali
    return null;
    }
}