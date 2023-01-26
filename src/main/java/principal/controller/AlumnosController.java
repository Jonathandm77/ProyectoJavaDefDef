package principal.controller;

import java.util.ArrayList;


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
import principal.servicio.interfaces.AlumnoService;



	@Controller
	@RequestMapping("/alumnos")
	public class AlumnosController {
		
		AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();
		
		@Autowired
		private AlumnoService alumnoService;
		@Autowired
		private ProfesorRepo profeRepo;
		@Autowired
		private CocheRepo cocheRepo;
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheRepo.findAll();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeRepo.findAll();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("alumnoNuevo", new Alumno());
			model.addAttribute("alumnoaEditar", new Alumno());
			return "alumnos";
		}
		
		@PostMapping("/add")
		public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) {
			Profesor ProfeNuevo=alumnoNew.getProfesor();
			alumnoNew.setProfesor(ProfeNuevo);
			ProfeNuevo.getAlumnos().add(alumnoNew);
			alumnoRepo.save(alumnoNew);
			return "redirect:/alumnos";
		}
		
		@PostMapping("/edit/{id}")
		public String editarAlumno(@PathVariable Integer id, @ModelAttribute("alumnoaEditar") Alumno alumnoEditado, BindingResult bidingresult) {
			Alumno alumnoaEditar=alumnoRepo.findById(id).get();
			alumnoaEditar.setNombre(alumnoEditado.getNombre());
			alumnoaEditar.setNotas(alumnoEditado.getNotas());
			alumnoRepo.save(alumnoaEditar);
			return "redirect:/alumnos";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteAlumno(Model model, @PathVariable Integer id) {
			Alumno alumnoaEliminar=alumnoRepo.findById(id).get();
			alumnoRepo.delete(alumnoaEliminar);
			return "redirect:/alumnos";
		}
		
		@GetMapping({"/{id}"})
		String idAlumno(Model model, @PathVariable Integer id) {
			Alumno alumnoMostrar=alumnoRepo.findById(id).get();
			model.addAttribute("alumnoMostrar", alumnoMostrar);
			return "alumno";
		}

}
