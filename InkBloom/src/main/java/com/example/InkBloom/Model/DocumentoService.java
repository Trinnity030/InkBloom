package com.example.InkBloom.Model;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocumentoService {
    private Documento documento; // Documento activo
    private final Stack<Documento> pilaDeshacer = new Stack<>(); // Pila para acciones de deshacer
    private final Stack<Documento> pilaRehacer = new Stack<>();  // Pila para acciones de rehacer
    private final LinkedList<Documento> documentosGuardados = new LinkedList<>(); // Almacena documentos guardados
    private final List<Documento> documentos = new ArrayList<>(); // Simula una base de datos
    private final Queue<String> cambiosPendientes = new LinkedList<>(); // Cola de cambios pendientes
    private final Queue<Documento> documentosEnVista = new LinkedList<>(); // Documentos abiertos en el área de trabajo

    // Devuelve la lista de documentos existentes.
    public List<Documento> listarDocumentos() {
        return documentos;
    }

    // Agrega un nuevo documento a la lista principal
    public void agregarDocumento(Documento documento) {
        documentos.add(documento);
    }

    // Crea y guarda un documento en la lista
    public void crearDocumento(String titulo) {
        this.documento = new Documento(titulo, LocalDateTime.now());
        pilaDeshacer.push(new Documento(documento)); // Guarda el estado inicial
        pilaRehacer.clear(); // Limpia la pila de rehacer
        agregarDocumento(documento); // Lo agrega a la lista principal
    }

    // Devuelve el documento actual
    public Documento obtenerDocumento() {
        return documento;
    }

    // Agregar texto al documento actual y registra el cambio
    public void agregarTexto(String texto) {
        pilaRehacer.clear();
        if (documento.pushLine(texto)) {
            pilaDeshacer.push(new Documento(documento)); // Guarda el estado
            registrarCambio("Se agregó texto: " + texto);
        }
    }

    // Quita texto del documento actual y registra el cambio
    public void quitarTexto() {
        if (!documento.isEmpty()) {
            pilaRehacer.clear();
            if (documento.removeLine()) {
                pilaDeshacer.push(new Documento(documento)); // Guarda el estado
                registrarCambio("Se eliminó una línea de texto.");
            }
        }
    }

    // Restaura un estado anterior del documento
    public void deshacer() {
        if (pilaDeshacer.size() > 1) {
            pilaRehacer.push(pilaDeshacer.pop());
            documento.copyState(pilaDeshacer.peek());
            registrarCambio("Se deshizo la última acción.");
        }
    }

    // Restaura un estado deshecho
    public void rehacer() {
        if (!pilaRehacer.isEmpty()) {
            pilaDeshacer.push(pilaRehacer.pop());
            documento.copyState(pilaDeshacer.peek());
            registrarCambio("Se rehizo la última acción.");
        }
    }

    // Cambios pendientes: registrar un cambio
    public void registrarCambio(String descripcionCambio) {
        cambiosPendientes.add(descripcionCambio);
    }

    // Procesa el cambio más antiguo
    public String procesarCambio() {
        return cambiosPendientes.poll();
    }

    // Lista los cambios pendientes
    public Queue<String> listarCambiosPendientes() {
        return new LinkedList<>(cambiosPendientes);
    }

    // Documentos en vista: agregar un documento
    public void agregarDocumentoAVista(Documento doc) {
        documentosEnVista.add(doc);
    }

    // Cerrar el documento más antiguo en vista
    public Documento cerrarDocumentoEnVista() {
        return documentosEnVista.poll();
    }

    // Cambiar entre documentos abiertos
    public Documento cambiarDocumentoEnVista() {
        Documento doc = documentosEnVista.poll();
        if (doc != null) {
            documentosEnVista.add(doc); // Mueve al final de la cola
        }
        return doc;
    }

    // Lista los documentos abiertos en el área de trabajo
    public Queue<Documento> listarDocumentosEnVista() {
        return new LinkedList<>(documentosEnVista);
    }

    //es para guardar documentos temporalmente
    public void guardarDocumento() {
        if (documento != null) {
            documentosGuardados.add(new Documento(documento)); // Copia del documento actual
        }
    }

}
