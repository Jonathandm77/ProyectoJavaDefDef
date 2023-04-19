package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.modelo.Rol;


public interface RolRepo extends JpaRepository<Rol, Integer> {

	public Optional<Rol> findByNombre(String nombre);
	
}
