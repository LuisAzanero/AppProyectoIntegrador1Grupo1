package pe.edu.utp.transvisa.business;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

public class MovimientoService {
    
    private static final Logger log = LoggerFactory.getLogger(MovimientoService.class);
    
    private final MovimientoRepository movimientoRepository;
    private final VehiculoRepository vehiculoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, VehiculoRepository vehiculoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.vehiculoRepository = vehiculoRepository;
        log.info("MovimientoService inicializado correctamente");
    }

    public void procesarRegistroGarita(MovimientoGarita movimiento) throws Exception {
        log.info("Procesando movimiento para vehiculo ID: {}", movimiento.getIdVehiculo());
        
        Preconditions.checkNotNull(movimiento, "Error: El registro de movimiento no puede ser nulo.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(movimiento.getTipoOperacion()), "Error: El tipo de operacion es obligatorio.");

        BigDecimal ultimoKm = movimientoRepository.obtenerUltimoKilometraje(movimiento.getIdVehiculo());
        log.debug("Ultimo kilometraje registrado: {}", ultimoKm);
        
        Preconditions.checkArgument(
            movimiento.getKilometrajeRegistro().compareTo(ultimoKm) >= 0,
            "Error: El kilometraje ingresado es menor al ultimo registrado (%s km).", 
            ultimoKm
        );

        if (movimiento.getTipoOperacion().equalsIgnoreCase("SALIDA")) {
            if (movimientoRepository.tieneMantenimientoAbierto(movimiento.getIdVehiculo())) {
                log.error("BLOQUEO DE DESPACHO: Vehiculo {} tiene mantenimiento abierto", movimiento.getIdVehiculo());
                throw new Exception("BLOQUEO DE DESPACHO: El vehiculo tiene una orden de mantenimiento ABIERTA en taller.");
            }
        }

        movimientoRepository.guardar(movimiento);
        log.info("Movimiento guardado exitosamente");

        Vehiculo vehiculo = vehiculoRepository.buscarPorId(movimiento.getIdVehiculo());
        Preconditions.checkNotNull(vehiculo, "Error: El vehiculo con ID %s no existe.", movimiento.getIdVehiculo());
        
        vehiculo.setKilometrajeActual(movimiento.getKilometrajeRegistro());

        if (movimiento.getTipoOperacion().equalsIgnoreCase("ENTRADA")) {
            vehiculo.setEstadoOperativo("Disponible");
        } else {
            vehiculo.setEstadoOperativo("En Ruta");
        }

        vehiculoRepository.actualizarEstadoYKilometraje(vehiculo);
        log.info("Vehiculo {} actualizado - Estado: {}, KM: {}", vehiculo.getIdVehiculo(), vehiculo.getEstadoOperativo(), vehiculo.getKilometrajeActual());
    }
}