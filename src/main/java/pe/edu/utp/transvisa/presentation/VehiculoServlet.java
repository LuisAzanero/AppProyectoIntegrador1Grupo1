package pe.edu.utp.transvisa.presentation;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MySQLVehiculoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(VehiculoServlet.class);
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
            if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                vehiculoRepository.eliminar(id);
                logger.info("Vehículo marcado como Inactivo. ID: {}", id);
                response.sendRedirect("vehiculos");
                return;
            }

            List<Vehiculo> listaVehiculos = vehiculoRepository.listarTodos();
            request.setAttribute("listaVehiculos", listaVehiculos);
            request.getRequestDispatcher("vehiculos.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error en módulo vehicular", e);
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("idVehiculo");
        String placa = request.getParameter("placa");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String kmStr = request.getParameter("kilometraje");
        String estadoOps = request.getParameter("estadoOperativo");

        try {
            // Validaciones defensivas impecables con Google Guava
            Preconditions.checkArgument(!Strings.isNullOrEmpty(placa), "La placa es obligatoria.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(marca), "La marca es obligatoria.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(modelo), "El modelo es obligatorio.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(kmStr), "El kilometraje es obligatorio.");

            Vehiculo v = new Vehiculo();
            v.setPlaca(placa.toUpperCase().trim());
            v.setMarca(marca.trim());
            v.setModelo(modelo.trim());
            v.setKilometrajeActual(new BigDecimal(kmStr));
            v.setEstadoOperativo(estadoOps);

            if (Strings.isNullOrEmpty(idStr)) {
                vehiculoRepository.registrar(v);
                logger.info("Vehículo insertado: Placa [{}]", placa);
            } else {
                v.setIdVehiculo(Integer.parseInt(idStr));
                //Llamamos al método correcto definido en tu interfaz y repositorio
                vehiculoRepository.actualizarEstadoYKilometraje(v);
                logger.info("Vehículo modificado ID: {}", idStr);
            }

            response.sendRedirect("vehiculos");

        } catch (Exception e) {
            logger.error("Error al procesar formulario vehicular", e);
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
