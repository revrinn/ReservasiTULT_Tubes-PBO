package ReservasiTULT;

public class LoginService {

    public Pengguna login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("LOGIN GAGAL: Username atau password kosong");
            return null;
        }

        String userTrim = username.trim();
        String passTrim = password.trim();

        System.out.println("Mencoba login dengan username: '" + userTrim + "'");
        System.out.println("Password yang dikirim: '" + passTrim + "'");

        PenggunaDAO dao = new PenggunaDAO();
        Pengguna user = dao.login(userTrim, passTrim);

        if (user != null) {
            System.out.println("LOGIN BERHASIL: " + user.getNama() + " (Role: " + user.getRole() + ")");
            return user;
        } else {
            System.out.println("LOGIN GAGAL: Tidak ditemukan di database untuk username '" + userTrim + "'");
            return null;
        }
    }
}