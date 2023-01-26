package principal.persistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.modelo.Alumno;

@Service
public class AlumnoService {
	


	@Autowired
	private AlumnoRepo alumnoRepo;

	public void save(Alumno alumno) {
	    alumnoRepo.save(alumno);
	} 

}
