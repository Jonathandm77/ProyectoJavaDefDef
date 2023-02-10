package principal.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="llave")
public class Llave {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="profesor_id", nullable=false)
	private Profesor profesor;
	
	@ManyToOne
	@JoinColumn(name = "coche_id", nullable = false)
	private Coche coche;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Llave(Profesor profesor, Coche coche) {
		this.profesor = profesor;
		this.coche = coche;
	}

	public Llave() {
	}

	@Override
	public String toString() {
		return "Llave [id=" + id + ", profesor=" + profesor + ", coche=" + coche + "]";
	}

	
	
}
