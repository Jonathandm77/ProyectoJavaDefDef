package principal.servicio.interfaces;

import java.util.List;
import java.util.Optional;

import principal.modelo.Profesor;
import principal.modelo.dto.ProfesorDTO;


public interface ProfesorService{
	public Profesor insertarProfesor(Profesor profe); 
	public Profesor insertarProfesorDTO(ProfesorDTO profeDTO);
	public List<Profesor> listarProfesores();
	public Optional<Profesor> obtenerProfesorPorId (Integer id);
	public Profesor obtenerProfesorPorNombre (String nombre);
	public void eliminarProfesor(Profesor profe);
	public void eliminarProfesorPorId(Integer id);
}
