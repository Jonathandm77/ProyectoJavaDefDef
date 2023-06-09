package principal.controller;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/")
	String home(Model model, HttpSession sesion) throws SQLException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails actualUser = null;
		if (principal instanceof UserDetails) {
			actualUser = (UserDetails) principal;// definimos el usuario logueado
		}
		ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) usuarioService.listarUsuarios();
		if (listaUsuarios.isEmpty()) {// si no hay ningun usuario en la base de datos se insertan datos
			crearTablas();
			File uploadsDir = new File("uploads");
			if (uploadsDir.exists() && uploadsDir.isDirectory()) {// vaciar el directorio de subidas de imagenes
				File[] files = uploadsDir.listFiles();
				if (files != null) {
					for (File file : files) {
						file.delete();
					}
				}
			}
		}
		if (actualUser != null) {// si el usuario esta logueado se aplica el tema
			sesion.setAttribute("tema", ((Usuario) actualUser).getTema());
		}
		return "index";

	}

	private void crearTablas() throws SQLException {
		// definimos entidades
		Alumno a1 = new Alumno("Alba", "52364897K");
		a1.setApellidos("Menendez Palacios");
		Profesor p1 = new Profesor("56239874M", "Pedro", "Gonzalez Fernandez");
		Coche c1 = new Coche("5894 GHL", "2023", "Peugeot");
		Alumno a2 = new Alumno("David", "85612478L");
		a2.setApellidos("Gonzalez Rodriguez");
		Profesor p2 = new Profesor("53624477N", "Alejandro", "Rodriguez Palacios");
		Coche c2 = new Coche("5894 DSF", "2021", "Seat");
		Rol rolBasico = new Rol("ROLE_USER");
		Rol rolAdmin = new Rol("ROLE_ADMIN");
		Rol rolProfesor = new Rol("ROLE_TEACHER");
		Usuario usuarioAdmin = new Usuario("admin", "admin", "admin");
		Usuario usuarioBasico = new Usuario("basic", "basic", "basic");
		Usuario usuarioProfesor = new Usuario("profesor", "profesor", "profesor");
		//establecemos fecha de ITV
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.MAY, 23);
		Date fecha1 = calendar.getTime();
		c1.setFechaITV(fecha1);
		
		//establecemos relaciones
		a1.setProfesor(p1);
		p1.getAlumnos().add(a1);
		a1.setCoche(c1);
		c1.getAlumnos().add(a1);

		// c1.getProfesores().add(p1);

		a2.setProfesor(p2);
		p2.getAlumnos().add(a2);
		a2.setCoche(c2);
		c2.getAlumnos().add(a2);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2024, Calendar.APRIL, 15);
		Date fecha2 = calendar2.getTime();

		c2.setFechaITV(fecha2);
		c2.setHoraITV(LocalTime.parse("11:30", DateTimeFormatter.ofPattern("HH:mm")));//establecemos fecha y hora de ITV
		
		//insertamos entidades en la base de datos

		cocheService.insertarCoche(c1);
		cocheService.insertarCoche(c2);

		profeService.insertarProfesor(p1);
		profeService.insertarProfesor(p2);

		//establecemos relaciones entre profesores y coches
		p1.juegoLlaves(c1);
		p2.juegoLlaves(c2);
		
		//establecemos entidades usuario y roles y sus relaciones
		rolRepo.save(rolAdmin);
		usuarioService.insertarUsuarioAdmin(usuarioAdmin);
		rolRepo.save(rolBasico);
		usuarioBasico.setIdAlumno(1);
		usuarioBasico.setTema("temaOscuro");
		usuarioService.insertarUsuarioBasico(usuarioBasico);
		rolRepo.save(rolProfesor);
		usuarioProfesor.setIdProfesor(p1.getId());
		usuarioProfesor.setTema("temaClaro");
		usuarioService.insertarUsuarioProfesor(usuarioProfesor);

		alumnoService.insertarAlumno(a1);
		alumnoService.insertarAlumno(a2);
		
		//creamos clases y establecemos sus parámetros
		Clase clase1 = new Clase(a1);
		Clase clase2 = new Clase(a2);
		Clase clase3 = new Clase(a1);
		Clase clase4 = new Clase(a2);
		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(2023, Calendar.JULY, 23);
		Date fecha3 = calendar3.getTime();
		clase1.setFecha(fecha3);

		LocalTime hora = LocalTime.of(15, 30);

		clase1.setHora(hora);
		a1.añadirClase(clase1);

		claseService.insertarClase(clase1);

		Calendar calendar4 = Calendar.getInstance();
		calendar4.set(2023, Calendar.JUNE, 12);
		Date fecha4 = calendar3.getTime();
		clase2.setFecha(fecha4);

		LocalTime hora2 = LocalTime.of(17, 30);

		clase2.setHora(hora2);
		a2.añadirClase(clase2);
		claseService.insertarClase(clase2);

		Calendar calendar5 = Calendar.getInstance();
		calendar5.set(2023, Calendar.JULY, 27);
		Date fecha5 = calendar5.getTime();
		clase3.setFecha(fecha5);

		LocalTime hora3 = LocalTime.of(16, 30);

		clase3.setHora(hora3);
		a1.añadirClase(clase3);

		claseService.insertarClase(clase3);

		Calendar calendar6 = Calendar.getInstance();
		calendar6.set(2023, Calendar.JULY, 28);
		Date fecha6 = calendar6.getTime();
		clase4.setFecha(fecha6);

		LocalTime hora4 = LocalTime.of(16, 30);

		clase4.setHora(hora4);
		a1.añadirClase(clase4);

		claseService.insertarClase(clase4);

		/*
		 * //Bucle carga extrema
		 * 
		 * for(int i=0; i<100; i++) { Alumno a = new Alumno("Alba", "523648"+i);
		 * a.setApellidos("aa"); Coche c = new Coche("589"+i+" GHL", "202"); Profesor p
		 * = new Profesor("562398"+i, "Pepe"); a.setProfesor(p); p.getAlumnos().add(a);
		 * a.setCoche(c); c.getAlumnos().add(a); cocheService.insertarCoche(c);
		 * profeService.insertarProfesor(p); alumnoService.insertarAlumno(a); Clase
		 * clase = new Clase(a); Calendar calendar7 = Calendar.getInstance();
		 * calendar7.set(2023, Calendar.JULY, (int) (Math.random()*29)); Date
		 * fecha7=calendar7.getTime(); clase.setFecha(fecha7); a.añadirClase(clase);
		 * LocalTime hora5 = LocalTime.of(16, 30); clase.setHora(hora5);
		 * claseService.insertarClase(clase);
		 * 
		 * 
		 * }
		 * 
		 */

	}
}
