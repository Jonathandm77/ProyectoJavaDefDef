package principal.modelo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="profesores_coches")
public class ProfesoresCoches {
	
	@EmbeddedId
	private ProfesoresCochesId id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("profesor_id")
	private Profesor profesor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("coche_id")
	private Coche coche;
	

	public ProfesoresCoches() {
		super();
	}

	public ProfesoresCoches(Profesor p, Coche c) {
		this.coche=c;
		this.profesor=p;
		this.id=new ProfesoresCochesId(p.getId(), c.getId());
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || getClass() !=o.getClass()) { 
			return false;
		}
		ProfesoresCoches that=(ProfesoresCoches) o;
		return Objects.equals(coche, that.coche) && Objects.equals(profesor, that.profesor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profesor, coche);
	}
	public ProfesoresCochesId getId() {
		return id;
	}

	public void setId(ProfesoresCochesId id) {
		this.id = id;
	}

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


	
	
	
	
	
}
