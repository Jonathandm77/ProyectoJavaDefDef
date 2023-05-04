package principal.controller;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.ProfesoresCoches;
import principal.modelo.Usuario;
import principal.modelo.dto.AlumnoBuscarDniDTO;
import principal.modelo.dto.AlumnoBuscarNameDTO;
import principal.modelo.dto.CambioContrasenaDTO;
import principal.modelo.dto.CocheBuscarMarcaDTO;
import principal.modelo.dto.CocheBuscarMatriculaDTO;
import principal.modelo.dto.EntityIdDTO;
import principal.modelo.dto.ProfesorBuscarDniDTO;
import principal.modelo.dto.ProfesorBuscarNameDTO;
import principal.modelo.dto.UsuarioDTO;
import principal.modelo.dto.UsuarioNombreUsernameImageDTO;
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
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    ArrayList<Usuario>listaUsuarios=(ArrayList<Usuario>) userService.listarUsuarios();
	model.addAttribute("usuarioPassword", new CambioContrasenaDTO());
	model.addAttribute("usuarioActual", actualUser);
	model.addAttribute("alumnoNuevo", new Alumno());
	model.addAttribute("alumnoaEliminar",new Alumno());
	model.addAttribute("alumnoaBuscar",new Alumno());
	model.addAttribute("listaProfesores", profeService.listarProfesores());
	model.addAttribute("profeNuevo", new Profesor());
	model.addAttribute("profeaEliminar", new Profesor());
	model.addAttribute("profeaBuscar", new Profesor());
	model.addAttribute("cocheNuevo", new Coche());
	model.addAttribute("cocheaEliminar", new Coche());
	model.addAttribute("cocheaBuscar", new Coche());
	model.addAttribute("listaCoches",cocheService.listarCoches());
	model.addAttribute("newUserDTO",new UsuarioDTO());
	model.addAttribute("listaUsuarios",listaUsuarios);
	model.addAttribute("userDelete", new UsuarioDTO());
	boolean vacio = (Boolean) model.asMap().getOrDefault("vacio", false);
    model.addAttribute("vacio", vacio);
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
		    	
		return "redirect:/seguridad/password#contras";
	}
	
	@PostMapping("/changeData")
	public String cambioDatos(@ModelAttribute("usuarioActual") UsuarioNombreUsernameImageDTO user, BindingResult bidingresult, @RequestParam("imagen") MultipartFile imagen) throws IOException {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser =(Usuario) auth.getPrincipal();

	    // Guardar imagen
	    String rutaImagen = "C:\\Users\\jonat\\Documents\\workspace-spring-tool-suite-4-4.17.2.RELEASE\\ProyectoDEF\\src\\main\\resources\\static\\img\\";
	    if (!imagen.isEmpty()) {
	        rutaImagen = "C:\\Users\\jonat\\Documents\\workspace-spring-tool-suite-4-4.17.2.RELEASE\\ProyectoDEF\\src\\main\\resources\\static\\img\\" + imagen.getOriginalFilename();
	        imagen.transferTo(new File(rutaImagen));
	        System.out.println("Ruta de destino: " + new File(rutaImagen).getAbsolutePath());
	    }

	    
	    actualUser.setNombre(user.getNombre());
	    actualUser.setUsername(user.getUsername());
	    actualUser.setRutaImagenPerfil(rutaImagen);
	    userService.insertarUsuario(actualUser);

	    return "redirect:/seguridad/password#data";
	
	    
	    //obtener imagen
	    
	   /* @GetMapping("/usuario/{id}")
	    public String verPerfil(@PathVariable Long id, Model model) {
	        // Obtener instancia de Usuario de la base de datos
	        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
	        if (usuario == null) {
	            return "error404";
	        }
	        
	        // Obtener ruta de la imagen almacenada en el atributo imagenPerfil de Usuario
	        String rutaImagen = usuario.getImagenPerfil();
	        
	        // Cargar imagen desde la ruta obtenida
	        File imagen = new File(rutaImagen);
	        if (imagen.exists()) {
	            // Agregar la imagen al modelo para mostrarla en la página
	            model.addAttribute("imagenPerfil", imagen);
	        }
	        
	        // Agregar el Usuario al modelo para mostrar su información en la página
	        model.addAttribute("usuario", usuario);
	        
	        return "perfil";
	    }*/
	    /*
	    <img src="file:${imagenPerfil.absolutePath}" alt="Imagen de perfil">*/


	}

	
	@PostMapping("/addAlumno")
	public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) throws SQLException {
		Profesor profeNuevo=profeService.obtenerProfesorPorId(alumnoNew.getProfesor().getId());
		Coche cocheNuevo=cocheService.obtenerCochePorId(alumnoNew.getCoche().getId());
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
		return "redirect:/seguridad/password#operat";
	}
	
	
	@GetMapping("/deleteAlumno")
	String deleteAlumno(@ModelAttribute("alumnoaEliminar")EntityIdDTO a) {
		alumnoService.eliminarAlumnoPorId(a.getId());
		
		return "redirect:/seguridad/password#operat";
	}
	
	@PostMapping("/searchAlumnoByName")
	String buscarAlumnoPorNombre(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarNameDTO alumnoBuscado, BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
		model.addAttribute("alumnosBuscados",misAlumnos);
		
		
		return "AlumnosBuscados";
		
	}
	
	@PostMapping("/searchAlumnoByDni")
	String buscarAlumnoPorDni(Model model,@ModelAttribute("alumnoaBuscar") AlumnoBuscarDniDTO alumnoBuscado, BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos= alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
		model.addAttribute("alumnosBuscados",misAlumnos);
		
		
		
		return "AlumnosBuscados";
		
	}
	
	@PostMapping("/addProfesor")
	public String addProfesor(@ModelAttribute("profeNuevo") Profesor profeNew, BindingResult bidingresult) {
		profeService.insertarProfesor(profeNew);
		return "redirect:/seguridad/password#operat";
	}
	
	@GetMapping("/deleteProfesor")
	String deleteProfe(@ModelAttribute("profeaEliminar") EntityIdDTO profesor,RedirectAttributes redirectAttributes) throws SQLException {
		Profesor profeaEliminar=profeService.obtenerProfesorPorId(profesor.getId());
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
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root","");

	      String sql = "DELETE FROM profesores_coches WHERE profesor_id = "+profeaEliminar.getId();

	      Statement statement = connection.createStatement();
	      statement.executeUpdate(sql);

	      connection.close();
		profeService.eliminarProfesor(profeaEliminar);
		}else {
			vacio=true;
		}
		redirectAttributes.addFlashAttribute("vacio", vacio);
		return "redirect:/seguridad/password#operat";
	}
	
	@PostMapping("/searchProfesorByName")
	String buscarProfesorPorNombre(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarNameDTO profeBuscado, BindingResult bidingresult) {
		ArrayList<Profesor> misProfes= profeService.encontrarProfesoresPorNombre(profeBuscado.getNombre());
		model.addAttribute("profesBuscados",misProfes);
		
		
		return "profesoresBuscados";
		
	}
	
	@PostMapping({"/searchProfesorByDni"})
	String buscarProfesorPorDni(Model model,@ModelAttribute("profeaBuscar") ProfesorBuscarDniDTO profeBuscado, BindingResult bidingresult) {
		ArrayList<Profesor> profesor= profeService.encontrarProfesorPorDni(profeBuscado.getDni());
		
		model.addAttribute("profesBuscados",profesor);
		
		
		return "profesoresBuscados";
		
	}
	
	@PostMapping("/addCoche")
	public String addCoche(@ModelAttribute("cocheNuevo") Coche cocheNew, BindingResult bidingresult) {
		cocheService.insertarCoche(cocheNew);
		return "redirect:/seguridad/password#operat";
	}
	
	@GetMapping({"/deleteCoche"})
	String deleteCoche(@ModelAttribute("cocheaEliminar")Coche cocheEliminar) throws SQLException {
		Coche cocheaEliminar=cocheService.obtenerCochePorId(cocheEliminar.getId());
		ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Profesor> misProfes=(ArrayList<Profesor>) profeService.listarProfesores();
		ArrayList<Profesor> profeTemp=new ArrayList<Profesor>();//almacenamos las nuevas combinaciones de profesores y coches
		ArrayList<Coche> cocheTemp=new ArrayList<Coche>();
		for(Alumno a:misAlumnos) {
			if(a.getCoche().getId()==cocheaEliminar.getId()) {
				int coche=(int) (Math.random()*misCoches.size());
				if(misCoches.isEmpty() || misCoches.size()==1) {
					Coche cGenerico=new Coche("5678 GHS","2021 XS","Nissan");
					misCoches.add(cGenerico);
					//misCoches.get(0).setFechaITV(fecha);
					a.setCoche(cGenerico);//si no queda ningun coche crear coche generico
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
					cocheService.insertarCoche(cGenerico);
				}else {
				
				
				do {
					 coche=(int)(Math.random()*misCoches.size());
				}while(coche==misCoches.indexOf(a.getCoche()));
				a.setCoche(misCoches.get(coche));
				profeTemp.add(a.getProfesor());
				cocheTemp.add(a.getCoche());
				}
				misCoches.get(coche).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				profeService.insertarProfesor(profeService.obtenerProfesorPorId(a.getProfesor().getId()));
				cocheService.insertarCoche(cocheService.obtenerCochePorId(misCoches.get(coche).getId()));//
			}
			}
		
		List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();
		
		for(Profesor a:misProfes) {
			if(!a.getCoches().isEmpty()) {
				for(ProfesoresCoches c:a.getCoches()) {
					if(c.getCoche().getId()==cocheaEliminar.getId()) {
						c.setCoche(null);
				c.setProfesor(null);
				elementosAEliminar.add(c);
					}
					
				}
				a.getCoches().removeAll(elementosAEliminar);
		        elementosAEliminar.clear();
			}
			profeService.insertarProfesor(a);
		}
			
		cocheaEliminar.getAlumnos().clear();
		cocheaEliminar.getProfesores().clear();
		if(profeTemp!=null) {
			for(int i=0;i<profeTemp.size();i++) {
				
				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));
			}
			}
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root","");

	      String sql = "DELETE FROM profesores_coches WHERE coche_id = "+cocheaEliminar.getId();

	      Statement statement = connection.createStatement();
	      statement.executeUpdate(sql);

	      connection.close();
		cocheService.eliminarCoche(cocheaEliminar);
		
		return "redirect:/seguridad/password#operat";
		}
	
	@PostMapping({"/searchCochesByMarca"})
	String buscarCochePorMarca(Model model,@ModelAttribute("cocheaBuscar") CocheBuscarMarcaDTO cocheBuscado,BindingResult bidingresult) {
		ArrayList<Coche> cochesMarca= cocheService.obtenerCochesPorMarca(cocheBuscado.getMarca());
		model.addAttribute("cochesMarca",cochesMarca);
		
		
		return "cochesBuscadosPorMarca";
		
	}
	
	@PostMapping({"/searchCocheByMatricula"})
	String buscarCochePorMatricula(Model model,@ModelAttribute("cocheaBuscar") CocheBuscarMatriculaDTO cocheBuscado,BindingResult bidingresult) {
		Coche c=cocheService.encontrarCochePorMatricula(cocheBuscado.getMatricula());
		int id=c.getId();
		
		return "redirect:/coches/"+id;
		
	}
	
	@PostMapping("/addUsuario")
	public String addUsuario(@ModelAttribute("newUserDTO") UsuarioDTO usuarioNew, BindingResult bidingresult) {
		if(usuarioNew.isEsProfesor()) {
			Usuario userProfesor=new Usuario();
			userProfesor.setNombre(usuarioNew.getNombre());
			userProfesor.setPassword(usuarioNew.getPassword());
			userProfesor.setUsername(usuarioNew.getUsername());
			userProfesor.setIdProfesor(usuarioNew.getIdProfesor());
			userService.insertarUsuarioProfesor(userProfesor);
		}else {
			
			userService.insertarUsuarioDTO(usuarioNew);
		}		
		
		return "redirect:/seguridad/password#operat";
	}
	
	@GetMapping({"/deleteUsuario" })
	String deleteUsuario(Model model, @ModelAttribute("userDelete") EntityIdDTO user) {
		Usuario usuarioaEliminar = userService.obtenerUsuarioPorId(user.getId());
		userService.eliminarUsuario(usuarioaEliminar);
		return "redirect:/seguridad/password#operat";
	}
	
}
