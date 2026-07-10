package pe.edu.utp.transvisa.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.OrdenMantenimiento;
import pe.edu.utp.transvisa.persistence.MySQLOrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.MySQLVehiculoRepository;
import pe.edu.utp.transvisa.persistence.OrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/mantenimientos")
public class MantenimientoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MantenimientoServlet.class);
    private final OrdenMantenimientoRepository ordenRepository = new MySQLOrdenMantenimientoRepository();
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

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
        
            if ("cerrar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                ordenRepository.cerrarOrden(id);
                logger.info("Orden de trabajo #{} cerrada con éxito.", id);
                response.sendRedirect("mantenimientos");
                return;
            }

            // Importar DataTables
            request.setAttribute("listaOrdenes", ordenRepository.listarTodas());
            request.setAttribute("listaVehiculos", vehiculoRepository.listarTodos());

            request.getRequestDispatcher("ordenes_mantenimiento.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error operativo en el módulo de mantenimiento", e);
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String tipoMantenimiento = request.getParameter("tipoMantenimiento");
            String descripcion = request.getParameter("descripcion");

            OrdenMantenimiento nuevaOrden = new OrdenMantenimiento();
            nuevaOrden.setIdVehiculo(idVehiculo);
            nuevaOrden.setIdUsuario(idUsuario);
            nuevaOrden.setTipoMantenimiento(tipoMantenimiento);
            nuevaOrden.setDescripcion(descripcion);

            ordenRepository.registrar(nuevaOrden);
            logger.info("Nueva orden registrada para el vehículo ID: {}", idVehiculo);

            response.sendRedirect("mantenimientos");

        } catch (Exception e) {
            logger.error("Fallo al registrar la orden de trabajo", e);
            response.sendRedirect("mantenimientos");
        }
    }
}
