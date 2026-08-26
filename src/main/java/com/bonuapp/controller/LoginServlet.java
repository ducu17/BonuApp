package com.bonuapp.controller;

import com.bonuapp.dao.UsuarioDAO;
import com.bonuapp.model.Usuario;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

// Define la ruta URL para el servicio de inicio de sesión
@WebServlet("/api/usuarios/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.usuarioDAO = new UsuarioDAO();
        this.gson = new Gson();
    }

    /**
     * Comentario: Este método maneja POST para verificar credenciales de inicio de sesión.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Lee las credenciales (JSON)
        BufferedReader reader = request.getReader();
        CredencialesLogin credenciales = gson.fromJson(reader, CredencialesLogin.class);

        // Valida la autenticación usando el DAO
        // Se asume que el DAO tiene un método para esto.
    Usuario usuario = usuarioDAO.obtenerPorCorreoYContrasena(credenciales.getEmail(), credenciales.getPassword());
        boolean autenticado = (usuario != null);
        // Responde según el resultado de la validación
        if (autenticado) {
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            out.print("{\"mensaje\": \"Autenticación satisfactoria\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            out.print("{\"error\": \"Error en la autenticación. Credenciales inválidas.\"}");
        }

        out.flush();
    }

    /**
     * Comentario: Clase auxiliar interna para leer el JSON de credenciales.
     */
    private static class CredencialesLogin {
        private String email;
        private String password;

        // Getters
        public String getEmail() { return email; }
        public String getPassword() { return password; }

        // Setters (Necesaarios para que GSON pueda mapear el JSON)
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }
}
