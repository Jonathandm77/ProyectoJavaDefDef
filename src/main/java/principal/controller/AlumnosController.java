package principal.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.AjaxResponseBody;
import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.Usuario;
import principal.modelo.dto.AlumnoAjaxDTO;
import principal.modelo.dto.AlumnoBuscarDniDTO;
import principal.modelo.dto.AlumnoBuscarNameDTO;
import principal.modelo.dto.AlumnoEditarNotasNombreApellidoDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;



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
		
		@GetMapping({"","/"})
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
		public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) throws SQLException {
			Profesor profeNuevo=profeService.obtenerProfesorPorId(alumnoNew.getProfesor().getId());
			Coche cocheNuevo=cocheService.obtenerCochePorId(alumnoNew.getCoche().getId());
			String dni=alumnoNew.getDni();
			char letra=dni.charAt(8);
			letra=Character.toUpperCase(letra);
			dni=dni.substring(0,8)+letra;
			alumnoNew.setDni(dni);
			alumnoNew.setProfesor(profeNuevo);
			profeNuevo.getAlumnos().add(alumnoNew);
			alumnoNew.setCoche(cocheNuevo);
			cocheNuevo.getAlumnos().add(alumnoNew);
			if(!profeNuevo.getCoches().contains(cocheNuevo)) {
				cocheService.insertarCoche(cocheNuevo);
				profeService.insertarProfesor(profeNuevo);
				profeNuevo.juegoLlaves(cocheNuevo);
			}
			alumnoService.insertarAlumno(alumnoNew);
			return "redirect:/alumnos";
		}
		
		@PostMapping("/edit/{id}")
		public String editarAlumno(@PathVariable Integer id, @ModelAttribute("alumnoaEditar") AlumnoEditarNotasNombreApellidoDTO alumnoEditado, BindingResult bidingresult) {
			Alumno alumnoaEditar=alumnoService.obtenerAlumnoPorId(id);
			if(alumnoEditado.getNombre()!=null)
			if(!alumnoEditado.getNombre().equals(""))
			alumnoaEditar.setNombre(alumnoEditado.getNombre());
			
			if(alumnoEditado.getApellidos()!=null)
			if(!alumnoEditado.getApellidos().equals(""))
				alumnoaEditar.setApellidos(alumnoEditado.getApellidos());
			
			if(alumnoEditado.getNotas()!=null)
			if(!alumnoEditado.getNotas().equals(""))
			alumnoaEditar.setNotas(alumnoEditado.getNotas());
			alumnoService.insertarAlumno(alumnoaEditar);
			return "redirect:/alumnos/"+id;
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
		
		@PostMapping({"/searchName"})
		String buscarAlumnoPorNombre(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarNameDTO alumnoBuscado, BindingResult bidingresult) {
			ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			return "AlumnosBuscados";
			
		}
		
		@PostMapping({"/searchName2"})
		ArrayList<Alumno> buscarAlumnoPorNombre2(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarNameDTO alumnoBuscado, BindingResult bidingresult) {
			ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			return misAlumnos;
			
		}
		
		@PostMapping({"/searchDni"})
		String buscarAlumnoPorDni(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarDniDTO alumnoBuscado, BindingResult bidingresult) {
			ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			
			return "AlumnosBuscados";
			
		}
		
		@GetMapping({"/getAll"})
		List<Alumno> obtenerTodosAlumnos(Model model,BindingResult bidingresult) {
			List<Alumno> misAlumnos= alumnoService.listarAlumnos();
			
			
			
			return misAlumnos;
			
		}
		
		@PostMapping(value={"/guardarAjax"})
		public ResponseEntity<?> ajaxAlumno(@RequestBody AlumnoAjaxDTO alumno) {
			
			AjaxResponseBody alumnoJSON=new AjaxResponseBody();
			
			Alumno alumnoEditado=alumnoService.obtenerAlumnoPorId(alumno.getId());
			alumnoEditado.setNombre(alumno.getNombre());
			alumnoService.insertarAlumno(alumnoEditado);
			
			alumnoJSON.setMensaje("ta bien");
			
			alumnoJSON.setAlumnoJSON(alumnoEditado);
			
			//meollo
			return ResponseEntity.ok(alumnoJSON);
		}
		

		

		

}
