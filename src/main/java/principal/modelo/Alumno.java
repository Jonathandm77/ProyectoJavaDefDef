package principal.modelo;

import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Alumnos")
public class Alumno {
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
	
	@Column (name="Notas")
	private String notas;
	
	
	
	@ManyToOne
	@JoinColumn(name ="id_profesor", nullable = false)
	private Profesor profesor;
	
	
	@ManyToOne
	@JoinColumn(name = "id_Coche", nullable = false)
	private Coche coche;
	
	
	
	
	
	
	
	
	


public Alumno() {
		profesor=new Profesor();
		coche=new Coche();
	}

public Alumno(String nombre, String dni) {
		
		this.nombre = nombre;
		this.dni = dni;
		profesor=new Profesor();
		coche=new Coche();
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

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", profesor="
				+ profesor + ", coche=" + coche + "]";
	}
	
	

	
}
