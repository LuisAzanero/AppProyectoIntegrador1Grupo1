<%-- 
    Document   : mecanico_tareas
    Created on : 10 jun. 2026, 10:14:53
    Author     : luis.azanero
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.transvisa.domain.Vehiculo" %>
<%@ page import="pe.edu.utp.transvisa.domain.OrdenMantenimiento" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TRANSVISA - Rol: Técnico Mecánico</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        
        <%
            List<OrdenMantenimiento> lista = (List<OrdenMantenimiento>) session.getAttribute("listaOrdenes");
            int totalOrdenes = 0;
            int ordenesAbiertas = 0;
            int ordenesCerradas = 0;
            
            if (lista != null) {
                totalOrdenes = lista.size();
                for (OrdenMantenimiento o : lista) {
                    if ("ABIERTA".equals(o.getEstadoOrden())) {
                        ordenesAbiertas++;
                    } else {
                        ordenesCerradas++;
                    }
                }
            }
            
            String eficienciaTaller = "100%";
            if (totalOrdenes > 0) {
                double porc = ((double) ordenesCerradas / totalOrdenes) * 100;
                eficienciaTaller = String.format("%.1f%%", porc);
            }
        %>
        
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold text-uppercase text-warning" href="#"><i class="bi bi-wrench-adjustable-circle-fill"></i> TRANSVISA Taller</a>
                <div class="collapse navbar-collapse" id="navMecanico">
                    <div class="navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link active fw-semibold text-warning" href="tareas"><i class="bi bi-speedometer"></i> Mi Panel Técnico</a>
                        <a class="nav-link text-white-50" href="../mantenimientos"><i class="bi bi-tools"></i> Consola de Registro Maestro</a>
                    </div>
                    <div class="d-flex align-items-center gap-3">
                        <span class="navbar-text text-white">
                            <i class="bi bi-person-gear text-warning"></i> Técnico: <strong class="text-warning"><%= session.getAttribute("usuarioLogueado")%></strong>
                        </span>
                        <a href="../login" class="btn btn-sm btn-outline-warning px-3"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="text-secondary fw-bold mb-0">🔧 Consola de Monitoreo y KPIs Mecánicos</h2>
                    <p class="text-muted mb-0">Métricas de rendimiento operativo del taller de soporte de TRANSVISA.</p>
                </div>
                <a href="../mantenimientos" class="btn btn-warning fw-bold shadow-sm d-flex align-items-center gap-2">
                    <i class="bi bi-pencil-square"></i> Gestionar en Consola Maestra
                </a>
            </div>

            <!-- KPIs -->
            <div class="row g-4 mb-4">
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-primary border-4 shadow-sm bg-white">
                        <div class="card-body py-4">
                            <h6 class="text-muted text-uppercase fw-bold small mb-1">Total Órdenes Registradas</h6>
                            <h2 class="fw-bold text-dark mb-0"><%= totalOrdenes %></h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-danger border-4 shadow-sm bg-white">
                        <div class="card-body py-4">
                            <h6 class="text-danger text-uppercase fw-bold small mb-1">Trabajos en Ejecución</h6>
                            <h2 class="fw-bold text-danger mb-0"><%= ordenesAbiertas %></h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-success border-4 shadow-sm bg-white">
                        <div class="card-body py-4">
                            <h6 class="text-success text-uppercase fw-bold small mb-1">Unidades Reparadas</h6>
                            <h2 class="fw-bold text-success mb-0"><%= ordenesCerradas %></h2>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-xl-3">
                    <div class="card border-start border-warning border-4 shadow-sm bg-white">
                        <div class="card-body py-4">
                            <h6 class="text-warning text-uppercase fw-bold small mb-1">Tasa de Cierre Técnico</h6>
                            <h2 class="fw-bold text-warning mb-0"><%= eficienciaTaller %></h2>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Tabla -->
            <div class="card border-0 shadow-sm rounded-3">
                <div class="card-body p-4">
                    <table id="tablaTareas" class="table table-hover align-middle mb-0 w-100">
                        <thead class="table-dark">
                            <tr>
                                <th>ID Orden</th>
                                <th>Placa Vehículo</th>
                                <th>Clasificación</th>
                                <th>Diagnóstico / Trabajo Realizado</th>
                                <th>Fecha Inicio</th>
                                <th>Estado Actual</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (lista != null) {
                                    for (OrdenMantenimiento o : lista) {
                                        String tipoBadge = "PREVENTIVO".equals(o.getTipoMantenimiento()) ? "badge bg-info text-dark" : "badge bg-dark text-white";
                                        String estadoBadge = "ABIERTA".equals(o.getEstadoOrden()) ? "badge bg-danger p-2" : "badge bg-success p-2";
                            %>
                            <tr>
                                <td><strong>#<%= o.getIdOrden()%></strong></td>
                                <td><span class="badge bg-secondary p-2 font-monospace fs-6"><%= o.getPlacaVehiculo()%></span></td>
                                <td><span class="<%= tipoBadge %> p-2 fw-bold"><%= o.getTipoMantenimiento()%></span></td>
                                <td><p class="mb-0 text-wrap font-monospace small" style="max-width: 500px;"><%= o.getDescripcion()%></p></td>
                                <td><%= o.getFechaInicio()%></td>
                                <td><span class="<%= estadoBadge %>"><i class="bi <%= "ABIERTA".equals(o.getEstadoOrden()) ? "bi-clock-history" : "bi-shield-fill-check" %>"></i> <%= o.getEstadoOrden()%></span></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
        
        <script>
            $(document).ready(function () {
                $('#tablaTareas').DataTable({
                    responsive: true,
                    order: [[0, "desc"]],
                    language: {
                        search: "Buscar:",
                        lengthMenu: "Mostrar _MENU_ registros",
                        info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                        infoEmpty: "Mostrando registros del 0 al 0 de un total de 0 registros",
                        emptyTable: "Ningún dato disponible en esta tabla",
                        paginate: { previous: "Anterior", next: "Siguiente" }
                    }
                });
            });
        </script>
    </body>
</html>