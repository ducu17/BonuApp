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

@WebServlet("/api/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Recepción de parámetros desde el cliente
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación de campos obligatorios
        if (nombre == null || email == null || password == null ||
                nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\": \"error\", \"mensaje\": \"Campos obligatorios vacíos\"}");
            return;
        }

        // Creación del objeto modelo Usuario usando su constructor
        Usuario nuevoUsuario = new Usuario(nombre, email, password, 0, "cliente");

        // Intento de inserción en la base de datos
        boolean registrado = usuarioDAO.insertarUsuario(nuevoUsuario);

        // Envío de respuesta según el resultado
        if (registrado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"status\": \"exito\", \"mensaje\": \"Usuario registrado correctamente\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\": \"error\", \"mensaje\": \"Error al registrar usuario\"}");
        }
    }
}