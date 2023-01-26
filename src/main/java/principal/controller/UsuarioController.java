package principal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Usuario;
import principal.modelo.dto.UsuarioDTO;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {
	@Autowired
	UsuarioServiceImpl userDetailsService;

@GetMapping(value={"","/"})
String homeusuarios(Model model) {
ArrayList<Usuario> listaUsuarios=(ArrayList<Usuario>) userDetailsService.listarUsuarios();
model.addAttribute("listaUsuarios", listaUsuarios);
model.addAttribute("usuarioaEditar", new Usuario());
model.addAttribute("usuarioNuevo", new Usuario());
return "usuarios";
}

	@PostMapping("/edit/{id}")
	public String editarUsuario(@PathVariable Integer id, @ModelAttribute("usuarioaEditar") Usuario usuarioEditado,
			BindingResult bidingresult) {
		Usuario usuarioaEditar =userDetailsService.obtenerUsuarioPorId(id);
		usuarioaEditar.setUsername(usuarioEditado.getUsername());
		usuarioaEditar.setPassword(usuarioEditado.getPassword());
		userDetailsService.insertarUsuario(usuarioaEditar);
		return "redirect:/usuarios";
	}

	@GetMapping({ "/{id}" })
	String idUsuario(Model model, @PathVariable Integer id) {
		Usuario usuarioMostrar = userDetailsService.obtenerUsuarioPorId(id);
		model.addAttribute("usuarioMostrar", usuarioMostrar);
		return "usuario";
	}

	@GetMapping({"/delete/{id}" })
	String deleteUsuario(Model model, @PathVariable Integer id) {
		
		if(esAdmin()) {
		
		Usuario usuarioaEliminar = userDetailsService.obtenerUsuarioPorId(id);
		userDetailsService.eliminarUsuario(usuarioaEliminar);
		}
		return "redirect:/usuarios";
	}

	private boolean esAdmin() {
		// TODO Auto-generated method stub
		return false;
	}

	@PostMapping("/add")
	public String addUsuario(@ModelAttribute("usuarioNuevo") Usuario usuarioNew, BindingResult bidingresult) {
		userDetailsService.insertarUsuario(usuarioNew);
		return "redirect:/usuarios";
	}
	@GetMapping({"/registro" })
	String mostrarRegistro(Model model, @ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew) {
		model.addAttribute("newUserDTO", new UsuarioDTO());
		return "registro";
	}
	@PostMapping("/addRegistro")
	public String addRegistro(@ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew, BindingResult bidingresult) {
		userDetailsService.insertarUsuarioDTO(usuarioNew);
		return "login";
	}
}