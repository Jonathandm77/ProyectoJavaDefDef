package principal.modelo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class AlumnoDTO {
	private Integer id;
	
	private String dni;
	
	private String nombre;
	
	private String apellidos;
	
	private String notas;
	
	
	
	
	
	
	
	
	
	
	
	
	
	


public AlumnoDTO() {
	}

public AlumnoDTO(String nombre, String dni) {
		
		this.nombre = nombre;
		this.dni = dni;
	}




	

//getters setters



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

	@Override
	public String toString() {
		return "AlumnoDTO [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", notas="
				+ notas + "]";
	}




	
	

	
}
