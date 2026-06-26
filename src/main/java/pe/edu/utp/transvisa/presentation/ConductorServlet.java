package pe.edu.utp.transvisa.presentation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Conductor;
import pe.edu.utp.transvisa.persistence.ConductorRepository;
import pe.edu.utp.transvisa.persistence.MySQLConductorRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/conductores")
public class ConductorServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ConductorServlet.class);
    private final ConductorRepository conductorRepository = new MySQLConductorRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🛡️ Filtro de Autenticación de Sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                conductorRepository.eliminar(id);
                logger.info("Conductor eliminado con éxito. ID: {}", id);
                response.sendRedirect("conductores");
                return;
            }

            // Flujo estándar: Cargar grilla de choferes fresca desde MySQL
            List<Conductor> lista = conductorRepository.listarTodos();
            request.setAttribute("listaConductores", lista);
            request.getRequestDispatcher("conductores.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error crítico en inicialización del módulo de conductores", e);
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("idConductor");
        String dni = request.getParameter("dni");
        String nombres = request.getParameter("nombres");
        String direccion = request.getParameter("direccion");
        String nroBreve = request.getParameter("nroBreve");

        try {
            // 🛠️ Validaciones defensivas utilizando Google Guava (Rúbrica - Robustez)
            Preconditions.checkArgument(!Strings.isNullOrEmpty(dni), "El DNI es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(nombres), "El nombre completo es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(nroBreve), "El número de brevete es obligatorio.");

            Conductor c = new Conductor();
            c.setDni(dni.trim());
            c.setNombres(nombres.trim());
            c.setDireccion(direccion != null ? direccion.trim() : "");
            c.setNroBreve(nroBreve.toUpperCase().trim());

            if (Strings.isNullOrEmpty(idStr)) {
                // 🛡️ Control de Duplicados: Validación de DNI único (Regla de negocio)
                if (conductorRepository.existeDni(c.getDni())) {
                    logger.warn("Registro rechazado: El DNI de conductor {} ya existe en la BD.", c.getDni());
                    
                    // Atributo exacto que interceptará el SweetAlert2 en conductores.jsp
                    request.setAttribute("errorDniDuplicado", "El DNI (" + c.getDni() + ") ya le pertenece a un conductor registrado.");
                    
                    request.setAttribute("listaConductores", conductorRepository.listarTodos());
                    request.getRequestDispatcher("conductores.jsp").forward(request, response);
                    return;
                }
                
                conductorRepository.registrar(c);
                logger.info("Nuevo conductor registrado exitosamente: {}", nombres);
            } else {
                // Flujo de edición
                c.setIdConductor(Integer.parseInt(idStr));
                conductorRepository.actualizar(c);
                logger.info("Datos actualizados para el conductor ID: {}", idStr);
            }

            response.sendRedirect("conductores");

        } catch (Exception e) {
            logger.error("Fallo general en procesamiento del formulario de conductores", e);
            request.setAttribute("error", e.getMessage());
            try {
                request.setAttribute("listaConductores", conductorRepository.listarTodos());
            } catch (Exception ex) {
                logger.error("Error al intentar recuperar la lista de contingencia", ex);
            }
            request.getRequestDispatcher("conductores.jsp").forward(request, response);
        }
    }
}