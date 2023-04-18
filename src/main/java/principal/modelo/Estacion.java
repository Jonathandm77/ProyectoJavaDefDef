package principal.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estacion")
public class Estacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="latitud")
	private double latitud;
	
	@Column(name="longitud")
	private double longitud;
	
	@Column(name="tipoEstacion")
	private TipoEstacion tipo;
	
	@Column(name="activa")
	private boolean activa;
	
	@Column(name="estado")
	private EstadoEstacion estado;

	public Estacion(int id, String nombre, double latitud, double longitud, TipoEstacion tipo, boolean activa,
			EstadoEstacion estado) {
		this.id = id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.tipo = tipo;
		this.activa = activa;
		this.estado = estado;
	}
	
	

	public Estacion(int id, String nombre, boolean activa) {
		this.id = id;
		this.nombre = nombre;
		this.activa = activa;
	}



	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public TipoEstacion getTipo() {
		return tipo;
	}

	public boolean isActiva() {
		return activa;
	}

	public EstadoEstacion getEstado() {
		return estado;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setTipo(TipoEstacion tipo) {
		this.tipo = tipo;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public void setEstado(EstadoEstacion estado) {
		this.estado = estado;
	}
}

enum TipoEstacion {
	EMISION, INMISION
}

enum EstadoEstacion {
	OPERATIVO, EN_CALIBRACION, EN_MANTENIMIENTO
}
