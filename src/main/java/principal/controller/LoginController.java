package principal.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto","root", "");
		String sql = "SELECT COUNT(*) FROM usuarios";
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery(sql);
		resultset.next();
		int count = resultset.getInt(1);
		if (count > 0) {

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
	public String addUsuario(@ModelAttribute("usuarioNuevo") UsuarioAddDTO usuarioNew, BindingResult bidingresult) {
		Usuario usuarioaInsertar = new Usuario();
		usuarioaInsertar.setNombre(usuarioNew.getNombre());
		usuarioaInsertar.setUsername(usuarioNew.getUsername());
		usuarioaInsertar.setIdAlumno(usuarioNew.getIdAlumno());
		usuarioaInsertar.setPassword(usuarioNew.getPassword());

		userService.insertarUsuarioBasico(usuarioaInsertar);
		return "redirect:/login";
	}

	@GetMapping({ "/registro" })
	String mostrarRegistro(Model model, @ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root", "");

		String sql = "SELECT COUNT(*) FROM usuarios";

		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery(sql);
		resultset.next();
		int count = resultset.getInt(1);
		if (count > 0) {
			connection.close();
			model.addAttribute("newUserDTO", new UsuarioDTO());
			return "registro";
		} else {
			connection.close();
			return "redirect:/";
		}
	}
}
