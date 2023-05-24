package principal.modelo.dto;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ClaseDTO {
	private EntityIdDTO alumno;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha;

	private LocalTime hora;

	public EntityIdDTO getAlumno() {
		return alumno;
	}

	public void setAlumno(EntityIdDTO alumno) {
		this.alumno = alumno;
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

	public ClaseDTO(EntityIdDTO alumno, Date fecha, LocalTime hora) {
		super();
		this.alumno = alumno;
		this.fecha = fecha;
		this.hora = hora;
	}

	public ClaseDTO() {
		super();
	}

}
