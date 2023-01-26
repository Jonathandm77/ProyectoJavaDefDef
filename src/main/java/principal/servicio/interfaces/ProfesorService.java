package principal.servicio.interfaces;

import java.util.List;


import principal.modelo.Profesor;
import principal.modelo.dto.ProfesorDTO;


public interface ProfesorService{
	public Profesor insertarProfesor(Profesor user); 
	public Profesor insertarProfesorDTO(ProfesorDTO alumnoDTO);
	public List<Profesor> listarProfesors();
	public Profesor obtenerProfesorPorId (Integer id);
	public Profesor obtenerProfesorPorNombre (String nombre);
	public void eliminarProfesor(Profesor user);
	public void eliminarProfesorPorId(Integer id);
}
