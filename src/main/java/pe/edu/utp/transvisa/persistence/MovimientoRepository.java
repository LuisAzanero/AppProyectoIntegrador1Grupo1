/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.MovimientoGarita;

/**
 *
 * @author luisazanero
 */
public interface MovimientoRepository {

    void guardar(MovimientoGarita movimiento);

    boolean tieneMantenimientoAbierto(int idVehiculo);

    BigDecimal obtenerUltimoKilometraje(int idVehiculo);
}
