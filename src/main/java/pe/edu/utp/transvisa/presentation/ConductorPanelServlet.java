package pe.edu.utp.transvisa.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.persistence.MySQLOrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.MySQLVehiculoRepository;
import pe.edu.utp.transvisa.persistence.OrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/conductor/panel")
public class ConductorPanelServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ConductorPanelServlet.class);
    private final OrdenMantenimientoRepository ordenRepository = new MySQLOrdenMantenimientoRepository();
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Conductor".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }

        try {
            req.setAttribute("miVehiculo", vehiculoRepository.listarTodos().stream().findFirst().orElse(null));
            
            req.setAttribute("alertasPendientes", ordenRepository.listarTodas().stream()
                    .filter(o -> "ABIERTA".equals(o.getEstadoOrden()))
                    .collect(Collectors.toList()));
            req.getRequestDispatcher("../conductor_panel.jsp").forward(req, res);
        } catch (Exception e) {
            logger.error("Error al cargar datos del piloto", e);
            req.getRequestDispatcher("../conductor_panel.jsp").forward(req, res);
        }
    }
}