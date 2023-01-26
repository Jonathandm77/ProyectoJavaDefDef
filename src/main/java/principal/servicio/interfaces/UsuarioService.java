package principal.servicio.interfaces;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import principal.modelo.Usuario;


public interface UsuarioService extends UserDetailsService{
	public Usuario insertarUsuario(Usuario user); 
	public Usuario insertarUsuarioDTO(UsuarioDTO userDTO);
	public List<Usuario> listarUsuarios();
	public Usuario obtenerUsuarioPorId (Integer id);
	public Usuario obtenerUsuarioPorNombre (String nombre);
	public void eliminarUsuario(Usuario user);
	public void eliminarUsuarioPorId(Integer id);
}
