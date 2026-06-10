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
    <title>TRANSVISA - Dashboard de Control Operativo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold text-uppercase tracking-wider" href="#">TRANSVISA E.I.R.L.</a>
            <div class="d-flex align-items-center">
                <span class="navbar-text text-white me-3">
                    Usuario Contectado: <strong class="text-info"><%= session.getAttribute("usuarioLogueado") != null ? session.getAttribute("usuarioLogueado") : "Sesión Temporal" %></strong>
                </span>
                <a href="login" class="btn btn-sm btn-outline-danger px-3">Cerrar Sesión</a>
            </div>
        </div>
    </nav>

    <div class="container-fluid px-4 py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-secondary fw-bold mb-0">Panel de Control Gerencial</h2>
            <span class="badge bg-primary px-3 py-2 fs-6 shadow-sm">Fase 3: Backend MySQL Operativo</span>
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

        <div class="row">
            <div class="col-md-6">
                <div class="card border-0 shadow-sm p-4">
                    <h5 class="fw-bold text-secondary mb-3">Cumplimiento del Plan de Mantenimiento (RF-13)</h5>
                    <div class="progress mb-3" style="height: 25px;">
                        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated fw-bold" role="progressbar" style="width: 85%;" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100">85%</div>
                    </div>
                    <p class="text-muted small mb-0">Muestreo dinámico: Mantenimientos preventivos realizados a tiempo para evitar fallas mecánicas críticas en ruta.</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>