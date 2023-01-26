package principal.servicio.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import principal.servicio.interfaces.UsuarioService;
@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	private UsuarioRepo usuarioRepo;
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UsuarioDTO> usuario=usuarioRepo.findByUsername(username);
		
		if(usuario.isPresent()) {
			UsuarioDTO springUsermio=usuario.get();
			return springUsermio;
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
	}
	@Override
	public UsuarioDTO insertarUsuario(UsuarioDTO user) {
		usuarioRepo.save(user);
		return null;
	}
	@Override
	public UsuarioDTO insertarUsuarioDTO(UsuarioDTO userDTO) {
		UsuarioDTO nuevoUsuario = new UsuarioDTO(userDTO.getNombre(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        usuarioRepo.save(nuevoUsuario);
		nuevoUsuario.getRoles().add(new Rol("ROL_ADMIN"));
		return usuarioRepo.save(nuevoUsuario);
	}
	@Override
	public List<UsuarioDTO> listarUsuarios() {
		return usuarioRepo.findAll();
	}
	@Override
	public UsuarioDTO obtenerUsuarioPorId(Integer id) {
		
		return usuarioRepo.findById(id).get();
	}
	@Override
	public UsuarioDTO obtenerUsuarioPorNombre(String nombre) {
	
		return usuarioRepo.findByUsername(nombre).get();
	}
	@Override
	public void eliminarUsuario(UsuarioDTO user) {
		usuarioRepo.delete(user);
		
	}
	@Override
	public void eliminarUsuarioPorId(Integer id) {
		usuarioRepo.delete(usuarioRepo.findById(id).get());
		
	}

}
