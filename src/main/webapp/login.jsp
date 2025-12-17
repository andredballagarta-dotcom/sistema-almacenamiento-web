<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Sistema de Almacenamiento</title>
	<link rel="stylesheet" href="/sistema-almacenamiento-web/css/styles.css">
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <h1>Sistema de Almacenamiento</h1>
            <h2>Iniciar Sesión</h2>
            
            <!-- Mostrar mensajes de error usando expresión JSP -->
            <% 
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-error">
                    <%= error %>
                </div>
            <% } %>
            
            <!-- FORMULARIO HTML con método POST -->
            <form action="<%= request.getContextPath() %>/login" method="POST">
                <div class="form-group">
                    <label for="nombreUsuario">Usuario:</label>
                    <input type="text" 
                           id="nombreUsuario" 
                           name="nombreUsuario" 
                           placeholder="Ingrese su usuario"
                           required>
                </div>
                
                <div class="form-group">
                    <label for="contrasena">Contraseña:</label>
                    <input type="password" 
                           id="contrasena" 
                           name="contrasena" 
                           placeholder="Ingrese su contraseña"
                           required>
                </div>
                
                <button type="submit" class="btn btn-primary">Ingresar</button>
            </form>
            
            <div class="footer-info">
                <p>Evidencia: GA7-220501096-AA2-EV02</p>
                <p>Michael Steven Ramirez Herrera - Ficha 2977494</p>
            </div>
        </div>
    </div>
</body>
</html>
