package pe.edu.utp.transvisa.presentation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Usuario;
import pe.edu.utp.transvisa.persistence.MySQLUsuarioRepository;
import pe.edu.utp.transvisa.persistence.UsuarioRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final UsuarioRepository usuarioRepository = new MySQLUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String txtUsuario = request.getParameter("username");
        String txtClave = request.getParameter("password");

        try {
            // Validaciones de formato de entrada usando Google Guava
            Preconditions.checkArgument(!Strings.isNullOrEmpty(txtUsuario), "El nombre de usuario es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(txtClave), "La contraseña es obligatoria.");

            // Consultar la base de datos
            Usuario usuarioDB = usuarioRepository.buscarPorUsername(txtUsuario);

            // Validar credenciales
            if (usuarioDB != null && usuarioDB.getPasswordHash().equals(txtClave)) {

                logger.info("Acceso concedido para el usuario: {} con Rol: {}", txtUsuario, usuarioDB.getRol());

                // Inicializar sesión segura
                HttpSession session = request.getSession();
                session.setAttribute("idUsuarioLogueado", usuarioDB.getIdUsuario());
                session.setAttribute("usuarioLogueado", usuarioDB.getNombre());
                session.setAttribute("rolUsuario", usuarioDB.getRol());

                String rol = usuarioDB.getRol();

                // 🔄 REDIRECCIONAMIENTO CONTROLADO Y SINCRONIZADO POR ROLES
                switch (rol) {
                    case "Administrador":
                    case "Gerente":
                        response.sendRedirect("dashboard");
                        break;

                    case "Supervisor de flota":
                        response.sendRedirect("supervisor/panel");
                        break;

                    case "Tecnico Mecanico":
                        response.sendRedirect("mecanico/tareas");
                        break;

                    case "Conductor":
                        // 🚚 Corregido: Coincide con ConductorPanelServlet
                        response.sendRedirect("conductor/panel");
                        break;

                    case "Operador de Garita":
                        // 🚪 Corregido: Coincide con GaritaPanelServlet
                        response.sendRedirect("garita/panel");
                        break;

                    default:
                        logger.warn("Usuario con rol no mapeado intentó ingresar: {}", rol);
                        request.setAttribute("errorAutenticacion", "Tu rol no tiene una vista asignada en el sistema.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        break;
                }

            } else {
                logger.warn("Fallo de inicio de sesión en BD para el usuario: {}", txtUsuario);
                request.setAttribute("errorAutenticacion", "Usuario o contraseña incorrectos en la base de datos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            logger.warn("Validación de campos fallida en Login: {}", e.getMessage());
            request.setAttribute("errorAutenticacion", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error del sistema en el proceso de autenticación", e);
            request.setAttribute("errorAutenticacion", "Error interno del servidor al conectar con la base de datos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
