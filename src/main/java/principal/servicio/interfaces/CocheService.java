package principal.servicio.interfaces;

import java.util.List;


import principal.modelo.Coche;
import principal.modelo.dto.CocheDTO;


public interface CocheService{
	public Coche insertarCoche(Coche user); 
	public Coche insertarCocheDTO(CocheDTO alumnoDTO);
	public List<Coche> listarCoches();
	public Coche obtenerCochePorId (Integer id);
	public Coche obtenerCochePorNombre (String nombre);
	public void eliminarCoche(Coche user);
	public void eliminarCochePorId(Integer id);
}
