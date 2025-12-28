<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Sistem Reservasi TULT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body class="bg-light" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh;">
    <div class="container py-5">
        <div class="row justify-content-center align-items-center" style="min-height: 90vh;">
            <div class="col-md-5">
                <div class="card shadow-lg border-0">
                    <div class="card-header text-center bg-primary text-white py-4">
                        <h2 class="mb-0"><i class="bi bi-building"></i> Reservasi TULT</h2>
                        <p class="mb-0">Sistem Reservasi Ruangan Kelas</p>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show">
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty param.message}">
                            <div class="alert alert-success alert-dismissible fade show">
                                ${param.message}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Username</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person"></i></span>
                                    <input type="text" name="username" class="form-control"
                                           placeholder="Masukkan username" required>
                                </div>
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-bold">Password</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                    <input type="password" name="password" class="form-control"
                                           placeholder="Masukkan password" required>
                                </div>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-box-arrow-in-right"></i> Login
                                </button>
                                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                                    <i class="bi bi-house"></i> Kembali ke Beranda
                                </a>
                            </div>
                        </form>
                        <div class="mt-3 text-center">
                            <small>Belum punya akun? <a href="${pageContext.request.contextPath}/signup.jsp">Daftar di sini</a></small>
                        </div>
                    </div>
                    <div class="card-footer text-center text-muted py-3">
                        <small>&copy; 2025 Reservasi TULT - Telkom University Landmark Tower</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>