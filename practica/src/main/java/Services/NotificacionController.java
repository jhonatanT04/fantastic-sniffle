package Services;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Startup
@Path("/notificaciones")
public class NotificacionController {
    
    @Inject
    private NotificacionService notificacionService;//123

    @GET
    @Path("/iniciar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarNotificaciones() {
        try {
            notificacionService.verificarHorariosYNotificar();
            return Response.ok("{\"mensaje\": \"Notificaciones enviadas correctamente.\"}").build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\": \"Error al iniciar las notificaciones.\"}").build();
        }
    }
}
