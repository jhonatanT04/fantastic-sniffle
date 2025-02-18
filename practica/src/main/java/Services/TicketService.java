package Services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Gestion.GestionContratos;
import Gestion.GestionHorarios;
import Gestion.GestionTickets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
    
    @Inject
    private GestionContratos gContratos;
    
    @Inject
    private GestionHorarios gestionHorarios;
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("Authorization") String authHeader,Ticket ticket) {
        try {
        	LocalTime ahora = LocalTime.now();
        	DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaActualSistem  = ahora.format(formato);
            LocalTime horaActual =LocalTime.parse(horaActualSistem, DateTimeFormatter.ofPattern("HH:mm:ss"));
        	LocalTime horaApertura = LocalTime.parse(gestionHorarios.getHorarioDia().getHoraApertura(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime horaCierre = LocalTime.parse(gestionHorarios.getHorarioDia().getHoraCierre(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            if (horaActual.isBefore(horaApertura) || horaActual.isAfter(horaCierre)) {
            	return Response.status(501).entity(new Respuesta(Respuesta.ERROR, "El parqueadero se encuentra cerrado")).build();
            }
        	
        	String a = ticket.getPlaca().toUpperCase();
        	ticket.setPlaca(a);
        	
            if(gTickets.buscarTicketPendientePorPlaca( ticket.getPlaca() ) != null||gContratos.BuscarContratoPorPlaca( ticket.getPlaca() ) != null) {
            	return Response.status(501).entity(new Respuesta(Respuesta.ERROR, "Número de placa ya tiene un ticket/contrato asociado")).build();
        	}
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(new Respuesta(Respuesta.ERROR, "Token no proporcionado")).build();
            }
            String token = authHeader.substring("Bearer".length()).trim();
            //String secretKey = System.getenv("JWT_SECRET_KEY"); 
            Claims claims;
            try {
                claims = Jwts.parser()
                		.setSigningKey(Keys.hmacShaKeyFor("mi_clave_secreta_que_tiene_256_bits!!!!!".getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (ExpiredJwtException e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Token expirado").build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Error al validar el token").build();
            }
            
            Integer id = claims.get("id", Integer.class);
            
            
            
            if(gTickets.buscarTicketPendientePorPersona(id).size() > 2) {
            	return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Existen Tickets pendientes")).build();
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
                gTickets.salidaTicket(request.getPlaca());
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
    
    @GET
    @Path("/validarPlaca/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarPlaca(@PathParam("placa") String placa) {
        try {
        	Ticket tieneTicket = gTickets.validarPlacaConTicketActiva(placa);
            return Response.ok(tieneTicket).build();
        }catch (Exception e) {
        	 e.printStackTrace();
             return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al listar los ticket")).build();		}
    }

}
