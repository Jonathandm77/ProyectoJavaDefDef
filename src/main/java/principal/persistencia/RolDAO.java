package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import principal.modelo.Rol;
import principal.utils.JPAUtil;

public class RolDAO {
	
		public void insertarRolJPA(Rol rol) {
			
			//JPA
		    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.persist(rol);
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
		
		 
			
		
		public void modificarRolJPA(Rol rol) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.merge(rol);
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
		
		public void editarProfeJPA (Rol rol) {
			
			 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
			 
			 try {
				em.getTransaction().begin();
				em.merge(rol);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				System.err.println(e.getMessage());
			} finally {
				em.close();
			} 
		 }
			
		public void eliminarRolJPA(Rol rol) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			
			em.remove(em.contains(rol)? rol:em.merge(rol) );
			
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
		
	 
		
		 
		
		public Rol buscarPorIdJPA(Integer id) {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				Rol rol = em.find(Rol.class, id);
				System.out.println("El nombre del rol buscado es :"+rol.getNombre());
				return rol;
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
		
		
		public ArrayList<Rol> listarRolsJPA() {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				
				ArrayList<Rol> misRols = (ArrayList<Rol>) em.createQuery("from Rol").getResultList();
				em.getTransaction().commit();
				return misRols;
				
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
		public void imprimirRols(ArrayList<Rol> misroles) {
			System.out.println("Listado de Roles");
			for(Rol a:misroles) {
				System.out.println(a.toString());
			}
		}

	}



