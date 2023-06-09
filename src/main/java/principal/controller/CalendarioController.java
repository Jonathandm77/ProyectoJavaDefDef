package principal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.ClaseDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.ClaseServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.RolServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

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
	String homeCalendar(Model model,@RequestParam(value = "fechaActual", required = false) Long fecha) {//carga de recursos con fecha para situarse en la fecha necesaria
		ArrayList<Alumno> listaAlumnos=(ArrayList<Alumno>) alumnoService.listarAlumnos();
		
		model.addAttribute("listaAlumnos",listaAlumnos);
       model.addAttribute("claseNueva",new ClaseDTO());
       model.addAttribute("claseBorrar",new Clase());
       if(fecha!=null) {
       model.addAttribute("fechaActual",fecha);
       }
       
		return "calendario";
	}
	
	@PostMapping({"/add"})//añadir clase
	String añadirClase(@ModelAttribute("claseNueva") ClaseDTO clase,@RequestParam("fecha") String fecha,@ApiIgnore RedirectAttributes redirectAttributes) throws ParseException {
		Alumno alumnoClase=alumnoService.obtenerAlumnoPorId(clase.getAlumno().getId()).get();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDate=dateFormat.parse(fecha);//formatear fecha recibida
		boolean error = false;
		for (Clase c:alumnoClase.getClases()) {
			if(c.getHora().equals(clase.getHora()) && c.getFecha().equals(fechaDate)) {//evitar clase ya existente
				error=true;
			}
		}
		if(!error) {
		Clase claseAñadir=new Clase(alumnoClase);
		claseAñadir.setFecha(fechaDate);
		claseAñadir.setHora(clase.getHora());
		claseService.insertarClase(claseAñadir);
		}
		redirectAttributes.addAttribute("fechaActual", fechaDate.getTime());//se sube la fecha actual para evitar recargar el calendario con la fecha de hoy por comodidad
		redirectAttributes.addFlashAttribute("error",error);//añadir mensaje de error
		
		return "redirect:/calendario";
		
	}
	
	@PostMapping({"/delete"})//eliminar clase
	String borrarClase(@ModelAttribute("claseBorrar") Clase clase,@RequestParam("id") int id, RedirectAttributes redirectAttributes) throws ParseException {
		Clase claseBorrar=claseService.obtenerClasePorId(clase.getId());
		redirectAttributes.addAttribute("fechaActual", claseBorrar.getFecha().getTime()); //añadir la fecha actual para evitar recargar el calendario con la fecha de hoy

		if(claseBorrar!=null)//validar si la clase existe
			claseService.eliminarClasePorId(id);
		
		return "redirect:/calendario";
		
	}
	
	@GetMapping({"/clases"})
	@ResponseBody
	Object[] obtenerClases() {//listar clases
		boolean esAdmin=false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    if(actualUser.getIdAlumno()!=null) {//si es alumno mostrar solo sus clases
	    	Object[] misClasesAlumno= alumnoService.obtenerAlumnoPorId(actualUser.getIdAlumno()).get().getClases().toArray();
	    		return misClasesAlumno;
	    }
	    
	    if (actualUser.getIdProfesor() != null) {//si es profesor mostrar solo sus clases
	        ArrayList<Alumno> listaAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Object> misClasesProfesor = new ArrayList<>();

	        for (Alumno a : listaAlumnos) {
	            if (a.getProfesor().getId() == actualUser.getIdProfesor()) {
	                misClasesProfesor.addAll(a.getClases());
	            }
	        }

	        Object[] misClasesProfesorArray = misClasesProfesor.toArray();
	        return misClasesProfesorArray;
	    }
	    
	    for(Rol r:actualUser.getRoles()) {
	    	if (r.getNombre().equals("ROLE_ADMIN"))
	    		esAdmin=true;
	    }
	    
	    if(esAdmin) {//si es admin mostrar todas los clases
		Object[] clases =claseService.listarClases().toArray();
		return clases;
	    }else {
		return new ArrayList<Object>().toArray();//si no esta logueado no devuelve nada
	    }
	}
}
