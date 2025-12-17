<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sena.almacenamiento.model.EstadoEquipo" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Estados</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>📊 Gestión de Estados</h1>
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
        
        <!-- FORMULARIO HTML para CREAR estado (método POST) -->
        <div class="form-section">
            <h2>Crear Nuevo Estado</h2>
            <form action="<%= request.getContextPath() %>/estados" method="POST">
                <input type="hidden" name="action" value="crear">
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nombreEstado">Nombre del Estado *</label>
                        <input type="text" id="nombreEstado" name="nombreEstado" 
                               placeholder="Ej: Disponible, En Reparación" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="descripcion">Descripción</label>
                        <input type="text" id="descripcion" name="descripcion" 
                               placeholder="Descripción del estado">
                    </div>
                </div>
                
                <button type="submit" class="btn btn-primary">Crear Estado</button>
            </form>
        </div>
        
        <!-- TABLA de estados (datos obtenidos con método GET) -->
        <div class="table-section">
            <h2>Listado de Estados</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<EstadoEquipo> estados = (List<EstadoEquipo>) request.getAttribute("estados");
                        if (estados != null && !estados.isEmpty()) {
                            for (EstadoEquipo estado : estados) {
                    %>
                    <tr>
                        <td><%= estado.getIdEstado() %></td>
                        <td><strong><%= estado.getNombreEstado() %></strong></td>
                        <td><%= estado.getDescripcion() != null ? estado.getDescripcion() : "-" %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/estados" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="<%= estado.getIdEstado() %>">
                                <button type="submit" class="btn btn-danger btn-sm" 
                                        onclick="return confirm('¿Está seguro de eliminar este estado?')">
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
                        <td colspan="4" style="text-align:center;">No hay estados registrados</td>
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
