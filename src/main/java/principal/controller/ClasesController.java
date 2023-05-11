package principal.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.servicio.implementacion.ClaseServiceImpl;


@Controller
@RequestMapping("/clases")
public class ClasesController {
	
	@Autowired
	ClaseServiceImpl claseService;
	
	@PostMapping("/add")
	public String addClase(@ModelAttribute("claseNueva") Clase claseNew, BindingResult bidingresult,HttpSession session) throws SQLException {
		Alumno alumno=(Alumno) session.getAttribute("alumnoaImpartir");
		claseNew.setAlumno(alumno);
		alumno.añadirClase(claseNew);
		claseService.insertarClase(claseNew);
		return "redirect:/alumnos/"+alumno.getId();
	}
	
	@GetMapping({"/delete/{id}"})
	String deleteClase(Model model, @PathVariable Integer id) {
		Clase claseaEliminar=claseService.obtenerClasePorId(id);
		if (claseaEliminar!=null)
			claseService.eliminarClasePorId(id);
		return "redirect:/alumnos";
	}
}
