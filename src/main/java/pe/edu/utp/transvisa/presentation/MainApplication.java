package pe.edu.utp.transvisa.presentation;

import java.math.BigDecimal;
import pe.edu.utp.transvisa.business.MovimientoService;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;
import pe.edu.utp.transvisa.persistence.MovimientoRepositoryImpl;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepositoryImpl;

public class MainApplication {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA TRANSVISA - PRUEBA DE VALIDACIONES ===\n");
        
        MovimientoRepository movRepo = new MovimientoRepositoryImpl();
        VehiculoRepository vehRepo = new VehiculoRepositoryImpl();
        MovimientoService servicio = new MovimientoService(movRepo, vehRepo);

        System.out.println("----- CASO 1: Kilometraje menor al registrado -----");
        MovimientoGarita movInvalido = new MovimientoGarita();
        movInvalido.setIdVehiculo(1);
        movInvalido.setIdUsuario(1);
        movInvalido.setTipoOperacion("ENTRADA");
        movInvalido.setKilometrajeRegistro(new BigDecimal("120000.00"));

        try {
            System.out.println("Vehiculo ID: 1");
            System.out.println("Kilometraje ingresado: 120000 km");
            System.out.println("\nIntentando registrar...");
            servicio.procesarRegistroGarita(movInvalido);
            System.out.println("ERROR: El sistema permitió un kilometraje menor.");
        } catch (Exception e) {
            System.out.println("\n[VALIDACION ACTIVADA]");
            System.out.println("Tipo: " + e.getClass().getSimpleName());
            System.out.println("Mensaje: " + e.getMessage());
        }

        System.out.println("\n\n----- CASO 2: Salida con mantenimiento pendiente -----");
        MovimientoGarita movMantenimiento = new MovimientoGarita();
        movMantenimiento.setIdVehiculo(2);
        movMantenimiento.setIdUsuario(1);
        movMantenimiento.setTipoOperacion("SALIDA");
        movMantenimiento.setKilometrajeRegistro(new BigDecimal("155000.00"));

        try {
            System.out.println("Vehiculo ID: 2");
            System.out.println("Tiene orden de mantenimiento ABIERTA");
            System.out.println("\nIntentando registrar SALIDA...");
            servicio.procesarRegistroGarita(movMantenimiento);
            System.out.println("ERROR: El sistema permitió la salida.");
        } catch (Exception e) {
            System.out.println("\n[BLOQUEO PREVENTIVO]");
            System.out.println("Mensaje: " + e.getMessage());
        }

        System.out.println("\n\n----- CASO 3: Entrada normal -----");
        MovimientoGarita movNormal = new MovimientoGarita();
        movNormal.setIdVehiculo(1);
        movNormal.setIdUsuario(1);
        movNormal.setTipoOperacion("ENTRADA");
        movNormal.setKilometrajeRegistro(new BigDecimal("165000.00"));

        try {
            System.out.println("Vehiculo ID: 1");
            System.out.println("Kilometraje: 165000 km");
            System.out.println("\nIntentando registrar ENTRADA...");
            servicio.procesarRegistroGarita(movNormal);
            System.out.println("\nREGISTRO EXITOSO!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}