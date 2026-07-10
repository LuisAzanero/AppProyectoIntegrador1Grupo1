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

@WebServlet("/mecanico/tareas")
public class MecanicoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MecanicoServlet.class);
    private final OrdenMantenimientoRepository ordenRepository = new MySQLOrdenMantenimientoRepository();
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Tecnico Mecanico".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }

        try {
            s.setAttribute("listaOrdenes", ordenRepository.listarTodas());
            s.setAttribute("listaVehiculos", vehiculoRepository.listarTodos());
            
            getServletContext().getRequestDispatcher("/mecanico_tareas.jsp").forward(req, res);
        } catch (Exception e) {
            logger.error("Error al cargar el panel del taller mecánico", e);
            res.sendRedirect("../login");
        }
    }
}