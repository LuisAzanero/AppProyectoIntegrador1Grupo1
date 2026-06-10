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
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServlet.class);
    private final UsuarioRepository usuarioRepository = new MySQLUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                usuarioRepository.eliminar(id);
                logger.info("Eliminación lógica de usuario ID: {}", id);
                response.sendRedirect("usuarios");
                return;
            }

            List<Usuario> listaUsuarios = usuarioRepository.listarTodos();
            request.setAttribute("listaUsuarios", listaUsuarios);
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error en operación GET de usuarios", e);
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("idUsuario");
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String clave = request.getParameter("password");
        String rol = request.getParameter("rol");

        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(dni), "El DNI es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(nombre), "El nombre es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "El nombre de usuario es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(clave), "La contraseña es obligatoria.");

            Usuario u = new Usuario();
            u.setDni(dni);
            u.setNombre(nombre);
            u.setUsername(username);
            u.setPasswordHash(clave);
            u.setRol(rol);

            if (Strings.isNullOrEmpty(idStr)) {
                usuarioRepository.registrar(u);
                logger.info("Usuario registrado: {}", username);
            } else {
                u.setIdUsuario(Integer.parseInt(idStr));
                usuarioRepository.actualizar(u);
                logger.info("Usuario actualizado ID: {}", idStr);
            }

            response.sendRedirect("usuarios");

        } catch (Exception e) {
            logger.error("Error en formulario de usuarios", e);
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}