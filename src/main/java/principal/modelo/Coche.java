package principal.modelo;

import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Coches")
public class Coche {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Integer id;
	
	@Column(name="Matricula")
	private String matricula;
	
	@Column(name="Modelo")
	private String modelo;
	
	@Column(name="Marca")
	private String marca;
	
	@Column(name="FechaITV")
	private Date fechaITV;
	
	@OneToMany(mappedBy = "coche", fetch = FetchType.EAGER)
	private Set<Alumno> alumnos;
	

	
	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(
			name="profesores_coches",
			joinColumns = {@JoinColumn(name="Id_Coche")},
			inverseJoinColumns = {@JoinColumn(name="Id_Profesor")}
	)
	private Set<Profesor> profesores;
	
	
	
	
	
	
	
	
	public Coche() {
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<Profesor>();
	}


	public Coche(String matricula, String modelo, String marca) {
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<Profesor>();
	}
	
	
	//getters setters
	
	
	public String getMatricula() {
		return matricula;
	}
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Date getFechaITV() {
		return fechaITV;
	}
	public void setFechaITV(Date fechaITV) {
		this.fechaITV = fechaITV;
	}
	public Set<Alumno> getAlumnos() {
		return alumnos;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	public Set<Profesor> getProfesores() {
		return profesores;
	}
	public void setProfesores(Set<Profesor> profesores) {
		this.profesores = profesores;
	}


	@Override
	public String toString() {
		return "Coche [id=" + id + ", matricula=" + matricula + ", modelo=" + modelo + ", marca=" + marca
				+ ", fechaITV=" + fechaITV + ", alumnos=" + alumnos + ", profesores=" + profesores + "]";
	}



	
	
	
	

}
