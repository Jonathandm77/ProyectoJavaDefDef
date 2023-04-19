package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;



import principal.utils.JPAUtil;
import principal.modelo.Llave;

public class LlaveDAO {
	
	public void insertarLlaveJPA(Llave llave) {
		
		//JPA
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		em.getTransaction().begin();
		em.persist(llave);
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
		
		public void modificarLlaveJPA(Llave llave) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.merge(llave);
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
		
	 
			
		public void eliminarLlaveJPA(Llave llave) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			
			em.remove(em.contains(llave)? llave:em.merge(llave) );
			
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
		
	 
		
		 
		
		public Llave buscarPorIdJPA(Integer id) {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				Llave llave = em.find(Llave.class, id);
				//System.out.println("El llave buscado se llama :"+llave.getNombre());
				return llave;
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
		
		
		 public void editarLlaveJPA (Llave llave) {
				
			 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
			 
			 try {
				em.getTransaction().begin();
				em.merge(llave);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				System.err.println(e.getMessage());
			} finally {
				em.close();
			} 
		 }
		
		
		public ArrayList<Llave> listarLlavesJPA() {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				
				ArrayList<Llave> misLlaves = (ArrayList<Llave>) em.createQuery("from Llave").getResultList();
				em.getTransaction().commit();
				return misLlaves;
				
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
		public void imprimirLlaves(ArrayList<Llave> misllaves) {
			System.out.println("Listado de Llaves");
			for(Llave a:misllaves) {
				System.out.println(a.toString());
			}
		}
		 
		
		
	}
	
	


