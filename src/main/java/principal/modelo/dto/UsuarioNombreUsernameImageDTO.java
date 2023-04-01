package principal.modelo.dto;

import org.springframework.web.multipart.MultipartFile;

public class UsuarioNombreUsernameImageDTO {
private String nombre;
private String username;
private MultipartFile imagen;
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
public MultipartFile getImagen() {
	return imagen;
}
public void setImagen(MultipartFile imagen) {
	this.imagen = imagen;
}



}
