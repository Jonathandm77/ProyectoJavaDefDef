package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import principal.modelo.Alumno;
import principal.modelo.Profesor;
import principal.utils.JPAUtil;

public class ProfesorDAO {
	
		public void insertarProfesorJPA(Profesor profesor) {
			
			//JPA
		    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.persist(profesor);
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
		
		 
			
		
		public void modificarProfesorJPA(Profesor profesor) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.merge(profesor);
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
		
		public void editarProfeJPA (Profesor profesor) {
			
			 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
			 
			 try {
				em.getTransaction().begin();
				em.merge(profesor);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				System.err.println(e.getMessage());
			} finally {
				em.close();
			} 
		 }
			
		public void eliminarProfesorJPA(Profesor profesor) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			
			em.remove(em.contains(profesor)? profesor:em.merge(profesor) );
			
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
		
	 
		
		 
		
		public Profesor buscarPorIdJPA(Integer id) {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				Profesor profesor = em.find(Profesor.class, id);
				System.out.println("El nombre del profesor buscado es :"+profesor.getNombre());
				return profesor;
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
		
		
		public ArrayList<Profesor> listarProfesorsJPA() {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				
				ArrayList<Profesor> misProfesors = (ArrayList<Profesor>) em.createQuery("from Profesor").getResultList();
				em.getTransaction().commit();
				return misProfesors;
				
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
		public void imprimirProfesors(ArrayList<Profesor> misprofesores) {
			System.out.println("Listado de Profesores");
			for(Profesor a:misprofesores) {
				System.out.println(a.toString());
			}
		}

	}



