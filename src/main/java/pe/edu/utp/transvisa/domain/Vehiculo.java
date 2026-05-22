/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.domain;

/**
 *
 * @author luisazanero
 */
public class Vehiculo {

    private long id;
    private String placa;
    private String marca;
    private double kilometrajeActual;
    private String estadoOperativo;

    public Vehiculo() {
    }

    public Vehiculo(long id, String placa, String marca, double kilometrajeActual, String estadoOperativo) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.kilometrajeActual = kilometrajeActual;
        this.estadoOperativo = estadoOperativo;
    }

    // Getters y Setters de todos los atributos
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

}
