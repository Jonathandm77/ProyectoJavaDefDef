package principal.modelo;

import java.util.Objects;

import javax.persistence.CascadeType;
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
@Table(name="profesores_coches")
public class ProfesoresCoches {
	
	@EmbeddedId
	private ProfesoresCochesId id;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@MapsId("profesor_id")
	private Profesor profesor;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@MapsId("coche_id")
	private Coche coche;
	
	@OneToOne
	@JoinColumn(name="id_llave")
	private Llave llave;
	

	public ProfesoresCoches() {
		
	}

	public ProfesoresCoches(Profesor p, Coche c, Llave l) {
		this.coche=c;
		this.profesor=p;
		this.llave=l;
		this.id=new ProfesoresCochesId(p.getId(), c.getId());
}

	@Override
	public boolean equals(Object o) {
		if(o==null || getClass() !=o.getClass()) { 
			return false;
		}
		ProfesoresCoches that=(ProfesoresCoches) o;
		return Objects.equals(coche, that.coche) && Objects.equals(profesor, that.profesor) && Objects.equals(llave, that.llave);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profesor, coche, llave);
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

	public Llave getLlave() {
		return llave;
	}

	public void setLlave(Llave llave) {
		this.llave = llave;
	}
	


	
	
	
	
	
}