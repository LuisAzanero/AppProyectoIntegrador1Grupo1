package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLUsuarioRepository implements UsuarioRepository {

    @Override
    public Usuario buscarPorUsername(String username) throws SQLException {

        String sql = "SELECT id_usuario, username, password_hash, nombre, rol, estado FROM usuarios WHERE username = ? AND estado = 1";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        return null; // Retorna null si el usuario no existe o está inactivo en la base de datos
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, dni, nombre, username, password_hash, rol, estado FROM usuarios WHERE estado = 1";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }
        return lista;
    }

    @Override
    public void registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (dni, nombre, username, password_hash, rol, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, 1, NOW())";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPasswordHash());
            stmt.setString(5, usuario.getRol());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET dni = ?, nombre = ?, username = ?, password_hash = ?, rol = ? WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPasswordHash());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idUsuario) throws SQLException {
        String sql = "UPDATE usuarios SET estado = 0 WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setDni(rs.getString("dni"));
        u.setNombre(rs.getString("nombre"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRol(rs.getString("rol"));
        u.setEstado(rs.getBoolean("estado"));
        return u;
    }
}
