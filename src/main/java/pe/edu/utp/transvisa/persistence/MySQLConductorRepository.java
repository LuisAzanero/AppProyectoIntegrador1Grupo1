package pe.edu.utp.transvisa.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Conductor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLConductorRepository implements ConductorRepository {

    private static final Logger logger = LoggerFactory.getLogger(MySQLConductorRepository.class);

    @Override
    public void registrar(Conductor c) throws SQLException {
        String sql = "INSERT INTO conductores (dni, nombres, direccion, nro_breve) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getDni().trim());
            stmt.setString(2, c.getNombres().trim());
            stmt.setString(3, c.getDireccion().trim());
            stmt.setString(4, c.getNroBreve().trim());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Conductor c) throws SQLException {
        String sql = "UPDATE conductores SET dni = ?, nombres = ?, direccion = ?, nro_breve = ? WHERE id_conductor = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getDni().trim());
            stmt.setString(2, c.getNombres().trim());
            stmt.setString(3, c.getDireccion().trim());
            stmt.setString(4, c.getNroBreve().trim());
            stmt.setInt(5, c.getIdConductor());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Conductor> listarTodos() throws SQLException {
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT id_conductor, dni, nombres, direccion, nro_breve FROM conductores ORDER BY nombres ASC";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Conductor(
                        rs.getInt("id_conductor"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("direccion"),
                        rs.getString("nro_breve")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean existeDni(String dni) throws SQLException {
        String sql = "SELECT COUNT(*) FROM conductores WHERE dni = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM conductores WHERE id_conductor = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
