package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUsuarioRepository implements UsuarioRepository {

    @Override
    public Usuario buscarPorUsername(String username) throws SQLException {
        // RNF-03: Consulta parametrizada eficiente para evitar SQL Injection (Seguridad)
        String sql = "SELECT id_usuario, username, password_hash, nombre, rol, estado FROM usuarios WHERE username = ? AND estado = 1";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPasswordHash(rs.getString("password_hash"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        }
        return null; // Retorna null si el usuario no existe o está inactivo
    }
}