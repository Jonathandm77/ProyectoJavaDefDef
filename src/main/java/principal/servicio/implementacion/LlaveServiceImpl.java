package principal.servicio.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Llave;
import principal.persistencia.LlaveRepo;
import principal.persistencia.LlaveRepo;
import principal.servicio.interfaces.LlaveService;
import principal.servicio.interfaces.LlaveService;

@Service
public class LlaveServiceImpl implements LlaveService {
	
	@Autowired
	LlaveRepo llaveRepo;

	@Override
	public Llave insertarLlave(Llave llave) {
		// TODO Auto-generated method stub
		return llaveRepo.save(llave);
	}

	/*@Override
	public Llave insertarLlaveDTO(LlaveDTO llaveDTO) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public List<Llave> listarLlaves() {
		// TODO Auto-generated method stub
		return llaveRepo.findAll();
	}

	@Override
	public Llave obtenerLlavePorId(Integer id) {
		// TODO Auto-generated method stub
		return llaveRepo.findById(id).get();
	}

	

	@Override
	public void eliminarLlave(Llave llave) {
		llaveRepo.delete(llave);
	}

	@Override
	public void eliminarLlavePorId(Integer id) {
		llaveRepo.deleteById(id);
		
	}

}
