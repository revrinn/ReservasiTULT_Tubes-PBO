package ReservasiTULT;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== TEST KONEKSI DATABASE ===");
        
        try {
            // Test 1: Koneksi database
            System.out.println("\n1. Testing database connection...");
            Connection conn = DatabaseConfig.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Koneksi database BERHASIL");
                
                // Test 2: Cek tabel
                System.out.println("\n2. Checking database tables...");
                DatabaseMetaData meta = conn.getMetaData();
                String[] tableTypes = {"TABLE"};
                ResultSet tables = meta.getTables("reservasi_tult", null, "%", tableTypes);
                
                boolean hasUsers = false;
                boolean hasRooms = false;
                boolean hasReservations = false;
                
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("   - Tabel ditemukan: " + tableName);
                    
                    if (tableName.equalsIgnoreCase("pengguna")) hasUsers = true;
                    if (tableName.equalsIgnoreCase("ruangan")) hasRooms = true;
                    if (tableName.equalsIgnoreCase("reservasi")) hasReservations = true;
                }
                
                // Test 3: Count data
                System.out.println("\n3. Counting records...");
                Statement stmt = conn.createStatement();
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM pengguna");
                if (rs.next()) {
                    System.out.println("   - Jumlah pengguna: " + rs.getInt("count"));
                }
                
                rs = stmt.executeQuery("SELECT COUNT(*) as count FROM ruangan");
                if (rs.next()) {
                    System.out.println("   - Jumlah ruangan: " + rs.getInt("count"));
                }
                
                DatabaseConfig.closeConnection(conn);
                System.out.println("\n=== TEST SELESAI ===");
                
            } else {
                System.out.println("✗ Koneksi database GAGAL");
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}