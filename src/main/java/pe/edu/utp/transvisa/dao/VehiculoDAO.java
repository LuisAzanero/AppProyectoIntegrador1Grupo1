package pe.edu.utp.transvisa.dao;

import pe.edu.utp.transvisa.domain.Vehiculo;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    
    public Vehiculo buscarPorId(int idVehiculo) {
        String sql = "SELECT * FROM vehiculos WHERE id_vehiculo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVehiculo);
            ResultSet rs = stmt.executeQuery();
            
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
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehiculo", e);
        }
    }
    
    public void actualizarEstadoYKilometraje(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculos SET kilometraje_actual = ?, estado_operativo = ? WHERE id_vehiculo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBigDecimal(1, vehiculo.getKilometrajeActual());
            stmt.setString(2, vehiculo.getEstadoOperativo());
            stmt.setInt(3, vehiculo.getIdVehiculo());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar vehiculo", e);
        }
    }
    
    public List<Vehiculo> listarTodos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setKilometrajeActual(rs.getBigDecimal("kilometraje_actual"));
                v.setEstadoOperativo(rs.getString("estado_operativo"));
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar vehiculos", e);
        }
        return lista;
    }
    
    public void crear(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, kilometraje_actual, estado_operativo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setBigDecimal(4, vehiculo.getKilometrajeActual());
            stmt.setString(5, vehiculo.getEstadoOperativo() != null ? vehiculo.getEstadoOperativo() : "Disponible");
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear vehiculo", e);
        }
    }
}