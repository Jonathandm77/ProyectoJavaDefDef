package principal.controller;

public class UsuarioAddDTO {
	
	private String nombre;
	
	private String username;
	
	private String password;
	
	private Integer idProfesor;
	
	private Integer idAlumno;

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

	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

	public UsuarioAddDTO(String nombre, String username, String password, Integer idProfesor, Integer idAlumno) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.idProfesor = idProfesor;
		this.idAlumno = idAlumno;
	}

	public UsuarioAddDTO() {
		super();
	}
	
	
}
