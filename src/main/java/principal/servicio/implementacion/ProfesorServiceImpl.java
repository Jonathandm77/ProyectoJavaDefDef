package principal.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Alumno;
import principal.modelo.Profesor;
import principal.modelo.dto.ProfesorDTO;
import principal.persistencia.ProfesorRepo;
import principal.servicio.interfaces.ProfesorService;
@Service
public class ProfesorServiceImpl implements ProfesorService{
	@Autowired
	private ProfesorRepo profeRepo;

	@Override
	public Profesor insertarProfesor(Profesor profe) {
		// TODO Auto-generated method stub
		return profeRepo.save(profe);
	}

	@Override
	public Profesor insertarProfesorDTO(ProfesorDTO profeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profesor> listarProfesores() {
		// TODO Auto-generated method stub
		return profeRepo.findAll();
	}

	@Override
	public Profesor obtenerProfesorPorId(Integer id) {
		// TODO Auto-generated method stub
		return profeRepo.findById(id).get();
	}

	@Override
	public Profesor obtenerProfesorPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return profeRepo.findByNombre(nombre).get();
	}

	@Override
	public void eliminarProfesor(Profesor profe) {
		profeRepo.delete(profe);
		
	}

	@Override
	public void eliminarProfesorPorId(Integer id) {
		profeRepo.deleteById(id);
		
	}
	
	public ArrayList<Profesor> encontrarProfesoresPorNombre(String nombre) {
		ArrayList<Profesor> lista=(ArrayList<Profesor>) profeRepo.findAllByNombre(nombre);
		return lista;
	}
	
	
	public Profesor encontrarProfesorPorDni(String dni) {
		Profesor lista= profeRepo.findByDni(dni);
		return lista;
	}
	

}
