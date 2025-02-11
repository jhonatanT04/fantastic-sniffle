package DAO;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Ticket;


@Stateless
public class TicketDAO {

    @PersistenceContext
    EntityManager em;

    public void agregarTicket(String placa) {
    	Ticket ticket = new Ticket();
        em.persist(ticket);
    }

    public Ticket buscarTicket(int id) {
        return em.find(Ticket.class, id);
    }

    public List<Ticket> listarTickets() {
        TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t", Ticket.class);
        return query.getResultList();
    }
    public List<Ticket> listarTicketsPorCliente(int clienteId) {
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t WHERE t.usuario.id = :id_cliente", Ticket.class);
        query.setParameter("id_cliente", clienteId);
        
        List<Ticket> resultados = query.getResultList();
        System.out.println(resultados); 
        
        return resultados;
    }
    
    public void eliminarTicket(int id) {
        Ticket ticket = em.find(Ticket.class, id);
        if (ticket != null) {
            em.remove(ticket);
        }
    }
    
    public Ticket modificarTicket(Ticket ticket) {
        return em.merge(ticket);
    }
}