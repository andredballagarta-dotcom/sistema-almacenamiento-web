<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sena.almacenamiento.model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio - Sistema de Almacenamiento</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <%
        // Verificar si hay sesión activa (Scriptlet JSP)
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    %>
    
    <div class="container">
        <header>
            <h1>Sistema de Gestión de Almacenamiento</h1>
            <div class="user-info">
                <!-- Expresión JSP -->
                <span>Bienvenido: <strong><%= usuario.getNombreUsuario() %></strong></span>
                <span>Tipo: <%= usuario.getTipoUsuario() %></span>
            </div>
        </header>
        
        <nav class="main-nav">
            <a href="<%= request.getContextPath() %>/equipos" class="nav-card">
                <h3>📦 Gestión de Equipos</h3>
                <p>Registrar, consultar y actualizar equipos</p>
            </a>
            
            <a href="<%= request.getContextPath() %>/estados" class="nav-card">
                <h3>📊 Gestión de Estados</h3>
                <p>Administrar estados de equipos</p>
            </a>
            
            <a href="<%= request.getContextPath() %>/login" class="nav-card logout">
                <h3>🚪 Cerrar Sesión</h3>
                <p>Salir del sistema</p>
            </a>
        </nav>
        
        <footer>
            <p>Evidencia: GA7-220501096-AA2-EV02 - Servlets y JSP</p>
            <p>Michael Steven Ramirez Herrera - Ficha 2977494 - SENA 2024</p>
        </footer>
    </div>
</body>
</html>
