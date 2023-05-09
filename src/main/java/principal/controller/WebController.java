package principal.controller;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.persistencia.RolRepo;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.ClaseServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
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
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	RolRepo rolRepo;
	
	@Autowired
	ClaseServiceImpl claseService;
	
	boolean created=false;
	
	@GetMapping("/")
	String home() throws SQLException {
		if(!created) {
		 crearTablas();
		 created=true;
		 File uploadsDir = new File("uploads");
		 if (uploadsDir.exists() && uploadsDir.isDirectory()) {
			 File[] files = uploadsDir.listFiles();
			 if (files != null) {
				 for (File file : files) {
					 file.delete();
				 }
			 }
		 }
		}
		return "index";
			
	}
	

	@GetMapping("/l")
	String homealumnos() {
		 
		return "alumnos";
			
	}
	private void crearTablas() throws SQLException {
		
		Alumno a1 = new Alumno("Alba", "52364897K");
		Profesor p1 = new Profesor("56239874M", "Pedro", "Gonzalez Fernandez");
		Coche c1 = new Coche("5894 GHL", "2023", "Peugeot");
		Alumno a2 = new Alumno("David", "85612478L");
		Profesor p2 = new Profesor("53624477N", "Alejandro","Rodriguez Palacios");
		Coche c2 = new Coche("5894 DSF", "2021", "Seat");
		Rol rolBasico = new Rol("ROLE_USER");
		Rol rolAdmin = new Rol("ROLE_ADMIN");
		Rol rolProfesor = new Rol("ROLE_TEACHER");
		Usuario usuarioAdmin = new Usuario("admin", "admin", "admin");
		Usuario usuarioBasico = new Usuario("basic", "basic", "basic");
		Usuario usuarioProfesor=new Usuario("profesor","profesor","profesor");

		/*
		 * LocalDate fecha=LocalDate.of(2024, 5, 13); c1.setFechaITV(fecha);
		 */
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.MAY, 23);
		Date fecha1=calendar.getTime();
		c1.setFechaITV(fecha1);

		a1.setProfesor(p1);
		p1.getAlumnos().add(a1);
		a1.setCoche(c1);
		c1.getAlumnos().add(a1);
		
		
		//c1.getProfesores().add(p1);
		
		a2.setProfesor(p2);
		p2.getAlumnos().add(a2);
		a2.setCoche(c2);
		c2.getAlumnos().add(a2);
		

		Calendar calendar2=Calendar.getInstance();
		calendar2.set(2024, Calendar.APRIL, 15);
		Date fecha2=calendar2.getTime();

		c2.setFechaITV(fecha2);
		c2.setHoraITV(LocalTime.parse("11:30", DateTimeFormatter.ofPattern("HH:mm")));
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
		
		
		
		cocheService.insertarCoche(c1);
		cocheService.insertarCoche(c2);
		
		
		profeService.insertarProfesor(p1);
		profeService.insertarProfesor(p2);
		
		p1.juegoLlaves(c1);
		p2.juegoLlaves(c2);
		

		
		
		
		
		rolRepo.save(rolAdmin);
		usuarioService.insertarUsuarioAdmin(usuarioAdmin);
		rolRepo.save(rolBasico);
		usuarioService.insertarUsuarioBasico(usuarioBasico);
		rolRepo.save(rolProfesor);
		usuarioProfesor.setIdProfesor(p1.getId());
		usuarioService.insertarUsuarioProfesor(usuarioProfesor);
		
		alumnoService.insertarAlumno(a1);
		alumnoService.insertarAlumno(a2);
		
		Clase clase1=new Clase(a1);
		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2023, Calendar.JULY, 23);
		Date fecha3=calendar3.getTime();
		clase1.setFecha(fecha3);
		
		Calendar calendar5 = Calendar.getInstance();
		calendar5.set(Calendar.HOUR_OF_DAY, 13);
		calendar5.set(Calendar.MINUTE, 20);
		Date hora = calendar5.getTime();
		clase1.setHora(hora);
		a1.añadirClase(clase1);

		
		claseService.insertarClase(clase1);
		
		Clase clase2=new Clase(a2);
		Calendar calendar4 = Calendar.getInstance();
		calendar4.set(2023, Calendar.JUNE, 12);
		Date fecha4=calendar3.getTime();
		clase2.setFecha(fecha4);
		
		Calendar calendar6 = Calendar.getInstance();
		calendar6.set(Calendar.HOUR_OF_DAY, 11);
		calendar6.set(Calendar.MINUTE, 30);
		Date hora2 = calendar6.getTime();
		clase2.setHora(hora2);
		a2.añadirClase(clase2);
		claseService.insertarClase(clase2);
		
		
		
	

	}
}
