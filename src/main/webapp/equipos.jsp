<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sena.almacenamiento.model.Equipo" %>
<%@ page import="com.sena.almacenamiento.model.EstadoEquipo" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Equipos</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>📦 Gestión de Equipos</h1>
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn-back">← Volver al Inicio</a>
        </header>
        
        <!-- Mostrar mensajes -->
        <%
            String mensaje = (String) session.getAttribute("mensaje");
            String error = (String) session.getAttribute("error");
            
            if (mensaje != null) {
        %>
            <div class="alert alert-success"><%= mensaje %></div>
        <%
                session.removeAttribute("mensaje");
            }
            
            if (error != null) {
        %>
            <div class="alert alert-error"><%= error %></div>
        <%
                session.removeAttribute("error");
            }
        %>
        
        <!-- FORMULARIO HTML para CREAR equipo (método POST) -->
        <div class="form-section">
            <h2>Registrar Nuevo Equipo</h2>
            <form action="<%= request.getContextPath() %>/equipos" method="POST">
                <input type="hidden" name="action" value="crear">
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="serial">Serial *</label>
                        <input type="text" id="serial" name="serial" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="marca">Marca *</label>
                        <input type="text" id="marca" name="marca" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="modelo">Modelo *</label>
                        <input type="text" id="modelo" name="modelo" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="procesador">Procesador *</label>
                        <input type="text" id="procesador" name="procesador" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="ram">RAM *</label>
                        <input type="text" id="ram" name="ram" placeholder="Ej: 8GB DDR4" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="ssd">SSD/Disco *</label>
                        <input type="text" id="ssd" name="ssd" placeholder="Ej: 256GB NVMe" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="tipoPantalla">Tipo de Pantalla *</label>
                        <input type="text" id="tipoPantalla" name="tipoPantalla" placeholder="Ej: 14 pulgadas Full HD" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="idEstado">Estado *</label>
                        <select id="idEstado" name="idEstado" required>
                            <option value="">Seleccione estado...</option>
                            <%
                                List<EstadoEquipo> estados = (List<EstadoEquipo>) request.getAttribute("estados");
                                if (estados != null) {
                                    for (EstadoEquipo estado : estados) {
                            %>
                                <option value="<%= estado.getIdEstado() %>">
                                    <%= estado.getNombreEstado() %>
                                </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="idCliente">ID Cliente *</label>
                        <input type="number" id="idCliente" name="idCliente" value="1" required>
                    </div>
                </div>
                
                <button type="submit" class="btn btn-primary">Registrar Equipo</button>
            </form>
        </div>
        
        <!-- TABLA de equipos (datos obtenidos con método GET) -->
        <div class="table-section">
            <h2>Listado de Equipos</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Serial</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Procesador</th>
                        <th>RAM</th>
                        <th>SSD</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Equipo> equipos = (List<Equipo>) request.getAttribute("equipos");
                        if (equipos != null && !equipos.isEmpty()) {
                            for (Equipo equipo : equipos) {
                    %>
                    <tr>
                        <td><%= equipo.getIdEquipo() %></td>
                        <td><%= equipo.getSerial() %></td>
                        <td><%= equipo.getMarca() %></td>
                        <td><%= equipo.getModelo() %></td>
                        <td><%= equipo.getProcesador() %></td>
                        <td><%= equipo.getRam() %></td>
                        <td><%= equipo.getSsd() %></td>
                        <td><span class="badge"><%= equipo.getNombreEstado() %></span></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/equipos" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="<%= equipo.getIdEquipo() %>">
                                <button type="submit" class="btn btn-danger btn-sm" 
                                        onclick="return confirm('¿Está seguro de eliminar este equipo?')">
                                    Eliminar
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="9" style="text-align:center;">No hay equipos registrados</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
