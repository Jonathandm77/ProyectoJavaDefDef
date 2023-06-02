package principal.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.ProfesoresCoches;
import principal.modelo.dto.EntidadNombreApellidoDTO;
import principal.modelo.dto.ProfesorBuscarDniDTO;
import principal.modelo.dto.ProfesorBuscarNameDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import springfox.documentation.annotations.ApiIgnore;



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
			boolean vacio = (Boolean) model.asMap().getOrDefault("vacio", false);
		    model.addAttribute("vacio", vacio);
			return "profesores";
		}
		
		@PostMapping("/add")
		public String addProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult, @ApiIgnore RedirectAttributes redirectAttributes) {
			try {
			String dni=profeNew.getDni();
			char letra=dni.charAt(8);
			letra=Character.toUpperCase(letra);
			dni=dni.substring(0,8)+letra;
			profeNew.setDni(dni);
			profeService.insertarProfesor(profeNew);
			}catch (DataIntegrityViolationException e) {
				redirectAttributes.addFlashAttribute("error", "El DNI ya existe.");
		    }
			return "redirect:/profesores";
		}
		
		@GetMapping({"/{id}"})
		String idProfesor(Model model, @PathVariable Integer id) {
			Profesor profeMostrar=profeService.obtenerProfesorPorId(id);
			model.addAttribute("profeMostrar", profeMostrar);
			return "profesor";
		}
		
		@PostMapping("/edit/{id}")
		public String editarProfe(@PathVariable Integer id, @ModelAttribute("profeaEditar") EntidadNombreApellidoDTO profeEditado, BindingResult bidingresult) {
			Profesor profeaEditar=profeService.obtenerProfesorPorId(id);
			if(!profeEditado.getNombre().equals(""))
			profeaEditar.setNombre(profeEditado.getNombre());
			if(!profeEditado.getApellidos().equals(""))
				profeaEditar.setApellidos(profeEditado.getApellidos());
			profeService.insertarProfesor(profeaEditar);
			return "redirect:/profesores";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteProfe(Model model, @PathVariable Integer id, @ApiIgnore RedirectAttributes redirectAttributes) throws SQLException {
			Profesor profeaEliminar=profeService.obtenerProfesorPorId(id);
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
			ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
			int profe=(int) (Math.random()*misProfesores.size());
			ArrayList<Profesor> profeTemp=new ArrayList<Profesor>();
			ArrayList<Coche> cocheTemp=new ArrayList<Coche>();
			boolean vacio=false;
	
			if(misProfesores.size()!=1) {
			for(Alumno a:misAlumnos) {//para que los alumnos no se queden sin profesor
				if(a.getProfesor().getId()==profeaEliminar.getId()) {
					
					
					do {
						 profe=(int)(Math.random()*(misProfesores.size()));
					}while(profe==misProfesores.indexOf(a.getProfesor()));//si solo queda un profesor no elimina, solucionar
					a.setProfesor(misProfesores.get(profe));
					for(Clase c:a.getClases()) {
						c.setProfesor(a.getProfesor());
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
					
					misProfesores.get(profe).getAlumnos().add(a);
					alumnoService.insertarAlumno(a);
				}
				}
			
			List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();

			for(Coche a: misCoches) {
			    if(!a.getProfesores().isEmpty()) {
			        for(ProfesoresCoches c: a.getProfesores()) {
			            if(c.getProfesor().getId() == profeaEliminar.getId()) {
			                c.setCoche(null);
			                c.setProfesor(null);
			                elementosAEliminar.add(c);
			            }
			        }
			        a.getProfesores().removeAll(elementosAEliminar);
			        elementosAEliminar.clear();
			    }
			    cocheService.insertarCoche(a);
			}

			
			profeaEliminar.getAlumnos().clear();
			profeaEliminar.getCoches().clear();
			if(profeTemp!=null) {
			for(int i=0;i<profeTemp.size();i++) {
				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));
			}
			}
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://mysqldb:3306/proyecto", "usuario","usuario");

		      String sql = "DELETE FROM profesores_coches WHERE profesor_id = "+profeaEliminar.getId();

		      Statement statement = connection.createStatement();
		      statement.executeUpdate(sql);

		      connection.close();
			profeService.eliminarProfesor(profeaEliminar);
			}else {
				vacio=true;
			}
			redirectAttributes.addFlashAttribute("vacio", vacio);
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
