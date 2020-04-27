package com.rba.examennaat.pojos;

public class Empresa {
    private String nombre;
    private String opciones;

    public Empresa() {
    }

    public Empresa(String nombre, String opciones) {
        this.nombre = nombre;
        this.opciones = opciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", opciones='" + opciones + '\'' +
                '}';
    }
}
