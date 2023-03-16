package principal.controller;

import java.util.ArrayList;
import java.util.List;

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
import principal.modelo.Llave;
import principal.modelo.Profesor;
import principal.modelo.ProfesoresCoches;
import principal.modelo.dto.ProfesorBuscarDniDTO;
import principal.modelo.dto.ProfesorBuscarNameDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.LlaveServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;



	@Controller
	@RequestMapping("/profesores")
	public class ProfesoresController {
		
		/*AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();*/
		
		@Autowired
		private AlumnoServiceImpl alumnoService;
		@Autowired
		private ProfesorServiceImpl profeService;
		@Autowired
		private CocheServiceImpl cocheService;
		@Autowired
		private LlaveServiceImpl llaveService;
		
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("profeNuevo", new Profesor());
			model.addAttribute("profeaEditar", new Profesor());
			model.addAttribute("profeaBuscar", new Profesor());
			return "profesores";
		}
		
		@PostMapping("/add")
		public String addProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult) {
			profeService.insertarProfesor(profeNew);
			return "redirect:/profesores";
		}
		
		@GetMapping({"/{id}"})
		String idProfesor(Model model, @PathVariable Integer id) {
			Profesor profeMostrar=profeService.obtenerProfesorPorId(id);
			model.addAttribute("profeMostrar", profeMostrar);
			return "profesor";
		}
		
		@PostMapping("/edit/{id}")
		public String editarProfe(@PathVariable Integer id, @ModelAttribute("profeaEditar") Profesor profeEditado, BindingResult bidingresult) {
			Profesor profeaEditar=profeService.obtenerProfesorPorId(id);
			profeaEditar.setNombre(profeEditado.getNombre());
			profeService.insertarProfesor(profeaEditar);
			return "redirect:/profesores";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteProfe(Model model, @PathVariable Integer id) {
			Profesor profeaEliminar=profeService.obtenerProfesorPorId(id);
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
			ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
			List<Llave> misLlaves=llaveService.listarLlaves();
			int profe=(int) (Math.random()*misProfesores.size());
			Llave llaveTemp=null;
	
			
			for(Alumno a:misAlumnos) {
				if(a.getProfesor().getId()==profeaEliminar.getId()) {
					
					
					do {
						 profe=(int)(Math.random()*(misProfesores.size()));
					}while(profe==misProfesores.indexOf(a.getProfesor()));//si solo queda un profesor no elimina, solucionar
					a.setProfesor(misProfesores.get(profe));
					
					misProfesores.get(profe).getAlumnos().add(a);
					alumnoService.insertarAlumno(a);
					profeService.insertarProfesor(misProfesores.get(profe));
				}
				}
			
			for(Llave a:misLlaves) {
				if(a.getProfesor()!=null) {
				if(a.getProfesor().getProfesor()==profeaEliminar) {
					a.setCoche(null);
					a.setProfesor(null);
					llaveTemp=a;
				}
				}
				}
			
			for(Coche a:misCoches) {
				
					for(ProfesoresCoches c:a.getProfesores()) {
						
						if(c.getProfesor()==profeaEliminar) {
							c.setCoche(null);
					c.setProfesor(null);
					c.setLlave(null);
						}
						
					}
				}
			
			profeaEliminar.getAlumnos().clear();
			profeaEliminar.getCoches().clear();
			profeaEliminar.getLlaves().clear();
			
			profeService.eliminarProfesor(profeaEliminar);
			llaveService.eliminarLlave(llaveTemp);
			return "redirect:/profesores";
		}
		
		@PostMapping({"/searchName"})
		String buscarProfesorPorNombre(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarNameDTO profeBuscado, BindingResult bidingresult) {
			ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.encontrarProfesoresPorNombre(profeBuscado.getNombre());
			
			model.addAttribute("profesBuscados",misProfesores);
			
			
			return "profesoresBuscados";
			
		}
		
		@PostMapping({"/searchDni"})
		String buscarProfesorPorDni(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarDniDTO profeBuscado, BindingResult bidingresult) {
			ArrayList<Profesor> profesor= profeService.encontrarProfesorPorDni(profeBuscado.getDni());
			
			model.addAttribute("profesBuscados",profesor);
			
			
			return "profesoresBuscados";
			
		}

}
