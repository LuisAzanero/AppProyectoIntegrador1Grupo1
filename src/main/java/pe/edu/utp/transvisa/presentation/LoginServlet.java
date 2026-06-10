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
//Redireccionamiento al login

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

            // Consultamos en la base de datos.
            Usuario usuarioDB = usuarioRepository.buscarPorUsername(txtUsuario);

            // Validamos el passowrd en el banco
            if (usuarioDB != null && usuarioDB.getPasswordHash().equals(txtClave)) {

                logger.info("Acceso concedido desde la Base de Datos para el usuario: {}", txtUsuario);

                HttpSession session = request.getSession();
                session.setAttribute("idUsuarioLogueado", usuarioDB.getIdUsuario());
                session.setAttribute("usuarioLogueado", usuarioDB.getNombre());
                session.setAttribute("rolUsuario", usuarioDB.getRol());

                // Redirección segura al Dashboard si el login es exitoso,cn cotrario, mostramos unmensja de erroren la vista
                response.sendRedirect("dashboard");
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
