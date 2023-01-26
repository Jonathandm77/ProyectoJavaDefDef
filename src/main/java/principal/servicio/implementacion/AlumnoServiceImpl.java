package principal.servicio.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import principal.modelo.Alumno;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.AlumnoDTO;
import principal.modelo.dto.UsuarioDTO;
import principal.persistencia.RolRepo;
import principal.persistencia.UsuarioRepo;
import principal.servicio.interfaces.AlumnoService;
import principal.servicio.interfaces.UsuarioService;
@Service
public class AlumnoServiceImpl implements AlumnoService{
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private RolRepo rolRepo;
	 
	@Override
	public Usuario insertarUsuario(Usuario user) {
		usuarioRepo.save(user);
		return null;
	}
	@Override
	public Usuario insertarUsuarioDTO(UsuarioDTO userDTO) {
		Usuario nuevoUsuario = new Usuario(userDTO.getNombre(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        usuarioRepo.save(nuevoUsuario);
        Optional<Rol> rolNuevo=rolRepo.findByNombre("ROLE_USER");
		nuevoUsuario.getRoles().add(rolNuevo.get());
		return usuarioRepo.save(nuevoUsuario);
	}
	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepo.findAll();
	}
	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		
		return usuarioRepo.findById(id).get();
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
	@Override
	public Alumno insertarAlumno(Alumno user) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Alumno insertarAlumnoDTO(AlumnoDTO alumnoDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Alumno> listarAlumnos() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Alumno obtenerAlumnoPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Alumno obtenerAlumnoPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void eliminarAlumno(Alumno user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void eliminarAlumnoPorId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	

}
