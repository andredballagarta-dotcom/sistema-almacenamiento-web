package com.sena.almacenamiento.model;

import java.time.LocalDateTime;

/**
 * Modelo de la entidad EQUIPO
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 */
public class Equipo {
    private Integer idEquipo;
    private String serial;
    private String procesador;
    private String ram;
    private String ssd;
    private String tipoPantalla;
    private String marca;
    private String modelo;
    private Integer idEstado;
    private Integer idCliente;
    private LocalDateTime fechaRegistro;
    
    // Para mostrar nombre del estado
    private String nombreEstado;
    
    // Constructor vacío
    public Equipo() {
    }
    
    // Getters y Setters
    public Integer getIdEquipo() {
        return idEquipo;
    }
    
    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }
    
    public String getSerial() {
        return serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public String getProcesador() {
        return procesador;
    }
    
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }
    
    public String getRam() {
        return ram;
    }
    
    public void setRam(String ram) {
        this.ram = ram;
    }
    
    public String getSsd() {
        return ssd;
    }
    
    public void setSsd(String ssd) {
        this.ssd = ssd;
    }
    
    public String getTipoPantalla() {
        return tipoPantalla;
    }
    
    public void setTipoPantalla(String tipoPantalla) {
        this.tipoPantalla = tipoPantalla;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Integer getIdEstado() {
        return idEstado;
    }
    
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public String getNombreEstado() {
        return nombreEstado;
    }
    
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
