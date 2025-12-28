<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up | Reservasi TULT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body class="bg-light" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh;">
    <div class="container py-5">
        <div class="row justify-content-center align-items-center" style="min-height: 90vh;">
            <div class="col-md-6">
                <div class="card shadow-lg border-0">
                    <div class="card-header text-center bg-primary text-white py-4">
                        <h3 class="mb-0"><i class="bi bi-person-plus"></i> Registrasi Akun Baru</h3>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show">
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/signup" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Role</label>
                                <select name="role" class="form-select" id="roleSelect" required>
                                    <option value="">-- Pilih Role --</option>
                                    <option value="DOSEN">Dosen</option>
                                    <option value="MAHASISWA">Mahasiswa</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Nama Lengkap</label>
                                <input type="text" name="nama" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Username (NIP untuk Dosen / NIM untuk Mahasiswa)</label>
                                <input type="text" name="username" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>

                            <!-- Field tambahan sesuai role -->
                            <div id="extraFields"></div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg">Daftar</button>
                                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-secondary">
                                    Sudah punya akun? Login
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('roleSelect').addEventListener('change', function() {
            const extra = document.getElementById('extraFields');
            extra.innerHTML = '';
            if (this.value === 'DOSEN') {
                extra.innerHTML = `
                    <div class="mb-3">
                        <label class="form-label fw-bold">Unit Kerja</label>
                        <input type="text" name="unitKerja" class="form-control" required>
                    </div>
                `;
            } else if (this.value === 'MAHASISWA') {
                extra.innerHTML = `
                    <div class="mb-3">
                        <label class="form-label fw-bold">Jurusan</label>
                        <input type="text" name="jurusan" class="form-control" required>
                    </div>
                `;
            }
        });
    </script>
</body>
</html>