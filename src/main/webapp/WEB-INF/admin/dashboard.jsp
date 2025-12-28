<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel - Reservasi TULT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { box-shadow: 0 0 15px rgba(0,0,0,.05); }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-danger">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-building"></i> Admin - Reservasi TULT
            </a>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login?logout=true">
                        <i class="bi bi-box-arrow-right"></i> Logout
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header bg-danger text-white">
                        <h4><i class="bi bi-speedometer2"></i> Dashboard Administrator</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <h5>Selamat datang, Administrator!</h5>
                        <p>Anda berhasil login sebagai admin.</p>
                        <hr>
                        <h5>Statistik Sistem</h5>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card text-center">
                                    <div class="card-body">
                                        <h3>${jumlahRuangan}</h3>
                                        <p>Total Ruangan</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card text-center">
                                    <div class="card-body">
                                        <h3>${jumlahReservasi}</h3>
                                        <p>Total Reservasi</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card text-center">
                                    <div class="card-body">
                                        <h3>${jumlahPengguna}</h3>
                                        <p>Total Pengguna</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <h5>Reservasi Menunggu Persetujuan</h5>
                        <c:choose>
                            <c:when test="${empty reservasiMenunggu}">
                                <p>Tidak ada reservasi yang menunggu.</p>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Kode</th>
                                                <th>Pemohon</th>
                                                <th>Ruangan</th>
                                                <th>Tanggal</th>
                                                <th>Waktu</th>
                                                <th>Aksi</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="res" items="${reservasiMenunggu}">
                                                <tr>
                                                    <td>${res.kodeReservasi}</td>
                                                    <td>${res.pemohon.nama}</td>
                                                    <td>${res.ruangan.kode}</td>
                                                    <td>${res.tanggal}</td>
                                                    <td>${res.jamMulai}:00 - ${res.jamSelesai}:00</td>
                                                    <td>
                                                        <a href="AdminServlet?action=setujui&kode=${res.kodeReservasi}"
                                                           class="btn btn-sm btn-success"
                                                           onclick="return confirm('Setujui reservasi ini?')">
                                                            <i class="bi bi-check"></i> Setujui
                                                        </a>

                                                        <!-- Tombol Tolak dengan Modal -->
                                                        <button type="button" class="btn btn-sm btn-danger" 
                                                                data-bs-toggle="modal" data-bs-target="#tolakModal"
                                                                onclick="setKodeTolak('${res.kodeReservasi}')">
                                                            <i class="bi bi-x"></i> Tolak
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Tolak dengan Alasan -->
    <div class="modal fade" id="tolakModal" tabindex="-1" aria-labelledby="tolakModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="tolakModalLabel">Tolak Reservasi</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="AdminServlet" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="action" value="tolak">
                        <input type="hidden" name="kode" id="kodeTolak" value="">
                        <div class="mb-3">
                            <label for="alasanTolak" class="form-label fw-bold">Alasan Penolakan</label>
                            <textarea class="form-control" id="alasanTolak" name="alasan" rows="4" required 
                                      placeholder="Masukkan alasan penolakan secara jelas..."></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Batal</button>
                        <button type="submit" class="btn btn-danger">Tolak Reservasi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function setKodeTolak(kode) {
            document.getElementById('kodeTolak').value = kode;
        }
    </script>
</body>
</html>