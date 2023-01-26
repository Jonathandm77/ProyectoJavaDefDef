package principal.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.modelo.Coche;


public interface CocheRepo extends JpaRepository<Coche, Integer> {

	//public Optional<Bocadillo> findByNombre(String nombre);
	
}
