package Services;



import java.util.List;


import Gestion.GestionHorarios;
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
import ups.practica.Horario;

@Path("/horarios")
public class HorarioService {
	
	@Inject
	private GestionHorarios gestionHorario;
	
	@POST 
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createHorario(Horario horario) { 
		try {
			gestionHorario.agregarHorario(horario);
			return Response.ok().build();
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@PUT
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateHorario(Horario horario) {
		try {
			gestionHorario.modificarHorario(horario);
			return Response.ok(horario).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Produces("application/json")
	public Response listHorarios(){
		try {
			List<Horario> listaHorarios = gestionHorario.listarHorarios();
			return Response.ok(listaHorarios).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHorario(@PathParam("id") int id) {
		try {
			Horario horario = gestionHorario.encontrarHorario(id);
			return Response.ok(horario).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@DELETE
	@Path("/deletePersona/{id}")
	public Response deletePersona(@PathParam("id") int id) {
	    try {
	    	gestionHorario.eliminarHorario(id);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();	
		}
	}
	
	@GET
    @Path("/normales")
    @Produces("application/json")
    public Response listHorariosNormales() {
        try {
            List<Horario> listaHorarios = gestionHorario.listarHorariosNormales();
            return Response.ok(listaHorarios).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }

    @GET
    @Path("/especiales")
    @Produces("application/json")
    public Response listHorariosEspeciales() {
        try {
            List<Horario> listaHorarios = gestionHorario.listarHorariosEspeciales();
            return Response.ok(listaHorarios).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }

    @POST
    @Path("/especial")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHorarioEspecial(Horario horario) {
        try {
            gestionHorario.agregarHorarioEspecial(horario);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }
    
    @GET
    @Path("/hoy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHorarioDelDia() {
        try {
            Horario horarioHoy = gestionHorario.getHorarioDia();
            if (horarioHoy != null) {
                return Response.ok(horarioHoy).build();
            } else {
                return Response.status(404).entity(new Respuesta(Respuesta.ERROR, "No hay horario registrado para hoy.")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }
}