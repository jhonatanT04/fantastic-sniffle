package DAO;

import java.util.List;
import ups.practica.Contrato;
import ups.practica.Espacio;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Stateless
public class ContratoDAO {
	@PersistenceContext
	EntityManager em;
	
	@Inject
	private EspacioDAO espacioDAO;
	
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
    
    public Contrato actualizarEspacio(int contratoId) {
        Contrato contrato = em.find(Contrato.class, contratoId);
        if (contrato == null) {
            return null;
        }
        Espacio espacio = contrato.getEspacio();
        if (espacio == null) {
        } else {
            espacio.setEstado("R");
            espacioDAO.modificarEspacio(espacio);
        }
        return em.merge(contrato);
    }
    
    public Contrato actualizarEspacioalEliminar(int contratoId) {
        Contrato contrato = em.find(Contrato.class, contratoId);
        if (contrato == null) {
            return null;
        }
        Espacio espacio = contrato.getEspacio();
        if (espacio == null) {
        } else {
            espacio.setEstado("D");
            espacioDAO.modificarEspacio(espacio);
        }
        return em.merge(contrato);
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
    public Contrato buscarContratoPorPlaca(String placa) {
    	placa.toUpperCase();
    	TypedQuery<Contrato> query = em.createQuery(
                "SELECT t FROM Contrato t WHERE t.placa = :placa ", Contrato.class);
            query.setParameter("placa", placa);
            List<Contrato> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
    }
}