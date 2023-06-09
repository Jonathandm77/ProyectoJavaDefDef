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
	
	@PostMapping("/add")//añadir clase
	public String addClase(@ModelAttribute("claseNueva") ClaseDTO claseNew, @ApiIgnore BindingResult bidingresult, @ApiIgnore HttpSession session) throws SQLException {
		Alumno alumno=(Alumno) session.getAttribute("alumnoaImpartir");//obtiene el alumno desde el que se mando la solicitud
		Clase claseaAñadir=new Clase();
		claseaAñadir.setAlumno(alumno);
		alumno.añadirClase(claseaAñadir);
		claseaAñadir.setFecha(claseNew.getFecha());
		claseaAñadir.setHora(claseNew.getHora());
		claseService.insertarClase(claseaAñadir);//la guardamos a la base de datos
		return "redirect:/alumnos/"+alumno.getId();
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
}
