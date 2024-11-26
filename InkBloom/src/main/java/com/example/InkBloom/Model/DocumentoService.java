package com.example.InkBloom.Model;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Servicio que contiene la lógica para manejar un documento
@Service
public class DocumentoService {
    private Documento documento; // Documento activo
    private final Stack<Documento> pilaDeshacer = new Stack<>(); // Pila para acciones de deshacer
    private final Stack<Documento> pilaRehacer = new Stack<>();  // Pila para acciones de rehacer
    // Lista en memoria para almacenar los documentos (simula una base de datos)
    private List<Documento> documentos = new ArrayList<>();

    /**
     * Devuelve la lista de documentos existentes.
     *
     * @return Una lista con los documentos.
     */
    public List<Documento> listarDocumentos() {
        return documentos;
    }

    /**
     * Agrega un nuevo documento a la lista.
     *
     * @param documento El objeto Documento que se desea agregar.
     */
    public void crearDocumento(Documento documento) {
        documentos.add(documento);
    }

    // Crea un nuevo documento y limpia las pilas de deshacer y rehacer
    public void crearDocumento(String titulo) {
        this.documento = new Documento(titulo, LocalDateTime.now()); // Crear documento con la fecha actual
        pilaDeshacer.push(new Documento(documento)); // Guarda una copia inicial
        pilaRehacer.clear(); // Limpia las acciones de rehacer
    }

    // Devuelve el documento actual
    public Documento obtenerDocumento() {
        return documento;
    }

    // Agrega una línea de texto al documento y actualiza la pila de deshacer
    public void agregarTexto(String texto) {
        pilaRehacer.clear(); // Limpia las acciones de rehacer
        if (documento.pushLine(texto)) {
            pilaDeshacer.push(new Documento(documento)); // Guarda el estado actual
        }
    }

    // Elimina la última línea del documento y actualiza la pila de deshacer
    public void quitarTexto() {
        if (!documento.isEmpty()) {
            pilaRehacer.clear();
            if (documento.removeLine()) {
                pilaDeshacer.push(new Documento(documento)); // Guarda el estado actual
            }
        }
    }

    // Restaura el estado anterior del documento (deshacer)
    public void deshacer() {
        if (pilaDeshacer.size() > 1) { // Asegura que hay algo que deshacer
            pilaRehacer.push(pilaDeshacer.pop()); // Mueve el estado actual a la pila de rehacer
            documento.copyState(pilaDeshacer.peek()); // Restaura el estado anterior
        }
    }

    // Restaura un estado previamente deshecho (rehacer)
    public void rehacer() {
        if (!pilaRehacer.isEmpty()) { // Asegura que hay algo que rehacer
            pilaDeshacer.push(pilaRehacer.pop()); // Mueve el estado de rehacer a deshacer
            documento.copyState(pilaDeshacer.peek()); // Restaura el estado
        }
    }
}
