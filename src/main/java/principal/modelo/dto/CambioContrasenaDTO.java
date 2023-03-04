package principal.modelo.dto;

public class CambioContrasenaDTO {
	private String actual;
	private String nueva;
	private String confirm;
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getNueva() {
		return nueva;
	}
	public void setNueva(String nueva) {
		this.nueva = nueva;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public CambioContrasenaDTO() {
	}
	public CambioContrasenaDTO(String actual, String nueva, String confirm) {
		super();
		this.actual = actual;
		this.nueva = nueva;
		this.confirm = confirm;
	}
	
	

}
