package principal.modelo.dto;

public class UsuarioDTO{
	private Integer id;
	
	private String nombre;
	
	private String username;
	
	private String password;

	public UsuarioDTO() {
	}
	
	public UsuarioDTO(String nombre, String usuario, String pass) {
		this.nombre=nombre;
		this.username=usuario;
		this.password=pass;
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
	

}
	 
	
