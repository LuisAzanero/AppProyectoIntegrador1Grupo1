/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Vehiculo;

/**
 *
 * @author luisazanero
 */
public class VehiculoDAOMock implements IVehiculoDAO {

    @Override
    public void guardar(Vehiculo vehiculo) {
        System.out.println("[MOCK-BD] Guardando vehiculo exitosamente en MySQL: Placa " + vehiculo.getPlaca());
    }

    @Override
    public Vehiculo buscarPorPlaca(String placa) {
        System.out.println("[MOCK-BD] Buscando vehiculo en el sistema manual de TRANSVISA...");
        // Retorna un vehículo simulado para probar el sistema
        return new Vehiculo(1L, placa, "Volvo", 48000.0, "OPERATIVO");
    }
}
