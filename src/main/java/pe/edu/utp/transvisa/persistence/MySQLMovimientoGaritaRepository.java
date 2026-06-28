package pe.edu.utp.transvisa.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLMovimientoGaritaRepository implements MovimientoGaritaRepository {

    private static final Logger logger = LoggerFactory.getLogger(MySQLMovimientoGaritaRepository.class);

    @Override
    public void registrarMovimiento(MovimientoGarita m) throws SQLException {
        String sqlInsert = "INSERT INTO movimientos_garita (id_vehiculo, id_usuario, tipo_operacion, fecha_hora, kilometraje_registro) VALUES (?, ?, ?, NOW(), ?)";
        String sqlUpdateVehiculo = "UPDATE vehiculos SET kilometraje_actual = ?, estado_operativo = ? WHERE id_vehiculo = ?";

        Connection conn = null;
        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setInt(1, m.getIdVehiculo());
                stmtInsert.setInt(2, m.getIdUsuario());
                stmtInsert.setString(3, m.getTipoOperacion());
                stmtInsert.setBigDecimal(4, m.getKilometrajeRegistro());
                stmtInsert.executeUpdate();
            }

            String nuevoEstado = "ENTRADA".equals(m.getTipoOperacion()) ? "Disponible" : "En Ruta";

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateVehiculo)) {
                stmtUpdate.setBigDecimal(1, m.getKilometrajeRegistro());
                stmtUpdate.setString(2, nuevoEstado);
                stmtUpdate.setInt(3, m.getIdVehiculo());
                stmtUpdate.executeUpdate();
            }

            conn.commit(); 
            logger.info("Transmisión de Garita exitosa para el vehículo ID: {}", m.getIdVehiculo());

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    logger.error("Rollback ejecutado en control perimetral por error en operación", e);
                } catch (SQLException ex) {
                    logger.error("Error fatal en rollback", ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<MovimientoGarita> listarHistorial() throws SQLException {
        List<MovimientoGarita> historial = new ArrayList<>();
        String sql = "SELECT m.id_movement, m.id_vehiculo, m.id_usuario, m.tipo_operacion, m.fecha_hora, m.kilometraje_registro, "
                + "v.placa AS placa_vehiculo, u.username AS nombre_usuario "
                + "FROM movimientos_garita m "
                + "INNER JOIN vehiculos v ON m.id_vehiculo = v.id_vehiculo "
                + "INNER JOIN usuarios u ON m.id_usuario = u.id_usuario "
                + "ORDER BY m.id_movement DESC";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MovimientoGarita m = new MovimientoGarita();
                m.setIdMovement(rs.getInt("id_movement"));
                m.setIdVehiculo(rs.getInt("id_vehiculo"));
                m.setIdUsuario(rs.getInt("id_usuario"));
                m.setTipoOperacion(rs.getString("tipo_operacion"));
                m.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                m.setKilometrajeRegistro(rs.getBigDecimal("kilometraje_registro"));
                m.setPlacaVehiculo(rs.getString("placa_vehiculo"));
                m.setNombreUsuario(rs.getString("nombre_usuario"));
                historial.add(m);
            }
        }
        return historial;
    }
}
