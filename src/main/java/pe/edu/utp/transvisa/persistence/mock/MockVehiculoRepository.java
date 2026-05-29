/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.persistence.mock;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

/**
 *
 * @author luisazanero
 */
public class MockVehiculoRepository implements VehiculoRepository {

    @Override
    public Vehiculo buscarPorId(int idVehiculo) {
        Vehiculo vehiculoSimulado = new Vehiculo();
        vehiculoSimulado.setIdVehiculo(idVehiculo);
        vehiculoSimulado.setMarca("Hyundai");
        vehiculoSimulado.setModelo("Venue");

        // Condicional para simular el escenario de bloqueo del taller (Fase 1 y 2)
        if (idVehiculo == 2) {
            vehiculoSimulado.setPlaca("XYZ-789");
            vehiculoSimulado.setKilometrajeActual(new BigDecimal("700000.00"));
            vehiculoSimulado.setEstadoOperativo("En Taller"); // Provocará el bloqueo preventivo
        } else {
            vehiculoSimulado.setPlaca("BYF-398");
            vehiculoSimulado.setKilometrajeActual(new BigDecimal("30000.00"));
            vehiculoSimulado.setEstadoOperativo("Salida"); // Flujo normal de ruta
        }

        return vehiculoSimulado;
    }

    @Override
    public void actualizarEstadoYKilometraje(Vehiculo vehiculo) {
        System.out.println("\n[MOCK PERSISTENCE] Tabla 'vehiculos' actualizada con éxito.");
        System.out.println(" -> Vehículo ID: " + vehiculo.getIdVehiculo());
        System.out.println(" -> Nuevo Km: " + vehiculo.getKilometrajeActual() + " km.");
        System.out.println(" -> Nuevo Estado Operativo: " + vehiculo.getEstadoOperativo());
    }
}
