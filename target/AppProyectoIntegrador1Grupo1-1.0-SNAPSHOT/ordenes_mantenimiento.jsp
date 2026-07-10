<%-- 
    Document   : ordenes_mantenimiento
    Created on : 09 jul. 2026, 20:55:45
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
        <title>TRANSVISA - Órdenes de Mantenimiento</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="dashboard">TRANSVISA E.I.R.L.</a>
                <div class="navbar-nav me-auto">
                    <a class="nav-link" href="mecanico/tareas">Dashboard</a>
                    <a class="nav-link active" href="mantenimientos">Mantenimiento</a>
                </div>
                <div class="d-flex align-items-center mt-3 mt-lg-0">
                    <span class="navbar-text text-white me-3">
                        <i class="bi bi-person-workspace text-warning"></i>  Usuario: <strong class="text-warning"><%= session.getAttribute("usuarioLogueado") != null ? session.getAttribute("usuarioLogueado") : "Administrador" %></strong>
                    </span>
                    <a href="login" class="btn btn-sm btn-outline-danger px-3"><i class="bi bi-power"></i> Cerrar Sesión</a>
                </div>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="text-secondary fw-bold mb-0">🛠️ Gestión de Órdenes de Mantenimiento</h2>
                    <p class="text-muted mb-0">Cumplimiento del requerimiento para soporte preventivo y correctivo.</p>
                </div>
                <button class="btn btn-warning fw-bold shadow-sm" data-bs-toggle="modal" data-bs-target="#modalOrden" onclick="prepararFormulario()">
                    🔧 Crear Órden de Trabajo
                </button>
            </div>

            <div class="card border-0 shadow-sm rounded-3">
                <div class="card-body p-4">
                    <table id="tablaOrdenes" class="table table-hover align-middle mb-0 w-100">
                        <thead class="table-dark">
                            <tr>
                                <th>ID Órden</th>
                                <th>Placa Vehículo</th>
                                <th>Tipo Soporte</th>
                                <th>Descripción de Falla / Diagnóstico</th>
                                <th>Fecha Inicio</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<OrdenMantenimiento> lista = (List<OrdenMantenimiento>) request.getAttribute("listaOrdenes");
                                if (lista != null) {
                                    for (OrdenMantenimiento o : lista) {
                                        String tipoBadge = "PREVENTIVO".equals(o.getTipoMantenimiento()) ? "badge bg-info text-dark" : "badge bg-purple text-white";
                                        String estadoBadge = "ABIERTA".equals(o.getEstadoOrden()) ? "badge bg-danger p-2" : "badge bg-success p-2";
                            %>
                            <tr>
                                <td><strong>#<%= o.getIdOrden()%></strong></td>
                                <td><span class="badge bg-secondary p-2 font-monospace fs-6"><%= o.getPlacaVehiculo()%></span></td>
                                <td><span class="<%= tipoBadge %> p-2 fw-bold"><%= o.getTipoMantenimiento()%></span></td>
                                <td><p class="mb-0 text-truncate" style="max-width: 300px;"><%= o.getDescripcion()%></p></td>
                                <td><%= o.getFechaInicio()%></td>
                                <td><span class="<%= estadoBadge %>"><%= o.getEstadoOrden()%></span></td>
                                <td class="text-center">
                                    <% if ("ABIERTA".equals(o.getEstadoOrden())) { %>
                                        <a href="mantenimientos?action=cerrar&id=<%= o.getIdOrden()%>" class="btn btn-sm btn-success fw-semibold" onclick="return confirm('¿Confirmar cierre de la orden de trabajo?')">✅ Cerrar Órden</a>
                                    <% } else { %>
                                        <span class="text-muted small">🔒 Completado</span>
                                    <% } %>
                                </td>
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

        <div class="modal fade" id="modalOrden" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content border-0 shadow">
                    <form action="mantenimientos" method="POST">
                        <div class="modal-header bg-dark text-white">
                            <h5 class="modal-title fw-bold">Apertura de Órden Técnica</h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body p-4">
                            <input type="hidden" name="idUsuario" value="<%= session.getAttribute("idUsuarioLogueado") != null ? session.getAttribute("idUsuarioLogueado") : "1" %>">
                            
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Seleccionar Camión afectado</label>
                                <select name="idVehiculo" id="formVehiculo" class="form-select" required>
                                    <option value="">-- Seleccione Placa --</option>
                                    <%
                                        List<Vehiculo> vehiculos = (List<Vehiculo>) request.getAttribute("listaVehiculos");
                                        if (vehiculos != null) {
                                            for (Vehiculo v : vehiculos) {
                                    %>
                                    <option value="<%= v.getIdVehiculo()%>"><%= v.getPlaca() %> - <%= v.getMarca() %> [<%= v.getEstadoOperativo() %>]</option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Clasificación de Mantenimiento</label>
                                <select name="tipoMantenimiento" id="formTipo" class="form-select" required>
                                    <option value="PREVENTIVO">🟢 MANTENIMIENTO PREVENTIVO (Rutina/Kilometraje)</option>
                                    <option value="CORRECTIVO">🔴 MANTENIMIENTO CORRECTIVO (Falla Mecánica/Incidente)</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Descripción del Diagnóstico / Tareas a realizar</label>
                                <textarea name="descripcion" id="formDescripcion" class="form-control" rows="4" placeholder="Ejemplo: Cambio de pastillas de freno delanteras y filtros de combustible..." required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer bg-light">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-warning fw-bold text-dark">Abrir Órden de Trabajo</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
        
        <script>
            $(document).ready(function () {
                $('#tablaOrdenes').DataTable({
                    responsive: true,
                    order: [[0, "desc"]], // El registro más reciente primero
                    language: {
                        url: 'https://cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
                    }
                });
            });

            function prepararFormulario() {
                document.getElementById('formVehiculo').value = '';
                document.getElementById('formTipo').value = 'PREVENTIVO';
                document.getElementById('formDescripcion').value = '';
            }
        </script>
        
        <style>
            .bg-purple { background-color: #6f42c1 !important; }
        </style>
    </body>
</html>