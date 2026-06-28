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

@WebServlet("/garita")
public class GaritaServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GaritaServlet.class);
    private final MovimientoGaritaRepository garitaRepository = new MySQLMovimientoGaritaRepository();
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🛡️ Control de seguridad de sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login");
            return;
        }

        try {
      
            List<MovimientoGarita> historial = garitaRepository.listarHistorial();
            request.setAttribute("listaMovimientos", historial);

           
            List<Vehiculo> vehiculosActivos = vehiculoRepository.listarTodos();
            request.setAttribute("listaVehiculosActivos", vehiculosActivos);

            // 3. Renderizar la vista
            request.getRequestDispatcher("movimientos_garita.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error al cargar la pantalla de Garita", e);
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idVehiculoStr = request.getParameter("idVehiculo");
        String idUsuarioStr = request.getParameter("idUsuario");
        String tipoOperacion = request.getParameter("tipoOperacion");
        String kmStr = request.getParameter("kilometrajeRegistro");

        try {
        
            Preconditions.checkArgument(!Strings.isNullOrEmpty(idVehiculoStr), "Debe seleccionar un vehículo.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(tipoOperacion), "El tipo de operación es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(kmStr), "El kilometraje de registro es obligatorio.");

            int idVehiculo = Integer.parseInt(idVehiculoStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);
            BigDecimal kmRegistro = new BigDecimal(kmStr);

          
            List<Vehiculo> listaVehiculos = vehiculoRepository.listarTodos();
            Vehiculo vehiculoSeleccionado = null;
            for (Vehiculo v : listaVehiculos) {
                if (v.getIdVehiculo() == idVehiculo) {
                    vehiculoSeleccionado = v;
                    break;
                }
            }

            if (vehiculoSeleccionado != null && kmRegistro.compareTo(vehiculoSeleccionado.getKilometrajeActual()) < 0) {
                logger.warn("Intento de registro inválido: Kilometraje ingresado ({}) es menor al actual ({})", kmRegistro, vehiculoSeleccionado.getKilometrajeActual());

                request.setAttribute("errorKilometraje", "El kilometraje ingresado no puede ser menor al kilometraje actual del vehículo (" + vehiculoSeleccionado.getKilometrajeActual() + " Km).");


                request.setAttribute("listaMovimientos", garitaRepository.listarHistorial());
                request.setAttribute("listaVehiculosActivos", listaVehiculos);
                request.getRequestDispatcher("movimientos_garita.jsp").forward(request, response);
                return;
            }

            MovimientoGarita movimiento = new MovimientoGarita();
            movimiento.setIdVehiculo(idVehiculo);
            movimiento.setIdUsuario(idUsuario);
            movimiento.setTipoOperacion(tipoOperacion);
            movimiento.setKilometrajeRegistro(kmRegistro);

      
            garitaRepository.registrarMovimiento(movimiento);
            logger.info("Movimiento de {} registrado para vehículo ID: {}", tipoOperacion, idVehiculo);

            response.sendRedirect("garita");

        } catch (Exception e) {
            logger.error("Error al procesar el ticket de garita", e);
            request.setAttribute("error", e.getMessage());
            try {
                request.setAttribute("listaMovimientos", garitaRepository.listarHistorial());
                request.setAttribute("listaVehiculosActivos", vehiculoRepository.listarTodos());
            } catch (Exception ex) {
                logger.error("Fallo doble en contingencia de garita", ex);
            }
            request.getRequestDispatcher("movimientos_garita.jsp").forward(request, response);
        }
    }
}
