package principal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.dto.ClaseDTO;
import principal.servicio.implementacion.ClaseServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;
import springfox.documentation.annotations.ApiIgnore;


@Controller
@RequestMapping("/clases")
public class ClasesController {
	
	@Autowired
	ClaseServiceImpl claseService;
	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@PostMapping("/add") // añadir clase
	public String addClase(@ModelAttribute("claseNueva") ClaseDTO claseNew, @ApiIgnore BindingResult bindingResult, @ApiIgnore HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  //  Date fechaDate = dateFormat.parse(claseNew.getFecha()); // formatear fecha recibida

	    Alumno alumno = (Alumno) session.getAttribute("alumnoaImpartir"); // obtiene el alumno desde el que se mando la solicitud

	    for (Clase clase : alumno.getClases()) {
	        if (clase.getHora().equals(claseNew.getHora()) && mismaFecha(clase.getFecha(), claseNew.getFecha())) {
	            redirectAttributes.addFlashAttribute("error", true);
	            return "redirect:/alumnos/" + alumno.getId();
	        }
	    }

	    Clase claseAñadir = new Clase();
	    claseAñadir.setAlumno(alumno);
	    alumno.añadirClase(claseAñadir);
	    claseAñadir.setFecha(claseNew.getFecha());
	    claseAñadir.setHora(claseNew.getHora());
	    claseService.insertarClase(claseAñadir); // la guardamos en la base de datos

	    return "redirect:/alumnos/" + alumno.getId();
	}

	
	@GetMapping({"/delete/{id}"})
	String deleteClase(Model model, @PathVariable Integer id) {
		if(usuarioService.esAdminActual()) {
		Clase claseaEliminar=claseService.obtenerClasePorId(id);
		if (claseaEliminar!=null)//si la clase existe la borramos
			claseService.eliminarClasePorId(id);
		}
		return "redirect:/alumnos";
	}
	
	private boolean mismaFecha(Date date1, Date date2) {
	    Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(date1);
	    Calendar cal2 = Calendar.getInstance();
	    cal2.setTime(date2);

	    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
	            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}
}
