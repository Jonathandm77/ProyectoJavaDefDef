package principal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Usuario;
import principal.servicio.implementacion.AlumnoServiceImpl;

@Controller
@RequestMapping("/miPerfil")
public class MiPerfilController {
	
	@Autowired 
	AlumnoServiceImpl alumnoService;
	
	@GetMapping(value= {"/",""})//pagina de mi perfil
	String miPerfil(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    if(actualUser.getIdAlumno()!=null) {//si el usuario esta logueado, como seguridad adicional
		Alumno alumnoMostrar=alumnoService.obtenerAlumnoPorId(actualUser.getIdAlumno()).get();
		model.addAttribute("username",actualUser.getUsername());
		model.addAttribute("alumnoMostrar",alumnoMostrar);//lo subimos a la pagina
	    }
		return "miPerfil";
		
	}
}
