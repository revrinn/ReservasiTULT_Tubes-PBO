package ReservasiTULT;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dosen/dashboard")
public class DosenDashboardServlet extends HttpServlet {
    
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
        if (!user.getRole().equals("DOSEN")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Dosen dosen = (Dosen) user;
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
        request.setAttribute("dosen", dosen);
        request.setAttribute("ruanganList", sistem.getAllRuangan());
        request.setAttribute("reservasiList", sistem.getReservasiByPengguna(user));
        
        // Forward ke JSP
        request.getRequestDispatcher("/WEB-INF/dosen/dashboard.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}