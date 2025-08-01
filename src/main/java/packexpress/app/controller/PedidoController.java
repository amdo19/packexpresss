package packexpress.app.controller;

import jakarta.servlet.http.HttpSession;
import packexpress.app.model.Pedido;
import packexpress.app.model.Usuario;
import packexpress.app.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PedidoController {

    @Autowired
    private PedidoRepository repo;

    // Página de inicio
    @GetMapping("/")
    public String home() {
        return "dashboard";
    }

    // Mostrar formulario nuevo pedido
    @GetMapping("/pedido/nuevo")
    public String nuevoPedido(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("pedido", new Pedido());
        return "pedido_nuevo";
    }

    // Guardar nuevo pedido
    @PostMapping("/pedido/guardar")
    public String guardar(@ModelAttribute Pedido pedido, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        pedido.setCliente(usuario.getNombre());
        pedido.setEstado("Pendiente");
        repo.save(pedido);
        return "redirect:/seguimiento?id=" + pedido.getId();
    }

    // Ver seguimiento de un pedido
    @GetMapping("/seguimiento")
    public String seguimiento(@RequestParam Long id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Pedido> pedido = repo.findById(id);
        if (pedido.isPresent()) {
            Pedido p = pedido.get();
            if (usuario.isAdmin() || p.getCliente().equalsIgnoreCase(usuario.getNombre())) {
                model.addAttribute("pedido", p);
            } else {
                return "redirect:/?error=acceso_denegado";
            }
        } else {
            model.addAttribute("pedido", null);
        }

        return "pedido_seguimiento";
    }

    // Panel administrador (protegido)
    @GetMapping("/admin/pedidos")
    public String admin(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }

        model.addAttribute("pedidos", repo.findAll());
        return "admin_pedidos";
    }

    // Reprogramar entrega
    @PostMapping("/pedido/reprogramar")
    public String reprogramar(@RequestParam Long id, @RequestParam String fecha, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Pedido> opt = repo.findById(id);
        opt.ifPresent(p -> {
            if (usuario.isAdmin() || p.getCliente().equalsIgnoreCase(usuario.getNombre())) {
                p.setFechaEntrega(LocalDate.parse(fecha));
                p.setEstado("Reprogramado");
                repo.save(p);
            }
        });

        return "redirect:/seguimiento?id=" + id;
    }
@GetMapping("/pedido/calificar")
public String mostrarCalificacion(@RequestParam Long id, Model model) {
    model.addAttribute("pedidoId", id);
    return "calificar";
}

@PostMapping("/pedido/calificar")
public String procesarCalificacion(@RequestParam Long pedidoId,
                                    @RequestParam int rating,
                                    @RequestParam String comentario) {
    System.out.println("Pedido: " + pedidoId + ", Puntuación: " + rating + ", Comentario: " + comentario);
    return "redirect:/seguimiento?id=" + pedidoId;
}

    // Cambiar estado desde el panel admin
    @PostMapping("/admin/cambiarEstado")
    public String cambiarEstado(@RequestParam Long id, @RequestParam String estado, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }

        Optional<Pedido> opt = repo.findById(id);
        opt.ifPresent(p -> {
            p.setEstado(estado);
            repo.save(p);
        });

        return "redirect:/admin/pedidos";
    }

    // Historial de pedidos del cliente
    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<Pedido> pedidos = repo.findByCliente(usuario.getNombre());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }
}
