<%-- 
    Document   : supervisor_panel
    Created on : 10 jun. 2026, 10:14:29
    Author     : luis.azanero
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TRANSVISA - Rol: Supervisor de Flota</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        
        <%
            Integer disponibles = (request.getAttribute("vehiculosDisponibles") != null) ? (Integer) request.getAttribute("vehiculosDisponibles") : 2;
            Integer enTaller = (request.getAttribute("vehiculosEnTaller") != null) ? (Integer) request.getAttribute("vehiculosEnTaller") : 1;
            Integer enRuta = (request.getAttribute("vehiculosEnRuta") != null) ? (Integer) request.getAttribute("vehiculosEnRuta") : 0;
            Integer total = disponibles + enTaller + enRuta;
        %>
        
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold text-uppercase" href="#"><i class="bi bi-shield-shaded text-primary"></i> TRANSVISA E.I.R.L.</a>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navSupervisor">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navSupervisor">
                    <div class="navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link active fw-semibold text-primary" href="panel"><i class="bi bi-speedometer2"></i> Mi Panel</a>
                        <a class="nav-link text-white-50" href="../conductores"><i class="bi bi-person-badge"></i> Conductores</a>
                        <a class="nav-link text-white-50" href="../mantenimientos"><i class="bi bi-tools"></i> Órdenes Taller</a>
                        <a class="nav-link text-white-50" href="../garita/panel"><i class="bi bi-door-open"></i> Registrar Entradas/Salidas</a>
                    </div>
                    
                    <div class="d-flex align-items-center gap-3">
                        <span class="navbar-text text-white">
                            <i class="bi bi-person-circle text-info"></i> Supervisor: <strong class="text-info"><%= session.getAttribute("usuarioLogueado") != null ? session.getAttribute("usuarioLogueado") : "Operaciones" %></strong>
                        </span>
                        <a href="../login" class="btn btn-sm btn-outline-danger px-3"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="text-secondary fw-bold mb-0">📋 Centro de Control de Operaciones</h2>
                    <p class="text-muted mb-0">Monitoreo y actualización del control operativo de las unidades en tiempo real.</p>
                </div>
                <span class="badge bg-success px-3 py-2 fs-6 shadow-sm"><i class="bi bi-broadcast"></i> Central de Flota Activa</span>
            </div>
            
            <div class="row g-4 mb-5">
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-primary border-4 shadow-sm bg-white">
                        <div class="card-body d-flex align-items-center justify-content-between py-4">
                            <div>
                                <h6 class="text-muted text-uppercase fw-bold small mb-1">Total Vehículos</h6>
                                <h2 class="fw-bold text-dark mb-0"><%= total %></h2>
                            </div>
                            <div class="fs-1 text-primary text-opacity-50"><i class="bi bi-truck"></i></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-success border-4 shadow-sm bg-white">
                        <div class="card-body d-flex align-items-center justify-content-between py-4">
                            <div>
                                <h6 class="text-muted text-uppercase fw-bold small mb-1">Disponibles en Base</h6>
                                <h2 class="fw-bold text-success mb-0"><%= disponibles %></h2>
                            </div>
                            <div class="fs-1 text-success text-opacity-50"><i class="bi bi-check-circle-fill"></i></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-info border-4 shadow-sm bg-white">
                        <div class="card-body d-flex align-items-center justify-content-between py-4">
                            <div>
                                <h6 class="text-muted text-uppercase fw-bold small mb-1">Unidades en Ruta</h6>
                                <h2 class="fw-bold text-info mb-0"><%= enRuta %></h2>
                            </div>
                            <div class="fs-1 text-info text-opacity-50"><i class="bi bi-geo-alt-fill"></i></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-warning border-4 shadow-sm bg-white">
                        <div class="card-body d-flex align-items-center justify-content-between py-4">
                            <div>
                                <h6 class="text-muted text-uppercase fw-bold small mb-1">Unidades en Taller</h6>
                                <h2 class="fw-bold text-warning mb-0"><%= enTaller %></h2>
                            </div>
                            <div class="fs-1 text-warning text-opacity-50"><i class="bi bi-tools"></i></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card border-0 shadow-sm rounded-3">
                <div class="card-body p-5 text-center">
                    <div class="mb-3 fs-1 text-secondary opacity-50">
                        <i class="bi bi-terminal-dash"></i>
                    </div>
                    <h4 class="text-secondary fw-bold">Consola Central de Operaciones</h4>
                    <p class="text-muted mx-auto" style="max-width: 650px;">
                        Para cumplir con la <strong>HU-1</strong>, utiliza el enlace de la barra superior **"Registrar Entradas/Salidas"** para abrir el control perimetral, auditar el kilometraje físico de los camiones y alternar su disponibilidad automática en el sistema.
                    </p>
                    <a href="../garita/panel" class="btn btn-primary mt-2 fw-semibold px-4 py-2">
                        <i class="bi bi-door-open-fill"></i> Ir a Control de Accesos
                    </a>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>