package principal.persistencia;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import principal.modelo.Alumno;
import principal.modelo.Coche;


public interface CocheRepo extends JpaRepository<Coche, Integer> {

	public ArrayList<Coche> findAllByMarca(String marca);
	public Coche findByMatricula(String matricula);
}
