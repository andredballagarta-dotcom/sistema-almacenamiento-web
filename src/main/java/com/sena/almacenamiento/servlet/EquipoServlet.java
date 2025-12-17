package com.sena.almacenamiento.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.sena.almacenamiento.dao.EquipoDao;
import com.sena.almacenamiento.dao.EstadoEquipoDao;
import com.sena.almacenamiento.model.Equipo;
import com.sena.almacenamiento.model.EstadoEquipo;

/**
 * Servlet para gestionar equipos
 * RF02 - Gestión CRUD de equipos
 * Usa métodos GET y POST
 * 
 * @author Michael Steven Ramirez Herrera - Ficha 2977494
 * Evidencia: GA7-220501096-AA2-EV02
 */
@WebServlet(name = "EquipoServlet", urlPatterns = {"/equipos"})
public class EquipoServlet extends HttpServlet {
    
    private EquipoDao equipoDao;
    private EstadoEquipoDao estadoDao;
    
    @Override
    public void init() throws ServletException {
        equipoDao = new EquipoDao();
        estadoDao = new EstadoEquipoDao();
    }
    
    /**
     * Método GET - Lista todos los equipos o muestra uno específico
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        
        if (action != null && action.equals("ver") && idParam != null) {
            // Ver detalle de un equipo
            try {
                Integer id = Integer.parseInt(idParam);
                Equipo equipo = equipoDao.consultarPorId(id);
                request.setAttribute("equipo", equipo);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID inválido");
            }
        }
        
        // Listar todos los equipos
        List<Equipo> equipos = equipoDao.consultarTodos();
        List<EstadoEquipo> estados = estadoDao.consultarTodos();
        
        request.setAttribute("equipos", equipos);
        request.setAttribute("estados", estados);
        
        // Redirigir a la página JSP
        request.getRequestDispatcher("/equipos.jsp").forward(request, response);
    }
    
    /**
     * Método POST - Crea, actualiza o elimina equipos
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
                crearEquipo(request, response);
                break;
            case "actualizar":
                actualizarEquipo(request, response);
                break;
            case "eliminar":
                eliminarEquipo(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/equipos");
        }
    }
    
    /**
     * Crear un nuevo equipo
     */
    private void crearEquipo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Obtener parámetros del formulario
            String serial = request.getParameter("serial");
            String procesador = request.getParameter("procesador");
            String ram = request.getParameter("ram");
            String ssd = request.getParameter("ssd");
            String tipoPantalla = request.getParameter("tipoPantalla");
            String marca = request.getParameter("marca");
            String modelo = request.getParameter("modelo");
            Integer idEstado = Integer.parseInt(request.getParameter("idEstado"));
            Integer idCliente = Integer.parseInt(request.getParameter("idCliente"));
            
            // Crear objeto Equipo
            Equipo equipo = new Equipo();
            equipo.setSerial(serial);
            equipo.setProcesador(procesador);
            equipo.setRam(ram);
            equipo.setSsd(ssd);
            equipo.setTipoPantalla(tipoPantalla);
            equipo.setMarca(marca);
            equipo.setModelo(modelo);
            equipo.setIdEstado(idEstado);
            equipo.setIdCliente(idCliente);
            
            // Insertar en la base de datos
            boolean resultado = equipoDao.insertar(equipo);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Equipo registrado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al registrar el equipo");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/equipos");
    }
    
    /**
     * Actualizar un equipo existente
     */
    private void actualizarEquipo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Integer idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
            String serial = request.getParameter("serial");
            String procesador = request.getParameter("procesador");
            String ram = request.getParameter("ram");
            String ssd = request.getParameter("ssd");
            String tipoPantalla = request.getParameter("tipoPantalla");
            String marca = request.getParameter("marca");
            String modelo = request.getParameter("modelo");
            Integer idEstado = Integer.parseInt(request.getParameter("idEstado"));
            Integer idCliente = Integer.parseInt(request.getParameter("idCliente"));
            
            Equipo equipo = new Equipo();
            equipo.setIdEquipo(idEquipo);
            equipo.setSerial(serial);
            equipo.setProcesador(procesador);
            equipo.setRam(ram);
            equipo.setSsd(ssd);
            equipo.setTipoPantalla(tipoPantalla);
            equipo.setMarca(marca);
            equipo.setModelo(modelo);
            equipo.setIdEstado(idEstado);
            equipo.setIdCliente(idCliente);
            
            boolean resultado = equipoDao.actualizar(equipo);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Equipo actualizado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al actualizar el equipo");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/equipos");
    }
    
    /**
     * Eliminar un equipo
     */
    private void eliminarEquipo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Integer idEquipo = Integer.parseInt(request.getParameter("id"));
            boolean resultado = equipoDao.eliminar(idEquipo);
            
            if (resultado) {
                request.getSession().setAttribute("mensaje", "Equipo eliminado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al eliminar el equipo");
            }
            
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/equipos");
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet de Equipos - Gestiona CRUD de equipos";
    }
}
