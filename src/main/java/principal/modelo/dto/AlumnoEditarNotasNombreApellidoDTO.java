package principal.modelo.dto;

public class AlumnoEditarNotasNombreApellidoDTO {
private String notas;
private String nombre;
private String apellidos;

public String getApellidos() {
	return apellidos;
}
public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
}
public String getNotas() {
	return notas;
}
public void setNotas(String notas) {
	this.notas = notas;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

}
