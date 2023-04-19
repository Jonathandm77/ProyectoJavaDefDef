package principal.modelo;
import javax.persistence.CascadeType;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	private ProfesoresCoches profesor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ProfesoresCoches coche;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProfesoresCoches getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesoresCoches profesor) {
		this.profesor = profesor;
	}

	public ProfesoresCoches getCoche() {
		return coche;
	}

	public void setCoche(ProfesoresCoches coche) {
		this.coche = coche;
	}
	
	public void addCocheProfesor(Coche c, Profesor p) {
		ProfesoresCoches pc=new ProfesoresCoches(p,c, this);
		this.setCoche(pc);
		this.setProfesor(pc);
	}
	public Llave(ProfesoresCoches profesor, ProfesoresCoches coche) {
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
