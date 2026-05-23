/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.business;

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

    // Constructor de inyeccion
    public MovimientoService(MovimientoRepository movimientoRepository, VehiculoRepository vehiculoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public void procesarRegistroGarita(MovimientoGarita movimiento) throws Exception {
        // 1. Validación de Kilometraje (No puede ser menor al histórico)
        BigDecimal ultimoKm = movimientoRepository.obtenerUltimoKilometraje(movimiento.getIdVehiculo());
        if (movimiento.getKilometrajeRegistro().compareTo(ultimoKm) < 0) {
            throw new Exception("Error: El kilometraje ingresado es menor al último registrado (" + ultimoKm + " km).");
        }

        // 2. Regla de Negocio Crítica: Bloqueo por Taller si intenta salir
        if (movimiento.getTipoOperacion().equals("SALIDA")) {
            if (movimientoRepository.tieneMantenimientoAbierto(movimiento.getIdVehiculo())) {
                throw new Exception("BLOQUEO DE DESPACHO: El vehículo tiene una orden de mantenimiento ABIERTA en taller.");
            }
        }

        // 3. Guardar el movimiento si pasa las reglas
        movimientoRepository.guardar(movimiento);

        // 4. Actualizar el estado del Vehículo en consecuencia
        Vehiculo vehiculo = vehiculoRepository.buscarPorId(movimiento.getIdVehiculo());
        vehiculo.setKilometrajeActual(movimiento.getKilometrajeRegistro());

        if (movimiento.getTipoOperacion().equals("Entrada")) {
            vehiculo.setEstadoOperativo("Disponible");
        } else {
            vehiculo.setEstadoOperativo("En Ruta");
        }

        vehiculoRepository.actualizarEstadoYKilometraje(vehiculo);
    }
}
