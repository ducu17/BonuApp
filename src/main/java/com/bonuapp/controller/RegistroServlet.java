package com.bonuapp.controller;

import com.bonuapp.dao.UsuarioDAO;
import com.bonuapp.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
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

        // Leer y parsear el JSON recibido usando Gson
        com.google.gson.JsonObject jsonObj = com.google.gson.JsonParser.parseReader(request.getReader()).getAsJsonObject();

        String nombre = jsonObj.has("nombre") ? jsonObj.get("nombre").getAsString() : "";
        String email = jsonObj.has("email") ? jsonObj.get("email").getAsString() : "";
        String password = jsonObj.has("password") ? jsonObj.get("password").getAsString() : "";

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
    }// Método auxiliar para extraer valores de un JSON plano
    private String extraerValorJson(String json, String propiedad) {
        try {
            String searchKey = "\"" + propiedad + "\":\"";
            int start = json.indexOf(searchKey);
            if (start == -1) return "";
            start += searchKey.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }
}