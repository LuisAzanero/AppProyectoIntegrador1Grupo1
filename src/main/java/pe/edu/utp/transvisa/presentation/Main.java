/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.presentation;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.business.VehiculoService;
import pe.edu.utp.transvisa.business.VehiculoService;
import pe.edu.utp.transvisa.persistence.IVehiculoDAO;
import pe.edu.utp.transvisa.persistence.VehiculoDAOMock;
/**
 *
 * @author luisazanero
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE GESTIÓN TRANSVISA E.I.R.L. ---\n");

        // 1. Instanciamos el almacén temporal (Mock)
        IVehiculoDAO persistenciaTemporal = new VehiculoDAOMock();

        // 2. Inyectamos la persistencia al servicio mediante el constructor (Desacoplamiento)
        VehiculoService servicioNegocio = new VehiculoService(persistenciaTemporal);

        // 3. Simulamos que el Supervisor registra un vehículo en el formulario web
        Vehiculo nuevoCamion = new Vehiculo(0, "ABC-123", "Fuso", 12000.0, "OPERATIVO");

        // 4. Procesamos a través de la capa de negocio
        try {
            servicioNegocio.registrarNuevoVehiculo(nuevoCamion);
            System.out.println("\n[PRESENTACIÓN] ¡Proceso completado con éxito!");
        } catch (Exception e) {
            System.out.println("[ERROR] No se pudo registrar: " + e.getMessage());
        }
    }
}
