package DAO;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Gestion.GestionEspacios;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Ticket;


@Stateless
public class TicketDAO {

    @PersistenceContext
    EntityManager em;
    
    
    public void agregarTicket(Ticket ticket) {
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
    	ticket.setFechaIngreso(ahora.format(formato));
    	ticket.setFechaSalida(null);
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
        return resultados;
    }	
    
    public Ticket cambiarEstadoTicket(String placa) {
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
        query.setParameter("placa", placa);
        List<Ticket> resultados = query.getResultList();
        System.out.println(resultados);
        Ticket tick = resultados.isEmpty() ? null : resultados.get(0);
        return tick;
    }
    public Ticket buscarTicketPendientePorPlaca(String placa) {
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
        query.setParameter("placa", placa);
        List<Ticket> resultados = query.getResultList();
        System.out.println(resultados);
        return resultados.isEmpty() ? null : resultados.get(0);
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