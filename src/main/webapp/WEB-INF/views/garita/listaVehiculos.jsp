<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehículos - TRANSVISA</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .menu { margin-bottom: 20px; }
        .menu a { margin-right: 15px; text-decoration: none; }
    </style>
</head>
<body>
    <h1>Gestión de Vehículos - TRANSVISA</h1>
    <div class="menu">
        <a href="${pageContext.request.contextPath}/vehiculos">Vehículos</a>
        <a href="${pageContext.request.contextPath}/garita">Registrar Movimiento</a>
    </div>
    
    <h2>Lista de Vehículos</h2>
    <table>
        <tr>
            <th>ID</th><th>Placa</th><th>Marca</th><th>Modelo</th><th>Kilometraje</th><th>Estado</th>
        </tr>
        <c:forEach items="${vehiculos}" var="v">
            <tr>
                <td>${v.idVehiculo}</td>
                <td>${v.placa}</td>
                <td>${v.marca}</td>
                <td>${v.modelo}</td>
                <td>${v.kilometrajeActual} km</td>
                <td>${v.estadoOperativo}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>