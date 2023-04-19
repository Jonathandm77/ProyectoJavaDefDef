package principal.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.modelo.Llave;


public interface LlaveRepo extends JpaRepository<Llave, Integer> {

	
}
