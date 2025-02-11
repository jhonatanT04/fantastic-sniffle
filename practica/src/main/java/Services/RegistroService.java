package Services;

import java.util.List;

import Gestion.GestionRegistros;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.practica.Registro;


@Path("/registros")
public class RegistroService {

    @Inject
    private GestionRegistros gRegistros;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Registro registro) {
        try {
            gRegistros.agregarRegistro(registro);
            return Response.ok(registro).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al crear el registro")).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Registro registro) {
        try {
            gRegistros.modificarRegistro(registro);            
            return Response.ok(registro).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar el registro")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@PathParam("id") int id) {
        try {
            Registro registro = gRegistros.buscarRegistro(id);
            if (registro == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(registro).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el registro")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            gRegistros.eliminarRegistro(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Registro eliminado con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar el registro")).build();
        }
    }
    
    @GET
    @Path("/historial")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorialPorPeriodo(@QueryParam("periodo") String periodo) {
        try {
            List<Registro> historial = gRegistros.obtenerHistorial(periodo);
            return Response.ok(historial).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }
    
    @GET
    @Path("/parqueadero")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVehiculosEnParqueadero() {
        try {
            List<Registro> vehiculos = gRegistros.listarEnParqueadero();
            return Response.ok(vehiculos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error en la base de datos")).build();
        }
    }

}
