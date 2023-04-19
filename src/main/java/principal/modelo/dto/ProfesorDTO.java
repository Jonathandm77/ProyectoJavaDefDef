package principal.modelo.dto;

public class ProfesorDTO {
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String dni;
	

	private String nombre;
	
	private String apellidos;


	
	

	public ProfesorDTO(String nombre, String dni) {
		
		this.nombre = nombre;
		this.dni = dni;

	}
	
	

	public ProfesorDTO(Integer id, String dni, String nombre, String apellidos) {
		super();
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	// getters y setters

	public String getNombre() {
		return nombre;
	}
	

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return "ProfesorDTO [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}

	

	
	
	
	

}
