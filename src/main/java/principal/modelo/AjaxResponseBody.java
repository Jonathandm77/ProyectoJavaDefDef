package principal.modelo;

public class AjaxResponseBody {
	
	String mensaje;
	Alumno alumnoJSON;
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Alumno getAlumnoJSON() {
		return alumnoJSON;
	}
	public void setAlumnoJSON(Alumno alumnoJSON) {
		this.alumnoJSON = alumnoJSON;
	}
	
	
}
