package com.sena.almacenamiento.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.sena.almacenamiento.dao.UsuarioDao;
import com.sena.almacenamiento.model.Usuario;

/**
 * Servlet para gestionar el login
 * RF01 - Inicio de sesión
 * Usa métodos GET y POST
 * 
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private UsuarioDao usuarioDao;
    
    @Override
    public void init() throws ServletException {
        usuarioDao = new UsuarioDao();
    }
    
    /**
     * Método GET - Muestra el formulario de login
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir a la página JSP de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    /**
     * Método POST - Procesa el formulario de login
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener parámetros del formulario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");
        
        // Validar que no estén vacíos
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty()) {
            
            request.setAttribute("error", "Por favor complete todos los campos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // Validar credenciales
        Usuario usuario = usuarioDao.validarCredenciales(nombreUsuario, contrasena);
        
        if (usuario != null) {
            // Login exitoso - Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
            session.setAttribute("tipoUsuario", usuario.getTipoUsuario());
            
            // Redirigir a página principal
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // Login fallido
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet de Login - Gestiona autenticación de usuarios";
    }
}
