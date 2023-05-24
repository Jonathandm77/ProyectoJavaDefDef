package principal.modelo.dto;

import principal.modelo.Coche;
import principal.modelo.Profesor;

public class AlumnoConProfeCocheDTO {
	private Profesor profesor;
	private Coche coche;
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	public Coche getCoche() {
		return coche;
	}
	public void setCoche(Coche coche) {
		this.coche = coche;
	}
	public AlumnoConProfeCocheDTO(Profesor profesor, Coche coche) {
		super();
		this.profesor = profesor;
		this.coche = coche;
	}
	public AlumnoConProfeCocheDTO() {
		super();
	}
	

}
