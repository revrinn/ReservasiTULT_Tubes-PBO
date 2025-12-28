package ReservasiTULT;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private PenggunaDAO penggunaDAO = new PenggunaDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("Login attempt: " + username);
        
        Pengguna user = penggunaDAO.login(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userNama", user.getNama());
            
            // Inisialisasi sistem
            SistemReservasiTULT sistem = (SistemReservasiTULT) 
                getServletContext().getAttribute("sistem");
            
            if (sistem == null) {
                sistem = new SistemReservasiTULT();
                getServletContext().setAttribute("sistem", sistem);
            }
            
            System.out.println("Login berhasil: " + user.getNama() + " (" + user.getRole() + ")");
            
            // Redirect sesuai role
            if (user.getRole().equals("DOSEN")) {
                response.sendRedirect(request.getContextPath() + "/dosen/dashboard");
            } else if (user.getRole().equals("MAHASISWA")) {
                response.sendRedirect(request.getContextPath() + "/mahasiswa/dashboard");
            } else if (user.getRole().equals("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/AdminServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
            
        } else {
            // Login gagal
            System.out.println("Login gagal untuk: " + username);
            request.setAttribute("error", "Username atau password salah");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Logout jika ada parameter logout
        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                System.out.println("Logout: " + session.getAttribute("userNama"));
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Jika sudah login, redirect ke dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Pengguna user = (Pengguna) session.getAttribute("user");
            
            if (user.getRole().equals("DOSEN")) {
                response.sendRedirect(request.getContextPath() + "/dosen/dashboard");
            } else if (user.getRole().equals("MAHASISWA")) {
                response.sendRedirect(request.getContextPath() + "/mahasiswa/dashboard");
            } else if (user.getRole().equals("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/AdminServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
            return;
        }
        
        // Jika belum login, tampilkan login page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}