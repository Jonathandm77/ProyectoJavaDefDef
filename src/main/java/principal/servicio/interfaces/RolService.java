package principal.servicio.interfaces;

import java.util.List;

import principal.modelo.Rol;


public interface RolService {
	public Rol insertarRol(Rol user); 
	//public Rol insertarRolDTO(RolDTO userDTO);
	public List<Rol> listarRoles();
	public Rol obtenerRolPorId (Integer id);
	public Rol obtenerRolPorNombre (String nombre);
	public void eliminarRol(Rol user);
	public void eliminarRolPorId(Integer id);
}
