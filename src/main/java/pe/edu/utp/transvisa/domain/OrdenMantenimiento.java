package pe.edu.utp.transvisa.domain;


import java.sql.Timestamp;

    /**
     *@author brianruiz
     */

public class OrdenMantenimiento {


    private int idOrden;
    private int idVehiculo;
    private int idUsuario;
    private String tipoMantenimiento; // Preventivo / Correctivo
    private String descripcion;
    private String estadoOrden;      // Abierta / Cerrada
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Timestamp fechaCreacion;

    public OrdenMantenimiento() {}

    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoMantenimiento() { return tipoMantenimiento; }
    public void setTipoMantenimiento(String tipoMantenimiento) { this.tipoMantenimiento = tipoMantenimiento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstadoOrden() { return estadoOrden; }
    public void setEstadoOrden(String estadoOrden) { this.estadoOrden = estadoOrden; }

    public Timestamp getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Timestamp fechaInicio) { this.fechaInicio = fechaInicio; }

    public Timestamp getFechaFin() { return fechaFin; }
    public void setFechaFin(Timestamp fechaFin) { this.fechaFin = fechaFin; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}