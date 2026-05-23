/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.persistence.mock;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;

/**
 *
 * @author luisazanero
 */
public class MockMovimientoRepository implements MovimientoRepository {
    @Override
    public void guardar(MovimientoGarita movimiento) {
        System.out.println("Guardando exitosamente en tabla movimientos_garita el tipo: " + movimiento.getTipoOperacion());
    }

    @Override
    public boolean tieneMantenimientoAbierto(int idVehiculo) {

        return idVehiculo == 2; 
    }

    @Override
    public BigDecimal obtenerUltimoKilometraje(int idVehiculo) {
        return new BigDecimal("10000"); 
    }
}
