package com.bonuapp.controller;

import com.bonuapp.dao.UsuarioDAO;
import com.bonuapp.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar cabeceras CORS y JSON
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Leer el cuerpo JSON enviado desde React
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String linea;
        while ((linea = reader.readLine()) != null) {
            sb.append(linea);
        }
        String jsonBuffer = sb.toString();

        // Extraer valores del JSON
        String nombre = extraerValorJson(jsonBuffer, "nombre");
        String email = extraerValorJson(jsonBuffer, "email");
        String password = extraerValorJson(jsonBuffer, "password");

        // Crear objeto Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(email);
        nuevoUsuario.setContrasena(password);
        nuevoUsuario.setSaldoPuntos(0);
        nuevoUsuario.setRol("cliente");

        // Guardar en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean guardado = usuarioDAO.insertarUsuario(nuevoUsuario);

        // Responder a React
        PrintWriter out = response.getWriter();
        if (guardado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"status\": \"ok\", \"mensaje\": \"Usuario registrado con exito\"}");
        } else {
            out.print("{\"status\": \"ok\", \"mensaje\": \"Usuario registrado con exito\"}");
            out.print("{\"status\": \"error\", \"mensaje\": \"No se pudo guardar el usuario\"}");
        }
        out.print("{\"status\": \"error\", \"mensaje\": \"No se pudo guardar el usuario\"}");
    }

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