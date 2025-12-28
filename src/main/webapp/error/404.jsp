<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Halaman Tidak Ditemukan</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
            max-width: 600px;
            width: 90%;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            line-height: 1;
        }
        .error-icon {
            font-size: 6rem;
            color: #dc3545;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <i class="bi bi-exclamation-octagon"></i>
        </div>
        <div class="error-code">404</div>
        <h1 class="mb-4">Halaman Tidak Ditemukan</h1>
        <p class="lead mb-4">
            Maaf, halaman yang Anda cari tidak dapat ditemukan. 
            Mungkin halaman telah dipindahkan atau alamat URL tidak benar.
        </p>
        
        <div class="row mt-4">
            <div class="col-md-6 mb-2">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary w-100">
                    <i class="bi bi-house"></i> Kembali ke Beranda
                </a>
            </div>
            <div class="col-md-6 mb-2">
                <a href="javascript:history.back()" class="btn btn-outline-primary w-100">
                    <i class="bi bi-arrow-left"></i> Kembali ke Halaman Sebelumnya
                </a>
            </div>
        </div>
        
        <div class="mt-4 text-muted">
            <small>
                Jika masalah ini terus berlanjut, silakan hubungi administrator sistem.
                <br>
                <strong>Reservasi TULT</strong> &copy; 2025
            </small>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>