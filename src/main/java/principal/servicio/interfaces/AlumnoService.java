package principal.servicio.interfaces;

import java.util.List;
import java.util.Optional;

import principal.modelo.Alumno;
import principal.modelo.dto.AlumnoDTO;


public interface AlumnoService{
	public Alumno insertarAlumno(Alumno alumno); 
	public Alumno insertarAlumnoDTO(AlumnoDTO alumnoDTO);
	public List<Alumno> listarAlumnos();
	public Optional<Alumno> obtenerAlumnoPorId (Integer id);
	public Alumno obtenerAlumnoPorNombre (String nombre);
	public void eliminarAlumno(Alumno alumno);
	public void eliminarAlumnoPorId(Integer id);
}
