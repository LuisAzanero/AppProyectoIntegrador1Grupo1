<%-- 
    Document   : conductor_ruta
    Created on : 10 jun. 2026, 10:15:16
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TRANSVISA - Rol: Conductor</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-success shadow-sm py-3">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold" href="#">TRANSVISA E.I.R.L. - Portal del Conductor</a>
                <span class="navbar-text text-white">Chofer: <strong><%= session.getAttribute("usuarioLogueado")%></strong></span>
                <a href="../login" class="btn btn-sm btn-outline-light">Cerrar Sesión</a>
            </div>
        </nav>
        <div class="container py-5">
            <div class="card p-5 shadow-sm border-0">
                <h2 class="text-success fw-bold">🚛 Mi Hoja de Ruta Semanal</h2>
                <p class="text-muted">Funcionalidades en desarrollo... lamentamos los inconvenientoes. :( </p>
            </div>
        </div>
    </body>
</html>