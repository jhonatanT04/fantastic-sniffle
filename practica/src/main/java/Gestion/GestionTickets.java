package Gestion;


import java.util.List;

import DAO.TicketDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ups.practica.Ticket;

@Stateless
public class GestionTickets {

    @EJB
    private TicketDAO ticketDAO;
    
    @Inject
    private GestionEspacios gestionEspacios;
    
    public void agregarTicket(Ticket ticket) {
    	ticket.getEspacio().setEstado("R");
    	gestionEspacios.modificarEspacio(ticket.getEspacio());
        ticketDAO.agregarTicket(ticket);
    }

    public List<Ticket> listarTickets() {
        return ticketDAO.listarTickets();
    }

    public Ticket buscarTicket(int id) {
        return ticketDAO.buscarTicket(id);
    }
    
    public Ticket buscarTicketPendientePorPlaca(String placa) {
        return ticketDAO.buscarTicketPendientePorPlaca(placa);
    }
    
    public List<Ticket> buscarTicketPendientePorPersona(int id) {
        return ticketDAO.buscarTicketPendientePorPersona(id);
    }
    
    public Ticket entradaTicket(String placa) {
        return ticketDAO.entradaTicket(placa);
    }
    
    public Ticket salidaTicket(String placa) {
        return ticketDAO.salidaTicket(placa);
    }
    
    public List<Ticket> ListarTicketpotPersona(int id) {
        return ticketDAO.listarTicketsPorCliente(id);
    }

    public Ticket modificarTicket(Ticket ticket) {
        return ticketDAO.modificarTicket(ticket);
    }
    
    public boolean validarPlacaConTicketActiva(String placa) {
    	return ticketDAO.validarPlacaConTicketActivo(placa);
    }

    public void eliminarTicket(int id) {
        ticketDAO.eliminarTicket(id);
    }
    
}