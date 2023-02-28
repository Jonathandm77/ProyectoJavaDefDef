package principal.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.modelo.Profesor;
import principal.persistencia.AlumnoDAO;
import principal.persistencia.AlumnoRepo;
import principal.persistencia.CocheDAO;
import principal.persistencia.CocheRepo;
import principal.persistencia.ProfesorDAO;
import principal.persistencia.ProfesorRepo;
import principal.servicio.implementacion.AlumnoServiceImpl;
import principal.servicio.implementacion.CocheServiceImpl;
import principal.servicio.implementacion.ProfesorServiceImpl;



	@Controller
	@RequestMapping("/coches")
	public class CochesController {
		/*AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();*/
		
		@Autowired
		private AlumnoServiceImpl alumnoService;
		@Autowired
		private ProfesorServiceImpl profeService;
		@Autowired
		private CocheServiceImpl cocheService;
		
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeService.listarProfesores();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("cocheNuevo", new Coche());
			model.addAttribute("cocheaEditar", new Coche());
			model.addAttribute("cocheaBuscar", new Coche());
			return "coches";
		}
		

		
		@PostMapping("/add")
		public String addCoche(@ModelAttribute("cocheNuevo") Coche cocheNew, BindingResult bidingresult) {
			/*LocalDate f=cocheNew.getFechaITV();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
			String fechaFormateada=f.format(formatter);
			String[] fechaString=fechaFormateada.split(" ");
			Integer[] fecha= {Integer.parseInt(fechaString[0]),
			Integer.parseInt(fechaString[1]),
			Integer.parseInt(fechaString[2])};
			LocalDate fechaAInsertar=LocalDate.of(fecha[0], fecha[1], fecha[3]);
			cocheNew.setFechaITV(fechaAInsertar);*/
			cocheService.insertarCoche(cocheNew);
			return "redirect:/coches";
		}
		
		@PostMapping("/edit/{id}")
		public String editarCoche(@PathVariable Integer id, @ModelAttribute("cocheaEditar") Coche cocheEditado, BindingResult bidingresult) {
			Coche cocheaEditar=cocheService.obtenerCochePorId(id);
			cocheaEditar.setMatricula(cocheEditado.getMatricula());
			cocheaEditar.setFechaITV(cocheEditado.getFechaITV());
			cocheService.insertarCoche(cocheaEditar);
			return "redirect:/coches";
		}
		
		@GetMapping({"/{id}"})
		String idCoche(Model model, @PathVariable Integer id) {
			Coche cocheMostrar=cocheService.obtenerCochePorId(id);
			model.addAttribute("cocheMostrar", cocheMostrar);
			return "coche";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteCoche(Model model, @PathVariable Integer id) {
			Coche cocheaEliminar=cocheService.obtenerCochePorId(id);
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
			for(Alumno a:misAlumnos) {
				if(a.getCoche().getId()==cocheaEliminar.getId()) {
					if(misCoches==null) {
						misCoches.add(new Coche("5678 GHS","2021 XS","Nissan"));
						LocalDate fecha=LocalDate.of(2025, 6, 16);
						//misCoches.get(0).setFechaITV(fecha);
					}
					
					a.setCoche(misCoches.get(0));//si no queda ningun coche crear coche generico
					misCoches.get(0).getAlumnos().add(a);
					alumnoService.insertarAlumno(a);
				}
				}
			cocheaEliminar.getAlumnos().clear();
			cocheService.eliminarCoche(cocheaEliminar);
			return "redirect:/coches";
			}
		
		
		@PostMapping({"/search/{marca}"})
		String buscarCochePorMarca(Model model,@ModelAttribute("cocheaBuscar") Coche cocheBuscado,BindingResult bidingresult) {
			ArrayList<Coche> cochesMarca= new ArrayList<Coche>();
			ArrayList<Coche> misCoches= (ArrayList<Coche>) cocheService.listarCoches();
			for(Coche c:misCoches) {
				if(c.getMarca().equals(cocheBuscado.getMarca())) {
					cochesMarca.add(c);
				}
			}
			model.addAttribute("cochesMarca",cochesMarca);
			
			
			return "cochesBuscadosPorMarca";
			
		}
		
		@PostMapping({"/searchMatricula/{matricula}"})
		String buscarCochePorMatricula(@ModelAttribute("cocheaBuscar") Coche cocheBuscado,BindingResult bidingresult) {
			Coche cochematricula= cocheService.encontrarCochePorMatricula(cocheBuscado.getMatricula());
			Integer id=cochematricula.getId();
			
			
			
			return "redirect:/coches/"+id;
			
		}
		}


