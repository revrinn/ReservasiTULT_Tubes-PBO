package ReservasiTULT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuanganDAO {
    
    public List<Ruangan_Kelas> findAll() {
        List<Ruangan_Kelas> ruanganList = new ArrayList<>();
        String sql = "SELECT * FROM ruangan ORDER BY kode_ruangan";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ruanganList.add(mapToRuangan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ruanganList;
    }
    
    public Ruangan_Kelas findByKode(String kode) {
        String sql = "SELECT * FROM ruangan WHERE kode_ruangan = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapToRuangan(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void updateKetersediaan(int idRuangan, boolean tersedia) {
        String sql = "UPDATE ruangan SET tersedia = ? WHERE id_ruangan = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, tersedia);
            stmt.setInt(2, idRuangan);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Ruangan_Kelas mapToRuangan(ResultSet rs) throws SQLException {
        Ruangan_Kelas ruangan = new Ruangan_Kelas(
            rs.getString("kode_ruangan"),
            rs.getInt("lantai"),
            rs.getInt("kapasitas")
        );
        ruangan.setId(rs.getInt("id_ruangan"));
        ruangan.setTersedia(rs.getBoolean("tersedia"));
        return ruangan;
    }
}