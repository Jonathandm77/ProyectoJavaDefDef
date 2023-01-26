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



	@Controller
	@RequestMapping("/profesores")
	public class ProfesoresController {
		
		AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();
		
		@Autowired
		private AlumnoRepo alumnoRepo;
		@Autowired
		private ProfesorRepo profeRepo;
		@Autowired
		private CocheRepo cocheRepo;
		
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheRepo.findAll();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoRepo.findAll();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeRepo.findAll();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("profeNuevo", new Profesor());
			model.addAttribute("profeaEditar", new Profesor());
			return "profesores";
		}
		
		@PostMapping("/add")
		public String addProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult) {
			profeRepo.save(profeNew);
			return "redirect:/profesores";
		}
		
		@PostMapping("/edit/{id}")
		public String editarProfe(@PathVariable Integer id, @ModelAttribute("profeaEditar") Profesor profeEditado, BindingResult bidingresult) {
			Profesor profeaEditar=profeRepo.findById(id).get();
			profeaEditar.setNombre(profeEditado.getNombre());
			profeRepo.save(profeaEditar);
			return "redirect:/profesores";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteProfe(Model model, @PathVariable Integer id) {
			Profesor profeaEliminar=profeRepo.findById(id).get();
			profeRepo.delete(profeaEliminar);
			return "redirect:/profesores";
		}

}
