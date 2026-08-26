package com.bonuapp.controller;

import com.bonuapp.dao.UsuarioDAO;
import com.bonuapp.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servicio Web (Servlet) encargado del registro de nuevos usuarios en BonuApp.
 * Ruta del endpoint: /api/registro
 */
@WebServlet("/api/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instancia del DAO para interactuar con la base de datos
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Método POST para recibir y procesar los datos del nuevo usuario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configuración del tipo de respuesta JSON y codificación de caracteres
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Recepción de parámetros desde el cliente (Frontend/Postman)
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación de campos obligatorios vacíos
        if (nombre == null || email == null || password == null ||
                nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Código 400
            out.print("{\"status\": \"error\", \"mensaje\": \"Error en el registro: Todos los campos son obligatorios\"}");
            return;
        }

        // Creación del objeto modelo Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(email);
        nuevoUsuario.setContrasena(password);

        // Intento de inserción en la base de datos
        boolean registrado = usuarioDAO.insertarUsuario(nuevoUsuario);

        // Envío de respuesta según el resultado
        if (registrado) {
            response.setStatus(HttpServletResponse.SC_CREATED); // Código 201
            out.print("{\"status\": \"exito\", \"mensaje\": \"Autenticación satisfactoria - Usuario registrado en BonuApp\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Código 500
            out.print("{\"status\": \"error\", \"mensaje\": \"Error en la autenticación / registro de usuario\"}");
        }
    }
}
