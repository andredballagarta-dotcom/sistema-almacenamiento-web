package com.sena.almacenamiento.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuración de conexión a base de datos usando JDBC
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
public class DatabaseConfig {
    
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_almacenamiento?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "app_almacen";
    private static final String PASSWORD = "Almacen2024!";
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado", e);
        }
    }
}
