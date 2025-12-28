<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Kesalahan Server</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .error-container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,.3);
            text-align: center;
            max-width: 700px;
            width: 90%;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            line-height: 1;
        }
        .error-icon {
            font-size: 6rem;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-details {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin-top: 20px;
            text-align: left;
            font-family: monospace;
            font-size: 12px;
            max-height: 200px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <i class="bi bi-bug"></i>
        </div>
        <div class="error-code">500</div>
        <h1 class="mb-3">Kesalahan Server Internal</h1>
        <p class="lead mb-4">
            Maaf, terjadi kesalahan pada server kami. 
            Tim teknis telah diberitahu dan akan segera memperbaiki masalah ini.
        </p>
        
        <!-- Error Details (only in development) -->
        <% if (exception != null) { %>
            <div class="error-details">
                <strong>Error Message:</strong> <%= exception.getMessage() %><br>
                <strong>Error Type:</strong> <%= exception.getClass().getName() %><br><br>
                
                <strong>Stack Trace:</strong><br>
                <% 
                    java.io.PrintWriter pw = new java.io.PrintWriter(out);
                    exception.printStackTrace(pw);
                %>
            </div>
        <% } %>
        
        <div class="row mt-4">
            <div class="col-md-6 mb-2">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary w-100">
                    <i class="bi bi-house"></i> Kembali ke Beranda
                </a>
            </div>
            <div class="col-md-6 mb-2">
                <button onclick="location.reload()" class="btn btn-outline-primary w-100">
                    <i class="bi bi-arrow-clockwise"></i> Coba Lagi
                </button>
            </div>
        </div>
        
        <div class="mt-4">
            <a href="mailto:admin@tult.ac.id" class="btn btn-outline-danger">
                <i class="bi bi-envelope"></i> Laporkan Masalah
            </a>
        </div>
        
        <div class="mt-4 text-muted">
            <small>
                <strong>Reservasi TULT</strong> &copy; 2025
                <br>
                Sistem Reservasi Ruangan Kelas - Telkom University Landmark Tower
            </small>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>