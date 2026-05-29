/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.business;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

/**
 *
 * @author luisazanero
 */
public class MovimientoService {
private final MovimientoRepository movimientoRepository;
    private final VehiculoRepository vehiculoRepository;

    // Constructor de inyección
    public MovimientoService(MovimientoRepository movimientoRepository, VehiculoRepository vehiculoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public void procesarRegistroGarita(MovimientoGarita movimiento) throws Exception {
        // --- VALIDACIONES INICIALES CON GUAVA ---
        // Asegura que el objeto no sea nulo antes de procesar
        Preconditions.checkNotNull(movimiento, "Error: El registro de movimiento no puede ser nulo.");
        // Asegura que el tipo de operación contenga texto válido
        Preconditions.checkArgument(!Strings.isNullOrEmpty(movimiento.getTipoOperacion()), "Error: El tipo de operación es obligatorio.");

        // 1. Validación de Kilometraje usando Guava (Regla A)
       BigDecimal ultimoKm = movimientoRepository.obtenerUltimoKilometraje(movimiento.getIdVehiculo());
        
        // Guava evalúa la condición; si es falsa, lanza un IllegalArgumentException automáticamente
        Preconditions.checkArgument(
            movimiento.getKilometrajeRegistro().compareTo(ultimoKm) >= 0,
            "Error: El kilometraje ingresado es menor al último registrado (%s km).", 
            ultimoKm
        );

        // 2. Regla de Negocio Crítica: Bloqueo por Taller si intenta salir (Regla B)
        // CORRECCIÓN: Usamos equalsIgnoreCase para evitar errores si viene "Salida" o "SALIDA"
        if (movimiento.getTipoOperacion().equalsIgnoreCase("SALIDA")) {
            if (movimientoRepository.tieneMantenimientoAbierto(movimiento.getIdVehiculo())) {
                throw new Exception("BLOQUEO DE DESPACHO: El vehículo tiene una orden de mantenimiento ABIERTA en taller.");
            }
        }

        // 3. Guardar el movimiento si pasa las reglas
        movimientoRepository.guardar(movimiento);

        // 4. Actualizar el estado del Vehículo en consecuencia
        Vehiculo vehiculo = vehiculoRepository.buscarPorId(movimiento.getIdVehiculo());
        
        // Validamos que el vehículo exista en nuestro repositorio antes de mutarlo
        Preconditions.checkNotNull(vehiculo, "Error: El vehículo con ID %s no existe.", movimiento.getIdVehiculo());
        
        vehiculo.setKilometrajeActual(movimiento.getKilometrajeRegistro());

        // CORRECCIÓN: Ajustado a ignoreCase para que haga match perfecto con tu MainApplication ("Entrada")
        if (movimiento.getTipoOperacion().equalsIgnoreCase("ENTRADA")) {
            vehiculo.setEstadoOperativo("Disponible");
        } else {
            vehiculo.setEstadoOperativo("En Ruta");
        }

        vehiculoRepository.actualizarEstadoYKilometraje(vehiculo);
    }
}
