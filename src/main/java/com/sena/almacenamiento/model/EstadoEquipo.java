package com.sena.almacenamiento.model;

/**
 * Modelo de la entidad ESTADO_EQUIPO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 */
public class EstadoEquipo {
    private Integer idEstado;
    private String nombreEstado;
    private String descripcion;
    
    // Constructor vacío
    public EstadoEquipo() {
    }
    
    // Constructor con parámetros
    public EstadoEquipo(String nombreEstado, String descripcion) {
        this.nombreEstado = nombreEstado;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Integer getIdEstado() {
        return idEstado;
    }
    
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    public String getNombreEstado() {
        return nombreEstado;
    }
    
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
