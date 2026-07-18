package pe.edu.utp.transvisa.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.persistence.ConductorRepository;
import pe.edu.utp.transvisa.persistence.MySQLConductorRepository;
import pe.edu.utp.transvisa.persistence.OrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.MySQLOrdenMantenimientoRepository;
import pe.edu.utp.transvisa.persistence.MovimientoGaritaRepository;
import pe.edu.utp.transvisa.persistence.MySQLMovimientoGaritaRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/supervisor/panel")
public class SupervisorServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(SupervisorServlet.class);
    
    private final ConductorRepository conductorRepository = new MySQLConductorRepository();
    private final OrdenMantenimientoRepository ordenRepository = new MySQLOrdenMantenimientoRepository();
    private final MovimientoGaritaRepository garitaRepository = new MySQLMovimientoGaritaRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        
        if (s == null || !"Supervisor de flota".equals(s.getAttribute("rolUsuario"))) {
            logger.warn("Acceso denegado: Sesion invalida o rol incorrecto.");
            res.sendRedirect("../login");
            return;
        }

        try {
            int totalConductores = 0;
            if (conductorRepository.listarTodos() != null) {
                totalConductores = conductorRepository.listarTodos().size();
            }
            
            long alertasCriticas = 0;
            int totalOrdenes = 0;
            long cerradas = 0;
            
            if (ordenRepository.listarTodas() != null) {
                totalOrdenes = ordenRepository.listarTodas().size();
                alertasCriticas = ordenRepository.listarTodas().stream()
                        .filter(orden -> "ABIERTA".equalsIgnoreCase(orden.getEstadoOrden()))
                        .count();
                cerradas = ordenRepository.listarTodas().stream()
                        .filter(orden -> "CERRADA".equalsIgnoreCase(orden.getEstadoOrden()))
                        .count();
            }

            long rutasDespacho = 0;
            if (garitaRepository.listarHistorial() != null) {
                rutasDespacho = garitaRepository.listarHistorial().stream()
                        .filter(mov -> "SALIDA".equalsIgnoreCase(mov.getTipoOperacion()))
                        .map(MovimientoGarita::getIdVehiculo)
                        .distinct()
                        .count();
            }

            double eficiencia = 0.0;
            if (totalOrdenes > 0) {
                eficiencia = ((double) cerradas / totalOrdenes) * 100;
            }

            req.setAttribute("choferesActivos", totalConductores);
            req.setAttribute("alertasCriticas", alertasCriticas);
            req.setAttribute("rutasDespacho", rutasDespacho);
            req.setAttribute("eficiencia", String.format("%.1f", eficiencia) + "%");

            req.getRequestDispatcher("/supervisor_panel.jsp").forward(req, res);

        } catch (Exception e) {
            logger.error("Error procesando metricas del supervisor", e);
            res.sendRedirect("../login");
        }
    }
}