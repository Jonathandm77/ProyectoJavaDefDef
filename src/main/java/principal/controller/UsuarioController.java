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
import principal.modelo.dto.UsuarioUsernamePasswordDTO;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {
	@Autowired
	UsuarioServiceImpl userService;

@GetMapping(value={"","/"})
String homeusuarios(Model model) {
ArrayList<Usuario> listaUsuarios=(ArrayList<Usuario>) userService.listarUsuarios();
model.addAttribute("listaUsuarios", listaUsuarios);
model.addAttribute("usuarioaEditar", new Usuario());
model.addAttribute("usuarioNuevo", new Usuario());
return "index";
}

	@PostMapping("/edit/{id}")
	public String editarUsuario(@PathVariable Integer id, @ModelAttribute("usuarioaEditar") UsuarioUsernamePasswordDTO usuarioEditado,
			BindingResult bidingresult) {
		Usuario usuarioaEditar =userService.obtenerUsuarioPorId(id);
		usuarioaEditar.setUsername(usuarioEditado.getUsername());
		usuarioaEditar.setPassword(usuarioEditado.getPassword());
		userService.insertarUsuario(usuarioaEditar);
		return "redirect:/usuarios";
	}

	@GetMapping({ "/{id}" })
	String idUsuario(Model model, @PathVariable Integer id) {
		Usuario usuarioMostrar = userService.obtenerUsuarioPorId(id);
		model.addAttribute("usuarioMostrar", usuarioMostrar);
		return "usuario";
	}

	@GetMapping({"/delete/{id}" })
	String deleteUsuario(Model model, @PathVariable Integer id) {
		
		if(esAdmin()) {
		
		Usuario usuarioaEliminar = userService.obtenerUsuarioPorId(id);
		userService.eliminarUsuario(usuarioaEliminar);
		}
		return "redirect:/usuarios";
	}

	private boolean esAdmin() {
		// TODO Auto-generated method stub
		return false;
	}

	@PostMapping("/add")
	public String addUsuario(@ModelAttribute("usuarioNuevo") Usuario usuarioNew, BindingResult bidingresult) {
		userService.insertarUsuario(usuarioNew);
		return "redirect:/usuarios";
	}
	@GetMapping({"/registro" })
	String mostrarRegistro(Model model, @ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew) {
		model.addAttribute("newUserDTO", new UsuarioDTO());
		return "registro";
	}
	@PostMapping("/addRegistro")
	public String addRegistro(@ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew, BindingResult bidingresult) {
		userService.insertarUsuarioDTO(usuarioNew);
		return "login";
	}
}