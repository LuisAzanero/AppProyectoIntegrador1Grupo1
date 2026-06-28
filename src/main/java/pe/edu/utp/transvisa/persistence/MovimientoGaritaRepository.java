package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.MovimientoGarita;
import java.util.List;
import java.sql.SQLException;

public interface MovimientoGaritaRepository {

    void registrarMovimiento(MovimientoGarita movimiento) throws SQLException;

    List<MovimientoGarita> listarHistorial() throws SQLException;
}
