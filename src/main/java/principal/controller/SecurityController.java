package principal.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Profesor;
import principal.modelo.Usuario;
import principal.modelo.dto.CambioContrasenaDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/seguridad/password")
@Controller
public class SecurityController {
	
	@Autowired UsuarioServiceImpl userService;
	@Autowired ProfesorServiceImpl profeService;
	@Autowired CocheServiceImpl cocheService;
	@Autowired AlumnoServiceImpl alumnoService;

	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@GetMapping(value={"","/"})
	String homeSecurity(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser =(Usuario) auth.getPrincipal();
	model.addAttribute("usuarioPassword", new CambioContrasenaDTO());
	model.addAttribute("usuarioActual", actualUser);
	model.addAttribute("alumnoNuevo", new Alumno());
	model.addAttribute("alumnoaEliminar",new Alumno());
	model.addAttribute("alumnoaBuscar",new Alumno());
	model.addAttribute("listaProfesores", profeService.listarProfesores());
	model.addAttribute("profeNuevo", new Profesor());
	model.addAttribute("profeaEliminar", new Profesor());
	model.addAttribute("listaCoches",cocheService.listarCoches());
	
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
	
	@PostMapping("/addAlumno")
	public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) {
		Profesor ProfeNuevo=alumnoNew.getProfesor();
		alumnoNew.setProfesor(ProfeNuevo);
		ProfeNuevo.getAlumnos().add(alumnoNew);
		alumnoService.insertarAlumno(alumnoNew);
		return "redirect:/seguridad/password#operat";
	}
	
	
	@GetMapping("/deleteAlumno")
	String deleteAlumno(@ModelAttribute("alumnoaEliminar")Alumno a) {
		alumnoService.eliminarAlumnoPorId(a.getId());
		
		return "redirect:/seguridad/password#operat";
	}
	
	@PostMapping("/searchAlumnoByName")
	String buscarAlumnoPorNombre(Model model,@ModelAttribute("alumnoaBuscar") Alumno alumnoBuscado, BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
		model.addAttribute("alumnosBuscados",misAlumnos);
		
		
		return "AlumnosBuscados";
		
	}
	
	@PostMapping("/searchAlumnoByDni")
	String buscarAlumnoPorDni(Model model,@ModelAttribute("alumnoaBuscar") Alumno alumnoBuscado, BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
		model.addAttribute("alumnosBuscados",misAlumnos);
		
		
		
		return "AlumnosBuscados";
		
	}
	
	@PostMapping("/addProfesor")
	public String addAlumnProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult) {
		profeService.insertarProfesor(profeNew);
		return "redirect:/seguridad/password#operat";
	}
	
	@GetMapping("/deleteProfesor")
	String deleteProfe(@ModelAttribute("profeaEliminar") Profesor profe) {
		profeService.eliminarProfesorPorId(profe.getId());
		return "redirect:/seguridad/password#operat";
	}
	
	
}
