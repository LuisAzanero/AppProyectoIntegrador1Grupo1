package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.dao.MovimientoGaritaDAO;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import java.math.BigDecimal;

public class MovimientoRepositoryImpl implements MovimientoRepository {
    private MovimientoGaritaDAO movimientoDAO = new MovimientoGaritaDAO();
    
    @Override
    public void guardar(MovimientoGarita movimiento) {
        movimientoDAO.guardar(movimiento);
    }
    
    @Override
    public boolean tieneMantenimientoAbierto(int idVehiculo) {
        return movimientoDAO.tieneMantenimientoAbierto(idVehiculo);
    }
    
    @Override
    public BigDecimal obtenerUltimoKilometraje(int idVehiculo) {
        return movimientoDAO.obtenerUltimoKilometraje(idVehiculo);
    }
}