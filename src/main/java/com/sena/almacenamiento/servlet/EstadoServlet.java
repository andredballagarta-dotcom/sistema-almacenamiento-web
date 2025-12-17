package com.sena.almacenamiento.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.sena.almacenamiento.dao.EstadoEquipoDao;
import com.sena.almacenamiento.model.EstadoEquipo;

/**
 * Servlet para gestionar estados de equipos
 * RF05 - Gestión de estados
 * Usa métodos GET y POST
 * 
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
@WebServlet(name = "EstadoServlet", urlPatterns = {"/estados"})
public class EstadoServlet extends HttpServlet {
    
    private EstadoEquipoDao estadoDao;
    
    @Override
    public void init() throws ServletException {
        estadoDao = new EstadoEquipoDao();
    }
    
    /**
     * Método GET - Lista todos los estados
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Listar todos los estados
        List<EstadoEquipo> estados = estadoDao.consultarTodos();
        request.setAttribute("estados", estados);
        
        // Redirigir a la página JSP
        request.getRequestDispatcher("/estados.jsp").forward(request, response);
    }
    
    /**
     * Método POST - Crea, actualiza o elimina estados
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "crear";
        }
        
        switch (action) {
            case "crear":
                crearEstado(request, response);
                break;
            case "actualizar":
                actualizarEstado(request, response);
                break;
            case "eliminar":
                eliminarEstado(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/estados");
        }
    }
    
    /**
     * Crear un nuevo estado
     */
    private void crearEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String nombreEstado = request.getParameter("nombreEstado");
            String descripcion = request.getParameter("descripcion");
            
            EstadoEquipo estado = new EstadoEquipo();
            estado.setNombreEstado(nombreEstado);
            estado.setDescripcion(descripcion);
            
            boolean resultado = estadoDao.insertar(estado);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Estado creado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al crear el estado");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/estados");
    }
    
    /**
     * Actualizar un estado existente
     */
    private void actualizarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Integer idEstado = Integer.parseInt(request.getParameter("idEstado"));
            String nombreEstado = request.getParameter("nombreEstado");
            String descripcion = request.getParameter("descripcion");
            
            EstadoEquipo estado = new EstadoEquipo();
            estado.setIdEstado(idEstado);
            estado.setNombreEstado(nombreEstado);
            estado.setDescripcion(descripcion);
            
            boolean resultado = estadoDao.actualizar(estado);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Estado actualizado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al actualizar el estado");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/estados");
    }
    
    /**
     * Eliminar un estado
     */
    private void eliminarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Integer idEstado = Integer.parseInt(request.getParameter("id"));
            boolean resultado = estadoDao.eliminar(idEstado);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Estado eliminado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al eliminar el estado");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/estados");
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet de Estados - Gestiona CRUD de estados de equipos";
    }
}
