package principal.persistencia;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import principal.modelo.Alumno;
import principal.modelo.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

	public Optional<Usuario> findByUsername(String nombre);
	
}
