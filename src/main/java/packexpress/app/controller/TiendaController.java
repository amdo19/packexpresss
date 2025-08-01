package packexpress.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import packexpress.app.model.Tienda;
import packexpress.app.repository.TiendaRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class TiendaController {

    @Autowired
    private TiendaRepository tiendaRepo;

    // Mostrar todas las tiendas (opcional si lo necesitas)
    @GetMapping("/tiendas")
    public String listarTiendas(Model model) {
        List<Tienda> tiendas = tiendaRepo.findAll();
        model.addAttribute("tiendas", tiendas);
        return "tiendas"; // crea un HTML llamado tiendas.html si lo usas
    }

    // Mostrar detalles de una tienda por nombre (URL amigable)
    @GetMapping("/tienda/{nombre}")
    public String verTienda(@PathVariable("nombre") String nombre, Model model) {
        Optional<Tienda> tiendaOpt = tiendaRepo.findByNombreIgnoreCase(nombre);
        if (tiendaOpt.isPresent()) {
            model.addAttribute("tienda", tiendaOpt.get());
            return "tienda_detalle"; // crea tienda_detalle.html en templates
        } else {
            return "redirect:/?error=tienda_no_encontrada";
        }
    }
}
