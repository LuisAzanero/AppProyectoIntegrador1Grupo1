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
        <title>TRANSVISA - Rol: Supervisor de Flota</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="#">TRANSVISA E.I.R.L. - Portal Área de Operaciones</a>
                <span class="navbar-text text-white">Supervisor: <strong><%= session.getAttribute("usuarioLogueado")%></strong></span>
                <a href="../login" class="btn btn-sm btn-outline-light">Cerrar Sesión</a>
            </div>
        </nav>
        <div class="container py-5">
            <div class="card p-5 shadow-sm border-0">
                <h2 class="text-primary fw-bold">📋 Panel de Supervisión de Flota</h2>
                <p class="text-muted">Módulo asignado para el monitoreo en tiempo real de unidades, asignación de rutas y control de incidencias.</p>
                <div class="alert alert-info mt-3">Funcionalidades en desarrollo... lamentamos los inconvenientoes. :( </div>
            </div>
        </div>
    </body>
</html>