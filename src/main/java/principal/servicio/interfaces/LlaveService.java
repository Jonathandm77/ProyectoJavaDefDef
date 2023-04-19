package principal.servicio.interfaces;

import java.util.List;


import principal.modelo.Llave;


public interface LlaveService{
	public Llave insertarLlave(Llave llave); 
	//public Llave insertarLlaveDTO(LlaveDTO alumnoDTO);
	public List<Llave> listarLlaves();
	public Llave obtenerLlavePorId (Integer id);
	public void eliminarLlave(Llave llave);
	public void eliminarLlavePorId(Integer id);
}
