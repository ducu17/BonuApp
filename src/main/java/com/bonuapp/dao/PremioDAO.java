package com.bonuapp.dao;

import com.bonuapp.model.Premio;
import com.bonuapp.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremioDAO {

    // 1. INSERTAR (Crear)
    public boolean insertarPremio(Premio premio) {
        String sql = "INSERT INTO premios (nombre, descripcion, puntos_requeridos) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, premio.getNombre());
            stmt.setString(2, premio.getDescripcion());
            stmt.setInt(3, premio.getPuntosRequeridos());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar premio: " + e.getMessage());
            return false;
        }
    }

    // 2. CONSULTAR TODOS (Leer)
    public List<Premio> obtenerPremios() {
        List<Premio> listaPremios = new ArrayList<>();
        String sql = "SELECT * FROM premios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Premio premio = new Premio(
                        rs.getInt("id_premio"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("puntos_requeridos")
                );
                listaPremios.add(premio);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar premios: " + e.getMessage());
        }
        return listaPremios;
    }

    // 3. ACTUALIZAR
    public boolean actualizarPremio(Premio premio) {
        String sql = "UPDATE premios SET nombre = ?, descripcion = ?, puntos_requeridos = ? WHERE id_premio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, premio.getNombre());
            stmt.setString(2, premio.getDescripcion());
            stmt.setInt(3, premio.getPuntosRequeridos());
            stmt.setInt(4, premio.getIdPremio());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar premio: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR
    public boolean eliminarPremio(int idPremio) {
        String sql = "DELETE FROM premios WHERE id_premio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPremio);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar premio: " + e.getMessage());
            return false;
        }
    }
}
