package principal.modelo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="profesores_coches_llaves")
public class ProfesoresCochesLlaves {
	
	@EmbeddedId
	private ProfesoresCochesLlavesId id=new ProfesoresCochesLlavesId();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("profesor_id")
	private Profesor profesor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("coche_id")
	private Coche coche;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("llave_id")
	private Llave llave;
	

	public ProfesoresCochesLlaves() {
		
	}

	public ProfesoresCochesLlaves(Profesor p, Coche c, Llave l) {
		this.coche=c;
		this.profesor=p;
		this.llave=l;
		this.id=new ProfesoresCochesLlavesId(p.getId(), c.getId(),l.getId());
}

	@Override
	public boolean equals(Object o) {
		if(o==null || getClass() !=o.getClass()) { 
			return false;
		}
		ProfesoresCochesLlaves that=(ProfesoresCochesLlaves) o;
		return Objects.equals(coche, that.coche) && Objects.equals(profesor, that.profesor) && Objects.equals(llave, that.llave);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profesor, coche, llave);
	}
	public ProfesoresCochesLlavesId getId() {
		return id;
	}

	public void setId(ProfesoresCochesLlavesId id) {
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

	public Llave getLlave() {
		return llave;
	}

	public void setLlave(Llave llave) {
		this.llave = llave;
	}
	


	
	
	
	
	
}
