package JWT;

import java.security.Key;
import java.util.logging.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

public class JwtAuthFilter implements ContainerRequestFilter {
	private static final Logger LOGGER = Logger.getLogger(JwtAuthFilter.class.getName());
    private static final String SECRET_KEY = "mi_clave_secreta_que_tiene_256_bits!!!!!"; 
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token JWT no proporcionado o inválido").build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validar el token y extraer los claims
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            boolean isAdmin = claims.containsKey("rol"); // Extraer el rol del token

            String path = requestContext.getUriInfo().getPath(); // Obtener la ruta solicitada

            // Restricción: Solo administradores pueden acceder a /admin/
            if (path.startsWith("admin") && !isAdmin) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Acceso denegado. Se requiere rol de administrador.").build());
                return;
            }

            // Aquí podrías agregar más lógica según la ruta solicitada
            LOGGER.info("Usuario autenticado: " + username + " | isAdmin: " + isAdmin);

        } catch (Exception e) {
            LOGGER.severe("Token JWT inválido o expirado: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token JWT inválido o expirado").build());
        }
    }
}
