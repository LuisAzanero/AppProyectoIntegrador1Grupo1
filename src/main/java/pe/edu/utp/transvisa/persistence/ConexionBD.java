/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.transvisa.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luisazanero
 */
public class ConexionBD {

    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class);

    // Conexion a la base de datos
    private static final String URL = "jdbc:mysql://bwtsfa0gnbbwz5w5uuf5-mysql.services.clever-cloud.com:3306/bwtsfa0gnbbwz5w5uuf5?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true";
    private static final String USER = "uqcyv98gxvvzxgqf";
    private static final String PASSWORD = "lr3UmmAursn6YKcpIbRB";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("Error: No se encontró el driver de MySQL en las dependencias.");
            throw new SQLException("Driver MySQL no disponible", e);
        } catch (SQLException e) {
            logger.error("Error crítico al conectar a MySQL en la URL: {}", URL);
            throw e;
        }
    }
}
