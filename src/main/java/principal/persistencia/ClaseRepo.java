package principal.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import principal.modelo.Clase;

public interface ClaseRepo extends JpaRepository<Clase,Integer> {

}
