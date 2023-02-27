package principal.servicio.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Rol;
import principal.persistencia.RolRepo;
import principal.servicio.interfaces.RolService;
@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
	RolRepo rolRepo;



	@Override
	public Rol insertarRol(Rol user) {
		// TODO Auto-generated method stub
		return rolRepo.save(user);
	}

	@Override
	public List<Rol> listarRoles() {
		// TODO Auto-generated method stub
		return rolRepo.findAll();
	}

	@Override
	public Rol obtenerRolPorId(Integer id) {
		// TODO Auto-generated method stub
		return rolRepo.findById(id).get();
	}

	@Override
	public Rol obtenerRolPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return rolRepo.findByNombre(nombre).get();
	}

	@Override
	public void eliminarRol(Rol user) {
		// TODO Auto-generated method stub
		rolRepo.delete(user);
	}

	@Override
	public void eliminarRolPorId(Integer id) {
		// TODO Auto-generated method stub
		rolRepo.deleteById(id);
	}

}
