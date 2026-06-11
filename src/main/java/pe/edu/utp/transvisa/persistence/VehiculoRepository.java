package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Vehiculo;
import java.sql.SQLException;
import java.util.List;

public interface VehiculoRepository {

    List<Vehiculo> listarTodos() throws SQLException;

    void registrar(Vehiculo vehiculo) throws SQLException;

    void actualizarEstadoYKilometraje(Vehiculo vehiculo) throws SQLException;

    void eliminar(int idVehiculo) throws SQLException;

    Vehiculo buscarPorId(int idVehiculo);

    boolean existePlaca(String placa) throws java.sql.SQLException;
}
