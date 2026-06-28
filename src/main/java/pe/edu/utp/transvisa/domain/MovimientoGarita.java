package pe.edu.utp.transvisa.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoGarita {

    private int idMovement;
    private int idVehiculo;
    private int idUsuario;
    private String tipoOperacion; 
    private LocalDateTime fechaHora;
    private BigDecimal kilometrajeRegistro;

    
    private String placaVehiculo;
    private String nombreUsuario;

    // Constructores
    public MovimientoGarita() {
    }

    // Getters y Setters
    public int getIdMovement() {
        return idMovement;
    }

    public void setIdMovement(int idMovement) {
        this.idMovement = idMovement;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int idUsuario() {
        return idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public BigDecimal getKilometrajeRegistro() {
        return kilometrajeRegistro;
    }

    public void setKilometrajeRegistro(BigDecimal kilometrajeRegistro) {
        this.kilometrajeRegistro = kilometrajeRegistro;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
