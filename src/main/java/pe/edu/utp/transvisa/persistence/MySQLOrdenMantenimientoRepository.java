package pe.edu.utp.transvisa.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.transvisa.domain.OrdenMantenimiento;

public class MySQLOrdenMantenimientoRepository implements OrdenMantenimientoRepository {

    @Override
    public List<OrdenMantenimiento> listarTodas() throws Exception {
        List<OrdenMantenimiento> lista = new ArrayList<>();
        String sql = "SELECT o.id_orden, o.id_vehiculo, o.id_usuario, o.tipo_mantenimiento, o.descripcion, o.estado_orden, o.fecha_inicio, o.fecha_fin, v.placa "
                + "FROM ordenes_mantenimiento o "
                + "INNER JOIN vehiculos v ON o.id_vehiculo = v.id_vehiculo "
                + "ORDER BY o.id_orden DESC";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrdenMantenimiento o = new OrdenMantenimiento();
                o.setIdOrden(rs.getInt("id_orden"));
                o.setIdVehiculo(rs.getInt("id_vehiculo"));
                o.setIdUsuario(rs.getInt("id_usuario"));
                o.setPlacaVehiculo(rs.getString("placa"));
                o.setTipoMantenimiento(rs.getString("tipo_mantenimiento"));
                o.setDescripcion(rs.getString("descripcion"));
                o.setEstadoOrden(rs.getString("estado_orden"));
                o.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
                if (rs.getTimestamp("fecha_fin") != null) {
                    o.setFechaFin(rs.getTimestamp("fecha_fin").toLocalDateTime());
                }
                lista.add(o);
            }
        }
        return lista;
    }

    @Override
    public void registrar(OrdenMantenimiento orden) throws Exception {
        String sql = "INSERT INTO ordenes_mantenimiento (id_vehiculo, id_usuario, tipo_mantenimiento, descripcion, estado_orden, fecha_inicio) "
                + "VALUES (?, ?, ?, ?, 'ABIERTA', NOW())";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orden.getIdVehiculo());
            ps.setInt(2, orden.getIdUsuario());
            ps.setString(3, orden.getTipoMantenimiento());
            ps.setString(4, orden.getDescripcion());
            ps.executeUpdate();
        }
    }

    @Override
    public void cerrarOrden(int idOrden) throws Exception {
        String sql = "UPDATE ordenes_mantenimiento SET estado_orden = 'CERRADA', fecha_fin = NOW() WHERE id_orden = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.executeUpdate();
        }
    }
}
