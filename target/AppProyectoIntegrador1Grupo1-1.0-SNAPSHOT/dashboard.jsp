<%-- 
    Document   : dashbord
    Created on : 9 jun. 2026, 19:08:37
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TRANSVISA - Dashboard de Control Operativo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold text-uppercase tracking-wider" href="#">TRANSVISA E.I.R.L.</a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <div class="navbar-nav me-auto">
                    <a class="nav-link active fw-semibold" aria-current="page" href="dashboard">📈 Dashboard</a>
                    <a class="nav-link text-white-50" href="usuarios">👥 Usuarios</a>
                    <a class="nav-link text-white-50" href="vehiculos">🚚 Vehículos</a>
                    <a class="nav-link text-white-50" href="conductores">🚚 Conductores</a>
                    <a class="nav-link text-white-50" href="movimientos_garita">🚚 Garita</a>
                </div>
                
                <div class="d-flex align-items-center mt-3 mt-lg-0">
                    <span class="navbar-text text-white me-3">
                        Usuario Conectado: <strong class="text-info"><%= session.getAttribute("usuarioLogueado") != null ? session.getAttribute("usuarioLogueado") : "Sesión Temporal" %></strong>
                    </span>
                    <a href="login" class="btn btn-sm btn-outline-danger px-3">Cerrar Sesión</a>
                </div>
            </div>
        </div>
    </nav>

    <div class="container-fluid px-4 py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-secondary fw-bold mb-0">Panel de Control Gerencial</h2>
            <span class="badge bg-primary px-3 py-2 fs-6 shadow-sm">Sistema Online</span>
        </div>
        
        <div class="row g-4 mb-4">
            <div class="col-sm-6 col-xl-3">
                <div class="card border-start border-primary border-4 shadow-sm">
                    <div class="card-body py-4">
                        <h6 class="text-primary text-uppercase fw-bold small mb-1">Total Flota</h6>
                        <h2 class="fw-bold text-dark mb-0"><%= request.getAttribute("totalVehiculos") != null ? request.getAttribute("totalVehiculos") : "3" %></h2>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-xl-3">
                <div class="card border-start border-success border-4 shadow-sm">
                    <div class="card-body py-4">
                        <h6 class="text-success text-uppercase fw-bold small mb-1">Unidades Disponibles</h6>
                        <h2 class="fw-bold text-dark mb-0"><%= request.getAttribute("vehiculosDisponibles") != null ? request.getAttribute("vehiculosDisponibles") : "2" %></h2>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-xl-3">
                <div class="card border-start border-warning border-4 shadow-sm">
                    <div class="card-body py-4">
                        <h6 class="text-warning text-uppercase fw-bold small mb-1">Unidades en Taller</h6>
                        <h2 class="fw-bold text-dark mb-0"><%= request.getAttribute("vehiculosEnTaller") != null ? request.getAttribute("vehiculosEnTaller") : "1" %></h2>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-xl-3">
                <div class="card border-start border-info border-4 shadow-sm">
                    <div class="card-body py-4">
                        <h6 class="text-info text-uppercase fw-bold small mb-1">Disponibilidad de Flota (%)</h6>
                        <h2 class="fw-bold text-dark mb-0"><%= request.getAttribute("porcentajeDisponibilidad") != null ? request.getAttribute("porcentajeDisponibilidad") : "66.6%" %></h2>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4 mb-4">
            <div class="col-md-4">
                <div class="card border-0 shadow-sm h-100">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-secondary mb-3">Estado de Unidades</h5>
                        <div style="position: relative; height:250px; width:100%">
                            <canvas id="chartEstados"></canvas>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-8">
                <div class="card border-0 shadow-sm h-100">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-secondary mb-3">Kilometraje Actual por Tracto (Control Preventivo)</h5>
                        <div style="position: relative; height:250px; width:100%">
                            <canvas id="chartKilometraje"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="card border-0 shadow-sm p-4">
                    <h5 class="fw-bold text-secondary mb-3">Cumplimiento del Plan de Mantenimiento (RF-13)</h5>
                    <div class="progress mb-3" style="height: 25px;">
                        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated fw-bold" role="progressbar" style="width: 85%;" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100">85%</div>
                    </div>
                    <p class="text-muted small mb-0">Muestreo analítico: Porcentaje de mantenimientos preventivos ejecutados satisfactoriamente dentro del rango de tolerancia mecánica establecido.</p>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
        // Estados de flota
        const ctxEstados = document.getElementById('chartEstados').getContext('2d');
        new Chart(ctxEstados, {
            type: 'doughnut',
            data: {
                labels: ['Disponibles', 'En Taller', 'En Ruta'],
                datasets: [{
                    data: [2, 1, 0], // Data dura estructurada para empate con tus KPIs
                    backgroundColor: ['#198754', '#ffc107', '#0dcaf0'],
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });

        // kilomeraje de las unidades
        const ctxKilometraje = document.getElementById('chartKilometraje').getContext('2d');
        new Chart(ctxKilometraje, {
            type: 'bar',
            data: {
                labels: ['V4X-912', 'F5T-204', 'A8B-311'], 
                datasets: [{
                    label: 'Kilómetros Recorridos (Km)',
                    data: [124500, 89200, 210150], 
                    backgroundColor: 'rgba(13, 110, 253, 0.85)',
                    borderColor: 'rgb(13, 110, 253)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: 'rgba(0, 0, 0, 0.05)'
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: false 
                    }
                }
            }
        });
    </script>
</body>
</html>