package pe.edu.utp.transvisa.dao;

import pe.edu.utp.transvisa.domain.MovimientoGarita;
import java.math.BigDecimal;
import java.sql.*;

public class MovimientoGaritaDAO {
    
    public void guardar(MovimientoGarita movimiento) {
        String sql = "INSERT INTO movimientos_garita (id_vehiculo, id_usuario, tipo_operacion, kilometraje_registro) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, movimiento.getIdVehiculo());
            stmt.setInt(2, movimiento.getIdUsuario());
            stmt.setString(3, movimiento.getTipoOperacion());
            stmt.setBigDecimal(4, movimiento.getKilometrajeRegistro());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }
    
    public BigDecimal obtenerUltimoKilometraje(int idVehiculo) {
        String sql = "SELECT TOP 1 kilometraje_registro FROM movimientos_garita WHERE id_vehiculo = ? ORDER BY fecha_hora DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("kilometraje_registro");
            }
            return BigDecimal.ZERO;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener ultimo kilometraje", e);
        }
    }
    
    public boolean tieneMantenimientoAbierto(int idVehiculo) {
        String sql = "SELECT COUNT(*) FROM ordenes_mantenimiento WHERE id_vehiculo = ? AND estado_orden = 'Abierta'";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar mantenimiento", e);
        }
    }
}