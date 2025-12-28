<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, ReservasiTULT.*" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Mahasiswa - Reservasi TULT</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .navbar { box-shadow: 0 2px 4px rgba(0,0,0,.1); }
        .card { border: none; box-shadow: 0 0 15px rgba(0,0,0,.05); }
        .status-badge {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        .status-menunggu { background-color: #ffc107; color: #000; }
        .status-disetujui { background-color: #28a745; color: #fff; }
        .status-ditolak { background-color: #dc3545; color: #fff; }
        .status-selesai { background-color: #17a2b8; color: #fff; }
        .status-dibatalkan { background-color: #6c757d; color: #fff; }
        .ukm-info {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .no-ukm-warning {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-building"></i> Reservasi TULT
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/mahasiswa/dashboard">
                            <i class="bi bi-speedometer2"></i> Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#reservasiModal">
                            <i class="bi bi-calendar-plus"></i> Ajukan Reservasi
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${mahasiswa.nama}
                        </a>
                        <ul class="dropdown-menu">
                            <li><span class="dropdown-item-text"><small>NIM: ${mahasiswa.nim}</small></span></li>
                            <li><span class="dropdown-item-text"><small>Jurusan: ${mahasiswa.jurusan}</small></span></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login?logout=true">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container mt-4">
        <!-- Messages -->
        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible fade show">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Welcome Card -->
        <div class="row mb-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title"><i class="bi bi-person-badge"></i> Selamat Datang, ${mahasiswa.nama}!</h4>
                        <div class="row mt-3">
                            <div class="col-md-3">
                                <div class="d-flex align-items-center">
                                    <div class="bg-primary text-white rounded-circle p-3 me-3">
                                        <i class="bi bi-person-fill" style="font-size: 2rem;"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-0">Mahasiswa</h6>
                                        <p class="text-muted mb-0">${mahasiswa.nim}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="d-flex align-items-center">
                                    <div class="bg-info text-white rounded-circle p-3 me-3">
                                        <i class="bi bi-building" style="font-size: 2rem;"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-0">Jurusan</h6>
                                        <p class="text-muted mb-0">${mahasiswa.jurusan}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="d-flex align-items-center">
                                    <div class="bg-success text-white rounded-circle p-3 me-3">
                                        <i class="bi bi-calendar-check" style="font-size: 2rem;"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-0">Total Reservasi</h6>
                                        <p class="text-muted mb-0">${reservasiList.size()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="d-flex align-items-center">
                                    <div class="${bolehAjukan ? 'bg-success' : 'bg-danger'} text-white rounded-circle p-3 me-3">
                                        <i class="bi bi-people" style="font-size: 2rem;"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-0">Status UKM</h6>
                                        <p class="text-muted mb-0">
                                            <c:choose>
                                                <c:when test="${bolehAjukan}">Aktif</c:when>
                                                <c:otherwise>Tidak Aktif</c:otherwise>
                                            </c:choose>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- UKM Information or Form -->
        <c:choose>
            <c:when test="${bolehAjukan && ukm != null}">
                <div class="ukm-info">
                    <h5><i class="bi bi-people-fill"></i> Informasi UKM</h5>
                    <p class="mb-1"><strong>Nama UKM:</strong> ${ukm.namaUnit}</p>
                    <p class="mb-0"><strong>Status:</strong> Aktif - Anda dapat mengajukan reservasi</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-ukm-warning">
                    <h5><i class="bi bi-exclamation-triangle-fill"></i> Perhatian!</h5>
                    <p class="mb-0">Anda belum terdaftar di UKM aktif. Hanya mahasiswa dengan UKM aktif yang dapat mengajukan reservasi ruangan.</p>
                </div>

                <!-- Form Daftar UKM -->
                <div class="card mt-4 border-warning">
                    <div class="card-header bg-warning text-dark">
                        <h5><i class="bi bi-plus-circle"></i> Daftar UKM Sekarang</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/update-ukm" method="post">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Kode UKM</label>
                                    <input type="text" name="kodeUkm" class="form-control" placeholder="Contoh: UKM001" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-bold">Nama UKM</label>
                                    <input type="text" name="namaUkm" class="form-control" placeholder="Contoh: Programming Club" required>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-warning">
                                <i class="bi bi-send"></i> Daftarkan UKM
                            </button>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Riwayat Reservasi -->
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="bi bi-clock-history"></i> Riwayat Reservasi</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty reservasiList}">
                                <div class="text-center py-5">
                                    <i class="bi bi-calendar-x text-muted" style="font-size: 4rem;"></i>
                                    <h5 class="mt-3 text-muted">Belum ada reservasi</h5>
                                    <p class="text-muted">Mulai dengan mengajukan reservasi pertama Anda</p>
                                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#reservasiModal"
                                            <c:if test="${!bolehAjukan}">disabled</c:if>>
                                        <i class="bi bi-calendar-plus"></i> Ajukan Reservasi Pertama
                                    </button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Kode Reservasi</th>
                                                <th>Ruangan</th>
                                                <th>Tanggal</th>
                                                <th>Waktu</th>
                                                <th>Keperluan</th>
                                                <th>Status</th>
                                                <th>Aksi</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="reservasi" items="${reservasiList}">
                                                <tr>
                                                    <td><code>${reservasi.kodeReservasi}</code></td>
                                                    <td>${reservasi.ruangan.kode}</td>
                                                    <td>${reservasi.tanggal}</td>
                                                    <td>${reservasi.jamMulai}:00 - ${reservasi.jamSelesai}:00</td>
                                                    <td>${reservasi.keperluan}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${reservasi.status == 'MENUNGGU'}">
                                                                <span class="status-badge status-menunggu">MENUNGGU</span>
                                                            </c:when>
                                                            <c:when test="${reservasi.status == 'DISETUJUI'}">
                                                                <span class="status-badge status-disetujui">DISETUJUI</span>
                                                            </c:when>
                                                            <c:when test="${reservasi.status == 'DITOLAK'}">
                                                                <span class="status-badge status-ditolak">DITOLAK</span>
                                                                <c:if test="${not empty reservasi.alasanTolak}">
                                                                    <br><small class="text-danger"><strong>Alasan:</strong> ${reservasi.alasanTolak}</small>
                                                                </c:if>
                                                            </c:when>
                                                            <c:when test="${reservasi.status == 'SELESAI'}">
                                                                <span class="status-badge status-selesai">SELESAI</span>
                                                            </c:when>
                                                            <c:when test="${reservasi.status == 'DIBATALKAN'}">
                                                                <span class="status-badge status-dibatalkan">DIBATALKAN</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <!-- Batalkan jika MENUNGGU atau DISETUJUI -->
                                                        <c:if test="${reservasi.status == 'MENUNGGU' || reservasi.status == 'DISETUJUI'}">
                                                            <form action="${pageContext.request.contextPath}/reservasi" method="post" style="display: inline;">
                                                                <input type="hidden" name="action" value="batalkan">
                                                                <input type="hidden" name="kode" value="${reservasi.kodeReservasi}">
                                                                <button type="submit" class="btn btn-sm btn-danger"
                                                                        onclick="return confirm('Batalkan reservasi ${reservasi.kodeReservasi}?')">
                                                                    <i class="bi bi-x-circle"></i> Batalkan
                                                                </button>
                                                            </form>
                                                        </c:if>

                                                        <!-- Selesaikan jika DISETUJUI -->
                                                        <c:if test="${reservasi.status == 'DISETUJUI'}">
                                                            <form action="${pageContext.request.contextPath}/reservasi" method="post" style="display: inline;">
                                                                <input type="hidden" name="action" value="selesaikan">
                                                                <input type="hidden" name="kode" value="${reservasi.kodeReservasi}">
                                                                <button type="submit" class="btn btn-sm btn-success"
                                                                        onclick="return confirm('Tandai reservasi ${reservasi.kodeReservasi} sebagai selesai?')">
                                                                    <i class="bi bi-check-circle-fill"></i> Selesaikan
                                                                </button>
                                                            </form>
                                                        </c:if>

                                                        <button class="btn btn-sm btn-info" onclick="alert('Detail reservasi ${reservasi.kodeReservasi} akan ditampilkan di update selanjutnya')">
                                                            <i class="bi bi-info-circle"></i> Detail
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

        <!-- Daftar Ruangan Tersedia -->
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0"><i class="bi bi-building"></i> Daftar Ruangan Tersedia</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <c:forEach var="ruangan" items="${ruanganList}">
                                <div class="col-md-4 mb-3">
                                    <div class="card h-100">
                                        <div class="card-body">
                                            <h5 class="card-title"><i class="bi bi-door-closed"></i> ${ruangan.kode}</h5>
                                            <p class="card-text">
                                                <i class="bi bi-arrow-up"></i> Lantai ${ruangan.lantai}<br>
                                                <i class="bi bi-people"></i> Kapasitas: ${ruangan.kapasitas} orang<br>
                                                <c:choose>
                                                    <c:when test="${ruangan.tersedia}">
                                                        <span class="badge bg-success"><i class="bi bi-check-circle"></i> Tersedia</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger"><i class="bi bi-x-circle"></i> Tidak Tersedia</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                            <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#reservasiModal"
                                                    onclick="pilihRuangan('${ruangan.kode}')"
                                                    <c:if test="${!bolehAjukan || !ruangan.tersedia}">disabled</c:if>>
                                                <i class="bi bi-calendar-plus"></i> Reservasi
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Ajukan Reservasi (FULL DIPERBAIKI) -->
    <div class="modal fade" id="reservasiModal" tabindex="-1" aria-labelledby="reservasiModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="reservasiModalLabel">
                        <i class="bi bi-calendar-plus"></i> Ajukan Reservasi Ruangan
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="${pageContext.request.contextPath}/reservasi" method="post" id="formReservasi">
                    <div class="modal-body">
                        <input type="hidden" name="action" value="ajukan">

                        <div class="mb-3">
                            <label class="form-label fw-bold">Ruangan</label>
                            <select name="kodeRuangan" id="kodeRuangan" class="form-select" required>
                                <option value="">-- Pilih Ruangan --</option>
                                <c:forEach var="ruangan" items="${ruanganList}">
                                    <option value="${ruangan.kode}" <c:if test="${!ruangan.tersedia}">disabled</c:if>>
                                        ${ruangan.kode} - Lantai ${ruangan.lantai} (Kapasitas: ${ruangan.kapasitas})
                                        <c:if test="${!ruangan.tersedia}"> [Tidak Tersedia]</c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Tanggal</label>
                            <input type="date" name="tanggal" class="form-control" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Jam Mulai</label>
                                <select name="jamMulai" id="jamMulai" class="form-select" required>
                                    <option value="">-- Pilih Jam Mulai --</option>
                                    <option value="0630">06:30</option>
                                    <option value="0700">07:00</option>
                                    <option value="0730">07:30</option>
                                    <option value="0800">08:00</option>
                                    <option value="0830">08:30</option>
                                    <option value="0900">09:00</option>
                                    <option value="0930">09:30</option>
                                    <option value="1000">10:00</option>
                                    <option value="1030">10:30</option>
                                    <option value="1100">11:00</option>
                                    <option value="1130">11:30</option>
                                    <option value="1200">12:00</option>
                                    <option value="1230">12:30</option>
                                    <option value="1300">13:00</option>
                                    <option value="1330">13:30</option>
                                    <option value="1400">14:00</option>
                                    <option value="1430">14:30</option>
                                    <option value="1500">15:00</option>
                                    <option value="1530">15:30</option>
                                    <option value="1600">16:00</option>
                                    <option value="1630">16:30</option>
                                    <option value="1700">17:00</option>
                                    <option value="1730">17:30</option>
                                    <option value="1800">18:00</option>
                                    <option value="1830">18:30</option>
                                    <option value="1900">19:00</option>
                                    <option value="1930">19:30</option>
                                    <option value="2000">20:00</option>
                                    <option value="2030">20:30</option>
                                </select>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-bold">Jam Selesai</label>
                                <select name="jamSelesai" id="jamSelesai" class="form-select" required>
                                    <option value="">-- Pilih Jam Selesai --</option>
                                    <option value="0700">07:00</option>
                                    <option value="0730">07:30</option>
                                    <option value="0800">08:00</option>
                                    <option value="0830">08:30</option>
                                    <option value="0900">09:00</option>
                                    <option value="0930">09:30</option>
                                    <option value="1000">10:00</option>
                                    <option value="1030">10:30</option>
                                    <option value="1100">11:00</option>
                                    <option value="1130">11:30</option>
                                    <option value="1200">12:00</option>
                                    <option value="1230">12:30</option>
                                    <option value="1300">13:00</option>
                                    <option value="1330">13:30</option>
                                    <option value="1400">14:00</option>
                                    <option value="1430">14:30</option>
                                    <option value="1500">15:00</option>
                                    <option value="1530">15:30</option>
                                    <option value="1600">16:00</option>
                                    <option value="1630">16:30</option>
                                    <option value="1700">17:00</option>
                                    <option value="1730">17:30</option>
                                    <option value="1800">18:00</option>
                                    <option value="1830">18:30</option>
                                    <option value="1900">19:00</option>
                                    <option value="1930">19:30</option>
                                    <option value="2000">20:00</option>
                                    <option value="2030">20:30</option>
                                    <option value="2100">21:00</option>
                                </select>
                            </div>
                        </div>

                        <div id="errorJam" class="alert alert-danger d-none mb-3">
                            Jam selesai harus lebih besar dari jam mulai!
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Keperluan</label>
                            <textarea name="keperluan" class="form-control" rows="3" required></textarea>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Batal</button>
                        <button type="submit" class="btn btn-primary">Ajukan Reservasi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function pilihRuangan(kode) {
            document.getElementById('kodeRuangan').value = kode;
        }

        const formReservasi = document.getElementById('formReservasi');
        formReservasi.addEventListener('submit', function(e) {
            const mulai = document.getElementById('jamMulai').value;
            const selesai = document.getElementById('jamSelesai').value;

            if (mulai && selesai && selesai <= mulai) {
                document.getElementById('errorJam').classList.remove('d-none');
                e.preventDefault();
            } else {
                document.getElementById('errorJam').classList.add('d-none');
            }
        });

        // Tanggal minimal hari ini
        const dateInput = document.querySelector('input[type="date"]');
        if (dateInput) {
            dateInput.min = new Date().toISOString().split('T')[0];
        }
    </script>
</body>
</html>