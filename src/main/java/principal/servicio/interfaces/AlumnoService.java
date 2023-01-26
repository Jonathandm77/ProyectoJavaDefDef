package principal.servicio.interfaces;

import java.util.List;


import principal.modelo.Alumno;
import principal.modelo.dto.AlumnoDTO;


public interface AlumnoService{
	public Alumno insertarAlumno(Alumno user); 
	public Alumno insertarAlumnoDTO(AlumnoDTO alumnoDTO);
	public List<Alumno> listarAlumnos();
	public Alumno obtenerAlumnoPorId (Integer id);
	public Alumno obtenerAlumnoPorNombre (String nombre);
	public void eliminarAlumno(Alumno user);
	public void eliminarAlumnoPorId(Integer id);
}
