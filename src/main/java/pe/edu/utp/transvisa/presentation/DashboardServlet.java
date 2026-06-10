package pe.edu.utp.transvisa.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        

        HttpSession session = request.getSession(false); 
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            logger.warn("Intento de acceso no autorizado al Dashboard interceptado.");
            // Si no hay sesión válida, redirige de inmediato al formulario de Loginpor temas de seguridad
            response.sendRedirect("login");
            return;
        }

      
        String nombreUsuario = (String) session.getAttribute("usuarioLogueado");
        String rolUsuario = (String) session.getAttribute("rolUsuario");
        logger.info("Cargando métricas del Dashboard para el usuario: {} [Rol: {}]", nombreUsuario, rolUsuario);


        request.setAttribute("totalVehiculos", 3);
        request.setAttribute("vehiculosDisponibles", 2);
        request.setAttribute("vehiculosEnTaller", 1);
        request.setAttribute("porcentajeDisponibilidad", "66.6%");
        request.setAttribute("cumplimientoMantenimiento", "85.0%");

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redireccionar peticiones POST accidentales hacia el método GET de manera segura
        doGet(request, response);
    }
}