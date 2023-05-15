package principal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.ClaseServiceImpl;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
	@Autowired
	AlumnoServiceImpl alumnoService;
	@Autowired
	ClaseServiceImpl claseService;
	
	@GetMapping({"","/"})
	String homeCalendar(Model model) {
		ArrayList<Alumno> listaAlumnos=(ArrayList<Alumno>) alumnoService.listarAlumnos();
		model.addAttribute("listaAlumnos",listaAlumnos);
       model.addAttribute("claseNueva",new Clase());
		return "calendario";
	}
	
	@PostMapping({"/add"})
	String añadirClase(@ModelAttribute("claseNueva") Clase clase,@RequestParam("fecha") String fecha) throws ParseException {
		Clase claseAñadir=new Clase(clase.getAlumno());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		claseAñadir.setFecha(dateFormat.parse(fecha));
		claseAñadir.setHora(clase.getHora());
		claseService.insertarClase(claseAñadir);
		
		return "redirect:/calendario";
		
	}
	
	@GetMapping({"/clases"})
	@ResponseBody
	ArrayList<Clase> obtenerClases() {
		ArrayList<Clase> clases =(ArrayList<Clase>) claseService.listarClases();
		return clases;
	}
}
