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
        // Data en duro
        MovimientoRepository movRepo = new MockMovimientoRepository();
        VehiculoRepository vehRepo = new MockVehiculoRepository(); 

        MovimientoService servicio = new MovimientoService(movRepo, vehRepo);

        MovimientoGarita movInvalido = new MovimientoGarita();
        movInvalido.setIdVehiculo(2);
        movInvalido.setIdUsuario(1);
        movInvalido.setTipoOperacion("Entrada");
        movInvalido.setKilometrajeRegistro(new BigDecimal("120000.00"));

        try {
            //Test semana 12
            System.out.println("Intentando registrar entrada con kilometraje alterado...");
            servicio.procesarRegistroGarita(movInvalido);
            System.out.println("¡Alerta! El sistema permitió un kilometraje menor.");
        } catch (Exception e) {
            // Aquí capturamos la excepción que arroja Preconditions de Guava
            System.out.println("\n[RESPUESTA DEL SISTEMA - DETECTADO POR GOOGLE GUAVA]");
            System.out.println("Tipo de error: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
        }
    }
}
