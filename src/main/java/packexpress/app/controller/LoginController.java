package packexpress.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import packexpress.app.model.Usuario;
import packexpress.app.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping("/login")
    public String mostrarLogin(Model model,
                                @RequestParam(value = "registro", required = false) Boolean registroActivo) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("registroActivo", registroActivo != null && registroActivo);
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                 @RequestParam String clave,
                                 HttpSession session,
                                 Model model) {

        Usuario usuario = usuarioRepo.findByCorreoAndClave(correo, clave);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            model.addAttribute("registroActivo", false);
            model.addAttribute("usuario", new Usuario());
            return "login";
        }
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (usuarioRepo.findByCorreo(usuario.getCorreo()) != null) {
            result.rejectValue("correo", "error.usuario", "El correo ya está registrado");
        }

        if (result.hasErrors()) {
            model.addAttribute("registroActivo", true);
            return "login";
        }

        usuario.setAdmin(false); // Por defecto no es admin
        usuarioRepo.save(usuario);

        model.addAttribute("mensaje", "Registro exitoso. ¡Ahora inicia sesión!");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("registroActivo", false);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
