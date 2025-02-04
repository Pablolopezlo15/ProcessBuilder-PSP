package com.processBuilder;

public class Proceso {
    private String nombre;
    private String id;
    private String tipo;
    private String memoria; // Ahora es un número en lugar de String

    public Proceso(String nombre, String id, String tipo, String memoria) {
        this.nombre = nombre;
        this.id = id;
        this.tipo = tipo;
        this.memoria = memoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMemoria() {
        return memoria;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s, Tipo: %s, Memoria: %.2f KB)", nombre, id, tipo, memoria);
    }


}
