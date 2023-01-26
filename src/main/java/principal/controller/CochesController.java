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



	@Controller
	@RequestMapping("/coches")
	public class CochesController {
		AlumnoDAO alumnoDAO=new AlumnoDAO();
		CocheDAO cocheDAO=new CocheDAO();
		ProfesorDAO profeDAO=new ProfesorDAO();
		
		@Autowired
		private AlumnoRepo alumnoRepo;
		@Autowired
		private ProfesorRepo profeRepo;
		@Autowired
		private CocheRepo cocheRepo;
		
		
		@GetMapping(value={"","/"})
		String homealumnos(Model model) {
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheRepo.findAll();
	        ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoRepo.findAll();
	        ArrayList<Profesor> misProfesores= (ArrayList<Profesor>) profeRepo.findAll();
	       
	        model.addAttribute("listaCoches", misCoches);
	        model.addAttribute("listaAlumnos", misAlumnos);
	        model.addAttribute("listaProfesores", misProfesores);
	        
			model.addAttribute("cocheNuevo", new Coche());
			model.addAttribute("cocheaEditar", new Coche());
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
			cocheRepo.save(cocheNew);
			return "redirect:/coches";
		}
		
		@PostMapping("/edit/{id}")
		public String editarCoche(@PathVariable Integer id, @ModelAttribute("cocheaEditar") Coche cocheEditado, BindingResult bidingresult) {
			Coche cocheaEditar=cocheRepo.findById(id).get();
			cocheaEditar.setMatricula(cocheEditado.getMatricula());
			cocheaEditar.setFechaITV(cocheEditado.getFechaITV());
			cocheRepo.save(cocheaEditar);
			return "redirect:/coches";
		}
		
		@GetMapping({"/delete/{id}"})
		String deleteCoche(Model model, @PathVariable Integer id) {
			Coche cocheaEliminar=cocheRepo.findById(id).get();
			ArrayList<Alumno> misAlumnos= (ArrayList<Alumno>) alumnoRepo.findAll();
			ArrayList<Coche> misCoches=(ArrayList<Coche>) cocheRepo.findAll();
			for(Alumno a:misAlumnos) {
				if(a.getCoche().getId()==cocheaEliminar.getId()) {
					if(misCoches==null) {
						misCoches.add(new Coche("5678 GHS","2021 XS","Nissan"));
						LocalDate fecha=LocalDate.of(2025, 6, 16);
						//misCoches.get(0).setFechaITV(fecha);
					}
					
					a.setCoche(misCoches.get(0));//si no queda ningun coche crear coche generico
					misCoches.get(0).getAlumnos().add(a);
					alumnoRepo.save(a);
				}
				}
			cocheaEliminar.getAlumnos().clear();
			cocheRepo.delete(cocheaEliminar);
			return "redirect:/coches";
			}
		}


