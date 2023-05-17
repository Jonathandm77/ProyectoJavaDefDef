package principal.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Alumnos")
public class Alumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Integer id;
	
	@Column(name="DNI", unique=true)
	private String dni;
	
	@Column(name="Nombre", nullable = false)
	private String nombre;
	
	@Column(name="Apellidos", nullable = false)
	private String apellidos;
	
	@Column (name="Notas")
	private String notas;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="id_profesor", nullable = false)
	private Profesor profesor;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_Coche", nullable = false)
	private Coche coche;
	
	@JsonIgnore
	@OneToMany(mappedBy = "alumno", cascade=CascadeType.REMOVE)
	private Set<Clase> clases;
	
	
	
	
	
	
	
	
	


public Alumno() {
		profesor=new Profesor();
		coche=new Coche();
		clases=new HashSet<Clase>();
	}

public Alumno(String nombre, String dni) {
		
		this.nombre = nombre;
		this.dni = dni;
		profesor=new Profesor();
		coche=new Coche();
		clases=new HashSet<Clase>();
	}





	

//getters setters



	public Alumno(Integer id, String dni, String nombre, String apellidos, String notas, Profesor profesor, Coche coche) {
	this.id = id;
	this.dni = dni;
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.notas = notas;
	this.profesor = new Profesor();
	this.coche = new Coche();
	clases=new HashSet<Clase>();
}

	public String getNombre() {
		return nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
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
	
	public Set<Clase> getClases() {
		return clases;
	}

	public void setClases(Set<Clase> clases) {
		this.clases = clases;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", profesor="
				+ profesor + ", coche=" + coche + "]";
	}
	
	public void añadirClase (Clase c) {
		this.clases.add(c);
	}
	
	

	
}
