package principal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Coche;
import principal.modelo.ImageInfo;
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
import principal.servicio.interfaces.FileStorageService;
import springfox.documentation.annotations.ApiIgnore;
//un controlador que actua como controlador global, acoge casi todas las operaciones, se ha creado aparte por variaciones en la gestion de entidades y porque devuelve distintas páginas
@RequestMapping("/seguridad/password")
@Controller
public class SecurityController {

	@Autowired
	UsuarioServiceImpl userService;
	@Autowired
	ProfesorServiceImpl profeService;
	@Autowired
	CocheServiceImpl cocheService;
	@Autowired
	AlumnoServiceImpl alumnoService;
	@Autowired
	FileStorageService storageService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping(value = { "", "/" })//carga de recursos
	String homeSecurity(Model model, HttpServletRequest request) {
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
	model.addAttribute("listaAlumnos",alumnoService.listarAlumnos());
	boolean vacio = (Boolean) model.asMap().getOrDefault("vacio", false);
    model.addAttribute("vacio", vacio);//modelo para ultimo profesor
    int status= (int) model.asMap().getOrDefault("status", 0);
    model.addAttribute("status",status);
    if(actualUser.getImagenPerfil()!=null) {
		Resource resource = storageService.load(actualUser.getImagenPerfil());
		String url = MvcUriComponentsBuilder.fromMethodName(SecurityController.class, "serveFile", resource.getFilename())
		    .build().toString();
		ImageInfo img = new ImageInfo(resource.getFilename(), url);

	model.addAttribute("img",img);
	}
	return "cambioPassword";
	}
	
	  @GetMapping("/images/{filename:.+}")//carga de imagenes
	  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		    Resource file = storageService.load(filename);

		    return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
		  }

	@PostMapping("/changePassword")//cambio de contraseña
	public String cambioPassword(@ModelAttribute("usuarioPassword") CambioContrasenaDTO userDTO, BindingResult bidingresult,Model model,RedirectAttributes redirec) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    Usuario actualUser =(Usuario) auth.getPrincipal();
		    int status=0;//estado de la operacion para mostrar el mensaje
		    if(encoder.matches(userDTO.getActual(), actualUser.getPassword()) && userDTO.getNueva().equals(userDTO.getConfirm())) {//si la contraseña actual es correcta y la nueva es la misma que la de confirmacion
		    	
		    	actualUser.setPassword(encoder.encode(userDTO.getNueva()));//la encripta
		    	userService.insertarUsuario((Usuario) actualUser);
		    	status=1;
		    }else {
		    	status=2;
		    }
		    redirec.addFlashAttribute("status",status);//subimos el estado para mostrar en la pagina
		return "redirect:/seguridad/password#contras";
	}

	@PostMapping("/changeData")//cambios de datos
	public String cambioDatos(@ModelAttribute("usuarioActual") UsuarioNombreUsernameImageDTO user,
			BindingResult bidingresult, @RequestParam(value="file", required=false) MultipartFile file,Model model, RedirectAttributes redirectAttributes) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario actualUser = (Usuario) auth.getPrincipal();

		// Guardar imagen

		try {
			if(actualUser.extensionValida(file)) {
			storageService.save(file);
			if(actualUser.getImagenPerfil()!=null) {//si ya tenia imagen de perfil la borra
				storageService.delete(actualUser.getImagenPerfil());
			}
			actualUser.setImagenPerfil(file.getOriginalFilename());
			}
			else
				redirectAttributes.addFlashAttribute("error", "La imagen no es válida");//controlamos el error

		} catch (Exception e) {
		}
		

		actualUser.setNombre(user.getNombre());
		actualUser.setUsername(user.getUsername());
		userService.insertarUsuario(actualUser);

		return "redirect:/seguridad/password#data";
	}
	
	//los siguientes metodos son copias de los controllers especificos con pequeñas variaciones

	@PostMapping("/addAlumno")
	public String addAlumno(@ModelAttribute("alumnoNuevo") Alumno alumnoNew, BindingResult bidingresult, RedirectAttributes redirectAttributes) throws SQLException
			 {
		try {
		Profesor profeNuevo = profeService.obtenerProfesorPorId(alumnoNew.getProfesor().getId()).get();
		Coche cocheNuevo = cocheService.obtenerCochePorId(alumnoNew.getCoche().getId()).get();
		String dni=alumnoNew.getDni();
		char letra=dni.charAt(8);
		letra=Character.toUpperCase(letra);
		dni=dni.substring(0,8)+letra;
		alumnoNew.setDni(dni);
		alumnoNew.setProfesor(profeNuevo);
		profeNuevo.getAlumnos().add(alumnoNew);
		alumnoNew.setCoche(cocheNuevo);
		cocheNuevo.getAlumnos().add(alumnoNew);
		if (!profeNuevo.getCoches().contains(cocheNuevo)) {
			cocheService.insertarCoche(cocheNuevo);
			profeService.insertarProfesor(profeNuevo);
			profeNuevo.juegoLlaves(cocheNuevo);
			alumnoService.insertarAlumno(alumnoNew);
		}
		}catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("error", "El DNI ya existe.");
	    }
		return "redirect:/seguridad/password#operat";
	}

	@GetMapping("/deleteAlumno")
	String deleteAlumno(@ModelAttribute("alumnoaEliminar") EntityIdDTO a, RedirectAttributes redirectAttributes) {
		Optional<Alumno> alumnoaEliminar = alumnoService.obtenerAlumnoPorId(a.getId());
		if(!alumnoaEliminar.isEmpty())
		alumnoService.eliminarAlumno(alumnoaEliminar.get());
		else
			redirectAttributes.addFlashAttribute("error", "El alumno no existe.");

		return "redirect:/seguridad/password#operat";
	}

	@PostMapping("/searchAlumnoByName")
	String buscarAlumnoPorNombre(Model model, @ModelAttribute("alumnoaBuscar") AlumnoBuscarNameDTO alumnoBuscado,
			BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos = alumnoService.encontrarAlumnosPorNombre(alumnoBuscado.getNombre());
		model.addAttribute("alumnosBuscados", misAlumnos);

		return "AlumnosBuscados";

	}

	@PostMapping("/searchAlumnoByDni")
	String buscarAlumnoPorDni(Model model, @ModelAttribute("alumnoaBuscar") AlumnoBuscarDniDTO alumnoBuscado,
			BindingResult bidingresult) {
		ArrayList<Alumno> misAlumnos = alumnoService.encontrarAlumnosPorDni(alumnoBuscado.getDni());
		model.addAttribute("alumnosBuscados", misAlumnos);

		return "AlumnosBuscados";

	}

	@PostMapping("/addProfesor")
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
		
		return "redirect:/seguridad/password#operat";
	}

	@GetMapping("/deleteProfesor")
	String deleteProfe(@ModelAttribute("profeaEliminar") EntityIdDTO profesor, @ApiIgnore RedirectAttributes redirectAttributes) throws SQLException {
		Optional <Profesor> profeaEliminar=profeService.obtenerProfesorPorId(profesor.getId());
		ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
		ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
		int profe=(int) (Math.random()*misProfesores.size());
		ArrayList<Profesor> profeTemp=new ArrayList<Profesor>();
		ArrayList<Coche> cocheTemp=new ArrayList<Coche>();
		boolean vacio=false;
		if(!profeaEliminar.isEmpty()) {
		if(misProfesores.size()!=1) {
		for(Alumno a:misAlumnos) {//para que los alumnos no se queden sin profesor
			if(a.getProfesor().getId()==profeaEliminar.get().getId()) {
				
				
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
			profeTemp.get(i).juegoLlaves(cocheTemp.get(i));
		}
		}
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://mysqldb:3306/proyecto", "usuario","usuario");

	      String sql = "DELETE FROM profesores_coches WHERE profesor_id = "+profeaEliminar.get().getId();

	      Statement statement = connection.createStatement();
	      statement.executeUpdate(sql);

	      connection.close();
		profeService.eliminarProfesor(profeaEliminar.get());
		}else {
			vacio=true;
		}
		}else
		redirectAttributes.addFlashAttribute("error","El profesor no existe.");
		redirectAttributes.addFlashAttribute("vacio", vacio);
		return "redirect:/seguridad/password#operat";
	}

	@PostMapping("/searchProfesorByName")
	String buscarProfesorPorNombre(Model model, @ModelAttribute("profeaBuscar") ProfesorBuscarNameDTO profeBuscado,
			BindingResult bidingresult) {
		ArrayList<Profesor> misProfes = profeService.encontrarProfesoresPorNombre(profeBuscado.getNombre());
		model.addAttribute("profesBuscados", misProfes);

		return "profesoresBuscados";

	}

	@PostMapping({ "/searchProfesorByDni" })
	String buscarProfesorPorDni(Model model, @ModelAttribute("profeaBuscar") ProfesorBuscarDniDTO profeBuscado,
			BindingResult bidingresult) {
		ArrayList<Profesor> profesor = profeService.encontrarProfesorPorDni(profeBuscado.getDni());

		model.addAttribute("profesBuscados", profesor);

		return "profesoresBuscados";

	}

	@PostMapping("/addCoche")
	public String addCoche(@ModelAttribute("cocheNuevo") Coche cocheNew, BindingResult bidingresult, @ApiIgnore RedirectAttributes redirectAttributes) {
		try {
		String matricula = cocheNew.getMatricula();
		String numeros = matricula.substring(0, 4);
		String letras = matricula.substring(5, 8).toUpperCase();
		String matriculaFinal = numeros + " " + letras;
		cocheNew.setMatricula(matriculaFinal);
		cocheService.insertarCoche(cocheNew);
		}catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("error", "La matrícula ya existe.");
	    }
		return "redirect:/seguridad/password#operat";
	}

	@GetMapping({ "/deleteCoche" })
	String deleteCoche(@ModelAttribute("cocheaEliminar") EntityIdDTO cocheEliminar, RedirectAttributes redirectAttributes) throws SQLException {
		Optional <Coche> cocheaEliminar = cocheService.obtenerCochePorId(cocheEliminar.getId());
		ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Coche> misCoches = (ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Profesor> misProfes = (ArrayList<Profesor>) profeService.listarProfesores();
		if(!cocheaEliminar.isEmpty()) {
		ArrayList<Profesor> profeTemp = new ArrayList<Profesor>();// almacenamos las nuevas combinaciones de profesores															// y coches
		ArrayList<Coche> cocheTemp = new ArrayList<Coche>();
		for (Alumno a : misAlumnos) {
			if (a.getCoche().getId() == cocheaEliminar.get().getId()) {
				int coche = (int) (Math.random() * misCoches.size());
				if (misCoches.isEmpty() || misCoches.size() == 1) {
					Coche cGenerico = new Coche("5678 GHS", "2021 XS", "Nissan");
					misCoches.add(cGenerico);
					// misCoches.get(0).setFechaITV(fecha);
					a.setCoche(cGenerico);
					for(Clase c:a.getClases()) {
						c.setCoche(cGenerico);
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
					cocheService.insertarCoche(cGenerico);
				} else {

					do {
						coche = (int) (Math.random() * misCoches.size());
					} while (coche == misCoches.indexOf(a.getCoche()));
					a.setCoche(misCoches.get(coche));
					for(Clase c:a.getClases()) {
						c.setCoche(misCoches.get(coche));
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
				}
				misCoches.get(coche).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				profeService.insertarProfesor(profeService.obtenerProfesorPorId(a.getProfesor().getId()).get());
				cocheService.insertarCoche(cocheService.obtenerCochePorId(misCoches.get(coche).getId()).get());//
			}
		}

		List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();

		for (Profesor a : misProfes) {
			if (!a.getCoches().isEmpty()) {
				for (ProfesoresCoches c : a.getCoches()) {
					if (c.getCoche().getId() == cocheaEliminar.get().getId()) {
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

		cocheaEliminar.get().getAlumnos().clear();
		cocheaEliminar.get().getProfesores().clear();
		if (profeTemp != null) {
			for (int i = 0; i < profeTemp.size(); i++) {

				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));
			}
		}
		Connection connection = DriverManager.getConnection("jdbc:mysql://mysqldb:3306/proyecto", "usuario","usuario");

		String sql = "DELETE FROM profesores_coches WHERE coche_id = " + cocheaEliminar.get().getId();

		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);

		connection.close();
		cocheService.eliminarCoche(cocheaEliminar.get());
		}else
			redirectAttributes.addFlashAttribute("error", "El coche no existe.");
			

		return "redirect:/seguridad/password#operat";
	}

	@PostMapping({ "/searchCochesByMarca" })
	String buscarCochePorMarca(Model model, @ModelAttribute("cocheaBuscar") CocheBuscarMarcaDTO cocheBuscado,
			BindingResult bidingresult) {
		ArrayList<Coche> cochesMarca = cocheService.obtenerCochesPorMarca(cocheBuscado.getMarca());
		model.addAttribute("cochesMarca", cochesMarca);

		return "cochesBuscadosPorMarca";

	}

	@PostMapping({ "/searchCocheByMatricula" })
	String buscarCochePorMatricula(Model model, @ModelAttribute("cocheaBuscar") CocheBuscarMatriculaDTO cocheBuscado,
			BindingResult bidingresult) {
		String matricula = cocheBuscado.getMatricula();
		String letras = matricula.substring(5, 8).toUpperCase();
		String mat = matricula.substring(0, 4) + " " + letras;
		Optional<Coche> cochematricula = cocheService.encontrarCochePorMatricula(mat);
		if (!cochematricula.isEmpty()) {
			Integer id = cochematricula.get().getId();
			return "redirect:/coches/" + id;
		} else {
			ArrayList<Coche> cocheVacio = new ArrayList<Coche>();
			model.addAttribute("cochesMarca", cocheVacio);
			return "cochesBuscadosPorMarca";
		}

	}

	@PostMapping("/addUsuario")
	public String addUsuario(@ModelAttribute("newUserDTO") UsuarioDTO usuarioNew, BindingResult bidingresult, RedirectAttributes redirectAttributes) {
		List<Usuario> usuarios = userService.listarUsuarios();
		boolean duplicado = false;
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(usuarioNew.getNombre())) {
                redirectAttributes.addFlashAttribute("error", "El usuario ya existe.");
                duplicado = true;}
		}
		
		if (!duplicado) {
		
		if (usuarioNew.isEsProfesor()) {
			Usuario userProfesor = new Usuario();
			userProfesor.setNombre(usuarioNew.getNombre());
			userProfesor.setPassword(usuarioNew.getPassword());
			userProfesor.setUsername(usuarioNew.getUsername());
			userProfesor.setIdProfesor(usuarioNew.getIdProfesor());
			userService.insertarUsuarioProfesor(userProfesor);
		} else {
			if(usuarioNew.isEsAlumno()) {
				Usuario userAlumno = new Usuario();
				userAlumno.setNombre(usuarioNew.getNombre());
				userAlumno.setPassword(usuarioNew.getPassword());
				userAlumno.setUsername(usuarioNew.getUsername());
				userAlumno.setIdAlumno(usuarioNew.getIdAlumno());
				userService.insertarUsuarioAlumno(userAlumno);
			}else {

			userService.insertarUsuarioDTO(usuarioNew);
			}
		}
		}

		return "redirect:/seguridad/password#operat";
	}

	@GetMapping({ "/deleteUsuario" })
	String deleteUsuario(Model model, @ModelAttribute("userDelete") EntityIdDTO user, RedirectAttributes redirectAttributes) {
		Optional<Usuario> usuarioaEliminar = userService.obtenerUsuarioPorId(user.getId());
		if (!usuarioaEliminar.isEmpty() && !usuarioaEliminar.get().esAdmin())
		userService.eliminarUsuario(usuarioaEliminar.get());
		else
			redirectAttributes.addFlashAttribute("error", "No puedes borrar a un administrador.");
		return "redirect:/seguridad/password#operat";
	}
	
	@PostMapping("/cambioTema")//cambio de tema de la pagina
	public String cambioTema(@RequestBody String temaSeleccionado, RedirectAttributes redirectAttributes, HttpSession session) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    ArrayList<String> temas = new ArrayList<>(List.of("temaClaro", "temaOscuro", "temaMinimalista", "temaElegante","temaShock"));//lista de temas aceptados
	    if (temas.contains(temaSeleccionado)) {//si se ha seleccionado un tema valido se establece para el usuario
	        actualUser.setTema(temaSeleccionado);
	        userService.insertarUsuario(actualUser);
	    }
	    return "redirect:/seguridad/password";
	}

}
