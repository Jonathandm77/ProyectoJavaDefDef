package principal.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.dto.CocheDTO;
import principal.persistencia.CocheRepo;
import principal.servicio.interfaces.CocheService;

@Service
public class CocheServiceImpl implements CocheService {
	
	@Autowired
	CocheRepo cocheRepo;

	@Override
	public Coche insertarCoche(Coche coche) {
		// TODO Auto-generated method stub
		return cocheRepo.save(coche);
	}

	@Override
	public Coche insertarCocheDTO(CocheDTO cocheDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coche> listarCoches() {
		// TODO Auto-generated method stub
		return cocheRepo.findAll();
	}

	@Override
	public Coche obtenerCochePorId(Integer id) {
		// TODO Auto-generated method stub
		return cocheRepo.findById(id).get();
	}

	@Override
	public Coche obtenerCochePorMarca(String marca) {
		// TODO Auto-generated method stub
		return cocheRepo.findByMarca(marca).get();
	}

	@Override
	public void eliminarCoche(Coche coche) {
		cocheRepo.delete(coche);
	}

	@Override
	public void eliminarCochePorId(Integer id) {
		cocheRepo.deleteById(id);
		
	}
	
	public Coche encontrarCochePorMatricula(String matricula) {
		Coche coche =cocheRepo.findByMatricula(matricula);
		return coche;
	}

}
