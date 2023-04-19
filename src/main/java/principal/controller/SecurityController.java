package principal.controller;


import java.io.File;
import java.io.IOException;
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

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Llave;
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
import principal.servicio.implementacion.LlaveServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/seguridad/password")
@Controller
public class SecurityController {
	
	@Autowired UsuarioServiceImpl userService;
	@Autowired ProfesorServiceImpl profeService;
	@Autowired CocheServiceImpl cocheService;
	@Autowired AlumnoServiceImpl alumnoService;
	@Autowired LlaveServiceImpl llaveService;

	
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
	public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult) {
		Profesor profeNuevo=alumnoNew.getProfesor();
		alumnoNew.setProfesor(profeNuevo);
		profeNuevo.getAlumnos().add(alumnoNew);
		if(!profeNuevo.getCoches().contains(alumnoNew.getCoche())) {
			
			Llave l=new Llave();
			cocheService.insertarCoche(alumnoNew.getCoche());
			profeService.insertarProfesor(profeNuevo);
			llaveService.insertarLlave(l);
			profeNuevo.juegoLlaves(alumnoNew.getCoche(), l);
			llaveService.insertarLlave(l);
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
	String deleteProfe(@ModelAttribute("profeaEliminar") EntityIdDTO profe) {
		Profesor profeaEliminar=profeService.obtenerProfesorPorId(profe.getId());
		ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
		ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
		List<Llave> misLlaves=llaveService.listarLlaves();
		int profesor=(int) (Math.random()*misProfesores.size());
		Llave llaveTemp=null;

		
		for(Alumno a:misAlumnos) {
			if(a.getProfesor().getId()==profeaEliminar.getId()) {
				
				
				do {
					int s=misProfesores.size();
					 profesor=(int)(Math.random()*(misProfesores.size()));
				}while(profesor==misProfesores.indexOf(a.getProfesor()));
				a.setProfesor(misProfesores.get(profesor));
				
				misProfesores.get(profesor).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				profeService.insertarProfesor(misProfesores.get(profesor));
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
		if(llaveTemp!=null)
		llaveService.eliminarLlave(llaveTemp);
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
		/*LocalDate f=cocheNew.getFechaITV();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String fechaFormateada=f.format(formatter);
		String[] fechaString=fechaFormateada.split(" ");
		Integer[] fecha= {Integer.parseInt(fechaString[0]),
		Integer.parseInt(fechaString[1]),
		Integer.parseInt(fechaString[2])};
		LocalDate fechaAInsertar=LocalDate.of(fecha[0], fecha[1], fecha[3]);
		cocheNew.setFechaITV(fechaAInsertar);*/
		cocheService.insertarCoche(cocheNew);
		return "redirect:/seguridad/password#operat";
	}
	
	@GetMapping({"/deleteCoche"})
	String deleteCoche(@ModelAttribute("cocheaEliminar")Coche cocheEliminar) {
		Coche cocheaEliminar=cocheService.obtenerCochePorId(cocheEliminar.getId());
		ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Profesor> misProfes=(ArrayList<Profesor>) profeService.listarProfesores();
		List<Llave> misLlaves=llaveService.listarLlaves();
		for(Alumno a:misAlumnos) {
			if(a.getCoche().getId()==cocheaEliminar.getId()) {
				int coche=(int) (Math.random()*misCoches.size());
				if(misCoches.isEmpty()|misCoches.size()==0) {
					misCoches.add(new Coche("5678 GHS","2021 XS","Nissan"));//conceptual,aun no sirve
					LocalDate fecha=LocalDate.of(2025, 6, 16);
					//misCoches.get(0).setFechaITV(fecha);
					a.setCoche(misCoches.get(0));//si no queda ningun coche crear coche generico
				}else {
				
				
				do {
					 coche=(int)(Math.random()*misCoches.size());
				}while(coche==misCoches.indexOf(a.getCoche()));
				a.setCoche(misCoches.get(coche));
				}
				misCoches.get(coche).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				cocheService.insertarCoche(misCoches.get(coche));
			}
			}
		
		for(Llave a:misLlaves) {
			if(a.getCoche()!=null) {
			if(a.getCoche().getCoche()==cocheaEliminar) {
				a.setCoche(null);
				a.setProfesor(null);
			}
			}
			}
		
		for(Profesor a:misProfes) {
			
				for(ProfesoresCoches c:a.getCoches()) {
					if(c.getCoche()==cocheaEliminar) {
						c.setCoche(null);
				c.setProfesor(null);
				c.setLlave(null);
					}
					
				}
			}
			
		cocheaEliminar.getAlumnos().clear();
		cocheaEliminar.getLlaves().clear();
		cocheaEliminar.getProfesores().clear();
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
