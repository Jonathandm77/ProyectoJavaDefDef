package principal.controller;

import java.util.Set;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Profesor;
import principal.modelo.Usuario;
import principal.servicio.implementacion.ProfesorServiceImpl;

@Controller
@RequestMapping("/misAlumnos")
public class MisAlumnosController {
	
	@Autowired
	private ProfesorServiceImpl profeService;
	
	@GetMapping({"","/"})//pagina de mis alumnos
	String obtenerMisAlumnos(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    Profesor profeUsuario=profeService.obtenerProfesorPorId(actualUser.getIdProfesor()).get();//obtener el profesor asociado con el usuario
	    Set<Alumno> alumnosProfe=profeUsuario.getAlumnos();
	    ArrayList<Alumno> misAlumnos=new ArrayList<Alumno>();
	    for(Alumno a:alumnosProfe) {
	    	misAlumnos.add(a);//añadimos cada alumno a un arraylist por compatibilidad
	    }
	    model.addAttribute("alumnosProfe",misAlumnos);
	    return "alumnosDeProfesor";
	}
}
