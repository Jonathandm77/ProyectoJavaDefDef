package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import principal.modelo.Alumno;
import principal.modelo.Coche;
import principal.utils.JPAUtil;

public class CocheDAO {
	public void insertarCocheJPA(Coche coche) {
		
		//JPA
	    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		em.getTransaction().begin();
		em.persist(coche);
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
	
	 
		
	
	public void modificarCocheJPA(Coche coche) {
		
		//JPA
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		em.getTransaction().begin();
		em.merge(coche);
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
	
 
		
	public void eliminarCocheJPA(Coche coche) {
		
		//JPA
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		em.getTransaction().begin();
		
		em.remove(em.contains(coche)? coche:em.merge(coche) );
		
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
	
	public void editarCocheJPA (Coche coche) {
		
		 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
		 
		 try {
			em.getTransaction().begin();
			em.merge(coche);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.err.println(e.getMessage());
		} finally {
			em.close();
		} 
	 }
 
	
	 
	
	public Coche buscarPorIdJPA(Integer id) {
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Coche coche = em.find(Coche.class, id);
			System.out.println("La matricula del coche buscado es :"+coche.getMatricula());
			return coche;
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
	
	
	public ArrayList<Coche> listarCochesJPA() {
		
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			
			ArrayList<Coche> misCoches = (ArrayList<Coche>) em.createQuery("from Coche").getResultList();
			em.getTransaction().commit();
			return misCoches;
			
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
	public void imprimirCoches(ArrayList<Coche> miscoches) {
		System.out.println("Listado de Coches");
		for(Coche a:miscoches) {
			System.out.println(a.toString());
		}
	}

}
