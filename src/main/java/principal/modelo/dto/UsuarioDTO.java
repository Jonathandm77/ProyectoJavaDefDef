package principal.modelo.dto;

public class UsuarioDTO{
	private Integer id;
	
	private String nombre;
	
	private String username;
	
	private String password;
	
	private boolean esProfesor;
	
	private Integer idProfesor;

	public UsuarioDTO() {
	}
	
	
	
	

	public UsuarioDTO(Integer id, String nombre, String username, String password, boolean esProfesor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.esProfesor = esProfesor;
	}





	public UsuarioDTO(Integer id, String nombre, String username, String password, boolean esProfesor,
			Integer idProfesor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.esProfesor = esProfesor;
		this.idProfesor = idProfesor;
	}





	public boolean isEsProfesor() {
		return esProfesor;
	}



	public void setEsProfesor(boolean esProfesor) {
		this.esProfesor = esProfesor;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}





	public Integer getIdProfesor() {
		return idProfesor;
	}





	public void setIdProfesor(Integer idProfesor) {
		this.idProfesor = idProfesor;
	}
	

}
	 
	
