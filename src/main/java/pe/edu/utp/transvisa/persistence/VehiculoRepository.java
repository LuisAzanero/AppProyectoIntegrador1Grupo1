package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Vehiculo;

public interface VehiculoRepository {
    Vehiculo buscarPorId(int idVehiculo);
    void actualizarEstadoYKilometraje(Vehiculo vehiculo);
}