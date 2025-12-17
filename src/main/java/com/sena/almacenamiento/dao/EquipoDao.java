package com.sena.almacenamiento.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sena.almacenamiento.config.DatabaseConfig;
import com.sena.almacenamiento.model.Equipo;

/**
 * Data Access Object para la entidad EQUIPO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
public class EquipoDao {
    
    /**
     * Inserta un nuevo equipo
     */
    public boolean insertar(Equipo equipo) {
        // Verificar serial único
        if (existeSerial(equipo.getSerial())) {
            throw new RuntimeException("El serial ya existe en el sistema");
        }
        
        String sql = "INSERT INTO EQUIPO (serial, procesador, ram, ssd, tipo_pantalla, marca, modelo, id_estado, id_cliente, fecha_registro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, equipo.getSerial());
            ps.setString(2, equipo.getProcesador());
            ps.setString(3, equipo.getRam());
            ps.setString(4, equipo.getSsd());
            ps.setString(5, equipo.getTipoPantalla());
            ps.setString(6, equipo.getMarca());
            ps.setString(7, equipo.getModelo());
            ps.setInt(8, equipo.getIdEstado());
            ps.setInt(9, equipo.getIdCliente());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        equipo.setIdEquipo(rs.getInt(1));
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
     * Consulta todos los equipos con información del estado
     */
    public List<Equipo> consultarTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT e.*, es.nombre_estado " +
                     "FROM EQUIPO e " +
                     "INNER JOIN ESTADO_EQUIPO es ON e.id_estado = es.id_estado " +
                     "ORDER BY e.fecha_registro DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                equipos.add(mapearEquipo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipos;
    }
    
    /**
     * Consulta un equipo por su ID
     */
    public Equipo consultarPorId(Integer idEquipo) {
        String sql = "SELECT e.*, es.nombre_estado " +
                     "FROM EQUIPO e " +
                     "INNER JOIN ESTADO_EQUIPO es ON e.id_estado = es.id_estado " +
                     "WHERE e.id_equipo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEquipo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEquipo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Busca un equipo por serial
     */
    public Equipo consultarPorSerial(String serial) {
        String sql = "SELECT e.*, es.nombre_estado " +
                     "FROM EQUIPO e " +
                     "INNER JOIN ESTADO_EQUIPO es ON e.id_estado = es.id_estado " +
                     "WHERE e.serial = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, serial);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEquipo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Actualiza un equipo
     */
    public boolean actualizar(Equipo equipo) {
        String sql = "UPDATE EQUIPO SET serial = ?, procesador = ?, ram = ?, ssd = ?, " +
                     "tipo_pantalla = ?, marca = ?, modelo = ?, id_estado = ?, id_cliente = ? " +
                     "WHERE id_equipo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, equipo.getSerial());
            ps.setString(2, equipo.getProcesador());
            ps.setString(3, equipo.getRam());
            ps.setString(4, equipo.getSsd());
            ps.setString(5, equipo.getTipoPantalla());
            ps.setString(6, equipo.getMarca());
            ps.setString(7, equipo.getModelo());
            ps.setInt(8, equipo.getIdEstado());
            ps.setInt(9, equipo.getIdCliente());
            ps.setInt(10, equipo.getIdEquipo());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Cambia el estado de un equipo
     */
    public boolean cambiarEstado(Integer idEquipo, Integer idEstado) {
        String sql = "UPDATE EQUIPO SET id_estado = ? WHERE id_equipo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEstado);
            ps.setInt(2, idEquipo);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina un equipo
     */
    public boolean eliminar(Integer idEquipo) {
        String sql = "DELETE FROM EQUIPO WHERE id_equipo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEquipo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Verifica si existe un serial
     */
    private boolean existeSerial(String serial) {
        String sql = "SELECT COUNT(*) FROM EQUIPO WHERE serial = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, serial);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Mapea un ResultSet a un objeto Equipo
     */
    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(rs.getInt("id_equipo"));
        equipo.setSerial(rs.getString("serial"));
        equipo.setProcesador(rs.getString("procesador"));
        equipo.setRam(rs.getString("ram"));
        equipo.setSsd(rs.getString("ssd"));
        equipo.setTipoPantalla(rs.getString("tipo_pantalla"));
        equipo.setMarca(rs.getString("marca"));
        equipo.setModelo(rs.getString("modelo"));
        equipo.setIdEstado(rs.getInt("id_estado"));
        equipo.setIdCliente(rs.getInt("id_cliente"));
        equipo.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        
        // Nombre del estado desde el JOIN
        equipo.setNombreEstado(rs.getString("nombre_estado"));
        
        return equipo;
    }
}
