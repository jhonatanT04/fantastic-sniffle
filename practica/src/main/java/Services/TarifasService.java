package Services;

import java.util.List;

import Gestion.GestionTarifas;
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
import ups.practica.Tarifa;


@Path("/tarifas")
public class TarifasService {

    @Inject
    private GestionTarifas gTarifas;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Tarifa tarifa) {
        try {
            gTarifas.agregarTarifa(tarifa);
            return Response.ok(tarifa).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al crear la tarifa")).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Tarifa tarifa) {
        try {
            if (tarifa != null && tarifa.getId() != 0) {
                gTarifas.modificarTarifa(tarifa);
                return Response.ok(new Respuesta(Respuesta.OK, "Tarifa actualizada")).build();
            }
            return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar la tarifa")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@PathParam("id") int id) {
        try {
            Tarifa tarifa = gTarifas.encontrarTarifa(id);
            if (tarifa == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(tarifa).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar la tarifa")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            gTarifas.eliminarTarifa(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Tarifa eliminada con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar la tarifa")).build();
        }
    }
    
    @GET
	@Produces("application/json")
	public Response listTarifas(){
		try {
			List<Tarifa> listatarifas = gTarifas.listarTarifas();
			return Response.ok(listatarifas).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
}