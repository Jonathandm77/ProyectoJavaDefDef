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
	
	@OneToMany(mappedBy = "llave", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProfesoresCochesLlaves> llaves;
	

	
	@OneToMany(mappedBy = "profesor", cascade=CascadeType.MERGE,orphanRemoval=true)
	private Set<ProfesoresCochesLlaves> profesores;
	
	
	
	
	
	
	
	
	public Coche() {
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCochesLlaves>();
		llaves=new HashSet<ProfesoresCochesLlaves>();
		
	}


	public Coche(String matricula, String modelo, String marca) {
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCochesLlaves>();
		llaves=new HashSet<ProfesoresCochesLlaves>();
	}
	
	public void addLlave(Profesor p, Llave l) {
		ProfesoresCochesLlaves pc=new ProfesoresCochesLlaves(p,this,l);
		if(llaves.contains(pc)) {
			llaves.remove(pc);
		}
		llaves.add(pc);
		p.getLlaves().add(pc);
		p.getCoches().add(pc);
	}
	
	
	//getters setters
	
	
	public String getMatricula() {
		return matricula;
	}
	public Integer getId() {
		return id;
	}
	
	


	public Set<ProfesoresCochesLlaves> getLlaves() {
		return llaves;
	}


	public void setLlaves(Set<ProfesoresCochesLlaves> llaves) {
		this.llaves = llaves;
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
	public Set<ProfesoresCochesLlaves> getProfesores() {
		return profesores;
	}
	public void setProfesores(Set<ProfesoresCochesLlaves> profesores) {
		this.profesores = profesores;
	}


	@Override
	public String toString() {
		return "Coche [id=" + id + ", matricula=" + matricula + ", modelo=" + modelo + ", marca=" + marca
				+ ", fechaITV=" + fechaITV + ", alumnos=" + alumnos + ", profesores=" + profesores + "]";
	}



	
	
	
	

}
