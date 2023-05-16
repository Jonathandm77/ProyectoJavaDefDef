package principal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Usuario;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.ClaseServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.RolServiceImpl;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
	@Autowired
	AlumnoServiceImpl alumnoService;
	@Autowired
	ClaseServiceImpl claseService;
	@Autowired
	RolServiceImpl rolService;
	
	@Autowired
	ProfesorServiceImpl profeService;
	
	@GetMapping({"","/"})
	String homeCalendar(Model model,@RequestParam(value = "fechaActual", required = false) Long fecha) {
		ArrayList<Alumno> listaAlumnos=(ArrayList<Alumno>) alumnoService.listarAlumnos();
		
		model.addAttribute("listaAlumnos",listaAlumnos);
       model.addAttribute("claseNueva",new Clase());
       model.addAttribute("claseBorrar",new Clase());
       if(fecha!=null) {
       model.addAttribute("fechaActual",fecha);
       }
		return "calendario";
	}
	
	@PostMapping({"/add"})
	String añadirClase(@ModelAttribute("claseNueva") Clase clase,@RequestParam("fecha") String fecha,RedirectAttributes redirectAttributes) throws ParseException {
		Clase claseAñadir=new Clase(clase.getAlumno());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		claseAñadir.setFecha(dateFormat.parse(fecha));
		claseAñadir.setHora(clase.getHora());
		redirectAttributes.addAttribute("fechaActual", claseAñadir.getFecha().getTime());
		claseService.insertarClase(claseAñadir);
		
		return "redirect:/calendario";
		
	}
	
	@PostMapping({"/delete"})
	String borrarClase(@ModelAttribute("claseBorrar") Clase clase,@RequestParam("id") int id, RedirectAttributes redirectAttributes) throws ParseException {
		Clase claseBorrar=claseService.obtenerClasePorId(clase.getId());
		redirectAttributes.addAttribute("fechaActual", claseBorrar.getFecha().getTime());

		if(claseBorrar!=null)
			claseService.eliminarClasePorId(id);
		
		return "redirect:/calendario";
		
	}
	
	@GetMapping({"/clases"})
	@ResponseBody
	ArrayList<Clase> obtenerClases() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	   // if(actualUser.getRoles().contains(rolService.obtenerRolPorId(3))
	    	//	return ArrayList<Clase> misClases=profeService.obtenerProfesorPorId(actualUser.getIdProfesor()).g
		ArrayList<Clase> clases =(ArrayList<Clase>) claseService.listarClases();
		return clases;
	}
}
