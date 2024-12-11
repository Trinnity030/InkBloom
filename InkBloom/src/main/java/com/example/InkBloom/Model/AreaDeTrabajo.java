package com.example.InkBloom.Model;

import java.util.List;
import java.util.Queue;

// Clase auxiliar para retornar el área de trabajo completa
public class AreaDeTrabajo {
    private Documento documento;
    private List<Documento> documentosEnVista;
    private Queue<String> cambiosPendientes;

    public AreaDeTrabajo(Documento documento, List<Documento> documentosEnVista, Queue<String> cambiosPendientes) {
        this.documento = documento;
        this.documentosEnVista = documentosEnVista;
        this.cambiosPendientes = cambiosPendientes;
    }

    // Getters y setters
}
