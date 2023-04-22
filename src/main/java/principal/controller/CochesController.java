package principal.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import principal.modelo.ProfesoresCoches;
import principal.modelo.dto.CocheAEditarMatriculaFechaDTO;
import principal.modelo.dto.CocheBuscarMarcaDTO;
import principal.modelo.dto.CocheBuscarMatriculaDTO;
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
		String homecoches(Model model) {
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
			
			cocheService.insertarCoche(cocheNew);
			return "redirect:/coches";
		}
		
		@PostMapping("/edit/{id}")
		public String editarCoche(@PathVariable Integer id, @ModelAttribute("cocheaEditar") CocheAEditarMatriculaFechaDTO cocheEditado, BindingResult bidingresult) {
			Coche cocheaEditar=cocheService.obtenerCochePorId(id);
			if(cocheEditado.getMatricula()!=null && cocheEditado.getMatricula()!="")
			cocheaEditar.setMatricula(cocheEditado.getMatricula());
			if(cocheEditado.getFechaITV()!=null)
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
		
		/*@GetMapping({"/delete/{id}"})
		String deleteCoche(Model model, @PathVariable Integer id) {
			Coche cocheaEliminar=cocheService.obtenerCochePorId(id);
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoService.listarAlumnos();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheService.listarCoches();
			ArrayList<Profesor> misProfes=(ArrayList<Profesor>) profeService.listarProfesores();
			List<Llave> misLlaves=llaveService.listarLlaves();
			for(Alumno a:misAlumnos) {
				if(a.getCoche().getId()==cocheaEliminar.getId()) {
					int coche=(int) (Math.random()*misCoches.size());
					if(misCoches.isEmpty()|misCoches.size()==0) {
						misCoches.add(new Coche("5678 GHS","2021 XS","Nissan"));
						LocalDate fecha=LocalDate.of(2025, 6, 16);
						//misCoches.get(0).setFechaITV(fecha);
						a.setCoche(misCoches.get(0));//si no queda ningun coche crear coche generico
					}else {
					
					
					do {
						 coche=(int)(Math.random()*misCoches.size());
					}while(coche==misCoches.indexOf(a.getCoche()));
					a.setCoche(misCoches.get(coche));
					}
					misCoches.get(coche).getAlumnos().add(a);
					alumnoService.insertarAlumno(a);
					cocheService.insertarCoche(misCoches.get(coche));
				}
				}
			
			for(Llave a:misLlaves) {
				if(a.getCoche()!=null) {
				if(a.getCoche().getCoche()==cocheaEliminar) {
					a.setCoche(null);
					a.setProfesor(null);
				}
				}
				}
			
			for(Profesor a:misProfes) {
				
					for(ProfesoresCoches c:a.getCoches()) {
						if(c.getCoche()==cocheaEliminar) {
							c.setCoche(null);
					c.setProfesor(null);
					c.setLlave(null);
						}
						
					}
				}
				
			cocheaEliminar.getAlumnos().clear();
			cocheaEliminar.getLlaves().clear();
			cocheaEliminar.getProfesores().clear();
			cocheService.eliminarCoche(cocheaEliminar);
			return "redirect:/coches";
			}*/
		
		
		@PostMapping({"/searchMarca"})
		String buscarCochePorMarca(Model model,@ModelAttribute("cocheaBuscar") CocheBuscarMarcaDTO cocheBuscado,BindingResult bidingresult) {
			ArrayList<Coche> cochesMarca= cocheService.obtenerCochesPorMarca(cocheBuscado.getMarca());
			model.addAttribute("cochesMarca",cochesMarca);
			
			
			return "cochesBuscadosPorMarca";
			
		}
		
		@PostMapping({"/searchMatricula"})
		String buscarCochePorMatricula(@ModelAttribute("cocheaBuscar") CocheBuscarMatriculaDTO cocheBuscado,BindingResult bidingresult) {
			String matricula=cocheBuscado.getMatricula();
			String letras=matricula.substring(5, 8).toUpperCase();
			String mat=matricula.substring(0,4)+" "+letras;
			Coche cochematricula= cocheService.encontrarCochePorMatricula(mat);
			Integer id=cochematricula.getId();
			
			
			
			return "redirect:/coches/"+id;
			
		}
		}


