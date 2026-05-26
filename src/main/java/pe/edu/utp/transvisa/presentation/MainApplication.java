/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.presentation;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.business.MovimientoService;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;
import pe.edu.utp.transvisa.persistence.mock.MockMovimientoRepository;
import pe.edu.utp.transvisa.persistence.mock.MockVehiculoRepository;


/**
 *
 * @author luisazanero
 */
public class MainApplication {

    public static void main(String[] args) {
        // 1. Se crean los componentes de infraestructura (Mocks temporales)
        MovimientoRepository movRepo = new MockMovimientoRepository();
        VehiculoRepository vehRepo = new MockVehiculoRepository(); // Clase mock simulada

        // 2. INYECCIÓN POR CONSTRUCTOR: Se le entregan las dependencias al servicio
        MovimientoService servicio = new MovimientoService(movRepo, vehRepo);

        // CASO DE PRUEBA 1: Intento de salida de vehículo bloqueado en taller (idVehiculo = 2)
        MovimientoGarita movInvalido = new MovimientoGarita();
        movInvalido.setIdVehiculo(2);
        movInvalido.setIdUsuario(1);
        movInvalido.setTipoOperacion("Entrada");
        movInvalido.setKilometrajeRegistro(new BigDecimal("155000.00"));

        try {
            System.out.println("Intentando despachar vehículo en taller...");
            servicio.procesarRegistroGarita(movInvalido);
        } catch (Exception e) {
            System.out.println("Respuesta del Sistema: " + e.getMessage());
        }
    }
}
