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
	private ProfesoresCochesLlaves profesor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ProfesoresCochesLlaves coche;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProfesoresCochesLlaves getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesoresCochesLlaves profesor) {
		this.profesor = profesor;
	}

	public ProfesoresCochesLlaves getCoche() {
		return coche;
	}

	public void setCoche(ProfesoresCochesLlaves coche) {
		this.coche = coche;
	}
	
	public void addCocheProfesor(Coche c, Profesor p) {
		ProfesoresCochesLlaves pc=new ProfesoresCochesLlaves(p,c, this);
		ProfesoresCochesLlavesId id=new ProfesoresCochesLlavesId(p.getId(),c.getId(),this.id);
		pc.setId(id);
		this.setCoche(pc);
		this.setProfesor(pc);
	}
	public Llave(ProfesoresCochesLlaves profesor, ProfesoresCochesLlaves coche) {
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
