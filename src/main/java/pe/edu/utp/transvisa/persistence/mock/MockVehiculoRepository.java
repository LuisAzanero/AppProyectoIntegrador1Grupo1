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
        vehiculoSimulado.setPlaca("BYF-398");
        vehiculoSimulado.setMarca("Hyundai");
        vehiculoSimulado.setModelo("Venu");
        vehiculoSimulado.setKilometrajeActual(new BigDecimal("10000"));
        vehiculoSimulado.setEstadoOperativo("Disponible");
        return vehiculoSimulado;
    }

    @Override
    public void actualizarEstadoYKilometraje(Vehiculo vehiculo) {
        
        System.out.println("Tabla 'vehiculos' actualizada con éxito.");
        System.out.println("-> Nuevo Km: " + vehiculo.getKilometrajeActual() + " km.");
        System.out.println("-> Nuevo Estado Operativo: " + vehiculo.getEstadoOperativo());
    }
}
