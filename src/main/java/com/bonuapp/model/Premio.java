package com.bonuapp.model;

public class Premio {
    // Atributos privados
    private int idPremio;
    private String nombre;
    private String descripcion;
    private int puntosRequeridos;

    // Constructor vacío
    public Premio() {}

    // Constructor con parámetros
    public Premio(int idPremio, String nombre, String descripcion, int puntosRequeridos) {
        this.idPremio = idPremio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
    }

    // Métodos Getter y Setter (camelCase)
    public int getIdPremio() { return idPremio; }
    public void setIdPremio(int idPremio) { this.idPremio = idPremio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPuntosRequeridos() { return puntosRequeridos; }
    public void setPuntosRequeridos(int puntosRequeridos) { this.puntosRequeridos = puntosRequeridos; }
}
