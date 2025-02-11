package DAO;

import java.util.List;
import ups.practica.Contrato;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Stateless
public class ContratoDAO {
	@PersistenceContext
	EntityManager em;
	public void agregarContrato(ups.practica.Contrato contrato) {
		em.persist(contrato);
    }
	
	public Contrato buscarContrato(int id) {
    	return em.find(Contrato.class, id);
    }
	
    public List<Contrato> listarContrato() {
        TypedQuery<Contrato> query = em.createQuery("SELECT p FROM Contrato p", Contrato.class);
        return query.getResultList();
    }
    
    public void eliminarContrato(int id) {
    	Contrato contrato= em.find(Contrato.class, id);
        if (contrato!= null) {
            em.remove(contrato);
        }
    }
    
    public Contrato modificarContrato(Contrato contrato) {
        return em.merge(contrato);
    }
    
    public List<Contrato> buscarContratosPorPersonaId(int personaId) {
        TypedQuery<Contrato> query = em.createQuery(
            "SELECT c FROM Contrato c WHERE c.usuario.id = :personaId", Contrato.class);
        query.setParameter("personaId", personaId);
        return query.getResultList();
    }
}