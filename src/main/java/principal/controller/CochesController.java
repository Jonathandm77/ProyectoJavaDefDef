package principal.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import principal.modelo.Alumno;
import principal.modelo.Clase;
import principal.modelo.Coche;
import principal.modelo.ImageInfo;
import principal.modelo.Profesor;
import principal.modelo.ProfesoresCoches;
import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.modelo.dto.CocheAEditarMatriculaFechaImgDTO;
import principal.modelo.dto.CocheBuscarMarcaDTO;
import principal.modelo.dto.CocheBuscarMatriculaDTO;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;
import principal.servicio.implementacion.UsuarioServiceImpl;
import principal.servicio.interfaces.FileStorageService;

@Controller
@RequestMapping("/coches")
public class CochesController {
	/*
	 * AlumnoDAO alumnoDAO=new AlumnoDAO(); CocheDAO cocheDAO=new CocheDAO();
	 * ProfesorDAO profeDAO=new ProfesorDAO();
	 */

	@Autowired
	private AlumnoServiceImpl alumnoService;
	@Autowired
	private ProfesorServiceImpl profeService;
	@Autowired
	private CocheServiceImpl cocheService;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	private UsuarioServiceImpl usuarioService;

	@GetMapping(value = { "", "/" })//carga de recursos
	String homecoches(Model model) {
		ArrayList<Coche> misCoches = (ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Profesor> misProfesores = (ArrayList<Profesor>) profeService.listarProfesores();

		for (Coche Coche : misCoches) {//carga de las imagenes de los coches
			if (Coche.getFoto() != null) {

				Resource resource = storageService.load(Coche.getFoto());
				String url = MvcUriComponentsBuilder
						.fromMethodName(SecurityController.class, "serveFile", resource.getFilename()).build()
						.toString();
				ImageInfo img = new ImageInfo(resource.getFilename(), url);
				Coche.setUrl(img.getUrl());
			}
		}

		model.addAttribute("listaCoches", misCoches);
		model.addAttribute("listaAlumnos", misAlumnos);
		model.addAttribute("listaProfesores", misProfesores);

		model.addAttribute("cocheNuevo", new Coche());
		model.addAttribute("cocheaEditar", new Coche());
		model.addAttribute("cocheaBuscar", new Coche());
		return "coches";
	}

	@PostMapping("/add")//añade un nuevo coche
	public String addCoche(@ModelAttribute("cocheNuevo") Coche cocheNew, BindingResult bidingresult,
			RedirectAttributes redirectAttributes) {
		try {
			String matricula = cocheNew.getMatricula();
			String numeros = matricula.substring(0, 4);
			String letras = matricula.substring(5, 8).toUpperCase();
			String matriculaFinal = numeros + " " + letras;//convertimos las letras a mayusculas
			cocheNew.setMatricula(matriculaFinal);
			cocheService.insertarCoche(cocheNew);
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("error", "La matrícula ya existe.");//controlamos si la matricula ya existe
		}
		return "redirect:/coches";
	}

	@PostMapping("/edit/{id}")//edita un coche
	public String editarCoche(@PathVariable Integer id,
			@ModelAttribute("cocheaEditar") CocheAEditarMatriculaFechaImgDTO cocheEditado, BindingResult bidingresult,
			Model model) {
		Coche cocheaEditar = cocheService.obtenerCochePorId(id).get();
		if (cocheEditado.getMatricula() != null && cocheEditado.getMatricula() != "") {
			String matricula = cocheEditado.getMatricula();
			String numeros = matricula.substring(0, 4);
			String letras = matricula.substring(5, 8).toUpperCase();
			String matriculaFinal = numeros + " " + letras;//convertimos las letras a mayusculas
			cocheaEditar.setMatricula(matriculaFinal);
		}
		if (cocheEditado.getFechaITV() != null)
			cocheaEditar.setFechaITV(cocheEditado.getFechaITV());
		cocheService.insertarCoche(cocheaEditar);//guardamos
		return "redirect:/coches/" + cocheaEditar.getId();
	}

	@PostMapping("/edit/photo/{id}")
	public String editarFotoCoche(@PathVariable Integer id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		Coche cocheaEditar = cocheService.obtenerCochePorId(id).get();
		if (cocheaEditar.extensionValida(file)) {
			if (cocheaEditar.getFoto() != null)//si el coche tiene foto
				storageService.delete(cocheaEditar.getFoto());//borramos la anterior
			cocheaEditar.setFoto(file.getOriginalFilename());
			storageService.save(file);//la guardamos en el servidor
			cocheService.insertarCoche(cocheaEditar);
		}else {
			redirectAttributes.addFlashAttribute("error", "El archivo no es válido.");//controlamos si el archivo es válido
		}
		return "redirect:/coches/" + cocheaEditar.getId();
	}

	@GetMapping({ "/{id}" })
	String idCoche(Model model, @PathVariable Integer id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario actualUser = (Usuario) auth.getPrincipal();
		Coche cocheMostrar = cocheService.obtenerCochePorId(id).get();
		model.addAttribute("cocheMostrar", cocheMostrar);
		boolean tieneRolProfesor = false;
		
		for(Rol r: actualUser.getRoles()) {
			if(r.getNombre().equals("ROLE_TEACHER"))
				tieneRolProfesor=true;
		}

		if (tieneRolProfesor) {//si es un profesor mostrara la llave de ese coche 
			Profesor profeUsuario=profeService.obtenerProfesorPorId(actualUser.getIdProfesor()).get();
			for(ProfesoresCoches pc:profeUsuario.getCoches()) {
				if(pc.getCoche()==cocheMostrar)
					model.addAttribute("miLlave",pc.getCodigoLlave());
			}
		}

		if (cocheMostrar.getFoto() != null) {//si el coche tiene foto, para decidir si mostrar o no en la pagina
			Resource resource = storageService.load(cocheMostrar.getFoto());
			String url = MvcUriComponentsBuilder
					.fromMethodName(SecurityController.class, "serveFile", resource.getFilename()).build().toString();
			ImageInfo img = new ImageInfo(resource.getFilename(), url);

			model.addAttribute("img", img);
		}
		return "coche";
	}

	@GetMapping({ "/delete/{id}" })//borrar coche
	String deleteCoche(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes) throws SQLException {
		if (usuarioService.esAdminActual()) {
		boolean creado=false;
		Optional<Coche> cocheaEliminar = cocheService.obtenerCochePorId(id);
		ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Coche> misCoches = (ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Profesor> misProfes = (ArrayList<Profesor>) profeService.listarProfesores();
		if (!cocheaEliminar.isEmpty()) {
		ArrayList<Profesor> profeTemp = new ArrayList<Profesor>();// almacenamos las nuevas combinaciones de profesores
																	// y coches
		ArrayList<Coche> cocheTemp = new ArrayList<Coche>();
		for (Alumno a : misAlumnos) {
			if (a.getCoche().getId() == cocheaEliminar.get().getId()) {
				int coche = (int) (Math.random() * misCoches.size());
				if (misCoches.isEmpty() || misCoches.size() == 1) {//si el coche a borrar es el ultimo, crea un coche genérico
					Coche cGenerico = new Coche("2021 XS", "Nissan");
					cGenerico.generarMatricula();//generamos la matrícula aleatoriamente
					misCoches.add(cGenerico);
					a.setCoche(cGenerico);
					for (Clase c : a.getClases()) {
						c.setCoche(cGenerico);
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());//añadimos el coche y profesor a las listas de combinaciones
					cocheService.insertarCoche(cGenerico);
					creado=true;
				} else {//si no es el ultimo, no se crea el coche generico

					do {
						coche = (int) (Math.random() * misCoches.size());//establecemos el nuevo coche de los alummnos que usan el coche que vamos a borrar
					} while (coche == misCoches.indexOf(a.getCoche()));//nos aseguramos de que no es el mismo coche que vamos a borrar
					a.setCoche(misCoches.get(coche));
					for (Clase c : a.getClases()) {
						c.setCoche(misCoches.get(coche));//tambien cambiamos el coche en las clases
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());//añadimos el coche y profesor a las listas de combinaciones
				}
				
				
				misCoches.get(coche).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				profeService.insertarProfesor(profeService.obtenerProfesorPorId(a.getProfesor().getId()).get());
				cocheService.insertarCoche(cocheService.obtenerCochePorId(misCoches.get(coche).getId()).get());//actualizamos
			}
		}

		List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();//guardamos las combinaciones de profesores y coches que vamos a borrar

		for (Profesor a : misProfes) {
			if (!a.getCoches().isEmpty()) {
				for (ProfesoresCoches pc : a.getCoches()) {
					if (pc.getCoche().getId() == cocheaEliminar.get().getId()) {//si el coche de la combinacion es el coche que vamos a borrar
						pc.setCoche(null);
						pc.setProfesor(null);
						elementosAEliminar.add(pc);//se añade para eliminarlo despues
					}

				}
				a.getCoches().removeAll(elementosAEliminar);//se eliminan todos los profesorescoches que contengan ese coche del profesor
				elementosAEliminar.clear();//vaciamos para optimizar
			}
			profeService.insertarProfesor(a);//guardamos
		}

		cocheaEliminar.get().getAlumnos().clear();
		cocheaEliminar.get().getProfesores().clear();
		if (profeTemp != null) {
			for (int i = 0; i < profeTemp.size(); i++) {

				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));//para cada nueva combinacion se genera la llave y la asociacion en la BBDD
			}
		}
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root", "");

		String sql = "DELETE FROM profesores_coches WHERE coche_id = " + cocheaEliminar.get().getId();

		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);//ejecutamos la consulta para dejar la tabla completamente limpia de asociaciones inservibles

		connection.close();
		if(!creado) {//se vuelve a ejecutar ya que si no existen alumnos no se ejecutaría antes
			if (misCoches.isEmpty() || misCoches.size() == 1) {
				Coche cGenerico = new Coche("2021 XS", "Nissan");
				cGenerico.generarMatricula();
				misCoches.add(cGenerico);
				// misCoches.get(0).setFechaITV(fecha);
				cocheService.insertarCoche(cGenerico);
				creado=true;
			}
		}
		cocheService.eliminarCoche(cocheaEliminar.get());//eliminamos el coche
		}else
			redirectAttributes.addFlashAttribute("error", "El coche no existe");//controlamos si el coche no existe
		}
			
		return "redirect:/coches";
	}

	@PostMapping({ "/searchMarca" })//buscar coche por marca
	String buscarCochePorMarca(Model model, @ModelAttribute("cocheaBuscar") CocheBuscarMarcaDTO cocheBuscado,
			BindingResult bidingresult) {
		ArrayList<Coche> cochesMarca = cocheService.obtenerCochesPorMarca(cocheBuscado.getMarca());
		model.addAttribute("cochesMarca", cochesMarca);

		return "cochesBuscadosPorMarca";

	}

	@PostMapping({ "/searchMatricula" })//buscar coche por matricula
	String buscarCochePorMatricula(@ModelAttribute("cocheaBuscar") CocheBuscarMatriculaDTO cocheBuscado,
			BindingResult bidingresult, Model model) {
		String matricula = cocheBuscado.getMatricula();
		String letras = matricula.substring(5, 8).toUpperCase();
		String mat = matricula.substring(0, 4) + " " + letras;//adaptamos matricula por si acaso a mayusculas
		Optional<Coche> cochematricula = cocheService.encontrarCochePorMatricula(mat);
		if (!cochematricula.isEmpty()) {//si existe
			Integer id = cochematricula.get().getId();
			return "redirect:/coches/" + id;
		} else {//si no existe
			ArrayList<Coche> cocheVacio = new ArrayList<Coche>();
			model.addAttribute("cochesMarca", cocheVacio);
			return "cochesBuscadosPorMarca";
		}
	}
}
