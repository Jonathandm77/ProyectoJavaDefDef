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

	@GetMapping(value = { "", "/" })
	String homecoches(Model model) {
		ArrayList<Coche> misCoches = (ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Profesor> misProfesores = (ArrayList<Profesor>) profeService.listarProfesores();

		for (Coche Coche : misCoches) {
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

	@PostMapping("/add")
	public String addCoche(@ModelAttribute("cocheNuevo") Coche cocheNew, BindingResult bidingresult,
			RedirectAttributes redirectAttributes) {
		try {
			String matricula = cocheNew.getMatricula();
			String numeros = matricula.substring(0, 4);
			String letras = matricula.substring(5, 8).toUpperCase();
			String matriculaFinal = numeros + " " + letras;
			cocheNew.setMatricula(matriculaFinal);
			cocheService.insertarCoche(cocheNew);
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("error", "La matrícula ya existe.");
		}
		return "redirect:/coches";
	}

	@PostMapping("/edit/{id}")
	public String editarCoche(@PathVariable Integer id,
			@ModelAttribute("cocheaEditar") CocheAEditarMatriculaFechaImgDTO cocheEditado, BindingResult bidingresult,
			Model model) {
		Coche cocheaEditar = cocheService.obtenerCochePorId(id);
		if (cocheEditado.getMatricula() != null && cocheEditado.getMatricula() != "")
			cocheaEditar.setMatricula(cocheEditado.getMatricula());
		if (cocheEditado.getFechaITV() != null)
			cocheaEditar.setFechaITV(cocheEditado.getFechaITV());
		cocheService.insertarCoche(cocheaEditar);
		return "redirect:/coches/" + cocheaEditar.getId();
	}

	@PostMapping("/edit/photo/{id}")
	public String editarFotoCoche(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
		Coche cocheaEditar = cocheService.obtenerCochePorId(id);
		if (!file.getOriginalFilename().equals("")) {
			if (cocheaEditar.getFoto() != null)
				storageService.delete(cocheaEditar.getFoto());
			cocheaEditar.setFoto(file.getOriginalFilename());
			storageService.save(file);
		}
		cocheService.insertarCoche(cocheaEditar);
		return "redirect:/coches/" + cocheaEditar.getId();
	}

	@GetMapping({ "/{id}" })
	String idCoche(Model model, @PathVariable Integer id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario actualUser = (Usuario) auth.getPrincipal();
		Coche cocheMostrar = cocheService.obtenerCochePorId(id);
		model.addAttribute("cocheMostrar", cocheMostrar);
		boolean tieneRolProfesor = false;
		
		for(Rol r: actualUser.getRoles()) {
			if(r.getNombre().equals("ROLE_TEACHER"))
				tieneRolProfesor=true;
		}

		if (tieneRolProfesor) {
			Profesor profeUsuario=profeService.obtenerProfesorPorId(actualUser.getIdProfesor()).get();
			for(ProfesoresCoches pc:profeUsuario.getCoches()) {
				if(pc.getCoche()==cocheMostrar)
					model.addAttribute("miLlave",pc.getCodigoLlave());
			}
		}

		if (cocheMostrar.getFoto() != null) {
			Resource resource = storageService.load(cocheMostrar.getFoto());
			String url = MvcUriComponentsBuilder
					.fromMethodName(SecurityController.class, "serveFile", resource.getFilename()).build().toString();
			ImageInfo img = new ImageInfo(resource.getFilename(), url);

			model.addAttribute("img", img);
		}
		return "coche";
	}

	@GetMapping({ "/delete/{id}" })
	String deleteCoche(Model model, @PathVariable Integer id) throws SQLException {
		boolean creado=false;
		Coche cocheaEliminar = cocheService.obtenerCochePorId(id);
		ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) alumnoService.listarAlumnos();
		ArrayList<Coche> misCoches = (ArrayList<Coche>) cocheService.listarCoches();
		ArrayList<Profesor> misProfes = (ArrayList<Profesor>) profeService.listarProfesores();
		ArrayList<Profesor> profeTemp = new ArrayList<Profesor>();// almacenamos las nuevas combinaciones de profesores
																	// y coches
		ArrayList<Coche> cocheTemp = new ArrayList<Coche>();
		for (Alumno a : misAlumnos) {
			if (a.getCoche().getId() == cocheaEliminar.getId()) {
				int coche = (int) (Math.random() * misCoches.size());
				if (misCoches.isEmpty() || misCoches.size() == 1) {
					Coche cGenerico = new Coche("2021 XS", "Nissan");
					cGenerico.generarMatricula();
					misCoches.add(cGenerico);
					// misCoches.get(0).setFechaITV(fecha);
					a.setCoche(cGenerico);
					for (Clase c : a.getClases()) {
						c.setCoche(cGenerico);
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
					cocheService.insertarCoche(cGenerico);
					creado=true;
				} else {

					do {
						coche = (int) (Math.random() * misCoches.size());
					} while (coche == misCoches.indexOf(a.getCoche()));
					a.setCoche(misCoches.get(coche));
					for (Clase c : a.getClases()) {
						c.setCoche(misCoches.get(coche));
					}
					profeTemp.add(a.getProfesor());
					cocheTemp.add(a.getCoche());
				}
				
				
				misCoches.get(coche).getAlumnos().add(a);
				alumnoService.insertarAlumno(a);
				profeService.insertarProfesor(profeService.obtenerProfesorPorId(a.getProfesor().getId()).get());
				cocheService.insertarCoche(cocheService.obtenerCochePorId(misCoches.get(coche).getId()));//
			}
		}

		List<ProfesoresCoches> elementosAEliminar = new ArrayList<>();

		for (Profesor a : misProfes) {
			if (!a.getCoches().isEmpty()) {
				for (ProfesoresCoches c : a.getCoches()) {
					if (c.getCoche().getId() == cocheaEliminar.getId()) {
						c.setCoche(null);
						c.setProfesor(null);
						elementosAEliminar.add(c);
					}

				}
				a.getCoches().removeAll(elementosAEliminar);
				elementosAEliminar.clear();
			}
			profeService.insertarProfesor(a);
		}

		cocheaEliminar.getAlumnos().clear();
		cocheaEliminar.getProfesores().clear();
		if (profeTemp != null) {
			for (int i = 0; i < profeTemp.size(); i++) {

				profeTemp.get(i).juegoLlaves(cocheTemp.get(i));
			}
		}
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto", "root", "");

		String sql = "DELETE FROM profesores_coches WHERE coche_id = " + cocheaEliminar.getId();

		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);

		connection.close();
		if(!creado) {
			if (misCoches.isEmpty() || misCoches.size() == 1) {
				Coche cGenerico = new Coche("2021 XS", "Nissan");
				cGenerico.generarMatricula();
				misCoches.add(cGenerico);
				// misCoches.get(0).setFechaITV(fecha);
				cocheService.insertarCoche(cGenerico);
				creado=true;
			}
		}
		cocheService.eliminarCoche(cocheaEliminar);
		return "redirect:/coches";
	}

	@PostMapping({ "/searchMarca" })
	String buscarCochePorMarca(Model model, @ModelAttribute("cocheaBuscar") CocheBuscarMarcaDTO cocheBuscado,
			BindingResult bidingresult) {
		ArrayList<Coche> cochesMarca = cocheService.obtenerCochesPorMarca(cocheBuscado.getMarca());
		model.addAttribute("cochesMarca", cochesMarca);

		return "cochesBuscadosPorMarca";

	}

	@PostMapping({ "/searchMatricula" })
	String buscarCochePorMatricula(@ModelAttribute("cocheaBuscar") CocheBuscarMatriculaDTO cocheBuscado,
			BindingResult bidingresult, Model model) {
		String matricula = cocheBuscado.getMatricula();
		String letras = matricula.substring(5, 8).toUpperCase();
		String mat = matricula.substring(0, 4) + " " + letras;
		Optional<Coche> cochematricula = cocheService.encontrarCochePorMatricula(mat);
		if (!cochematricula.isEmpty()) {
			Integer id = cochematricula.get().getId();
			return "redirect:/coches/" + id;
		} else {
			ArrayList<Coche> cocheVacio = new ArrayList<Coche>();
			model.addAttribute("cochesMarca", cocheVacio);
			return "cochesBuscadosPorMarca";
		}
	}
}
