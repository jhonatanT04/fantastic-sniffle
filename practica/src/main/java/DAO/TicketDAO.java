package DAO;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Espacio;
import ups.practica.Ticket;


@Stateless
public class TicketDAO {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    private EspacioDAO espacioDAO;

    @Inject
    private TarifaDAO tarifaDAO;
    
    public void agregarTicket(Ticket ticket) {
        ticket.setFechaIngreso(null);
    	ticket.setFechaSalida(null);
    	ticket.setValorTotal(0);
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
    
    public Ticket salidaTicket(String placa) {
    	placa.toUpperCase();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
            query.setParameter("placa", placa);
            List<Ticket> resultados = query.getResultList();
        Ticket ticket = resultados.isEmpty() ? null : resultados.get(0);
        if (ticket!=null) {
        	ticket.setValorTotal(10.2);
        	
        	LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        	
            ticket.setFechaSalida(ahora.format(formato));
            Espacio espacio = ticket.getEspacio();
            espacio.setEstado("D");
            espacioDAO.modificarEspacio(espacio);
            ticket.setValorTotal(tarifaDAO.consultaValorApagar(ticket.getFechaIngreso(), ticket.getFechaSalida()));	
		}
        return em.merge(ticket);
    }
    
    public Ticket entradaTicket(String placa) {
    	placa.toUpperCase();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
            query.setParameter("placa", placa);
            List<Ticket> resultados = query.getResultList();
        Ticket ticket = resultados.isEmpty() ? null : resultados.get(0);
        if (ticket!=null) {
        	
        	
        	LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        	ticket.setFechaIngreso(ahora.format(formato));
            Espacio espacio = ticket.getEspacio();
            espacio.setEstado("O");
            espacioDAO.modificarEspacio(espacio);	
		}
        return em.merge(ticket);
    }
    
    
    public Ticket buscarTicketPendientePorPlaca(String placa) {
    	placa.toUpperCase();
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
        query.setParameter("placa", placa);
        List<Ticket> resultados = query.getResultList();
        
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    
    public List<Ticket> buscarTicketPendientePorPersona(int id) {
    	TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.usuario.id = :id_cliente AND t.fechaSalida IS NULL", Ticket.class);
            query.setParameter("id_cliente", id);
        List<Ticket> resultados = query.getResultList();
        
        return resultados;
    }
    
    public Ticket validarPlacaConTicketActivo(String placa) {
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t WHERE t.placa = :placa AND t.fechaSalida IS NULL", Ticket.class);
        query.setParameter("placa", placa);

        List<Ticket> resultados = query.getResultList();

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
    
    public List<Ticket> listarTicketsActivos() {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.fechaSalida IS NULL", Ticket.class)
                 .getResultList();
    }

}