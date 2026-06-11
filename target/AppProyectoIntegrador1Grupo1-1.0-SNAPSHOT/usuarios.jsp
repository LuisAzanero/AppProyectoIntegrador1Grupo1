<%-- 
    Document   : usuarios
    Created on : 9 jun. 2026, 19:37:41
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.utp.transvisa.domain.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TRANSVISA - Mantenimiento de Usuarios</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="dashboard">TRANSVISA E.I.R.L.</a>
                <div class="navbar-nav me-auto">
                    <a class="nav-link" href="dashboard">Dashboard</a>
                    <a class="nav-link active" href="usuarios">Usuarios</a>
                </div>
                <span class="navbar-text text-white">
                    Operador: <strong><%= session.getAttribute("usuarioLogueado")%></strong>
                </span>
            </div>
        </nav>

        <div class="container-fluid px-4 py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-secondary fw-bold mb-0">Mantenimiento de Usuarios</h2>
                <div class="d-flex gap-2">
                    <div class="dropdown">
                        <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownUsuarios" data-bs-toggle="dropdown" aria-expanded="false">
                            📥 Exportar Data
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="dropdownUsuarios">
                            <li>
                                <a class="dropdown-item fw-medium text-success" href="usuarios/exportar?tipo=excel">
                                    🟢 Descargar Excel (.xlsx)
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item fw-medium text-info" href="usuarios/exportar?tipo=csv">
                                    🔵 Descargar CSV (.csv)
                                </a>
                            </li>
                        </ul>
                    </div>

                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalUsuario" onclick="limpiarFormulario()">
                        ➕ Nuevo Usuario
                    </button>
                </div>
            </div>

            <div class="card border-0 shadow-sm">
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>DNI</th>
                                <th>Nombre Real</th>
                                <th>Username</th>
                                <th>Rol</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
                                if (lista != null) {
                                    for (Usuario u : lista) {
                            %>
                            <tr>
                                <td><%= u.getIdUsuario()%></td>
                                <td><%= u.getDni()%></td>
                                <td><%= u.getNombre()%></td>
                                <td><strong><%= u.getUsername()%></strong></td>
                                <td><span class="badge bg-secondary"><%= u.getRol()%></span></td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-warning me-1" onclick="cargarDatosEditar('<%= u.getIdUsuario()%>', '<%= u.getDni()%>', '<%= u.getNombre()%>', '<%= u.getUsername()%>', '<%= u.getPasswordHash()%>', '<%= u.getRol()%>')">✏️ Editar</button>
                                    <a href="usuarios?action=eliminar&id=<%= u.getIdUsuario()%>" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro que desea dar de baja a este usuario?')">🗑️ Dar de Baja</a>
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

        <div class="modal fade" id="modalUsuario" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="usuarios" method="POST">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">Registrar Operador</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idUsuario" id="formId">
                            <div class="mb-3">
                                <label class="form-label">DNI</label>
                                <input type="text" name="dni" id="formDni" class="form-control" required maxlength="8" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nombre Completo</label>
                                <input type="text" name="nombre" id="formNombre" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Username</label>
                                <input type="text" name="username" id="formUsername" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Contraseña</label>
                                <input type="password" name="password" id="formClave" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Rol Operativo</label>
                                <select name="rol" id="formRol" class="form-select" required>
                                    <option value="">Seleccione Rol:</option>
                                    <option value="Supervisor de flota">Supervisor de flota</option>
                                    <option value="Tecnico Mecanico">Tecnico Mecanico</option>
                                    <option value="Gerente">Gerente</option>
                                    <option value="Conductor">Conductor</option>
                                    <option value="Administrador">Administrador</option>
                                    <option value="Operador de Garita">Operador de Garita</option>
                                        
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                        function limpiarFormulario() {
                                            document.getElementById('formId').value = '';
                                            document.getElementById('formDni').value = '';
                                            document.getElementById('formNombre').value = '';
                                            document.getElementById('formUsername').value = '';
                                            document.getElementById('formClave').value = '';
                                            document.getElementById('formRol').value = 'OPERADOR';
                                            document.getElementById('modalTitle').innerText = 'Registrar Operador';
                                        }

                                        function cargarDatosEditar(id, dni, nombre, user, clave, rol) {
                                            document.getElementById('formId').value = id;
                                            document.getElementById('formDni').value = dni;
                                            document.getElementById('formNombre').value = nombre;
                                            document.getElementById('formUsername').value = user;
                                            document.getElementById('formClave').value = clave;
                                            document.getElementById('formRol').value = rol;
                                            document.getElementById('modalTitle').innerText = 'Modificar Datos de Usuario';

                                            var modal = new bootstrap.Modal(document.getElementById('modalUsuario'));
                                            modal.show();
                                        }
        </script>
    </body>
</html>