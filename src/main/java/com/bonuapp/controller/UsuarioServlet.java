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

/**
 * Servlet encargado de procesar peticiones JSON desde el Front-End (React)
 */
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Configurar cabeceras CORS para permitir peticiones desde React
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 2. Leer la cadena JSON enviada por React
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonBody = sb.toString(); // Cadena que contiene los datos del formulario

        // 3. Extracción de datos del JSON recibido
        // Nota: Extraemos los valores parseando la cadena enviada por React
        String nombre = extraerValorJson(jsonBody, "nombre");
        String email = extraerValorJson(jsonBody, "email");
        String password = extraerValorJson(jsonBody, "password");

        // 4. Instanciar la entidad Usuario con los datos recibidos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(email);
        nuevoUsuario.setContrasena(password);
        nuevoUsuario.setSaldoPuntos(0);
        nuevoUsuario.setRol("cliente");

        // 5. Guardar en la base de datos usando UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean guardado = usuarioDAO.guardar(nuevoUsuario);

        // 6. Responder a React en formato JSON
        PrintWriter out = response.getWriter();
        if (guardado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"status\": \"ok\", \"mensaje\": \"Usuario registrado con exito\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\": \"error\", \"mensaje\": \"No se pudo guardar el usuario\"}");
        }
        out.flush();
    }

    // Método auxiliar para obtener el valor de una clave JSON
    private String extraerValorJson(String json, String clave) {
        try {
            String patron = "\"" + clave + "\":\"";
            int inicio = json.indexOf(patron);
            if (inicio == -1) return "";
            inicio += patron.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } catch (Exception e) {
            return "";
        }
    }
}
