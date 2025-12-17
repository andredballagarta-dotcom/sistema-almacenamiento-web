package com.sena.almacenamiento.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sena.almacenamiento.config.DatabaseConfig;
import com.sena.almacenamiento.model.Usuario;

/**
 * Data Access Object para la entidad USUARIO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
public class UsuarioDao {
    
    /**
     * Valida las credenciales de un usuario
     */
    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM USUARIO WHERE nombre_usuario = ? AND contraseña = ? AND estado = 'activo'";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Inserta un nuevo usuario
     */
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO USUARIO (nombre_usuario, contraseña, tipo_usuario, estado, fecha_creacion) " +
                     "VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getTipoUsuario());
            ps.setString(4, usuario.getEstado());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getInt(1));
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
     * Consulta todos los usuarios activos
     */
    public List<Usuario> consultarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO WHERE estado = 'activo' ORDER BY fecha_creacion DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    /**
     * Consulta un usuario por su ID
     */
    public Usuario consultarPorId(Integer idUsuario) {
        String sql = "SELECT * FROM USUARIO WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Actualiza los datos de un usuario
     */
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE USUARIO SET nombre_usuario = ?, tipo_usuario = ?, estado = ? " +
                     "WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getTipoUsuario());
            ps.setString(3, usuario.getEstado());
            ps.setInt(4, usuario.getIdUsuario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina (inactiva) un usuario
     */
    public boolean eliminar(Integer idUsuario) {
        String sql = "UPDATE USUARIO SET estado = 'inactivo' WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Mapea un ResultSet a un objeto Usuario
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setContrasena(rs.getString("contraseña"));
        usuario.setTipoUsuario(rs.getString("tipo_usuario"));
        usuario.setEstado(rs.getString("estado"));
        usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        return usuario;
    }
}
