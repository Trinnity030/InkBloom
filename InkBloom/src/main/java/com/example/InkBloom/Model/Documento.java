package com.example.InkBloom.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

// Representa un documento con un título y un cuerpo que es una lista de líneas de texto
public class Documento {
    private String titulo;
    private ArrayList<String> cuerpo;
    private LocalDateTime fechaEdicion; // Fecha de la última edición

    // Constructor que inicializa el documento con un título y fecha de edición
    public Documento(String titulo, LocalDateTime fechaEdicion) {
        this.titulo = titulo;
        this.cuerpo = new ArrayList<>();
        this.fechaEdicion = fechaEdicion; // Asignar la fecha de edición aquí
    }

    // Constructor copia
    public Documento(Documento doc) {
        this.titulo = doc.titulo;
        this.cuerpo = new ArrayList<>(doc.cuerpo);
        this.fechaEdicion = doc.fechaEdicion; // Copiar también la fecha de edición
    }

    // Obtiene el título del documento
    public String getTitulo() {
        return titulo;
    }

    // Obtiene una copia del cuerpo del documento
    public ArrayList<String> getCuerpo() {
        return new ArrayList<>(cuerpo);
    }

    // Copia el estado de otro documento
    public void copyState(Documento doc) {
        this.cuerpo = new ArrayList<>(doc.cuerpo);
    }

    // Agrega una línea al cuerpo del documento
    public boolean pushLine(String line) {
        return cuerpo.add(line);
    }

    // Elimina la última línea del cuerpo del documento
    public boolean removeLine() {
        if (!cuerpo.isEmpty()) {
            cuerpo.remove(cuerpo.size() - 1);
            return true;
        }
        return false;
    }

    // Verifica si el cuerpo del documento está vacío
    public boolean isEmpty() {
        return cuerpo.isEmpty();
    }

    public LocalDateTime getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(LocalDateTime fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }
}
