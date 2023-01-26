package principal.persistencia;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import principal.modelo.Usuario;
import principal.utils.JPAUtil;

public class UsuarioDAO {
	
		public void insertarUsuarioJPA(Usuario usuario) {
			
			//JPA
		    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.persist(usuario);
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
		
		 
			
		
		public void modificarUsuarioJPA(Usuario usuario) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			em.merge(usuario);
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
		
		public void editarProfeJPA (Usuario usuario) {
			
			 EntityManager em =  JPAUtil.getEntityManagerFactory().createEntityManager();
			 
			 try {
				em.getTransaction().begin();
				em.merge(usuario);
				em.getTransaction().commit();
			} catch (PersistenceException e) {
				em.getTransaction().rollback();
				System.err.println(e.getMessage());
			} finally {
				em.close();
			} 
		 }
			
		public void eliminarUsuarioJPA(Usuario usuario) {
			
			//JPA
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
			em.getTransaction().begin();
			
			em.remove(em.contains(usuario)? usuario:em.merge(usuario) );
			
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
		
	 
		
		 
		
		public Usuario buscarPorIdJPA(Integer id) {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				Usuario usuario = em.find(Usuario.class, id);
				System.out.println("El nombre del usuario buscado es :"+usuario.getNombre());
				return usuario;
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
		
		
		public ArrayList<Usuario> listarUsuariosJPA() {
			
			EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
			try {
				em.getTransaction().begin();
				
				ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) em.createQuery("from Usuario").getResultList();
				em.getTransaction().commit();
				return misUsuarios;
				
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
		public void imprimirUsuarios(ArrayList<Usuario> misUsuarios) {
			System.out.println("Listado de Usuarioes");
			for(Usuario a:misUsuarios) {
				System.out.println(a.toString());
			}
		}

	}



