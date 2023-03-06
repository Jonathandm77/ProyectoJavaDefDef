package principal.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.AlumnoDTO;
import principal.modelo.dto.UsuarioDTO;
import principal.persistencia.AlumnoRepo;
import principal.persistencia.RolRepo;
import principal.persistencia.UsuarioRepo;
import principal.servicio.interfaces.AlumnoService;
import principal.servicio.interfaces.UsuarioService;
@Service
public class AlumnoServiceImpl implements AlumnoService{
	@Autowired
	private AlumnoRepo alumnoRepo;
	
	@Override
	public Alumno insertarAlumno(Alumno user) {
		return alumnoRepo.save(user);
	}
	@Override
	public Alumno insertarAlumnoDTO(AlumnoDTO a) {
		Alumno nuevoAlumno=new Alumno(a.getId(),a.getDni(),a.getNombre(),a.getApellidos(),a.getNotas(),a.getProfesor(),a.getCoche());

		
		return alumnoRepo.save(nuevoAlumno);
	}
	@Override
	public List<Alumno> listarAlumnos() {
		
		return alumnoRepo.findAll();
	}
	@Override
	public Alumno obtenerAlumnoPorId(Integer id) {
		return alumnoRepo.findById(id).get();
	}
	@Override
	public Alumno obtenerAlumnoPorNombre(String nombre) {
		alumnoRepo.findByNombre(nombre);
		return alumnoRepo.findByNombre(nombre).get();
	}
	@Override
	public void eliminarAlumno(Alumno user) {
		alumnoRepo.delete(user);
		
	}
	@Override
	public void eliminarAlumnoPorId(Integer id) {
		alumnoRepo.deleteById(id);
		
	}
	
	public ArrayList<Alumno> encontrarAlumnosPorNombre(String nombre) {
		ArrayList<Alumno> lista=(ArrayList<Alumno>) alumnoRepo.findAllByNombre(nombre);
		return lista;
	}
	
	public ArrayList<Alumno> encontrarAlumnosPorDni(String dni) {
		ArrayList<Alumno> lista= alumnoRepo.findByDni(dni);
		return lista;
	}

	

}
