package com.bonuapp.dao;

import com.bonuapp.model.Premio;
import com.bonuapp.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremioDAO {

    public List<Premio> listarTodos() {
        List<Premio> lista = new ArrayList<>();
        String sql = "SELECT * FROM premios"; // Ajusta el nombre de tu tabla si es diferente
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Premio p = new Premio();
                p.setId(rs.getInt("id")); // Ajusta según el nombre de tus columnas en MySQL
                p.setNombre(rs.getString("nombre_premio"));
                p.setPuntos(rs.getInt("costo_puntos"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar premios: " + e.getMessage());
        }
        return lista;
    }

    // 1. INSERTAR (Crear) - Adaptado a tabla 'premios'
    public boolean insertarPremio(Premio premio) {
        String sql = "INSERT INTO premios (nombre_premio, descripcion, costo_puntos, stock, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Si tu clase Premio aún no tiene nombre_premio, usamos su descripción o el campo que use como título
            stmt.setString(1, premio.getNombre() != null ? premio.getNombre() : "Premio Nuevo");
            stmt.setString(2, premio.getDescripcion());
            stmt.setInt(3, premio.getPuntosRequeridos());
            stmt.setInt(4, 10); // Valor por defecto para stock inicial
            stmt.setString(5, "activo");

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar premio: " + e.getMessage());
            return false;
        }
    }

    // 2. OBTENER PREMIOS (Consulta) - Adaptado a tabla 'premios' y columnas reales
    public List<Premio> obtenerPremios() {
        List<Premio> lista = new ArrayList<>();
        String sql = "SELECT * FROM premios WHERE estado = 'activo'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Premio p = new Premio();
                p.setIdPremio(rs.getInt("id_premio"));
                p.setNombre(rs.getString("nombre_premio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPuntosRequeridos(rs.getInt("costo_puntos"));
                // Si en tu modelo 'Premio' agregas la variable stock, puedes mapearla aquí:
                // p.setStock(rs.getInt("stock"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener premios: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZAR - Adaptado a tabla 'premios'
    public boolean actualizarPremio(Premio premio) {
        String sql = "UPDATE premios SET nombre_premio = ?, descripcion = ?, costo_puntos = ? WHERE id_premio = ?";
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

    // 4. ELIMINAR (Lógico o Físico) - Adaptado a tabla 'premios'
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

    // 5. CANJEAR PREMIO CONTROLANDO EL STOCK (Transaccional)
    // Se mapearon correctamente las tablas 'premios', 'usuarios' y los saldos reales
    public String canjearPremio(int idUsuario, int idPremio) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psRestarPuntos = null;
        PreparedStatement psRestarStock = null;
        PreparedStatement psHistorial = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción (Indispensable en ADSO)

            // Consulta corregida con los nombres nuevos
            String sqlCheck = "SELECT p.costo_puntos, p.stock, u.saldo_puntos " +
                    "FROM premios p, usuarios u " +
                    "WHERE p.id_premio = ? AND u.id_usuario = ?";

            psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idPremio);
            psCheck.setInt(2, idUsuario);
            rs = psCheck.executeQuery();

            if (rs.next()) {
                int costo = rs.getInt("costo_puntos");
                int stock = rs.getInt("stock");
                int puntosUsuario = rs.getInt("saldo_puntos");

                if (stock <= 0) {
                    conn.rollback();
                    return "AGOTADO";
                }

                if (puntosUsuario < costo) {
                    conn.rollback();
                    return "PUNTOS_INSUFICIENTES";
                }

                // A. Restar puntos al usuario en la tabla 'usuarios'
                String sqlRestarPuntos = "UPDATE usuarios SET saldo_puntos = saldo_puntos - ? WHERE id_usuario = ?";
                psRestarPuntos = conn.prepareStatement(sqlRestarPuntos);
                psRestarPuntos.setInt(1, costo);
                psRestarPuntos.setInt(2, idUsuario);
                psRestarPuntos.executeUpdate();

                // B. Restar 1 unidad al stock del catálogo en 'premios'
                String sqlRestarStock = "UPDATE premios SET stock = stock - 1 WHERE id_premio = ?";
                psRestarStock = conn.prepareStatement(sqlRestarStock);
                psRestarStock.setInt(1, idPremio);
                psRestarStock.executeUpdate();

                // C. REGISTRO DE AUDITORÍA (¡Requisito del SENA para ganar puntos extras!)
                // Insertamos el movimiento en la tabla 'historial_puntos'
                String sqlHistorial = "INSERT INTO historial_puntos (id_usuario, tipo_movimiento, cantidad_puntos, id_premio) VALUES (?, 'SALIDA', ?, ?)";
                psHistorial = conn.prepareStatement(sqlHistorial);
                psHistorial.setInt(1, idUsuario);
                psHistorial.setInt(2, costo);
                psHistorial.setInt(3, idPremio);
                psHistorial.executeUpdate();

                conn.commit(); // Confirmar los 3 pasos juntos de forma segura
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
                if (psHistorial != null) psHistorial.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "ERROR_DESCONOCIDO";
    }
}
