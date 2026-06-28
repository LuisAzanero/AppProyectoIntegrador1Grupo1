package pe.edu.utp.transvisa.presentation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MySQLMovimientoGaritaRepository;
import pe.edu.utp.transvisa.persistence.MySQLVehiculoRepository;
import pe.edu.utp.transvisa.persistence.MovimientoGaritaRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/garita/panel")
public class GaritaPanelServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GaritaPanelServlet.class);
    private final MovimientoGaritaRepository garitaRepository = new MySQLMovimientoGaritaRepository();
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"Operador de Garita".equals(session.getAttribute("rolUsuario"))) {
            response.sendRedirect("../login");
            return;
        }

        try {
            // Cargar datos para la tabla DataTables y los selectores
            request.setAttribute("listaMovimientos", garitaRepository.listarHistorial());
            request.setAttribute("listaVehiculosActivos", vehiculoRepository.listarTodos());

            // Despachar a la vista unificada
            request.getRequestDispatcher("../garita_control.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error al cargar el panel de Garita", e);
            response.sendRedirect("../login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || !"Operador de Garita".equals(session.getAttribute("rolUsuario"))) {
            response.sendRedirect("../login");
            return;
        }

        String idVehiculoStr = request.getParameter("idVehiculo");
        String idUsuarioStr = request.getParameter("idUsuario");
        String tipoOperacion = request.getParameter("tipoOperacion");
        String kmStr = request.getParameter("kilometrajeRegistro");

        try {
            // Validaciones con Google Guava
            Preconditions.checkArgument(!Strings.isNullOrEmpty(idVehiculoStr), "Debe seleccionar un vehículo.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(tipoOperacion), "El tipo de operación es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(kmStr), "El kilometraje de registro es obligatorio.");

            int idVehiculo = Integer.parseInt(idVehiculoStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);
            BigDecimal kmRegistro = new BigDecimal(kmStr);

            // Validar consistencia de odómetro
            List<Vehiculo> listaVehiculos = vehiculoRepository.listarTodos();
            Vehiculo vehiculoSeleccionado = null;
            for (Vehiculo v : listaVehiculos) {
                if (v.getIdVehiculo() == idVehiculo) {
                    vehiculoSeleccionado = v;
                    break;
                }
            }

            if (vehiculoSeleccionado != null && kmRegistro.compareTo(vehiculoSeleccionado.getKilometrajeActual()) < 0) {
                logger.warn("Kilometraje inválido: {} Km es menor al actual", kmRegistro);

                request.setAttribute("errorKilometraje", "El kilometraje ingresado no puede ser menor al kilometraje actual del vehículo (" + vehiculoSeleccionado.getKilometrajeActual() + " Km).");
                request.setAttribute("listaMovimientos", garitaRepository.listarHistorial());
                request.setAttribute("listaVehiculosActivos", listaVehiculos);
                
                request.getRequestDispatcher("../garita_control.jsp").forward(request, response);
                return;
            }

            MovimientoGarita movimiento = new MovimientoGarita();
            movimiento.setIdVehiculo(idVehiculo);
            movimiento.setIdUsuario(idUsuario);
            movimiento.setTipoOperacion(tipoOperacion);
            movimiento.setKilometrajeRegistro(kmRegistro);

            garitaRepository.registrarMovimiento(movimiento);
            logger.info("Movimiento de {} registrado con éxito", tipoOperacion);

            response.sendRedirect("panel"); // Recarga el panel limpiamente

        } catch (Exception e) {
            logger.error("Error al procesar el ticket de garita", e);
            request.setAttribute("error", e.getMessage());
            try {
                request.setAttribute("listaMovimientos", garitaRepository.listarHistorial());
                request.setAttribute("listaVehiculosActivos", vehiculoRepository.listarTodos());
            } catch (Exception ex) {
                logger.error("Error en fallback", ex);
            }
            request.getRequestDispatcher("../garita_control.jsp").forward(request, response);
        }
    }
}