package principal.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import principal.servicio.implementacion.UsuarioServiceImpl;
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
		@Autowired
		UsuarioServiceImpl usuarioService;
		
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {//carga de recursos
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("profeNuevo", new Profesor());
			model.addAttribute("profeaEditar", new Profesor());
			model.addAttribute("profeaBuscar", new Profesor());
			boolean vacio = (Boolean) model.asMap().getOrDefault("vacio", false);//accedemos al atributo subido por si era el ultimo profesor para mostrar el mensaje
		    model.addAttribute("vacio", vacio);
			return "profesores";
		}
		
		@PostMapping("/add")//añade un nuevo profesor
		public String addProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult, @ApiIgnore RedirectAttributes redirectAttributes) {
			try {
			String dni=profeNew.getDni();
			char letra=dni.charAt(8);
			letra=Character.toUpperCase(letra);
			dni=dni.substring(0,8)+letra;
			profeNew.setDni(dni);//adaptamos el DNI a mayusculas
			profeService.insertarProfesor(profeNew);
			}catch (DataIntegrityViolationException e) {
				redirectAttributes.addFlashAttribute("error", "El DNI ya existe.");//controlamos si el dni ya existe
		    }
			return "redirect:/profesores";
		}
		
		@GetMapping({"/{id}"})//ver profesor
		String idProfesor(Model model, @PathVariable Integer id) {
			Profesor profeMostrar=profeService.obtenerProfesorPorId(id).get();
			model.addAttribute("profeMostrar", profeMostrar);
			return "profesor";
		}
		
		@PostMapping("/edit/{id}")//editar profesor
		public String editarProfe(@PathVariable Integer id, @ModelAttribute("profeaEditar") EntidadNombreApellidoDTO profeEditado, BindingResult bidingresult) {
			Profesor profeaEditar=profeService.obtenerProfesorPorId(id).get();
			if(!profeEditado.getNombre().equals(""))
			profeaEditar.setNombre(profeEditado.getNombre());
			if(!profeEditado.getApellidos().equals(""))//validamos campos
				profeaEditar.setApellidos(profeEditado.getApellidos());
			profeService.insertarProfesor(profeaEditar);//actualizamos
			return "redirect:/profesores";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteProfe(Model model, @PathVariable Integer id, @ApiIgnore RedirectAttributes redirectAttributes) throws SQLException {
			if(usuarioService.esAdminActual()) {//si el usuario es admin, como medida de seguridad
			Optional<Profesor> profeaEliminar=profeService.obtenerProfesorPorId(id);
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
			ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
			int profe=(int) (Math.random()*misProfesores.size());
			ArrayList<Profesor> profeTemp=new ArrayList<Profesor>();
			ArrayList<Coche> cocheTemp=new ArrayList<Coche>();//combinaciones de coches y profesores a borrar
			boolean vacio=false;
			
			if(!profeaEliminar.isEmpty()) {
			if(misProfesores.size()!=1) {
			for(Alumno a:misAlumnos) {//para que los alumnos no se queden sin profesor
				if(a.getProfesor().getId()==profeaEliminar.get().getId()) {
					
					
					do {
						 profe=(int)(Math.random()*(misProfesores.size()));//asigna un nuevo profesor a los alumnos del profesor que vamos a borrar
					}while(profe==misProfesores.indexOf(a.getProfesor()));
					a.setProfesor(misProfesores.get(profe));
					for(Clase c:a.getClases()) {
						c.setProfesor(a.getProfesor());//lo mismo con las clases del alumno
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
					
					misProfesores.get(profe).getAlumnos().add(a);
					alumnoService.insertarAlumno(a);//actualizamos el alumno
				}
				}
			
			List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();

			for(Coche a: misCoches) {//repite el proceso con los coches
			    if(!a.getProfesores().isEmpty()) {
			        for(ProfesoresCoches c: a.getProfesores()) {
			            if(c.getProfesor().getId() == profeaEliminar.get().getId()) {
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

			
			profeaEliminar.get().getAlumnos().clear();
			profeaEliminar.get().getCoches().clear();
			if(profeTemp!=null) {
			for(int i=0;i<profeTemp.size();i++) {
				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));//crea las asociaciones entre los profesores y los coches que se juntan al haber eliminado el profesor
			}
			}
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root","");

		      String sql = "DELETE FROM profesores_coches WHERE profesor_id = "+profeaEliminar.get().getId();

		      Statement statement = connection.createStatement();
		      statement.executeUpdate(sql);//ejecutamos la consulta para limpiar la tabla de registros inservibles

		      connection.close();
			profeService.eliminarProfesor(profeaEliminar.get());
			}else {
				vacio=true;//si es el ultimo profesor
			}
			}else
				redirectAttributes.addFlashAttribute("error", "El profesor no existe.");//controlamos si el profesor no existe
			redirectAttributes.addFlashAttribute("vacio", vacio);//añadimos la variable vacio para mostrar el mensaje de que es el ultimo profesor
			}
			return "redirect:/profesores";
		}
		
		@PostMapping({"/searchName"})//buscar profesor por nombre
		String buscarProfesorPorNombre(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarNameDTO profeBuscado, BindingResult bidingresult) {
			ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.encontrarProfesoresPorNombre(profeBuscado.getNombre());
			
			model.addAttribute("profesBuscados",misProfesores);
			
			
			return "profesoresBuscados";
			
		}
		
		@PostMapping({"/searchDni"})//buscar profesor por DNI
		String buscarProfesorPorDni(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarDniDTO profeBuscado, BindingResult bidingresult) {
			ArrayList<Profesor> profesor= profeService.encontrarProfesorPorDni(profeBuscado.getDni());
			
			model.addAttribute("profesBuscados",profesor);
			
			
			return "profesoresBuscados";
			
		}

}
