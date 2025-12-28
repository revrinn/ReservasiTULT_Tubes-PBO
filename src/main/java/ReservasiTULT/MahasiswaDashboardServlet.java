package ReservasiTULT;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/mahasiswa/dashboard")
public class MahasiswaDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Pengguna user = (Pengguna) session.getAttribute("user");
        
        // Cek login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Cek role
        if (!user.getRole().equals("MAHASISWA")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Mahasiswa mahasiswa = (Mahasiswa) user;
        SistemReservasiTULT sistem = (SistemReservasiTULT) 
            getServletContext().getAttribute("sistem");
        
        if (sistem == null) {
            sistem = new SistemReservasiTULT();
            getServletContext().setAttribute("sistem", sistem);
        }
        
        // Ambil data dari parameter jika ada
        String message = request.getParameter("message");
        String error = request.getParameter("error");
        
        if (message != null) {
            request.setAttribute("message", message);
        }
        
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        // Set data untuk JSP
        request.setAttribute("mahasiswa", mahasiswa);
        request.setAttribute("ruanganList", sistem.getAllRuangan());
        request.setAttribute("reservasiList", sistem.getReservasiByPengguna(user));
        request.setAttribute("bolehAjukan", mahasiswa.bolehMengajukanReservasi());
        request.setAttribute("ukm", mahasiswa.getUkm());
        
        // Forward ke JSP
        request.getRequestDispatcher("/WEB-INF/mahasiswa/dashboard.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}