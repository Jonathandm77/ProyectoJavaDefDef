package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.modelo.Profesor;


public interface ProfesorRepo extends JpaRepository<Profesor, Integer> {

	public Optional<Profesor> findById(Integer id);
	
}
