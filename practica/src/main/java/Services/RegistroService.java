package Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Gestion.GestionContratos;
import Gestion.GestionRegistros;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.practica.Registro;
import ups.practica.Ticket;


@Path("/registros")
public class RegistroService {

    @Inject
    private GestionRegistros gRegistros;
    
    @Inject
    private GestionContratos gContratos;
    
    @Inject
    private GestionTickets gTickets;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Registro registro) {
        try {
        	String a = registro.getPlaca().toUpperCase();
        	registro.setPlaca(a);
        	
        	if(gRegistros.buscarRegistroPendientePorPlaca(a)!=null) {
            	return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Esta placa ya esta dentro del parqueadero")).build();
        	}

        	registro.setPlaca(a);
        	if(gTickets.buscarTicketPendientePorPlaca(registro.getPlaca())==null && gContratos.BuscarContratoPorPlaca(registro.getPlaca())==null) {
        		return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Esta placa no esta asociada a ningun ticket/Contrato")).build();
        	}
        	if(gContratos.BuscarContratoPorPlaca(registro.getPlaca())!=null) {
        		registro.setTipo('C');
        	}else if(gTickets.buscarTicketPendientePorPlaca(registro.getPlaca())!=null) {
        		registro.setTipo('T');
        		gTickets.entradaTicket(a);
        	}
        	LocalDateTime fechaHora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            registro.setFechaIngreso(fechaHora.format(formato));
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
    
    
    @PUT
    @Path("/salidaVehiculo")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salidaVehiculo(Registro registro) {
        try {
        	Registro reg = gRegistros.buscarRegistroPendientePorPlaca(registro.getPlaca());
        	LocalDateTime fechaHora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            reg.setFechaSalida(fechaHora.format(formato));
            System.out.println(reg.getTipo()+"  "+(reg.getTipo()=='T'));
            if(reg.getTipo()=='T') {
            	gTickets.salidaTicket(reg.getPlaca());
            }
            gRegistros.modificarRegistro(reg);            
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
