package principal.modelo.dto;

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

public class CocheDTO {
	
	@Id
	private Integer id;
	
	private String matricula;
	
	private String modelo;
	
	private String marca;
	
	private Date fechaITV;
	
	
	
	public CocheDTO() {
	}


	public CocheDTO(String matricula, String modelo, String marca) {
		this.matricula = matricula;
		this.modelo = modelo;
		this.marca = marca;
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


	@Override
	public String toString() {
		return "CocheDTO [id=" + id + ", matricula=" + matricula + ", modelo=" + modelo + ", marca=" + marca
				+ ", fechaITV=" + fechaITV + "]";
	}

	



	
	
	
	

}