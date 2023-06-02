package principal.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Profesores")
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name="id")
	private Integer id;

	@Column(name = "DNI", unique=true)
	private String dni;

	@Column(name = "Nombre")
	private String nombre;

	@Column(name = "Apellidos")
	private String apellidos;

	@OneToMany(mappedBy = "profesor", fetch = FetchType.EAGER)
	private Set<Alumno> alumnos;

	@OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProfesoresCoches> coches;

	public Profesor() {
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}
	
	

	public Profesor(String dni, String nombre, String apellidos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}



	public Profesor(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}

	public Profesor(Integer id, String dni, String nombre, String apellidos, Set<Alumno> alumnos,
			Set<ProfesoresCoches> coches) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		alumnos = new HashSet<Alumno>();
		coches = new HashSet<ProfesoresCoches>();
	}

	public void juegoLlaves(Coche c) throws SQLException {
		Random random = new Random();
	    String l = String.format("%c%c-%d",'A' + random.nextInt(26),'A' + random.nextInt(26),random.nextInt(100));
		ProfesoresCoches pc=new ProfesoresCoches(this,c,l);
		boolean presente=false;
			
			 // Crear una conexión a la base de datos
		Connection connection = DriverManager.getConnection("jdbc:mysql://mysqldb:3306/proyecto", "usuario","usuario");

            
            // Preparar la consulta SQL
            String sql = "SELECT * FROM profesores_coches WHERE coche_id = "+c.getId()+" AND profesor_id = "+this.getId();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Ejecutar la consulta y obtener el resultado
            ResultSet resultSet = statement.executeQuery();
            
            // Comprobar si hay resultados y mostrarlos
            if (resultSet.next()) {
            	presente = true;
            } else {
                System.out.println("La combinación de coche_id = "  +
                                   " y profesor_id = "  + " no existe en la tabla.");
            }
			
			
			if (!presente) {
			coches.add(pc);
			c.getProfesores().add(pc);
			}
			}

	

	// getters y setters

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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Set<ProfesoresCoches> getCoches() {
		return coches;
	}

	public void setCoches(Set<ProfesoresCoches> coches) {
		this.coches = coches;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", alumnos="
				+ alumnos + ", coches=" + coches + "]";
	}

}
