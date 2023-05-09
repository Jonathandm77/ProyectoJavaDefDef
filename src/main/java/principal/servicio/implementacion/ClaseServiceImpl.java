package principal.servicio.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Clase;
import principal.persistencia.ClaseRepo;
import principal.servicio.interfaces.ClaseService;
@Service
public class ClaseServiceImpl implements ClaseService {
	
	@Autowired
	ClaseRepo claseRepo;

	@Override
	public Clase insertarClase(Clase clase) {
		// TODO Auto-generated method stub
		return claseRepo.save(clase);
	}

	@Override
	public List<Clase> listarClases() {
		// TODO Auto-generated method stub
		return claseRepo.findAll();
	}

	@Override
	public Clase obtenerClasePorId(Integer id) {
		// TODO Auto-generated method stub
		return claseRepo.findById(id).get();
	}

	@Override
	public void eliminarClasePorId(Integer id) {
		// TODO Auto-generated method stub
		claseRepo.deleteById(id);
		
	}

}
