package principal.servicio.interfaces;

import java.util.List;

import principal.modelo.Clase;

public interface ClaseService {
	public Clase insertarClase(Clase clase); 
	public List<Clase> listarClases();
	public Clase obtenerClasePorId (Integer id);
	public void eliminarClasePorId(Integer id);
}
