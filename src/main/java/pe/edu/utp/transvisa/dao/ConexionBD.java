package pe.edu.utp.transvisa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = "jdbc:sqlserver://localhost;databaseName=transvisa_db;user=transvisa_user;password=1234;encrypt=false;";
    
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println(" Conexion exitosa a SQL Server");
            System.out.println("   Usuario: transvisa_user");
            return conn;
        } catch (SQLException e) {
            System.err.println(" Error de conexion: " + e.getMessage());
            throw e;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== PROBANDO CONEXION ===\n");
        try {
            Connection conn = getConnection();
            System.out.println("\n ¡Conexion exitosa!");
            System.out.println("Base de datos: " + conn.getCatalog());
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}