package pe.edu.utp.transvisa.persistence;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.domain.MovimientoGarita;

public interface MovimientoRepository {
    void guardar(MovimientoGarita movimiento);
    boolean tieneMantenimientoAbierto(int idVehiculo);
    BigDecimal obtenerUltimoKilometraje(int idVehiculo);
}