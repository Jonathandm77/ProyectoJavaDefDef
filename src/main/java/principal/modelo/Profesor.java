package principal.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Profesores")
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Integer id;
	

	@Column(name="DNI")
	private String dni;
	

	@Column(name="Nombre")
	private String nombre;
	
	@Column(name="Apellidos")
	private String apellidos;


	
	
	@OneToMany(mappedBy = "profesor", fetch = FetchType.EAGER)
	private Set<Alumno> alumnos;
	
	@OneToMany(mappedBy = "profesor", fetch = FetchType.EAGER)
	private Set<ProfesoresCochesLlaves> llaves;

	@OneToMany(mappedBy = "profesor", cascade=CascadeType.MERGE,orphanRemoval=true)
	private Set<ProfesoresCochesLlaves> coches;

	public Profesor() {
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCochesLlaves>();
		llaves=new HashSet<ProfesoresCochesLlaves>();
	}

	
	
	

	public Profesor(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCochesLlaves>();
		llaves=new HashSet<ProfesoresCochesLlaves>();
	}





	public Profesor(Integer id, String dni, String nombre, String apellidos, Set<Alumno> alumnos, Set<ProfesoresCochesLlaves> coches) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCochesLlaves>();
		llaves=new HashSet<ProfesoresCochesLlaves>();
	}

	
	public void addCoche(Coche c, Llave l) {
		ProfesoresCochesLlaves pc=new ProfesoresCochesLlaves(this,c,l);
		if(coches.contains(pc)) {
			coches.remove(pc);
		}
		coches.add(pc);
		c.getProfesores().add(pc);
		c.getLlaves().add(pc);
			
	}
	
	
	// getters y setters

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Set<ProfesoresCochesLlaves> getCoches() {
		return coches;
	}

	public void setCoches(Set<ProfesoresCochesLlaves> coches) {
		this.coches = coches;
	}
	

	public Set<ProfesoresCochesLlaves> getLlaves() {
		return llaves;
	}





	public void setLlaves(Set<ProfesoresCochesLlaves> llaves) {
		this.llaves = llaves;
	}





	@Override
	public String toString() {
		return "Profesor [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", alumnos="
				+ alumnos + ", coches=" + coches + "]";
	}

	
	
	
	

}
