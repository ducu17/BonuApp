package com.bonuapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(urlPatterns = {"/BonoServlet", "/api/bonos/generar", "/api/generar-bono"})
public class BonoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String body = sb.toString();
        String tienda = extraerValorJson(body, "tienda");
        if (tienda.isEmpty()) {
            String paramTienda = req.getParameter("tienda");
            tienda = (paramTienda != null && !paramTienda.isBlank()) ? paramTienda.trim() : "Tienda Aliada";
        }

        String valorRaw = extraerValorJson(body, "valor");
        int valor = 0;
        try {
            if (!valorRaw.isEmpty()) {
                valor = Integer.parseInt(valorRaw.replaceAll("[^0-9]", ""));
            } else if (req.getParameter("valor") != null) {
                valor = Integer.parseInt(req.getParameter("valor").replaceAll("[^0-9]", ""));
            }
        } catch (NumberFormatException ignored) {
            valor = 0;
        }

        int numeroAleatorio = ThreadLocalRandom.current().nextInt(100000, 999999);
        String codigoBono = "BONU-" + numeroAleatorio;

        resp.setStatus(HttpServletResponse.SC_OK);
        String jsonRespuesta = String.format(
                "{\"status\":\"exito\",\"codigo\":\"%s\",\"tienda\":\"%s\",\"valor\":%d}",
                escaparJson(codigoBono),
                escaparJson(tienda),
                valor
        );

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonRespuesta);
            out.flush();
        }
    }

    private String extraerValorJson(String json, String clave) {
        if (json == null || json.isEmpty()) {
            return "";
        }
        String patron = "\"" + clave + "\"";
        int idxClave = json.indexOf(patron);
        if (idxClave == -1) {
            return "";
        }
        int idxDosPuntos = json.indexOf(":", idxClave + patron.length());
        if (idxDosPuntos == -1) {
            return "";
        }
        String resto = json.substring(idxDosPuntos + 1).trim();
        if (resto.startsWith("\"")) {
            int finComilla = resto.indexOf("\"", 1);
            return finComilla > 1 ? resto.substring(1, finComilla).trim() : "";
        }
        int finToken = 0;
        while (finToken < resto.length()
                && resto.charAt(finToken) != ','
                && resto.charAt(finToken) != '}') {
            finToken++;
        }
        return resto.substring(0, finToken).trim();
    }

    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
