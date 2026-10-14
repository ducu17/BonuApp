package com.bonuapp.controller;

import com.bonuapp.dao.PremioDAO;
import com.bonuapp.model.Premio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/premios/*")
public class PremioRestController extends HttpServlet {
    private PremioDAO premioDAO = new PremioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Premio> lista = premioDAO.listarTodos();

        StringBuilder json = new StringBuilder("[");
        if (lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                Premio p = lista.get(i);
                json.append("{");
                json.append("\"id\":").append(p.getId()).append(",");
                json.append("\"nombre\":\"").append(p.getNombre()).append("\",");
                json.append("\"puntos\":").append(p.getPuntos());
                json.append("}");
                if (i < lista.size() - 1) {
                    json.append(",");
                }
            }
        }
        json.append("]");

        resp.getWriter().write(json.toString());
    }
}