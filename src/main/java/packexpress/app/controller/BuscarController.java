package packexpress.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import packexpress.app.repository.TiendaRepository;

@Controller
public class BuscarController {

    @Autowired
    private TiendaRepository tiendaRepo;

    @GetMapping("/buscar")
    public String mostrarBusqueda(@RequestParam(value = "q", required = false) String consulta, Model model) {
        model.addAttribute("consulta", consulta);

        if (consulta == null || consulta.trim().isEmpty()) {
            model.addAttribute("error", "No se puede realizar una búsqueda vacía.");
            return "buscar";
        }

        var resultados = tiendaRepo.findByNombreContainingIgnoreCase(consulta);
        model.addAttribute("resultados", resultados);

        return "buscar"; // busca en buscar.html
    }
}
