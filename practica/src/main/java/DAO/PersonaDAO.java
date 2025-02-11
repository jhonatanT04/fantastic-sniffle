package DAO;

import java.util.List;
import java.util.Optional;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Persona;



@Stateless
public class PersonaDAO {
	@PersistenceContext
	EntityManager em;
	
	public void agregarPersona(Persona persona) {
		em.persist(persona);
    }
	
	public Persona encontrarPersona(int id) {
        return em.find(Persona.class, id);
    }
	
	public Persona encontrarPersonaEmail(String email) {
	    try {
	        TypedQuery<Persona> query = em.createQuery(
	            "SELECT p FROM Persona p WHERE p.email = :email", Persona.class);
	        query.setParameter("email", email);
	        return query.getSingleResult(); 
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public Persona modificarPersona(Persona persona) {
        return em.merge(persona);
    }


    public void eliminarPersona(int id) {
        Persona persona = em.find(Persona.class, id);
        if (persona != null) {
            em.remove(persona);
        }
    }
    
    public List<Persona> listarPersona() {
        TypedQuery<Persona> query = em.createQuery("SELECT p FROM Persona p", Persona.class);
        return query.getResultList();
    }
    
    public Optional<Persona> validarCredenciales(String email, String password) {
        try {
            Persona persona = em.createQuery("SELECT p FROM Persona p WHERE p.email = :email", Persona.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (persona != null && persona.getPassword().equals(password)) {
                return Optional.of(persona);
            } else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Persona getPersona(String email, String password) {
    	try {
            Persona persona = em.createQuery("SELECT p FROM Persona p WHERE p.email = :email", Persona.class)
                                .setParameter("email", email)
                                .getSingleResult();

            if (persona.getPassword().equals(password)) {
                return persona;
            } else {
                throw new IllegalArgumentException("Contraseña incorrecta");
            }
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }
}

