package com.sena.almacenamiento.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sena.almacenamiento.config.DatabaseConfig;
import com.sena.almacenamiento.model.EstadoEquipo;

/**
 * Data Access Object para la entidad ESTADO_EQUIPO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
public class EstadoEquipoDao {
    
    /**
     * Inserta un nuevo estado
     */
    public boolean insertar(EstadoEquipo estado) {
        String sql = "INSERT INTO ESTADO_EQUIPO (nombre_estado, descripcion) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, estado.getNombreEstado());
            ps.setString(2, estado.getDescripcion());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        estado.setIdEstado(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Consulta todos los estados
     */
    public List<EstadoEquipo> consultarTodos() {
        List<EstadoEquipo> estados = new ArrayList<>();
        String sql = "SELECT * FROM ESTADO_EQUIPO ORDER BY nombre_estado";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                estados.add(mapearEstado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estados;
    }
    
    /**
     * Consulta un estado por su ID
     */
    public EstadoEquipo consultarPorId(Integer idEstado) {
        String sql = "SELECT * FROM ESTADO_EQUIPO WHERE id_estado = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEstado);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEstado(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Actualiza un estado
     */
    public boolean actualizar(EstadoEquipo estado) {
        String sql = "UPDATE ESTADO_EQUIPO SET nombre_estado = ?, descripcion = ? WHERE id_estado = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estado.getNombreEstado());
            ps.setString(2, estado.getDescripcion());
            ps.setInt(3, estado.getIdEstado());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina un estado
     */
    public boolean eliminar(Integer idEstado) {
        String sql = "DELETE FROM ESTADO_EQUIPO WHERE id_estado = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEstado);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Mapea un ResultSet a un objeto EstadoEquipo
     */
    private EstadoEquipo mapearEstado(ResultSet rs) throws SQLException {
        EstadoEquipo estado = new EstadoEquipo();
        estado.setIdEstado(rs.getInt("id_estado"));
        estado.setNombreEstado(rs.getString("nombre_estado"));
        estado.setDescripcion(rs.getString("descripcion"));
        return estado;
    }
}
