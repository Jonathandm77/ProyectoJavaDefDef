package principal.modelo;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="Coches")
public class Coche {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="Matricula", unique=true)
	private String matricula;
	
	@Column(name="Modelo")
	private String modelo;
	
	@Column(name="Marca")
	private String marca;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="FechaITV")
	private Date fechaITV;
	
	@Column(name="HoraITV")
	private LocalTime horaITV;
	
	@OneToMany(mappedBy = "coche", fetch = FetchType.EAGER)
	private Set<Alumno> alumnos;
	
	@OneToMany(mappedBy = "profesor", cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<ProfesoresCoches> profesores;
	
	@Column(name="foto")
	public String foto;
	
	public String url;
	
	
	public void generarMatricula() {
	    Random random = new Random();
	    
	    String numeros = "";
	    for (int i = 0; i < 4; i++) {
	        int numero = random.nextInt(10); // Generar un número aleatorio entre 0 y 9
	        numeros += numero;
	    }
	    
	    String letras = "";
	    for (int i = 0; i < 3; i++) {
	        char letra = (char) (random.nextInt(26) + 'A'); // Generar una letra aleatoria entre A y Z
	        letras += letra;
	    }
	    
	    String matricula = numeros + " " + letras;
	    
	   this.setMatricula(matricula);
	}
	
	
	
	
	public Coche() {
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCoches>();
		
	}


	public Coche(String matricula, String modelo, String marca, Date fechaITV, LocalTime horaITV) {
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		this.fechaITV=fechaITV;
		this.horaITV=horaITV;
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCoches>();
	}
	
	/*public void addLlave(Profesor p, Llave l) {
		ProfesoresCoches pc=new ProfesoresCoches(p,this,l);
		if(llaves.contains(pc)) {
			llaves.remove(pc);
		}
		llaves.add(pc);
		p.getLlaves().add(pc);
		p.getCoches().add(pc);
	}*/
	
	
	//getters setters
	
	
	


	public String getMatricula() {
		return matricula;
	}
	public Coche(String matricula, String modelo, String marca) {
		super();
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCoches>();
	}
	
	public Coche(String modelo, String marca) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		alumnos=new HashSet<Alumno>();
		profesores=new HashSet<ProfesoresCoches>();
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


	public LocalTime getHoraITV() {
		return horaITV;
	}


	public void setHoraITV(LocalTime horaITV) {
		this.horaITV = horaITV;
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
	public Set<ProfesoresCoches> getProfesores() {
		return profesores;
	}
	public void setProfesores(Set<ProfesoresCoches> profesores) {
		this.profesores = profesores;
	}
	

	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "Coche [id=" + id + ", matricula=" + matricula + ", modelo=" + modelo + ", marca=" + marca
				+ ", fechaITV=" + fechaITV + ", alumnos=" + alumnos + ", profesores=" + profesores + "]";
	}
	
	public boolean extensionValida(MultipartFile file) {
		
	if (file != null) {
	    String fotoNombre = file.getOriginalFilename();
	    int indicePunto = fotoNombre.lastIndexOf(".");
	    if (indicePunto > 0 && indicePunto < fotoNombre.length() - 1) {
	        String extension = fotoNombre.substring(indicePunto + 1).toLowerCase();
	        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
	        	return true;
	        } else {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
	return false;
	
	}



	
	
	
	

}
