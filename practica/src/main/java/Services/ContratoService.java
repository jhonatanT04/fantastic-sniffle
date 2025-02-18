package Services;

import java.util.List;

import Gestion.GestionContratos;
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
import ups.practica.Contrato;

@Path("/contratos")
public class ContratoService {
	
	@Inject
	private GestionContratos gestionContrato;
	
	@Inject
	private GestionTickets gestionTickets;
	
	@POST 
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createContrato(Contrato contrato) { 
		try {
			String a = contrato.getPlaca().toUpperCase();
			
			if(gestionTickets.buscarTicketPendientePorPlaca(a)!=null) {
				return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Placa asociada a un ticket")).build();
			}
			if(gestionContrato.BuscarContratoPorPlaca(a)!=null) {
				return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Placa asociada a un contratos")).build();
			}
			
        	contrato.setPlaca(a);
			gestionContrato.agregarContrato(contrato);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@PUT
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateContrato(Contrato contrato) {
		try {
			gestionContrato.modificarContrato(contrato);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Produces("application/json")
	public Response listContratos(){
		try {
			List<Contrato> listContrato = gestionContrato.listHorario();
			return Response.ok(listContrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Path("/contrato/{id}")
	@Produces("application/json")
	public Response buscarContratoIdPersona(@PathParam("id") int id){
		try {
			List<Contrato> listContrato = gestionContrato.buscarContratosIdPersona(id);
			return Response.ok(listContrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContrato(@PathParam("id") int id) {
		try {
			Contrato contrato = gestionContrato.readContrato(id);
			return Response.ok(contrato).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@DELETE
	@Path("/deletePersona/{id}")
	public Response deleteContrato(@PathParam("id") int id) {
		try {
			gestionContrato.eliminarContrato(id);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@PUT
    @Path("/reserva/{id}")
    @Produces("application/json")
    public Response actualizarEspacio(@PathParam("id") int id) {
        try {
            Contrato contratoFinalizado = gestionContrato.actualizarEspacio(id); 
            
            if (contratoFinalizado == null) {
                return Response.status(404).entity(new Respuesta(Respuesta.ERROR, "Contrato no encontrado.")).build();
            }

            return Response.ok(new Respuesta(Respuesta.OK, "Contrato finalizado y espacio actualizado.")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }
	
	@PUT
    @Path("/reservaeliminar/{id}")
    @Produces("application/json")
    public Response actualizarEspacioalEliminar(@PathParam("id") int id) {
        try {
            Contrato contratoFinalizado = gestionContrato.actualizarEspacioalEliminar(id); 
            
            if (contratoFinalizado == null) {
                return Response.status(404).entity(new Respuesta(Respuesta.ERROR, "Contrato no encontrado.")).build();
            }

            return Response.ok(new Respuesta(Respuesta.OK, "Contrato finalizado y espacio actualizado.")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }
	
	@PUT
	@Path("/finalizar/{id}")
	@Produces("application/json")
	public Response finalizarContrato(@PathParam("id") int id) {
		try {
			boolean finalizado = gestionContrato.finalizarContrato(id);

			if (!finalizado) {
				return Response.status(404).entity(new Respuesta(Respuesta.ERROR, "Contrato no encontrado o ya finalizado.")).build();
			}

			return Response.ok(new Respuesta(Respuesta.OK, "Contrato finalizado y espacio liberado correctamente.")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al finalizar el contrato en la base de datos")).build();
		}
	}
}