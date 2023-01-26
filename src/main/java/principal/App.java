package principal;

import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.persistencia.AlumnoDAO;
import principal.persistencia.CocheDAO;
import principal.persistencia.ProfesorDAO;
import principal.persistencia.Tablas;

@SpringBootApplication
public class App {
	  

	public static void main(String[] args) throws ParseException{
		//Tablas t = new Tablas();
    	//t.crearTablas();
    	 SpringApplication.run(App.class, args);


}
}