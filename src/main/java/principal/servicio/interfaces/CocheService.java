package principal.servicio.interfaces;

import java.util.List;


import principal.modelo.Coche;
import principal.modelo.dto.CocheDTO;


public interface CocheService{
	public Coche insertarCoche(Coche coche); 
	public Coche insertarCocheDTO(CocheDTO cocheDTO);
	public List<Coche> listarCoches();
	public Coche obtenerCochePorId (Integer id);
	public Coche obtenerCochePorMarca (String marca);
	public void eliminarCoche(Coche coche);
	public void eliminarCochePorId(Integer id);
}
