package principal.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfesoresCochesLlavesId implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Column(name="profesor_id")
	private Integer profesorId;
	
	@Column(name="coche_id")
	private Integer cocheId;
	
	@Column(name="llave_id")
	private Integer llaveId;

	public ProfesoresCochesLlavesId() {
		super();
	}

	public ProfesoresCochesLlavesId(Integer idP, Integer idC, Integer idL) {
		this.profesorId = idP;
		this.cocheId = idC;
		this.cocheId=idL;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || getClass() !=o.getClass()) { 
			return false;
		}
		ProfesoresCochesLlavesId that=(ProfesoresCochesLlavesId) o;
		return Objects.equals(profesorId, that.profesorId) && Objects.equals(cocheId, that.cocheId) && Objects.equals(llaveId, that.llaveId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cocheId, profesorId, llaveId);
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
	

	public Integer getLlaveId() {
		return llaveId;
	}

	public void setLlaveId(Integer llaveId) {
		this.llaveId = llaveId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
