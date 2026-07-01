package com.bonuapp.dao;

import com.bonuapp.model.Premio;
import com.bonuapp.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremioDAO {

    // 1. INSERTAR (Crear)
    public boolean insertarPremio(Premio premio) {
        // Ajustado a la tabla 'premio' y columnas 'descripcion', 'valor_puntos', 'stock_disponible'
        String sql = "INSERT INTO premio (descripcion, valor_puntos, stock_disponible) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, premio.getDescripcion());
            stmt.setInt(2, premio.getPuntosRequeridos());
            stmt.setInt(3, 10); // Valor por defecto para stock

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar premio: " + e.getMessage());
            return false;
        }
    }

    // 2. OBTENER PREMIOS (Consulta)
    public List<Premio> obtenerPremios() {
        List<Premio> lista = new ArrayList<>();
        String sql = "SELECT * FROM premio";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Premio p = new Premio();
                // Mapeamos id_bono al ID de tu modelo
                p.setIdPremio(rs.getInt("id_bono"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNombre(rs.getString("descripcion")); // Usamos descripcion como nombre temporalmente si tu modelo lo requiere
                p.setPuntosRequeridos(rs.getInt("valor_puntos"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener premios: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZAR
    public boolean actualizarPremio(Premio premio) {
        String sql = "UPDATE premio SET descripcion = ?, valor_puntos = ? WHERE id_bono = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, premio.getDescripcion());
            stmt.setInt(2, premio.getPuntosRequeridos());
            stmt.setInt(3, premio.getIdPremio());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar premio: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR
    public boolean eliminarPremio(int idPremio) {
        String sql = "DELETE FROM premio WHERE id_bono = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPremio);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar premio: " + e.getMessage());
            return false;
        }
    }

    // 5. CANJEAR PREMIO CONTROLANDO EL STOCK
    public String canjearPremio(int idUsuario, int idPremio) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psRestarPuntos = null;
        PreparedStatement psRestarStock = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Ajustado a: premio p, id_bono, valor_puntos, stock_disponible
            String sqlCheck = "SELECT p.valor_puntos, p.stock_disponible, u.puntos " +
                    "FROM premio p, usuario u " +
                    "WHERE p.id_bono = ? AND u.id_usuario = ?";

            psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idPremio);
            psCheck.setInt(2, idUsuario);
            rs = psCheck.executeQuery();

            if (rs.next()) {
                int costo = rs.getInt("valor_puntos");
                int stock = rs.getInt("stock_disponible");
                int puntosUsuario = rs.getInt("puntos");

                if (stock <= 0) {
                    conn.rollback();
                    return "AGOTADO";
                }

                if (puntosUsuario < costo) {
                    conn.rollback();
                    return "PUNTOS_INSUFICIENTES";
                }

                String sqlRestarPuntos = "UPDATE usuario SET puntos = puntos - ? WHERE id_usuario = ?";
                psRestarPuntos = conn.prepareStatement(sqlRestarPuntos);
                psRestarPuntos.setInt(1, costo);
                psRestarPuntos.setInt(2, idUsuario);
                psRestarPuntos.executeUpdate();

                // Cambiado a tabla 'premio' e 'id_bono'
                String sqlRestarStock = "UPDATE premio SET stock_disponible = stock_disponible - 1 WHERE id_bono = ?";
                psRestarStock = conn.prepareStatement(sqlRestarStock);
                psRestarStock.setInt(1, idPremio);
                psRestarStock.executeUpdate();

                conn.commit();
                return "EXITO";
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Error al procesar el canje de premio: " + e.getMessage());
            return "ERROR_DATABASE";
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheck != null) psCheck.close();
                if (psRestarPuntos != null) psRestarPuntos.close();
                if (psRestarStock != null) psRestarStock.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "ERROR_DESCONOCIDO";
    }
}

