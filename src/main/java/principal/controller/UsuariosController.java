package principal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import principal.modelo.ImageInfo;
import principal.modelo.Profesor;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.UsuarioDTO;
import principal.modelo.dto.UsuarioUsernamePasswordDTO;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;
import principal.servicio.interfaces.FileStorageService;

@RequestMapping("/usuarios")
@Controller
public class UsuariosController {
	@Autowired
	UsuarioServiceImpl userService;
	@Autowired 
	ProfesorServiceImpl profeService;
	@Autowired
	FileStorageService storageService;

@GetMapping(value={"","/"})
String homeusuarios(Model model) {
	if(isAdmin()) {
ArrayList<Usuario> listaUsuarios=(ArrayList<Usuario>) userService.listarUsuarios();
for(Usuario user:listaUsuarios) {
	if(user.getImagenPerfil()!=null) {
		Resource resource = storageService.load(user.getImagenPerfil());
		String url = MvcUriComponentsBuilder.fromMethodName(SecurityController.class, "serveFile", resource.getFilename())
		    .build().toString();
		ImageInfo img = new ImageInfo(resource.getFilename(), url);
		user.setUrl(img.getUrl());
	}
}
model.addAttribute("listaUsuarios", listaUsuarios);
model.addAttribute("usuarioaEditar", new Usuario());
model.addAttribute("usuarioNuevo", new Usuario());
return "usuarios";
	}
	return "redirect:/";
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

	@GetMapping({"/{id}" })
	String idUsuario(Model model, @PathVariable Integer id) {
		Usuario usuarioMostrar = userService.obtenerUsuarioPorId(id);
		model.addAttribute("usuarioMostrar", usuarioMostrar);
		return "usuario";
	}

	@GetMapping({"/delete/{id}" })
	String deleteUsuario(Model model, @PathVariable Integer id) {
		
		if(isAdmin()) {
		
		Usuario usuarioaEliminar = userService.obtenerUsuarioPorId(id);
		userService.eliminarUsuario(usuarioaEliminar);
		}
		return "redirect:/usuarios";
	}


	@PostMapping("/add")
	public String addUsuario(@ModelAttribute("usuarioNuevo") Usuario usuarioNew, BindingResult bidingresult) {
		userService.insertarUsuario(usuarioNew);
		return "redirect:/usuarios";
	}
	@GetMapping({"/registro" })
	String mostrarRegistro(Model model, @ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew) {
		ArrayList<Profesor> listaProfes=(ArrayList<Profesor>) profeService.listarProfesores();
		model.addAttribute("listaProfesores",listaProfes);
		model.addAttribute("newUserDTO", new UsuarioDTO());
		return "registro";
	}
	@PostMapping("/addRegistro")
	public String addRegistro(@ModelAttribute("usuarioNuevo") UsuarioDTO usuarioNew, BindingResult bindingResult, Model model) {
	    try {
	        if (usuarioNew.isEsProfesor()) {
	            Usuario userProfesor = new Usuario();
	            userProfesor.setNombre(usuarioNew.getNombre());
	            userProfesor.setPassword(usuarioNew.getPassword());
	            userProfesor.setUsername(usuarioNew.getUsername());
	            userProfesor.setIdProfesor(usuarioNew.getIdProfesor());
	            userService.insertarUsuarioProfesor(userProfesor);
	        } else {
	            userService.insertarUsuarioDTO(usuarioNew);
	        }
	        return "login";
	    } catch (DataIntegrityViolationException e) {
	        // Manejo de la excepción de clave duplicada
	        // Agrega un mensaje de error al modelo para mostrarlo en la página de registro
	        model.addAttribute("error", "El nombre de usuario ya está en uso");
	        model.addAttribute("newUserDTO", new UsuarioDTO());
	        return "registro";
	    }
	}

	
	private boolean isAdmin(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Usuario actualUser = (Usuario) auth.getPrincipal();
	    
	    for(Rol r:actualUser.getRoles()) {
	    	if(r.getNombre().compareTo("ROLE_ADMIN")==0) {
	    		return true;
	    }
	    }
		return false;
	    
	}
}