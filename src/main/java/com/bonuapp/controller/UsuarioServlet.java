package com.bonuapp.controller;

import com.bonuapp.dao.UsuarioDAO;
import com.bonuapp.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Esta anotación mapea el Servlet con la acción del formulario HTML
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    // Usamos doPost porque el formulario envía los datos por el método POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener los parámetros enviados desde el JSP
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Crear la entidad del modelo
        Usuario nuevoUsuario = new Usuario(nombre, email, password);

        // 3. Invocar la persistencia mediante el DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean guardado = usuarioDAO.registrarUsuario(nuevoUsuario);

        // 4. Decidir la respuesta según el resultado (aquí el flujo web maneja los mensajes)
        if (guardado) {
            // Guardamos un mensaje de éxito en el request
            request.setAttribute("mensaje", "¡Registro exitoso para " + nombre + "! Ya puedes iniciar sesión.");
            // Reenviamos el flujo de vuelta a la misma página o a una pantalla de bienvenida
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } else {
            request.setAttribute("mensaje", "Error crítico: No se pudo completar el registro. Intente de nuevo.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

    // Opcional: El método doGet podría usarse si alguien entra directamente a la URL /UsuarioServlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simplemente redirige al formulario de registro limpio
        response.sendRedirect("registro.jsp");
    }
}
