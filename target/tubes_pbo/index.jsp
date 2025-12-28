<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistem Reservasi TULT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
          rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            
            <div class="card shadow border-0">
                <div class="card-body p-5">
                    
                    <h1 class="display-5 mb-4">🏛️ Sistem Reservasi TULT</h1>
                    
                    <p class="lead mb-4">
                        Sistem reservasi ruangan kelas di Telkom University Landmark Tower.
                    </p>
                    
                    <div class="d-grid gap-2 col-md-6 mx-auto">
                        <a href="login.jsp" class="btn btn-primary btn-lg">
                            <i class="bi bi-box-arrow-in-right"></i> Login
                        </a>
                        <a href="AdminServlet" class="btn btn-outline-secondary btn-lg">
                            <i class="bi bi-gear"></i> Admin Panel
                        </a>
                    </div>
                    
                    <hr class="my-4">
                    
                    <h5>Akun Demo:</h5>
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <h6>👨‍🏫 Dosen</h6>
                                    <p class="mb-1"><small>User: 23990046</small></p>
                                    <p class="mb-0"><small>Pass: dosen123</small></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h6>👨‍🎓 Mahasiswa</h6>
                                    <p class="mb-1"><small>User: 103052300224</small></p>
                                    <p class="mb-0"><small>Pass: mhs123</small></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
            
        </div>
    </div>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

</body>
</html>