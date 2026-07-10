/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author luis.azanero
 */
package pe.edu.utp.transvisa.persistence;

import java.util.List;
import pe.edu.utp.transvisa.domain.OrdenMantenimiento;

public interface OrdenMantenimientoRepository {

    List<OrdenMantenimiento> listarTodas() throws Exception;

    void registrar(OrdenMantenimiento orden) throws Exception;

    void cerrarOrden(int idOrden) throws Exception;
}
