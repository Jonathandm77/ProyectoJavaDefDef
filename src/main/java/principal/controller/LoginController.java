package principal.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Usuario;
import principal.modelo.dto.UsuarioDTO;
import principal.servicio.implementacion.UsuarioServiceImpl;

@RequestMapping("/login")
@Controller
public class LoginController {

	@Autowired
	UsuarioServiceImpl userService;

	@GetMapping(value = { "", "/" })
	String homelogin(Model model) throws SQLException {

		List<Usuario> usuarios = userService.listarUsuarios();

		if (usuarios.size() > 0) {

			return "login";
		} else {
			return "redirect:/";
		}

	}

	@GetMapping("/logout")
	public String logout(Model model) {

		return "redirect:/login";
	}

	@PostMapping("/add")
	public String addUsuario(@ModelAttribute("usuarioNuevo") UsuarioAddDTO usuarioNew,RedirectAttributes redirectAttributtes, BindingResult bidingresult) {
		List<Usuario> usuarios = userService.listarUsuarios();
		boolean duplicado = false;

		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().equals(usuarioNew.getNombre())) {
				duplicado = true;
			}
		}
		if (!duplicado) {
			Usuario usuarioaInsertar = new Usuario();
			usuarioaInsertar.setNombre(usuarioNew.getNombre());
			usuarioaInsertar.setUsername(usuarioNew.getUsername());
			usuarioaInsertar.setIdAlumno(usuarioNew.getIdAlumno());
			usuarioaInsertar.setPassword(usuarioNew.getPassword());
			userService.insertarUsuarioBasico(usuarioaInsertar);
		} else {
			redirectAttributtes.addFlashAttribute("error", "El nombre de usuario ya existe");
			return "redirect:/login/registro";
		}

		return "redirect:/login";
	}

	@GetMapping({ "/registro" })
	String mostrarRegistro(Model model, @ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew) throws SQLException {
		List<Usuario> usuarios = userService.listarUsuarios();

		if (usuarios.size() > 0) {
			model.addAttribute("newUserDTO", new UsuarioDTO());
			return "registro";
		} else {
			return "redirect:/";
		}

	}
}
