package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.modelo.Coche;


public interface CocheRepo extends JpaRepository<Coche, Integer> {

	public Optional<Coche> findByMarca(String marca);
	
}
