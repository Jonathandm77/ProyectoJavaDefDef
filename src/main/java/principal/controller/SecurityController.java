package principal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seguridad")
public class SecurityController {
	
	
	@GetMapping("/password")
	public String panelContraseña() {
		
		return "cambioContraseña";
	}
	
	@PostMapping("/changePassword")
	public String cambioPassword() {
		
		
		
		return "cambioContraseña";
	}
	
}
