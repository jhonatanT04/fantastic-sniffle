package Services;

import Gestion.GestionVehiculos;
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
import ups.practica.Vehiculo;


@Path("/vehiculos")
public class VehiculosService {

    @Inject
    private GestionVehiculos gVehiculos;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Vehiculo vehiculo) {
        try {
            gVehiculos.agregarVehiculo(vehiculo);
            return Response.ok(vehiculo).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al crear el vehículo")).build();
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Vehiculo vehiculo) {
        try {
            if (vehiculo != null && vehiculo.getId() != 0) {
                gVehiculos.modificarVehiculo(vehiculo);
                return Response.ok(new Respuesta(Respuesta.OK, "Vehículo actualizado con éxito")).build();
            }
            return Response.status(400).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos para la actualización")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar el vehículo")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@PathParam("id") int id) {
        try {
            Vehiculo vehiculo = gVehiculos.encontrarVehiculo(id);
            if (vehiculo == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(vehiculo).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el vehículo")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            gVehiculos.eliminarVehiculo(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Vehículo eliminado con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar el vehículo")).build();
        }
    }
}