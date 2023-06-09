package principal.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.AjaxResponseBody;
import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.dto.AlumnoAjaxDTO;
import principal.modelo.dto.AlumnoBuscarDniDTO;
import principal.modelo.dto.AlumnoBuscarNameDTO;
import principal.modelo.dto.AlumnoEditarNotasNombreApellidoDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;
import springfox.documentation.annotations.ApiIgnore;



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
		@Autowired
		private UsuarioServiceImpl usuarioService;
		
		@GetMapping({"","/"})
		String homealumnos(Model model) {//lista de alumnos
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("alumnoNuevo", new Alumno());
			model.addAttribute("alumnoaEditar", new Alumno());
			model.addAttribute("alumnoaBuscar", new Alumno());//carga de recursos
			return "alumnos";
		}
		
		@SuppressWarnings("unlikely-arg-type")
		@PostMapping("/add")
		public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew,RedirectAttributes redirectAttributes,Model model, BindingResult bidingresult) throws SQLException {
			//añadir alumno
			try {
			Profesor profeNuevo=profeService.obtenerProfesorPorId(alumnoNew.getProfesor().getId()).get();//profe del alumno
			Coche cocheNuevo=cocheService.obtenerCochePorId(alumnoNew.getCoche().getId()).get();//coche del alumno
			String dni=alumnoNew.getDni();
			char letra=dni.charAt(8);
			letra=Character.toUpperCase(letra);
			dni=dni.substring(0,8)+letra;
			alumnoNew.setDni(dni);//conversion de letra minuscula a mayuscula
			alumnoNew.setProfesor(profeNuevo);
			profeNuevo.getAlumnos().add(alumnoNew);
			alumnoNew.setCoche(cocheNuevo);
			cocheNuevo.getAlumnos().add(alumnoNew);
			if(!profeNuevo.getCoches().contains(cocheNuevo)) {//si el profesor aun no usa el coche nuevo
				cocheService.insertarCoche(cocheNuevo);
				profeService.insertarProfesor(profeNuevo);
				profeNuevo.juegoLlaves(cocheNuevo);//crea la combinacion y altera las tablas de la BDD
			}
			alumnoService.insertarAlumno(alumnoNew);
			}catch (DataIntegrityViolationException e) {
				redirectAttributes.addFlashAttribute("error", "El DNI ya existe.");//controlar DNI duplicado
		    }
			return "redirect:/alumnos";
		}
		
		@PostMapping("/edit/{id}")
		public String editarAlumno(@PathVariable Integer id, @ModelAttribute("alumnoaEditar") AlumnoEditarNotasNombreApellidoDTO alumnoEditado, BindingResult bidingresult) {
			//editar alumno
			Alumno alumnoaEditar=alumnoService.obtenerAlumnoPorId(id).get();
			if(alumnoEditado.getNombre()!=null)//verificamos datos y asignamos
			if(!alumnoEditado.getNombre().equals(""))
			alumnoaEditar.setNombre(alumnoEditado.getNombre());
			
			if(alumnoEditado.getApellidos()!=null)
			if(!alumnoEditado.getApellidos().equals(""))
				alumnoaEditar.setApellidos(alumnoEditado.getApellidos());
			
			if(alumnoEditado.getNotas()!=null)
			if(!alumnoEditado.getNotas().equals(""))
			alumnoaEditar.setNotas(alumnoEditado.getNotas());
			alumnoService.insertarAlumno(alumnoaEditar);//guardamos
			return "redirect:/alumnos/"+id;
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteAlumno(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes) {//borrar alumno
			if(usuarioService.esAdminActual()) {//solo se ejecuta si lo hace el admin
			Optional<Alumno> alumnoaEliminar=alumnoService.obtenerAlumnoPorId(id);
			if (!alumnoaEliminar.isEmpty())//si existe el alumno lo borra
			alumnoService.eliminarAlumno(alumnoaEliminar.get());
			else
				redirectAttributes.addFlashAttribute("error", "El alumno no existe.");// controlar alumno inexistente
			}
			
			return "redirect:/alumnos";
		}
		
		@GetMapping({"/{id}"})
		String idAlumno(@ApiIgnore Model model, @PathVariable Integer id, @ApiIgnore HttpSession session, RedirectAttributes redirectAttributes) {//ver alumno
				Optional<Alumno> alumnoMostrar = alumnoService.obtenerAlumnoPorId(id);
				if (!alumnoMostrar.isEmpty()) {//si existe el alumno
				Object[] clases = alumnoMostrar.get().getClases().toArray();//lista de sus clases
				model.addAttribute("alumnoMostrar", alumnoMostrar);
				model.addAttribute("listaClases", clases);
				model.addAttribute("claseNueva", new Clase());
				session.setAttribute("alumnoaImpartir", alumnoMostrar);//se sube a la sesión para acceder desde el controlador clases y establecer el alumno si se crea una clase
				}else {
					redirectAttributes.addFlashAttribute("error", "El alumno no existe.");// controlar alumno inexistente
					return "redirect:/alumnos";
				}
					
			return "alumno";
		}
		
		@PostMapping({"/searchName"})
		String buscarAlumnoPorNombre(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarNameDTO alumnoBuscado, BindingResult bidingresult) {//buscar alumno por nombre
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
		String buscarAlumnoPorDni(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarDniDTO alumnoBuscado, BindingResult bidingresult) {//buscar alumno por dni
			ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
			model.addAttribute("alumnosBuscados",misAlumnos);
			
			
			
			return "AlumnosBuscados";
			
		}
		
		@GetMapping({"/getAll"})
		List<Alumno> obtenerTodosAlumnos(Model model,BindingResult bidingresult) {//listar todos los alumnos
			List<Alumno> misAlumnos= alumnoService.listarAlumnos();
			
			
			
			return misAlumnos;
			
		}
		
		@PostMapping(value={"/guardarAjax"})
		public ResponseEntity<?> ajaxAlumno(@RequestBody AlumnoAjaxDTO alumno) {//guardar alumno AJAX
			
			AjaxResponseBody alumnoJSON=new AjaxResponseBody();
			
			Alumno alumnoEditado=alumnoService.obtenerAlumnoPorId(alumno.getId()).get();
			alumnoEditado.setNombre(alumno.getNombre());
			alumnoService.insertarAlumno(alumnoEditado);
			
			alumnoJSON.setMensaje("Correcto");
			
			alumnoJSON.setAlumnoJSON(alumnoEditado);
			
			//meollo
			return ResponseEntity.ok(alumnoJSON);
		}

		

		

		

}
