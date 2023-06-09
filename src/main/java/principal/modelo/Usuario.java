package principal.modelo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="nombre", unique=true)
	private String nombre;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="id_profesor")
	private Integer idProfesor;
	
	@Column(name="id_alumno")
	private Integer idAlumno;
	
	@Column(name="imagenPerfil")
	public String imagenPerfil;
	
	public String url;
	
	public String tema;

	@ManyToMany(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(
			name="usuarios_roles",
			joinColumns= {@JoinColumn(name="id_usuario")},
			inverseJoinColumns= {@JoinColumn(name="id_rol")}
			)
	@JsonIgnore
	private Set<Rol> roles;
	public Usuario() {
		roles=new HashSet<Rol>();
	}
	
	

	public Usuario(String nombre, String username, String password) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		roles=new HashSet<Rol>();
	}
	
	



	public Usuario(String nombre, String username, String password, Integer idProfesor) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.idProfesor = idProfesor;
		roles=new HashSet<Rol>();
	}


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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}
	
	



	public String getTema() {
		return tema;
	}



	public void setTema(String tema) {
		this.tema = tema;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
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



	public String getImagenPerfil() {
		return imagenPerfil;
	}



	public void setImagenPerfil(String imagenPerfil) {
		this.imagenPerfil = imagenPerfil;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		  return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean extensionValida(MultipartFile file) {
		
	if (file != null) {
	    String fotoNombre = file.getOriginalFilename();
	    int indicePunto = fotoNombre.lastIndexOf(".");
	    if (indicePunto > 0 && indicePunto < fotoNombre.length() - 1) {
	        String extension = fotoNombre.substring(indicePunto + 1).toLowerCase();
	        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
	        	return true;
	        } else {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
	return false;
	
	}
	
	
	public boolean esAdmin() {
		for (Rol r: this.getRoles()) {
		if(r.getNombre().compareTo("ROLE_ADMIN") == 0){
		return true;
		}
		}
		return false;
		}
}
	 
	
