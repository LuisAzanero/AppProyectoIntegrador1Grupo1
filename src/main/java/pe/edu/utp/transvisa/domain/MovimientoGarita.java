/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.domain;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

/**
 *
 * @author luisazanero
 */
public class MovimientoGarita {

    private int idMovimiento;
    private int idVehiculo; 
    private int idUsuario; 
    private String tipoOperacion; 
    private Date fechaHora;
    private BigDecimal kilometrajeRegistro;
    private Timestamp fechaCreacion;

    public MovimientoGarita() {
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
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

    public BigDecimal getKilometrajeRegistro() {
        return kilometrajeRegistro;
    }

    public void setKilometrajeRegistro(BigDecimal kilometrajeRegistro) {
        this.kilometrajeRegistro = kilometrajeRegistro;
    }
}
