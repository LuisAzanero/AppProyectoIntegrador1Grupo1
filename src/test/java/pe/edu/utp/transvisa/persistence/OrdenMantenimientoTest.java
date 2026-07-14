/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.utp.transvisa.domain.OrdenMantenimiento;
import java.util.List;

/**
 *
 * @author luis.azanero
 */
public class OrdenMantenimientoTest {

    private OrdenMantenimientoRepository repository;

    @BeforeEach
    public void setUp() {
        // Inicializa el repositorio concreto conectado a la BD de pruebas
        this.repository = new MySQLOrdenMantenimientoRepository();
    }

    @Test
    @DisplayName("CP-01: Validar que la consulta de órdenes no retorne una lista nula")
    public void testListarTodasNotNull() throws Exception {
        List<OrdenMantenimiento> resultado = repository.listarTodas();
        assertNotNull(resultado, "El repositorio no debe retornar nulo, incluso si la tabla está vacía");
    }

    @Test
    @DisplayName("CP-02: Validar inserción correcta de una nueva Orden de Trabajo")
    public void testRegistrarOrden() {
        OrdenMantenimiento nuevaOrden = new OrdenMantenimiento();
        nuevaOrden.setIdVehiculo(1); // ID existente en base de datos de prueba
        nuevaOrden.setIdUsuario(6);   // ID de usuario existente
        nuevaOrden.setTipoMantenimiento("PREVENTIVO");
        nuevaOrden.setDescripcion("Prueba unitaria automatizada JUnit - Cambio de pastillas");

        assertDoesNotThrow(() -> {
            repository.registrar(nuevaOrden);
        }, "La inserción de la orden falló en la capa de persistencia MySQL");
    }
}
