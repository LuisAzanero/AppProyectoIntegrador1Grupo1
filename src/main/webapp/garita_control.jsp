<%-- 
    Document   : garita_control
    Created on : 10 jun. 2026, 10:15:48
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.transvisa.domain.Vehiculo" %>
<%@ page import="pe.edu.utp.transvisa.domain.MovimientoGarita" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TRANSVISA - Control de Accesos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        
        <nav class="navbar navbar-dark bg-dark shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="#">TRANSVISA E.I.R.L. - Portal Control de Accesos</a>
                <div class="d-flex align-items-center gap-3">
                    <span class="navbar-text text-white mb-0">Garita: <strong><%= session.getAttribute("usuarioLogueado")%></strong></span>
                    <a href="../login" class="btn btn-sm btn-outline-danger">Cerrar Sesión</a>
                </div>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="text-secondary fw-bold mb-0">🚪 Monitoreo de Entradas y Salidas (Portería)</h2>
                    <p class="text-muted mb-0">Registro en tiempo real del kilometraje y estado operativo de las unidades.</p>
                </div>
                <button class="btn btn-primary btn-lg shadow-sm" data-bs-toggle="modal" data-bs-target="#modalMovimiento" onclick="prepararFormulario()">
                    ➕ Registrar Ticket de Acceso
                </button>
            </div>

            <div class="card border-0 shadow-sm rounded-3">
                <div class="card-body p-4">
                    <table id="tablaMovimientos" class="table table-hover align-middle mb-0 w-100">
                        <thead class="table-dark">
                            <tr>
                                <th>ID Ticket</th>
                                <th>Placa Vehículo</th>
                                <th>Tipo Operación</th>
                                <th>Fecha / Hora Registro</th>
                                <th>Odómetro Registrado</th>
                                <th>Registrado Por</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<MovimientoGarita> lista = (List<MovimientoGarita>) request.getAttribute("listaMovimientos");
                                if (lista != null) {
                                    for (MovimientoGarita m : lista) {
                                        String opBadge = "ENTRADA".equals(m.getTipoOperacion()) ? "badge bg-success p-2 fs-6" : "badge bg-danger p-2 fs-6";
                            %>
                            <tr>
                                <td>#<%= m.getIdMovement()%></td>
                                <td><span class="badge bg-secondary p-2 font-monospace fs-6"><%= m.getPlacaVehiculo()%></span></td>
                                <td><span class="<%= opBadge %> fw-bold"><%= m.getTipoOperacion()%></span></td>
                                <td><%= m.getFechaHora()%></td>
                                <td><strong><%= m.getKilometrajeRegistro()%></strong> Km</td>
                                <td><small class="text-muted"><%= m.getNombreUsuario()%></small></td>
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

        <div class="modal fade" id="modalMovimiento" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content border-0 shadow">
                    <form action="../garita" method="POST"> <div class="modal-header bg-primary text-white">
                            <h5 class="modal-title fw-bold">Generar Ticket de Control Perimetral</h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body p-4">
                            <input type="hidden" name="idUsuario" value="<%= session.getAttribute("idUsuarioLogueado") != null ? session.getAttribute("idUsuarioLogueado") : "1" %>">
                            
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Seleccionar Unidad (Placa)</label>
                                <select name="idVehiculo" id="formVehiculo" class="form-select form-select-lg" required>
                                    <option value="">-- Seleccione Camión --</option>
                                    <%
                                        List<Vehiculo> vehiculos = (List<Vehiculo>) request.getAttribute("listaVehiculosActivos");
                                        if (vehiculos != null) {
                                            for (Vehiculo v : vehiculos) {
                                    %>
                                    <option value="<%= v.getIdVehiculo()%>"><%= v.getPlaca() %> - <%= v.getMarca() %> (<%= v.getKilometrajeActual() %> Km)</option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Tipo de Operación</label>
                                <div class="d-flex gap-4 border p-3 rounded bg-white justify-content-center">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tipoOperacion" id="opSalida" value="SALIDA" checked>
                                        <label class="form-check-label text-danger fw-bold" for="opSalida">📦 SALIDA</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tipoOperacion" id="opEntrada" value="ENTRADA">
                                        <label class="form-check-label text-success fw-bold" for="opEntrada">🏭 ENTRADA</label>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Kilometraje Marcado en Tablero (Km)</label>
                                <input type="number" step="0.01" name="kilometrajeRegistro" id="formKm" class="form-control form-control-lg" placeholder="Ej: 12500.80" required>
                                <div class="form-text text-muted">⚠️ El odómetro ingresado actualizará los maestros de la flota.</div>
                            </div>
                        </div>
                        <div class="modal-footer bg-light">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Confirmar Movimiento</button>
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
                $('#tablaMovimientos').DataTable({
                    responsive: true,
                    order: [[0, "desc"]], // Muestra el ticket más reciente arriba
                    language: {
                        url: 'https://cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
                    }
                });
            });

            function prepararFormulario() {
                document.getElementById('formVehiculo').value = '';
                document.getElementById('formKm').value = '';
                document.getElementById('opSalida').checked = true;
            }

            <% if (request.getAttribute("errorKilometraje") != null) {%>
                Swal.fire({
                    icon: 'warning',
                    title: 'Inconsistencia de Odómetro',
                    text: '<%= request.getAttribute("errorKilometraje")%>',
                    confirmButtonColor: '#0d6efd'
                });
            <% }%>
        </script>
    </body>
</html>