package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLVehiculoRepository implements VehiculoRepository {

    @Override
    public List<Vehiculo> listarTodos() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        // Seleccionamos la tabalde vehiculos
        String sql = "SELECT id_vehiculo, placa, marca, modelo, kilometraje_actual, estado_operativo FROM vehiculos";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearVehiculo(rs));
            }
        }
        return lista;
    }

    @Override
    public void registrar(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, kilometraje_actual, estado_operativo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setBigDecimal(4, vehiculo.getKilometrajeActual());
            stmt.setString(5, vehiculo.getEstadoOperativo());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizarEstadoYKilometraje(Vehiculo vehiculo) throws SQLException {

        String sql = "UPDATE vehiculos SET kilometraje_actual = ?, estado_operativo = ? WHERE id_vehiculo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, vehiculo.getKilometrajeActual());
            stmt.setString(2, vehiculo.getEstadoOperativo());
            stmt.setInt(3, vehiculo.getIdVehiculo());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idVehiculo) throws SQLException {

        String sql = "UPDATE vehiculos SET estado_operativo = 'Inactivo' WHERE id_vehiculo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVehiculo);
            stmt.executeUpdate();
        }
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Vehiculo v = new Vehiculo();
        v.setIdVehiculo(rs.getInt("id_vehiculo"));
        v.setPlaca(rs.getString("placa"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setKilometrajeActual(rs.getBigDecimal("kilometraje_actual"));
        v.setEstadoOperativo(rs.getString("estado_operativo"));
        return v;
    }

    @Override
    public Vehiculo buscarPorId(int idVehiculo) {
        String sql = "SELECT id_vehiculo, placa, marca, modelo, kilometraje_actual, estado_operativo FROM vehiculos WHERE id_vehiculo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVehiculo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehiculo v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setKilometrajeActual(rs.getBigDecimal("kilometraje_actual"));
                    v.setEstadoOperativo(rs.getString("estado_operativo"));
                    return v;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar vehículo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean existePlaca(String placa) throws java.sql.SQLException {
        String sql = "SELECT COUNT(*) FROM vehiculos WHERE placa = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa.toUpperCase().trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
