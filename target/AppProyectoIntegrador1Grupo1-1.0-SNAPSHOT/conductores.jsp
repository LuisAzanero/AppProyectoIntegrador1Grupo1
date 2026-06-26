<%-- 
    Document   : conductores
    Created on : 25 jun. 2026
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.transvisa.domain.Conductor" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TRANSVISA - Registro de Conductores</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="dashboard">TRANSVISA E.I.R.L.</a>
                <div class="navbar-nav me-auto">
                    <a class="nav-link" href="dashboard">Dashboard</a>
                    <a class="nav-link" href="usuarios">Usuarios</a>
                    <a class="nav-link" href="vehiculos">Vehículos</a>
                    <a class="nav-link active" href="conductores">Conductores</a>
                </div>
                <span class="navbar-text text-white">
                    Operador: <strong><%= session.getAttribute("usuarioLogueado")%></strong>
                </span>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-secondary fw-bold mb-0">Control de Personal de Conducción</h2>
                <div class="d-flex gap-2">
                    <div class="dropdown">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            📥 Exportar Data
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="dropdownMenuButton">
                            <li>
                                <a class="dropdown-item fw-medium text-success" href="conductores/exportar?tipo=excel">
                                    🟢 Descargar Excel (.xlsx)
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item fw-medium text-info" href="conductores/exportar?tipo=csv">
                                    🔵 Descargar CSV (.csv)
                                </a>
                            </li>
                        </ul>
                    </div>

                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalConductor" onclick="limpiarFormulario()">
                        👤 Nuevo Conductor
                    </button>
                </div>
            </div>

            <div class="card border-0 shadow-sm">
                <div class="card-body p-4">
                    <table id="tablaConductores" class="table table-hover align-middle mb-0 w-100">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>DNI</th>
                                <th>Nombres Completos</th>
                                <th>Dirección Domiciliaria</th>
                                <th>Nro. Licencia (Brevete)</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Conductor> lista = (List<Conductor>) request.getAttribute("listaConductores");
                                if (lista != null) {
                                    for (Conductor c : lista) {
                            %>
                            <tr>
                                <td><%= c.getIdConductor()%></td>
                                <td><strong><%= c.getDni()%></strong></td>
                                <td><%= c.getNombres()%></td>
                                <td><%= c.getDireccion() != null ? c.getDireccion() : "-"%></td>
                                <td><span class="badge bg-info text-dark p-2 font-monospace fs-6"><%= c.getNroBreve()%></span></td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-warning me-1" onclick="cargarDatosEditar('<%= c.getIdConductor()%>', '<%= c.getDni()%>', '<%= c.getNombres()%>', '<%= c.getDireccion()%>', '<%= c.getNroBreve()%>')">✏️ Editar</button>
                                    <a href="conductores?action=eliminar&id=<%= c.getIdConductor()%>" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro de remover este conductor del sistema?')">🗑️ Eliminar</a>
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

        <div class="modal fade" id="modalConductor" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="conductores" method="POST">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">Registrar Conductor</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idConductor" id="formId">
                            <div class="mb-3">
                                <label class="form-label">DNI del Conductor</label>
                                <input type="text" name="dni" id="formDni" class="form-control" placeholder="8 dígitos numéricos" required maxlength="8">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellidos y Nombres Completos</label>
                                <input type="text" name="nombres" id="formNombres" class="form-control" placeholder="Ejemplo: Azanero Luis" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Dirección Actual</label>
                                <input type="text" name="direccion" id="formDireccion" class="form-control" placeholder="Av. Ejemplo 123 - Lima">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nro. de Brevete (Licencia)</label>
                                <input type="text" name="nroBreve" id="formBreve" class="form-control" placeholder="Ejemplo: Q12345678" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Guardar Chofer</button>
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
                                            $('#tablaConductores').DataTable({
                                                responsive: true,
                                                language: {
                                                    url: 'https://cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
                                                }
                                            });
                                        });

                                        function limpiarFormulario() {
                                            document.getElementById('formId').value = '';
                                            document.getElementById('formDni').value = '';
                                            document.getElementById('formNombres').value = '';
                                            document.getElementById('formDireccion').value = '';
                                            document.getElementById('formBreve').value = '';
                                            document.getElementById('modalTitle').innerText = 'Registrar Conductor';
                                        }

                                        function cargarDatosEditar(id, dni, nombres, direccion, breve) {
                                            document.getElementById('formId').value = id;
                                            document.getElementById('formDni').value = dni;
                                            document.getElementById('formNombres').value = nombres;
                                            document.getElementById('formDireccion').value = (direccion === 'null') ? '' : direccion;
                                            document.getElementById('formBreve').value = breve;
                                            document.getElementById('modalTitle').innerText = 'Modificar Conductor';

                                            var modal = new bootstrap.Modal(document.getElementById('modalConductor'));
                                            modal.show();
                                        }

            <% if (request.getAttribute("errorDniDuplicado") != null) {%>
                                        Swal.fire({
                                            icon: 'error',
                                            title: '¡DNI Existente!',
                                            text: '<%= request.getAttribute("errorDniDuplicado")%>',
                                            confirmButtonColor: '#0d6efd'
                                        });
            <% }%>
        </script>
    </body>
</html>