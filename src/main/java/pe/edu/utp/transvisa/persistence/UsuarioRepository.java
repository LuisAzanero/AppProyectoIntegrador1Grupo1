/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import java.sql.SQLException;
import pe.edu.utp.transvisa.domain.Usuario;
import java.sql.SQLException;
/**
 *
 * @author luisazanero
 */
public interface UsuarioRepository {

    // Busca un usuario en la base de datos por su credencial única (username)
    Usuario buscarPorUsername(String username) throws SQLException;
}
