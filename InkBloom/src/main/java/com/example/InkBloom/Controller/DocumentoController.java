package com.example.InkBloom.Controller;

import com.example.InkBloom.Model.Documento;
import com.example.InkBloom.Model.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

// Controlador que maneja las rutas para interactuar con el documento
@Controller
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService; // Servicio para manejar documentos

    /**
     * Maneja la solicitud para mostrar la lista de documentos.
     * Se utiliza el metodo GET en la URL "/documentos".
     *
     * @param model El objeto Model permite enviar datos al archivo HTML.
     * @return El nombre del archivo HTML que se renderizará (sin extensión).
     */
    @GetMapping
    public String listarDocumentos(Model model) {
        List<Documento> documentos = documentoService.listarDocumentos();
        model.addAttribute("documentos", documentos);
        return "documento";
    }

    @GetMapping("/crear")
    public String crearDocumento(@RequestParam(required = true) String titulo, Model model) {
        if (titulo == null || titulo.trim().isEmpty()) {
            model.addAttribute("error", "El título no puede estar vacío.");
            return "error"; // Una vista de error adecuada
        }
        documentoService.crearDocumento(titulo);
        model.addAttribute("documento", documentoService.obtenerDocumento());
        return "documento";
    }


    @PostMapping("/agregar")
    public String agregarTexto(@RequestParam String texto, Model model) {
        documentoService.agregarTexto(texto);
        model.addAttribute("documento", documentoService.obtenerDocumento());
        return "documento";
    }

    @PostMapping("/quitar")
    public String quitarTexto(Model model) {
        documentoService.quitarTexto();
        model.addAttribute("documento", documentoService.obtenerDocumento());
        return "documento";
    }

    @PostMapping("/deshacer")
    public String deshacer(Model model) {
        documentoService.deshacer();
        model.addAttribute("documento", documentoService.obtenerDocumento());
        return "documento";
    }

    @PostMapping("/rehacer")
    public String rehacer(Model model) {
        documentoService.rehacer();
        model.addAttribute("documento", documentoService.obtenerDocumento());
        return "documento";
    }

    @PostMapping("/guardar")
    public String guardarDocumento(Model model) {
        documentoService.guardarDocumento();
        model.addAttribute("documentosGuardados", documentoService.listarDocumentos());
        return "documento";
    }

    @GetMapping("/cambios")
    public String listarCambiosPendientes(Model model) {
        Queue<String> cambios = documentoService.listarCambiosPendientes();
        model.addAttribute("cambios", cambios);
        return "cambiosPendientes"; // Vista con la lista de cambios
    }

    @PostMapping("/vista/abrir")
    public String abrirDocumentoEnVista(@RequestParam String titulo, Model model) {
        documentoService.listarDocumentos().stream()
                .filter(d -> d.getTitulo().equals(titulo))
                .findFirst()
                .ifPresent(documentoService::agregarDocumentoAVista);

        model.addAttribute("documentosEnVista", documentoService.listarDocumentosEnVista());
        return "documentosEnVista";
    }


    @PostMapping("/vista/cerrar")
    public String cerrarDocumentoEnVista(Model model) {
        Documento cerrado = documentoService.cerrarDocumentoEnVista();
        model.addAttribute("cerrado", cerrado);
        model.addAttribute("documentosEnVista", documentoService.listarDocumentosEnVista());
        return "documentosEnVista";
    }

    @GetMapping("/area-de-trabajo")
    public String mostrarAreaDeTrabajo(Model model) {
        // Documento actual
        model.addAttribute("documento", documentoService.obtenerDocumento());
        // Documentos abiertos en el área de trabajo
        model.addAttribute("documentosEnVista", documentoService.listarDocumentosEnVista());
        // Cambios pendientes
        model.addAttribute("cambiosPendientes", documentoService.listarCambiosPendientes());
        return "areaDeTrabajo";
    }

}
