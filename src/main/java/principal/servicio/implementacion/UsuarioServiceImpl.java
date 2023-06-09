package principal.servicio.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.UsuarioDTO;
import principal.persistencia.UsuarioRepo;
import principal.servicio.interfaces.RolService;
import principal.servicio.interfaces.UsuarioService;
@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private RolServiceImpl rolService;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Usuario> usuario=usuarioRepo.findByUsername(username);
		
		if(usuario.isPresent()) {
			Usuario springUsermio=usuario.get();
			return springUsermio;
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
	}
	@Override
	public Usuario insertarUsuario(Usuario user) {
		usuarioRepo.save(user);
		return null;
	}
	
	
	public Usuario insertarUsuarioBasico(Usuario user) {
		user.getRoles().add(rolService.obtenerRolPorId(2));
		String rawPassword=user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		return usuarioRepo.save(user);
	}
	
	public Usuario insertarUsuarioAdmin(Usuario user) {
		user.getRoles().add(rolService.obtenerRolPorId(1));
		String rawPassword=user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		return usuarioRepo.save(user);
	}
	
	public Usuario insertarUsuarioProfesor(Usuario user) {
		user.getRoles().add(rolService.obtenerRolPorId(3)); //role Teacher
		String rawPassword=user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		return usuarioRepo.save(user);
	}
	
	public Usuario insertarUsuarioAlumno(Usuario user) {
		user.getRoles().add(rolService.obtenerRolPorId(2)); //role user
		String rawPassword=user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		return usuarioRepo.save(user);
	}
	
	@Override
	public Usuario insertarUsuarioDTO(UsuarioDTO user) {
		Usuario nuevoUsuario = new Usuario(user.getNombre(), user.getUsername(), encoder.encode(user.getPassword()));
        
        Rol rolNuevo=rolService.obtenerRolPorId(2); //role User
		nuevoUsuario.getRoles().add(rolNuevo);
		String rawPassword=user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		return usuarioRepo.save(nuevoUsuario);
	}
	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepo.findAll();
	}
	@Override
	public Optional<Usuario> obtenerUsuarioPorId(Integer id) {
		
		return usuarioRepo.findById(id);
	}
	@Override
	public Usuario obtenerUsuarioPorNombre(String nombre) {
	
		return usuarioRepo.findByUsername(nombre).get();
	}
	@Override
	public void eliminarUsuario(Usuario user) {
		usuarioRepo.delete(user);
		
	}
	@Override
	public void eliminarUsuarioPorId(Integer id) {
		usuarioRepo.delete(usuarioRepo.findById(id).get());
		
	}
	
	public boolean esAdminActual() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if(principal instanceof UserDetails) {
		userDetails = (UserDetails) principal;
		Usuario u = this.obtenerUsuarioPorNombre(userDetails.getUsername());
		for (Rol r: u.getRoles()) {
		if(r.getNombre().compareTo("ROLE_ADMIN") == 0){
		return true;
		}
		}
		}
		return false;
		}
	

}
