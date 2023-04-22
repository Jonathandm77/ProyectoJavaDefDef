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
	// @Column(name="id")
	private Integer id;

	@Column(name = "DNI")
	private String dni;

	@Column(name = "Nombre")
	private String nombre;

	@Column(name = "Apellidos")
	private String apellidos;

	@OneToMany(mappedBy = "profesor", fetch = FetchType.EAGER)
	private Set<Alumno> alumnos;

	@OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProfesoresCoches> coches;

	public Profesor() {
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}

	public Profesor(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}

	public Profesor(Integer id, String dni, String nombre, String apellidos, Set<Alumno> alumnos,
			Set<ProfesoresCoches> coches) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}

	public void juegoLlaves(Coche c, String l) {
		ProfesoresCoches pc=new ProfesoresCoches(this,c,l);
		boolean presente=false;
		for (ProfesoresCoches set : coches) {
			if (set.getCoche().getMatricula().equals(c.getMatricula())) {
			presente = true;
			}
			}
			if (!presente) {
			coches.add(pc);
			c.getProfesores().add(pc);
			}
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

	public Set<ProfesoresCoches> getCoches() {
		return coches;
	}

	public void setCoches(Set<ProfesoresCoches> coches) {
		this.coches = coches;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", alumnos="
				+ alumnos + ", coches=" + coches + "]";
	}

}
