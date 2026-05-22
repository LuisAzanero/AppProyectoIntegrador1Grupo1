/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.business;

import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.IVehiculoDAO;
import pe.edu.utp.transvisa.persistence.IVehiculoDAO;

/**
 *
 * @author luisazanero
 */
public class VehiculoService {

    // Dependemos de la interfaz ("enchufe"), NO de la clase física
    private final IVehiculoDAO vehiculoDAO;

    // Inyección por Constructor (Regla estricta de la Fase 2)
    public VehiculoService(IVehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    // Regla de Negocio obligatoria antes de guardar
    public void registrarNuevoVehiculo(Vehiculo vehiculo) {
        System.out.println("[NEGOCIO] Ejecutando validaciones para TRANSVISA...");

        // Simulación de regla: Validar que la placa no esté duplicada
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }

        // Si pasa las reglas, se manda a guardar a través del enchufe
        vehiculoDAO.guardar(vehiculo);
    }
}
