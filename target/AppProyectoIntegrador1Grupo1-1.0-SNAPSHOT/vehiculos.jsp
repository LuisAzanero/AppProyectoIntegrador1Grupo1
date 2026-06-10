<%-- 
    Document   : vehiculos
    Created on : 9 jun. 2026, 19:50:24
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.transvisa.domain.Vehiculo" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TRANSVISA - Control de Flota</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="dashboard">TRANSVISA E.I.R.L.</a>
                <div class="navbar-nav me-auto">
                    <a class="nav-link" href="dashboard">Dashboard</a>
                    <a class="nav-link" href="usuarios">Usuarios</a>
                    <a class="nav-link active" href="vehiculos">Vehículos</a>
                </div>
                <span class="navbar-text text-white">
                    Operador: <strong><%= session.getAttribute("usuarioLogueado")%></strong>
                </span>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-secondary fw-bold mb-0">Gestión de Unidades Vehiculares</h2>
                <div class="d-flex gap-2">
                    <div class="dropdown">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            📥 Exportar Data
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="dropdownMenuButton">
                            <li>
                                <a class="dropdown-item fw-medium text-success" href="vehiculos/exportar?tipo=excel">
                                    🟢 Descargar Excel (.xlsx)
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item fw-medium text-info" href="vehiculos/exportar?tipo=csv">
                                    🔵 Descargar CSV (.csv)
                                </a>
                            </li>
                        </ul>
                    </div>

                    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalVehiculo" onclick="limpiarFormulario()">
                        🚚 Registrar Vehículo
                    </button>
                </div>
            </div>

            <div class="card border-0 shadow-sm">
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Placa</th>
                                <th>Marca</th>
                                <th>Modelo</th>
                                <th>Kilometraje Actual</th>
                                <th>Estado Operativo</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Vehiculo> lista = (List<Vehiculo>) request.getAttribute("listaVehiculos");
                                if (lista != null) {
                                    for (Vehiculo v : lista) {
                                        String badgeColor = "bg-success";
                                        if ("En Taller".equals(v.getEstadoOperativo())) {
                                            badgeColor = "bg-warning text-dark";
                                        }
                                        if ("En Ruta".equals(v.getEstadoOperativo())) {
                                            badgeColor = "bg-info text-dark";
                                        }
                                        if ("Inactivo".equals(v.getEstadoOperativo()))
                                            badgeColor = "bg-danger";
                            %>
                            <tr>
                                <td><%= v.getIdVehiculo()%></td>
                                <td><span class="badge bg-secondary p-2 font-monospace fs-6"><%= v.getPlaca()%></span></td>
                                <td><%= v.getMarca()%></td>
                                <td><%= v.getModelo()%></td>
                                <td><strong><%= v.getKilometrajeActual()%></strong> Km</td>
                                <td><span class="badge <%= badgeColor%>"><%= v.getEstadoOperativo()%></span></td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-warning me-1" onclick="cargarDatosEditar('<%= v.getIdVehiculo()%>', '<%= v.getPlaca()%>', '<%= v.getMarca()%>', '<%= v.getModelo()%>', '<%= v.getKilometrajeActual()%>', '<%= v.getEstadoOperativo()%>')">✏️ Editar</button>
                                    <a href="vehiculos?action=eliminar&id=<%= v.getIdVehiculo()%>" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro de deshabilitar este vehículo?')">🗑️ Desactivar</a>
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

        <div class="modal fade" id="modalVehiculo" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="vehiculos" method="POST">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">Registrar Vehículo</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idVehiculo" id="formId">
                            <div class="mb-3">
                                <label class="form-label">Placa</label>
                                <input type="text" name="placa" id="formPlaca" class="form-control" placeholder="Ejemplo: F3V-820" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Marca</label>
                                <input type="text" name="marca" id="formMarca" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Modelo</label>
                                <input type="text" name="modelo" id="formModelo" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Kilometraje Actual (Km)</label>
                                <input type="number" step="0.01" name="kilometraje" id="formKm" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Estado Operativo</label>
                                <select name="estadoOperativo" id="formEstadoOps" class="form-select">
                                    <option value="Disponible">Disponible</option>
                                    <option value="En Taller">En Taller</option>
                                    <option value="En Ruta">En Ruta</option>
                                    <option value="Inactivo">Inactivo</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-success">Guardar Datos</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                        function limpiarFormulario() {
                                            document.getElementById('formId').value = '';
                                            document.getElementById('formPlaca').value = '';
                                            document.getElementById('formMarca').value = '';
                                            document.getElementById('formModelo').value = '';
                                            document.getElementById('formKm').value = '0.00';
                                            document.getElementById('formEstadoOps').value = 'Disponible';
                                            document.getElementById('modalTitle').innerText = 'Registrar Vehículo';
                                        }

                                        function cargarDatosEditar(id, placa, marca, modelo, km, estado) {
                                            document.getElementById('formId').value = id;
                                            document.getElementById('formPlaca').value = placa;
                                            document.getElementById('formMarca').value = marca;
                                            document.getElementById('formModelo').value = modelo;
                                            document.getElementById('formKm').value = km;
                                            document.getElementById('formEstadoOps').value = estado;
                                            document.getElementById('modalTitle').innerText = 'Modificar Vehículo';

                                            var modal = new bootstrap.Modal(document.getElementById('modalVehiculo'));
                                            modal.show();
                                        }
        </script>
    </body>
</html>