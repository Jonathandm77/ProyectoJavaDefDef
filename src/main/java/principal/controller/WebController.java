package principal.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Llave;
import principal.modelo.Profesor;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.persistencia.RolRepo;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.LlaveServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;


@Controller
public class WebController {
	
	@Autowired
	AlumnoServiceImpl alumnoService;
	
	@Autowired 
	ProfesorServiceImpl profeService;
	
	@Autowired
	CocheServiceImpl cocheService;
	
	@Autowired
	LlaveServiceImpl llaveService;
	
	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	RolRepo rolRepo;
	
	@GetMapping("/")
	String home() {
		
		 //crearTablas();
		return "index";
			
	}
	

	@GetMapping("/l")
	String homealumnos() {
		 
		return "alumnos";
			
	}
	private void crearTablas() {
		
		Alumno a1 = new Alumno("Alba", "541245F");
		Profesor p1 = new Profesor("Pedro", "4529L");
		Coche c1 = new Coche("5894 GHL", "2023", "Peugeot");
		Alumno a2 = new Alumno("David", "45446G");
		Profesor p2 = new Profesor("Alejandro", "4515K");
		Coche c2 = new Coche("5894 DSF", "2021", "Seat");
		Rol rolBasico = new Rol("ROLE_USER");
		Rol rolAdmin = new Rol("ROLE_ADMIN");
		Llave llave1=new Llave();
		Llave llave2=new Llave();
		Usuario usuarioAdmin = new Usuario("admin", "admin", "$2a$12$jcd/P/f86nT9YXQbx/3tJeQye8h0aMhNm6EzzKkZMNLUCtIxpTNJa");
		Usuario usuarioBasico = new Usuario("basic", "basic", "$2a$12$.LxoR0UizFfliIRjthfELu3aNpoRfnfSt6Y.B0CwKWvKmFg.OZyxK");
		

		/*
		 * LocalDate fecha=LocalDate.of(2024, 5, 13); c1.setFechaITV(fecha);
		 */
		Date fecha = new Date(125, 6, 23);
		c1.setFechaITV(fecha);

		a1.setProfesor(p1);
		p1.getAlumnos().add(a1);
		a1.setCoche(c1);
		c1.getAlumnos().add(a1);
		
		
		//c1.getProfesores().add(p1);
		
		a2.setProfesor(p2);
		p2.getAlumnos().add(a2);
		a2.setCoche(c2);
		c2.getAlumnos().add(a2);
		
		
		/*c2.addLlave(p2,llave2);
		llave1.addCocheProfesor(c2, p2);
		p1.addCoche(c1, llave1);
		llave2.addCocheProfesor(c2, p2);
		
		p2.addCoche(c2, llave2);
		c1.addLlave(p1,llave1);*/ //el metodo addLlave tambien añade la llave al profesor
		
		//c2.getProfesores().add(p2);

		/*RolRepo rolRepo = new RolRepo();
		UsuarioRepo usuarioRepo = new UsuarioRepo();
		AlumnoRepo alumnoRepo = new AlumnoRepo();
		ProfesorRepo profesorRepo = new ProfesorRepo();
		CocheRepo cocheRepo = new CocheRepo();*/
		
		rolRepo.save(rolAdmin);
		usuarioService.insertarUsuarioAdmin(usuarioAdmin);
		rolRepo.save(rolBasico);
		usuarioService.insertarUsuarioBasico(usuarioBasico);
		
		cocheService.insertarCoche(c1);
		cocheService.insertarCoche(c2);
		
		
		profeService.insertarProfesor(p1);
		profeService.insertarProfesor(p2);
		
		alumnoService.insertarAlumno(a1);
		alumnoService.insertarAlumno(a2);
		
		p1.juegoLlaves(c1, llave1);
		p2.juegoLlaves(c2, llave2);
		llaveService.insertarLlave(llave1);
		llaveService.insertarLlave(llave2);
		
	

	}
}
