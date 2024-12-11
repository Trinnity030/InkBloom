package com.example.InkBloom.Controller;

import com.example.InkBloom.Model.AreaDeTrabajo;
import com.example.InkBloom.Model.Documento;
import com.example.InkBloom.Model.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// Controlador que maneja las rutas para interactuar con el documento
@RestController
@RequestMapping("/api/documento")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService; // Servicio para manejar documentos

    /**
     * Maneja la solicitud para mostrar la lista de documentos.
     * Se utiliza el metodo GET en la URL "/documentos".
     */
    // Listar documentos
    @GetMapping
    public List<Documento> listarDocumentos() {
        return documentoService.listarDocumentos();
    }

    // Crear un documento
    @PostMapping("/crear")
    public Documento crearDocumento(@RequestParam String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        documentoService.crearDocumento(titulo);
        return documentoService.obtenerDocumento();
    }


    // Agregar texto a un documento
    @PostMapping("/agregar")
    public Documento agregarTexto(@RequestParam String texto) {
        documentoService.agregarTexto(texto);
        return documentoService.obtenerDocumento();
    }


    @PostMapping("/quitar")
    public Documento quitarTexto() {
        documentoService.quitarTexto();
        return documentoService.obtenerDocumento();
    }


    @PostMapping("/deshacer")
    public Documento deshacer() {
        documentoService.deshacer();
        return documentoService.obtenerDocumento();
    }


    @PostMapping("/rehacer")
    public Documento rehacer() {
        documentoService.rehacer();
        return documentoService.obtenerDocumento();
    }


    // Guardar documento
    @PostMapping("/guardar")
    public List<Documento> guardarDocumento() {
        documentoService.guardarDocumento();
        return documentoService.listarDocumentos();
    }

    // Obtener el documento actual
    @GetMapping("/actual")
    public Documento obtenerDocumentoActual() {
        return documentoService.obtenerDocumento();
    }

    @GetMapping("/cambios")
    public Queue<String> listarCambiosPendientes() {
        return documentoService.listarCambiosPendientes();
    }


    @PostMapping("/vista/abrir")
    public String abrirDocumentoEnVista(@RequestParam String titulo, Model model) {
        documentoService.listarDocumentos().stream()
                .filter(d -> d.getTitulo().equals(titulo))
                .findFirst()
                .ifPresent(documentoService::agregarDocumentoAVista);

        // Convierte la cola a una lista antes de agregarla al modelo
        List<Documento> documentosEnVista = new ArrayList<>(documentoService.listarDocumentosEnVista());
        model.addAttribute("documentosEnVista", documentosEnVista);

        return "documentosEnVista";
    }



    @PostMapping("/vista/cerrar")
    public Documento cerrarDocumentoEnVista() {
        return documentoService.cerrarDocumentoEnVista();
    }


    @GetMapping("/area-de-trabajo")
    public AreaDeTrabajo mostrarAreaDeTrabajo() {
        return new AreaDeTrabajo(
                documentoService.obtenerDocumento(),
                new ArrayList<>(documentoService.listarDocumentosEnVista()), // Convertir a List
                documentoService.listarCambiosPendientes()
        );
    }


}
