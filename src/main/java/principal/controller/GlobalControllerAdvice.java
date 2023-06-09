package principal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import principal.modelo.Usuario;
//controlador auxiliar para que el atributo tema esté disponible en todas las url
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("tema")
    public String getTema(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof Usuario) {
        Usuario actualUser = (Usuario) auth.getPrincipal();
        
        if (actualUser != null && actualUser.getTema() != null) {
            return actualUser.getTema();
        }
        }

        // Valor predeterminado del tema si no se encuentra ningún tema personalizado
        return "temaClaro";
    }
}
