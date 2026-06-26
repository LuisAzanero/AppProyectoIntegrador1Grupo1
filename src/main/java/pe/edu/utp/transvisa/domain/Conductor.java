/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.domain;

/**
 *
 * @author luis.azanero
 */
public class Conductor {

    private int idConductor;
    private String dni;
    private String nombres;
    private String direccion;
    private String nroBreve;

    // Constructores
    public Conductor() {
    }

    public Conductor(int idConductor, String dni, String nombres, String direccion, String nroBreve) {
        this.idConductor = idConductor;
        this.dni = dni;
        this.nombres = nombres;
        this.direccion = direccion;
        this.nroBreve = nroBreve;
    }

    // Getters y Setters
    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNroBreve() {
        return nroBreve;
    }

    public void setNroBreve(String nroBreve) {
        this.nroBreve = nroBreve;
    }
}
