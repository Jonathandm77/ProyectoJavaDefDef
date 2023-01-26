package principal.persistencia;

import java.text.ParseException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;

public class Tablas {
	  @Autowired 
	  private AlumnoRepo alumnoRepo;
	  @Autowired 
	  private CocheRepo cocheRepo;
	  @Autowired 
	  private ProfesorRepo profeRepo;
	public void crearTablas() throws ParseException {
		
		
		Alumno a1=new Alumno("Alba","541245F");
		Profesor p1=new Profesor("Pedro","4529L");
		Coche c1=new Coche("5894 GHL","2023","Peugeot");
		
		
		/*LocalDate fecha=LocalDate.of(2024, 5, 13);
		c1.setFechaITV(fecha);*/
		Date fecha=new Date(125,6,23);
		c1.setFechaITV(fecha);
		
		a1.setProfesor(p1);
		p1.getAlumnos().add(a1);
		a1.setCoche(c1);
		c1.getAlumnos().add(a1);
		p1.getCoches().add(c1);
		c1.getProfesores().add(p1);
		
		
		
		
		  AlumnoDAO alumnoDAO=new AlumnoDAO(); ProfesorDAO profesorDAO=new
		  ProfesorDAO(); CocheDAO cocheDAO=new CocheDAO();
		  
		  /*alumnoRepo.save(a1);
		  profeRepo.save(p1);
		  cocheRepo.save(c1);*/ //Esperar a tener los controllers de la web
		  
		  profesorDAO.insertarProfesorJPA(p1); 
		  cocheDAO.insertarCocheJPA(c1);
		  alumnoDAO.insertarAlumnoJPA(a1);
		  
	}

}
