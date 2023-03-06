package principal.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Usuario;
import principal.modelo.dto.CambioContrasenaDTO;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/seguridad/password")
@Controller
public class PasswordController {
	
	@Autowired UsuarioServiceImpl userService;

	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@GetMapping(value={"","/"})
	String homeSecurity(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser =(Usuario) auth.getPrincipal();
	model.addAttribute("usuarioPassword", new CambioContrasenaDTO());
	model.addAttribute("usuarioActual", actualUser);
	
	return "cambioPassword";
	}
	
	
	
	@PostMapping("/changePassword")
	public String cambioPassword(@ModelAttribute("usuarioPassword") CambioContrasenaDTO userDTO, BindingResult bidingresult) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    Usuario actualUser =(Usuario) auth.getPrincipal();
		    if(encoder.matches(userDTO.getActual(), actualUser.getPassword()) && userDTO.getNueva().equals(userDTO.getConfirm())) {
		    	
		    	actualUser.setPassword(encoder.encode(userDTO.getNueva()));
		    	userService.insertarUsuario((Usuario) actualUser);
		    }
		    	
		return "cambioPassword";
	}
	
	@PostMapping("/changeData")
	public String cambioDatos(@ModelAttribute("usuarioActual") Usuario user, BindingResult bidingresult) {
		 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser =(Usuario) auth.getPrincipal();
		    actualUser.setNombre(user.getNombre());
		    actualUser.setUsername(user.getUsername());
		    userService.insertarUsuario(actualUser);
		    
		    	
		return "cambioPassword";
	}
	
}
