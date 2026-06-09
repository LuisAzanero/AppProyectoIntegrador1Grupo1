<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Control de Garita - TRANSVISA</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 150px; }
        input, select { padding: 5px; width: 200px; }
        button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        .success { color: green; background-color: #dff0d8; padding: 10px; border-radius: 5px; }
        .error { color: red; background-color: #f2dede; padding: 10px; border-radius: 5px; }
        .menu { margin-bottom: 20px; }
        .menu a { margin-right: 15px; text-decoration: none; }
    </style>
</head>
<body>
    <h1>Control de Garita - TRANSVISA</h1>
    
    <div class="menu">
        <a href="${pageContext.request.contextPath}/vehiculos">Vehiculos</a>
        <a href="${pageContext.request.contextPath}/garita">Registrar Movimiento</a>
    </div>
    
    <c:if test="${not empty mensaje}">
        <div class="${tipoMensaje == 'success' ? 'success' : 'error'}">${mensaje}</div><br>
    </c:if>
    
    <form method="post">
        <div class="form-group">
            <label>Vehículo:</label>
            <select name="idVehiculo" required>
                <option value="">Seleccione...</option>
                <c:forEach items="${vehiculos}" var="v">
                    <option value="${v.idVehiculo}">${v.placa} - ${v.marca} ${v.modelo}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Tipo Operación:</label>
            <select name="tipoOperacion" required>
                <option value="ENTRADA">ENTRADA</option>
                <option value="SALIDA">SALIDA</option>
            </select>
        </div>
        <div class="form-group">
            <label>Kilometraje:</label>
            <input type="number" step="0.01" name="kilometraje" required>
        </div>
        <button type="submit">Registrar</button>
    </form>
</body>
</html>