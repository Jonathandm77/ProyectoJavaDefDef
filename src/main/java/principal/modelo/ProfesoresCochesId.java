package principal.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfesoresCochesId implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Column(name="profesor_id")
	private Integer profesorId;
	
	@Column(name="coche_id")
	private Integer cocheId;

	public ProfesoresCochesId() {
		super();
	}

	public ProfesoresCochesId(Integer idP, Integer idC) {
		this.profesorId = idP;
		this.cocheId = idC;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || getClass() !=o.getClass()) { 
			return false;
		}
		ProfesoresCochesId that=(ProfesoresCochesId) o;
		return Objects.equals(profesorId, that.profesorId) && Objects.equals(cocheId, that.cocheId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cocheId, profesorId);
	}

	public Integer getProfesorId() {
		return profesorId;
	}

	public void setProfesorId(Integer profesorId) {
		this.profesorId = profesorId;
	}

	public Integer getCocheId() {
		return cocheId;
	}

	public void setCocheId(Integer cocheId) {
		this.cocheId = cocheId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
