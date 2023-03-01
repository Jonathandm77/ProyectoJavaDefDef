package principal.persistencia;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import antlr.collections.List;
import principal.modelo.Alumno;

public interface AlumnoRepo extends JpaRepository<Alumno, Integer> {

	public Optional<Alumno> findByNombre(String nombre);
	public Alumno findByDni(String dni);
	public ArrayList<Alumno> findAllByNombre(String nombre);
	
}
