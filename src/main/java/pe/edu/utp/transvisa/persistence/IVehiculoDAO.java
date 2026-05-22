/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import pe.edu.utp.transvisa.domain.Vehiculo;

/**
 *
 * @author luisazanero
 */
public interface IVehiculoDAO {

    void guardar(Vehiculo vehiculo);

    Vehiculo buscarPorPlaca(String placa);
}
