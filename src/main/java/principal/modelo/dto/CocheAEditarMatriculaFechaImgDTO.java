package principal.modelo.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CocheAEditarMatriculaFechaImgDTO {
	String matricula;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	Date fechaITV;
	String foto;
	

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getMatricula() {
		return matricula;
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
	
	

}
