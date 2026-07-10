package com.bonuapp.dao; // <-- Asegúrate de que esta línea esté arriba del todo

import com.bonuapp.model.Usuario;       // <-- IMPORTANTE: Para que reconozca la clase Usuario
import com.bonuapp.util.DatabaseConnection; // <-- IMPORTANTE: Para que reconozca DatabaseConnection
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // 1. INSERTAR (Actualizado con getConnection)
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, saldo_puntos, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); // <-- CAMBIADO A getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContrasena());
            stmt.setInt(4, usuario.getSaldoPuntos());
            stmt.setString(5, usuario.getRol());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // 2. CONSULTAR / LISTAR (Actualizado con getConnection)
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection(); // <-- CAMBIADO A getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setSaldoPuntos(rs.getInt("saldo_puntos"));
                u.setRol(rs.getString("rol"));
                u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZAR (Actualizado con getConnection)
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, saldo_puntos = ?, rol = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // <-- CAMBIADO A getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setInt(3, usuario.getSaldoPuntos());
            stmt.setString(4, usuario.getRol());
            stmt.setInt(5, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR (Actualizado con getConnection)
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // <-- CAMBIADO A getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
