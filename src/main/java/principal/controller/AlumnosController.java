package principal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.persistencia.AlumnoDAO;
import principal.persistencia.AlumnoRepo;
import principal.persistencia.CocheDAO;
import principal.persistencia.CocheRepo;
import principal.persistencia.ProfesorDAO;
import principal.persistencia.ProfesorRepo;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.interfaces.AlumnoService;



	@Controller
	@RequestMapping("/alumnos")
	public class AlumnosController {
		
		/*AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();*/
		
		@Autowired
		private AlumnoServiceImpl alumnoService;
		@Autowired
		private ProfesorServiceImpl profeService;
		@Autowired
		private CocheServiceImpl cocheService;
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("alumnoNuevo", new Alumno());
			model.addAttribute("alumnoaEditar", new Alumno());
			model.addAttribute("alumnoaBuscar", new Alumno());
			return "alumnos";
		}
		
		@PostMapping("/add")
		public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) {
			Profesor ProfeNuevo=alumnoNew.getProfesor();
			alumnoNew.setProfesor(ProfeNuevo);
			ProfeNuevo.getAlumnos().add(alumnoNew);
			alumnoService.insertarAlumno(alumnoNew);
			return "redirect:/alumnos";
		}
		
		@PostMapping("/edit/{id}")
		public String editarAlumno(@PathVariable Integer id, @ModelAttribute("alumnoaEditar") Alumno alumnoEditado, BindingResult bidingresult) {
			Alumno alumnoaEditar=alumnoService.obtenerAlumnoPorId(id);
			alumnoaEditar.setNombre(alumnoEditado.getNombre());
			alumnoaEditar.setNotas(alumnoEditado.getNotas());
			alumnoService.insertarAlumno(alumnoaEditar);
			return "redirect:/alumnos";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteAlumno(Model model, @PathVariable Integer id) {
			Alumno alumnoaEliminar=alumnoService.obtenerAlumnoPorId(id);
			alumnoService.eliminarAlumno(alumnoaEliminar);
			return "redirect:/alumnos";
		}
		
		@GetMapping({"/{id}"})
		String idAlumno(Model model, @PathVariable Integer id) {
			Alumno alumnoMostrar=alumnoService.obtenerAlumnoPorId(id);
			model.addAttribute("alumnoMostrar", alumnoMostrar);
			return "alumno";
		}
		
		@GetMapping({"/searchName/{nombre}"})
		String buscarAlumnoPorNombre(Model model,@ModelAttribute("alumnoaBuscar") Alumno alumnoBuscado, BindingResult bidingresult) {
			ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			return "AlumnosBuscados";
			
		}
		
		@GetMapping({"/searchDni/{dni}"})
		String buscarAlumnoPorDni(Model model,@ModelAttribute("alumnoaBuscar") Alumno alumnoBuscado, BindingResult bidingresult) {
			Alumno misAlumnos= alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			return "AlumnosBuscados";
			
		}
		
		@GetMapping({"/getAll"})
		List<Alumno> obtenerTodosAlumnos(Model model,BindingResult bidingresult) {
			List<Alumno> misAlumnos= alumnoService.listarAlumnos();
			
			
			
			return misAlumnos;
			
		}

}
