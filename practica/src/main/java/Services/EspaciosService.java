package Services;

import java.util.List;

import Gestion.GestionEspacios;
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
import ups.practica.Espacio;


@Path("/espacios")
public class EspaciosService {

    @Inject
    private GestionEspacios gEspacios;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("Authorization") String authHeader,Espacio espacio) {
    	try {
    		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Token no proporcionado").build();
            }
            System.out.println(authHeader);
            String token = authHeader.substring("Bearer".length()).trim();
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor("mi_clave_secreta_que_tiene_256_bits!!!!!".getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
            
            String username = claims.getSubject(); 
            boolean isAdmin = claims.get("rol",Boolean.class); 
            
            System.out.println("Usuario autenticado: " + username + " | isAdmin: " + isAdmin);

            
            if (!isAdmin) {
                return Response.status(Response.Status.FORBIDDEN).entity("No tienes permisos para esta acción").build();
            }

            gEspacios.agregarEspacio(espacio);
            return Response.ok(espacio).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al crear el espacio")).build();
        }
    
    }

    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam("Authorization") String authHeader,Espacio espacio) {
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
        	
        	
        	
            if (espacio != null && espacio.getId() != 0) {
                gEspacios.modificarEspacio(espacio);
                return Response.ok(new Respuesta(Respuesta.OK, "Espacio Actualizado")).build();
            }
            return Response.status(404).entity(new Respuesta(Respuesta.ERROR, "Datos inválidos para la actualización")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al actualizar el espacio")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response read(@HeaderParam("Authorization") String authHeader,@PathParam("id") int id) {
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
        	
        	
        	
        	
            Espacio espacio = gEspacios.encontrarEspacio(id);
            if (espacio == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(espacio).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al buscar el espacio")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam("Authorization") String authHeader,@PathParam("id") int id) {
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
        	
        	
        	
        	
            gEspacios.eliminarEspacio(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Espacio eliminado con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar el espacio")).build();
        }
    }
    
    @GET
	@Produces("application/json")
    public Response listEspacios(@HeaderParam("Authorization") String authHeader) {
        try {
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Token no proporcionado").build();
            }
            String token = authHeader.substring("Bearer".length()).trim();
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor("mi_clave_secreta_que_tiene_256_bits!!!!!".getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Usuario autenticadoaksiydbasoihbdjanhispbodluvj asnhipljbkdas ");
            String username = claims.getSubject();  
            boolean isAdmin = claims.get("rol",Boolean.class); 

            System.out.println("Usuario autenticado: " + username + " | isAdmin: " + isAdmin);

            
            List<Espacio> listaEspacios = gEspacios.listarEspacios();
			return Response.ok(listaEspacios).build();

        } catch (Exception e) {
        	e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
        }
    }
}