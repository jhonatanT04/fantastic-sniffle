package Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
    public Response iniciarNotificaciones(@HeaderParam("Authorization") String authHeader) {
        try {
        	
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
                return Response.status(Response.Status.UNAUTHORIZED).entity(new Respuesta(Respuesta.ERROR, "Token expirado")).build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(new Respuesta(Respuesta.ERROR, "Error al validar el token")).build();
            }
            notificacionService.verificarHorariosYNotificar();
            return Response.ok("{\"mensaje\": \"Notificaciones enviadas correctamente.\"}").build();
        } catch (Exception e) {
            return Response.serverError().entity("{\"error\": \"Error al iniciar las notificaciones.\"}").build();
        }
    }
}
