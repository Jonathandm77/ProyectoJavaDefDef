package principal.modelo;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="Clases")
public class Clase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name ="id_alumno", nullable = false)
	private Alumno alumno;
	
	@OneToOne
	@JoinColumn(name ="id_profesor", nullable = false)
	private Profesor profesor;
	
	@OneToOne
	@JoinColumn(name ="id_coche", nullable = false)
	private Coche coche;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="Fecha", nullable=false)
	private Date fecha;

	@Column(name="Hora", nullable=false)
	private LocalTime hora;
	
	


	public Clase() {
	}

	public Clase(Alumno alumno) {
		this.alumno = alumno;
		this.profesor = alumno.getProfesor();
		this.coche = alumno.getCoche();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
		this.coche=alumno.getCoche();
		this.profesor=alumno.getProfesor();
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}


	


	 
	
	
}
