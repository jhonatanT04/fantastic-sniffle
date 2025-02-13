package Gestion;


import java.util.List;

import DAO.TicketDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Ticket;

@Stateless
public class GestionTickets {

    @EJB
    private TicketDAO ticketDAO;

    public void agregarTicket(Ticket ticket) {
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
    
    public Ticket cambiarEstadoTicket(String placa) {
        return ticketDAO.cambiarEstadoTicket(placa);
    }
    
    public List<Ticket> ListarTicketpotPersona(int id) {
        return ticketDAO.listarTicketsPorCliente(id);
    }

    public Ticket modificarTicket(Ticket ticket) {
        return ticketDAO.modificarTicket(ticket);
    }

    public void eliminarTicket(int id) {
        ticketDAO.eliminarTicket(id);
    }
    
}