package com.bonuapp.model;

import java.sql.Timestamp;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private int saldoPuntos;
    private String rol;             // <-- NUEVO campo
    private Timestamp fechaRegistro; // <-- NUEVO campo

    // Constructor vacío
    public Usuario() {}

    // Constructor lleno (para cuando registres un nuevo usuario)
    public Usuario(String nombre, String correo, String contrasena, int saldoPuntos, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.saldoPuntos = saldoPuntos;
        this.rol = rol;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public int getSaldoPuntos() { return saldoPuntos; }
    public void setSaldoPuntos(int saldoPuntos) { this.saldoPuntos = saldoPuntos; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}