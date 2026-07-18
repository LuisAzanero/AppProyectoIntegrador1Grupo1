<%-- 
    Document   : conductor_panel
    Created on : 10 jul. 2026, 15:15:00
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
        <title>TRANSVISA - Inspección Pre-Ruta</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        
        <!-- Navbar para el Conductor -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold text-uppercase" href="#"><i class="bi bi-truck text-info"></i> TRANSVISA - Rol: Conductor</a>
                <div class="d-flex align-items-center gap-3">
                    <span class="navbar-text text-white">
                        <i class="bi bi-person-fill text-info"></i> Conductor: <strong><%= session.getAttribute("usuarioLogueado") != null ? session.getAttribute("usuarioLogueado") : "Conductor" %></strong>
                    </span>
                    <a href="../login" class="btn btn-sm btn-outline-danger px-3"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a>
                </div>
            </div>
        </nav>

        <div class="container py-5">
            <%
                Vehiculo v = (Vehiculo) request.getAttribute("miVehiculo");
                List<OrdenMantenimiento> alertas = (List<OrdenMantenimiento>) request.getAttribute("alertasPendientes");
                int numAlertas = (alertas != null) ? alertas.size() : 0;
                
                // Definir semáforo visual de salida
                String semaforoClase = numAlertas > 0 ? "alert-danger text-danger border-danger" : "alert-success text-success border-success";
                String semaforoIcono = numAlertas > 0 ? "bi-exclamation-octagon-fill" : "bi-shield-check";
                String semaforoMensaje = numAlertas > 0 ? "⛔ UNIDAD RESTRINGIDA: Se detectaron trabajos mecánicos pendientes en taller." : "✅ RUTA AUTORIZADA: Unidad libre de alertas críticas de mantenimiento.";
            %> <!-- 🛠️ CORREGIDO: Se cerró el bloque scriptlet aquí -->

            <%-- Semáforo de Salida Inmediata --%>
            <div class="alert <%= semaforoClase %> d-flex align-items-center gap-3 shadow-sm py-3 mb-4 rounded-3 fw-bold border-2" role="alert">
                <i class="bi <%= semaforoIcono %> fs-3"></i>
                <div><%= semaforoMensaje %></div>
            </div>

            <div class="row g-4">
                <!-- Ficha Técnica de la Unidad Asignada -->
                <div class="col-lg-4">
                    <div class="card border-0 shadow-sm rounded-3 h-100">
                        <div class="card-header bg-secondary text-white fw-bold py-3">
                            <i class="bi bi-card-list"></i> Estado Técnico de la Unidad
                        </div>
                        <div class="card-body p-4 bg-white">
                            <div class="text-center mb-4">
                                <span class="badge bg-dark px-4 py-2 fs-5 font-monospace mb-2">
                                    <%= (v != null) ? v.getPlaca() : "BYF-398" %>
                                </span>
                                <h5 class="text-muted mb-0"><%= (v != null) ? v.getMarca() + " - " + v.getModelo() : "Hyundai Venue" %></h5>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between mb-3">
                                <span class="text-muted">Odómetro Actual:</span>
                                <strong><%= (v != null) ? v.getKilometrajeActual() : "31,000.00" %> Km</strong>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="text-muted">Estado Operativo:</span>
                                <span class="badge bg-success p-2">Disponible</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Bloque de Órdenes Pendientes -->
                <div class="col-lg-8">
                    <div class="card border-0 shadow-sm rounded-3 h-100">
                        <div class="card-header bg-dark text-white fw-bold py-3 d-flex justify-content-between align-items-center">
                            <span><i class="bi bi-cone-striped text-warning"></i> Alertas y Mantenimientos Críticos Pendientes</span>
                            <span class="badge bg-danger"><%= numAlertas %> Reportes</span>
                        </div>
                        <div class="card-body p-4 bg-white">
                            <% if (numAlertas == 0) { %>
                                <div class="text-center py-5">
                                    <i class="bi bi-check-circle text-success display-3 opacity-70"></i>
                                    <h5 class="mt-3 text-secondary fw-bold">¡Todo en orden, compañero!</h5>
                                    <p class="text-muted small">No hay registros de fallas activas reportadas para esta placa en el taller mecánico.</p>
                                </div>
                            <% } else { %>
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle mb-0">
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID Orden</th>
                                                <th>Clasificación</th>
                                                <th>Diagnóstico de Alerta</th>
                                                <th>Fecha Apertura</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (OrdenMantenimiento o : alertas) { %>
                                            <tr>
                                                <td><code>#<%= o.getIdOrden() %></code></td>
                                                <td><span class="badge bg-warning text-dark fw-bold"><%= o.getTipoMantenimiento() %></span></td>
                                                <td><p class="mb-0 text-wrap font-monospace small" style="max-width: 320px;"><%= o.getDescripcion() %></p></td>
                                                <td><small class="text-muted"><%= o.getFechaInicio() %></small></td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>