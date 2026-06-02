package pe.edu.utp.transvisa.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MovimientoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceTest {
    
    @Mock private MovimientoRepository movimientoRepository;
    @Mock private VehiculoRepository vehiculoRepository;
    private MovimientoService service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new MovimientoService(movimientoRepository, vehiculoRepository);
    }
    
    @Test
    void debeRechazarKilometrajeMenor() {
        MovimientoGarita mov = new MovimientoGarita();
        mov.setIdVehiculo(1);
        mov.setTipoOperacion("ENTRADA");
        mov.setKilometrajeRegistro(BigDecimal.valueOf(500));
        
        when(movimientoRepository.obtenerUltimoKilometraje(1)).thenReturn(BigDecimal.valueOf(1000));
        
        assertThrows(IllegalArgumentException.class, () -> service.procesarRegistroGarita(mov));
    }
    
    @Test
    void debeBloquearSalidaConMantenimiento() {
        MovimientoGarita mov = new MovimientoGarita();
        mov.setIdVehiculo(2);
        mov.setTipoOperacion("SALIDA");
        mov.setKilometrajeRegistro(BigDecimal.valueOf(2000));
        
        when(movimientoRepository.obtenerUltimoKilometraje(2)).thenReturn(BigDecimal.valueOf(1500));
        when(movimientoRepository.tieneMantenimientoAbierto(2)).thenReturn(true);
        
        assertThrows(Exception.class, () -> service.procesarRegistroGarita(mov));
    }
}