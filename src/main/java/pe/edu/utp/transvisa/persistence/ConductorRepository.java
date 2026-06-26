/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author luis.azanero
 */
package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Conductor;
import java.util.List;
import java.sql.SQLException;

public interface ConductorRepository {

    void registrar(Conductor conductor) throws SQLException;

    void actualizar(Conductor conductor) throws SQLException;

    List<Conductor> listarTodos() throws SQLException;

    boolean existeDni(String dni) throws SQLException;

    void eliminar(int id) throws SQLException;
}
