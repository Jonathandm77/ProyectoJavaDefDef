package principal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
	
	//aqui iria crear Tablas en caso de querer usar repo
	
	@GetMapping("/")
	String home() {
		 
		return "index";
			
	}
	
	@GetMapping("/l")
	String homealumnos() {
		 
		return "alumnos";
			
	}
}
