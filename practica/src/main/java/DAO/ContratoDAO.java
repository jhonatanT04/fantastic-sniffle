package DAO;

import java.util.Date;
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
	
	@Inject
	private TicketDAO ticketDAO;
	
	
	
	public void agregarContrato(Contrato contrato) {
	    em.persist(contrato);;
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
            espacio.setEstado("O");
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
    
    
    public void revisarYFinalizarContratos() {
        Date hoy = new Date(); 

        List<Contrato> contratos = listarContrato(); 

        for (Contrato contrato : contratos) {
            if (contrato.getFechaFin().compareTo(hoy) <= 0) { 
                liberarEspacioPorContrato(contrato.getId());
                eliminarContrato(contrato.getId());

            }
        }
    }

    
    public void liberarEspacioPorContrato(int contratoId) {
        Contrato contrato = em.find(Contrato.class, contratoId);
        if (contrato != null) {
            Espacio espacio = contrato.getEspacio();
            if (espacio != null) {
                espacio.setEstado("D"); 
                espacioDAO.modificarEspacio(espacio);
            }
        } else {
        }
    }

}