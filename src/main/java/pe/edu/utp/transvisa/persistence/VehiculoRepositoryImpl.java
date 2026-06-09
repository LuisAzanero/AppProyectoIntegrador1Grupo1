package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.dao.VehiculoDAO;
import pe.edu.utp.transvisa.domain.Vehiculo;

public class VehiculoRepositoryImpl implements VehiculoRepository {
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    
    @Override
    public Vehiculo buscarPorId(int idVehiculo) {
        return vehiculoDAO.buscarPorId(idVehiculo);
    }
    
    @Override
    public void actualizarEstadoYKilometraje(Vehiculo vehiculo) {
        vehiculoDAO.actualizarEstadoYKilometraje(vehiculo);
    }
}