package com.bonuapp.dao;

import com.bonuapp.model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private String url = "jdbc:mysql://localhost:3306/tu_base_de_datos?useSSL=false&serverTimezone=UTC";
    private String username = "root";
    private String passwordDb = ""; // Tu contraseña de MySQL

    // Método para registrar un usuario en la BD
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, contrasenia) VALUES (?, ?, ?)"; // Asegura que coincida con tus columnas exactas
        boolean registroExitoso = false;

        // Cargar el driver de MySQL (crucial en entornos web tradicionales)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        // Conexión y ejecución
        try (Connection conn = DriverManager.getConnection(url, username, passwordDb);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword()); // En producción usarías hashing, pero para el SENA en este punto está bien así

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                registroExitoso = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registroExitoso;
    }
}
