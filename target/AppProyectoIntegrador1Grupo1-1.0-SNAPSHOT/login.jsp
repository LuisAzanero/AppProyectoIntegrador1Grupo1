<%-- 
    Document   : newjsp
    Created on : 9 jun. 2026, 19:08:37
    Author     : luis.azanero
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TRANSVISA - Sistema de Gestión de Flota</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #1f4068 0%, #162447 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .login-card {
                background: rgba(255, 255, 255, 0.95);
                border: none;
                border-radius: 16px;
                box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.3);
            }
            .btn-login {
                background-color: #e43f5a;
                border: none;
                transition: background-color 0.3s ease;
            }
            .btn-login:hover {
                background-color: #b9324b;
            }
            .brand-title {
                color: #1f4068;
                letter-spacing: 1.5px;
            }
        </style>
    </head>
    <body class="d-flex align-items-center py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-5 col-lg-4">
                    <div class="card login-card p-4 p-sm-5">
                        <div class="text-center mb-4">
                            <div class="mb-3 text-center">
                                <img src="https://www.transvisa.pe/images/tgm-logo.png" 
                                     alt="Logo TRANSVISA" 
                                     class="img-fluid" 
                                     style="max-height: 80px; object-fit: contain;">
                            </div>
                            <h2 class="brand-title fw-bold mb-1">TRANSVISA E.I.R.L.</h2>
                            <p class="text-muted small">Gestión de Flota & Mantenimiento Vehicular</p>
                        </div>

                        <% if (request.getAttribute("errorAutenticacion") != null) {%>
                        <div class="alert alert-danger py-2 px-3 small border-0 text-center" role="alert">
                            <strong>⚠️ Error:</strong> <%= request.getAttribute("errorAutenticacion")%>
                        </div>
                        <% }%>

                        <form action="login" method="POST">
                            <div class="form-floating mb-3">
                                <input type="text" name="username" class="form-control" id="userInput" placeholder="Nombre de usuario" required autocomplete="off">
                                <label for="userInput">Usuario:</label>
                            </div>
                            <div class="form-floating mb-4">
                                <input type="password" name="password" class="form-control" id="passInput" placeholder="Contraseña de acceso" required>
                                <label for="passInput">Contraseña:</label>
                            </div>
                            <button type="submit" class="btn btn-login text-white w-100 py-2.5 fw-semibold rounded-3 shadow-sm">
                                Iniciar Sesion
                            </button>
                        </form>
                    </div>
                    <div class="text-center text-white-50 mt-4 small">
                        &copy; 2026 TRANSVISA E.I.R.L. &bull; Avance 3 Integrador 1
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>