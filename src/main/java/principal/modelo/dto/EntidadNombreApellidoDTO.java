package principal.modelo.dto;

public class EntidadNombreApellidoDTO {
	String nombre;
	String apellidos;
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
	
	public EntidadNombreApellidoDTO() {
		super();
	}
	public EntidadNombreApellidoDTO(String nombre, String apellidos) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
}
