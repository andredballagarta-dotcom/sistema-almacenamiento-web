package com.sena.almacenamiento.model;

import java.time.LocalDateTime;

/**
 * Modelo de la entidad USUARIO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 */
public class Usuario {
    private Integer idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String tipoUsuario;
    private String estado;
    private LocalDateTime fechaCreacion;
    
    // Constructor vacío
    public Usuario() {
    }
    
    // Constructor con parámetros
    public Usuario(String nombreUsuario, String contrasena, String tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.estado = "activo";
    }
    
    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
