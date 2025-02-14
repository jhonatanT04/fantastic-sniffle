package Services;

import java.util.List;

import Gestion.GestionTickets;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.practica.Ticket;


@Path("/tickets")
public class TicketService {

    @Inject
    private GestionTickets gTickets;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Ticket ticket) {
        try {
        	String a = (ticket.getPlaca().toUpperCase().charAt(0) + ticket.getPlaca().substring(1, ticket.getPlaca().length()).toLowerCase()).trim();
        	ticket.setPlaca(a);
        	
            if(gTickets.buscarTicketPendientePorPlaca( ticket.getPlaca() ) != null) {
            	return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Número de placa ya tiene un ticket/contrato asociado")).build();
        	}
            gTickets.agregarTicket(ticket);
            
            return Response.ok(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al crear el ticket")).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Ticket ticket) {
        try {
            if (ticket != null && ticket.getId() != 0) {
                gTickets.modificarTicket(ticket);
                return Response.ok(new Respuesta(Respuesta.OK, "Ticket actualizado con éxito")).build();
            }
            return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos para la actualización")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar el ticket")).build();
        }
    }
    
    
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@PathParam("id") int id) {
        try {
            Ticket ticket = gTickets.buscarTicket(id);
            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el ticket")).build();
        }
    }
    
    
    @GET
    @Path("/ticketPendiente/{placa}")
    @Produces("application/json")
    public Response buscarPorPlacaTicketsPendientes(@PathParam("placa") String placa) {
        try {
            Ticket ticket = gTickets.buscarTicketPendientePorPlaca(placa);
            
            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(ticket).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el ticket")).build();
        }
    }
    
    
    @PUT
    @Path("/cambiarEstado")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cambiarEstadoDeTicket(Ticket request) {
        try {
            if (request != null && request.getPlaca() != null && !request.getPlaca().isEmpty()) {
                gTickets.cambiarEstadoTicket(request.getPlaca());
                return Response.ok(new Respuesta(Respuesta.OK, "Ticket actualizado con éxito")).build();
            }
            return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos para la actualización")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar el ticket")).build();
        }
    }
    

    @GET
    @Path("/getTicketIDpersona/{id}")
    @Produces("application/json")
    public Response getTicketPersona(@PathParam("id") int id) {
        try {
            List<Ticket> tickets = gTickets.ListarTicketpotPersona(id);
            if (tickets == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(tickets).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el ticket")).build();
        }
    }
    
    
    @GET
    @Produces("application/json")
    public Response listar() {
        try {
        	List<Ticket> listaHorarios = gTickets.listarTickets();
            if (listaHorarios == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(listaHorarios).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al listar los ticket")).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            gTickets.eliminarTicket(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Ticket eliminado con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar el ticket")).build();
        }
    }
}
