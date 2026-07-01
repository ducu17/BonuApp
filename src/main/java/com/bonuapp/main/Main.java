package com.bonuapp.main;

import com.bonuapp.dao.PremioDAO;
import com.bonuapp.model.Premio;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PremioDAO dao = new PremioDAO();

        System.out.println("--- Probando Inserción ---");
        Premio nuevoPremio = new Premio(0, "Gorra BonuApp", "Gorra oficial con logo bordado", 150);
        if (dao.insertarPremio(nuevoPremio)) {
            System.out.println("¡Premio insertado con éxito!");
        }

        System.out.println("\n--- Probando Consulta ---");
        List<Premio> premios = dao.obtenerPremios();
        for (Premio p : premios) {
            System.out.println(p.getIdPremio() + " - " + p.getNombre() + " (" + p.getPuntosRequeridos() + " pts)");
        }
        // --- PRUEBA DEL NUEVO MÉTODO DE CANJE CON STOCK ---
        System.out.println("\n--- Probando Canje de Premio con Control de Stock ---");

        int idUsuarioSimulado = 1;
        int idPremioSimulado = 1;

        String resultadoCanje = dao.canjearPremio(idUsuarioSimulado, idPremioSimulado);

        System.out.println("Resultado del canje: " + resultadoCanje);
        System.out.println("-----------------------------------------------------");
    }
}
