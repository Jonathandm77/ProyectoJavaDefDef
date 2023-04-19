package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;



import principal.utils.JPAUtil;
import principal.modelo.Alumno;

public class AlumnoDAO {
	
	public void insertarAlumnoJPA(Alumno alumno) {
		
		//JPA
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		em.getTransaction().begin();
		em.persist(alumno);
		em.getTransaction().commit();
		}
		catch(PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
		}
		finally {
			em.close();
		}
	}
		
		public void modificarAlumnoJPA(Alumno alumno) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.merge(alumno);
			em.getTransaction().commit();
			}
			catch(PersistenceException e) {
				em.getTransaction().rollback();
				System.out.println(e.getMessage());
			}
			finally {
				em.close();
			}
			
		}
		
	 
			
		public void eliminarAlumnoJPA(Alumno alumno) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			
			em.remove(em.contains(alumno)? alumno:em.merge(alumno) );
			
			em.getTransaction().commit();
			}
			catch(PersistenceException e) {
				em.getTransaction().rollback();
				System.out.println(e.getMessage());
			}
			finally {
				em.close();
			}
					
		}
		
	 
		
		 
		
		public Alumno buscarPorIdJPA(Integer id) {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				Alumno alumno = em.find(Alumno.class, id);
				System.out.println("El alumno buscado se llama :"+alumno.getNombre());
				return alumno;
				}
				catch(PersistenceException e) {
					em.getTransaction().rollback();
					System.out.println(e.getMessage());
				}
				finally {
					em.close();
				}
				return null;		
		}
		
		
		 public void editarAlumnoJPA (Alumno alumno) {
				
			 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
			 
			 try {
				em.getTransaction().begin();
				em.merge(alumno);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				System.err.println(e.getMessage());
			} finally {
				em.close();
			} 
		 }
		
		
		public ArrayList<Alumno> listarAlumnosJPA() {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				
				ArrayList<Alumno> misAlumnos = (ArrayList<Alumno>) em.createQuery("from Alumno").getResultList();
				em.getTransaction().commit();
				return misAlumnos;
				
				}
				catch(PersistenceException e) {
					em.getTransaction().rollback();
					System.out.println(e.getMessage());
				}
				finally {
					em.close();
				}
			
			return null;
			
		}
		public void imprimirAlumnos(ArrayList<Alumno> misalumnos) {
			System.out.println("Listado de Alumnos");
			for(Alumno a:misalumnos) {
				System.out.println(a.toString());
			}
		}
		 
		
		
	}
	
	


