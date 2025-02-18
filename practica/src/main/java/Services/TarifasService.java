package Services;

import java.util.List;

import Gestion.GestionTarifas;
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
import ups.practica.Tarifa;


@Path("/tarifas")
public class TarifasService {

    @Inject
    private GestionTarifas gTarifas;

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam("Authorization") String authHeader,Tarifa tarifa) {
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
        	
        	if(tarifa.getTipo()=='m') {
        		if(tarifa.getTiempo()>59) {
        			return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "El tiempo de ser menor a 59 minutos")).build();
        		}
        	}else if(tarifa.getTipo()=='H') {
        		if(tarifa.getTiempo()>24) {
        			return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "El tiempo de ser menor a 24 horas")).build();
        		}
        	}else if(tarifa.getTipo()=='D') {
        		if(tarifa.getTiempo()>24) {
        			return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "El tiempo de ser menor a 30 dias")).build();
        		}
        	}else if(tarifa.getTipo()=='M') {
        		if(tarifa.getTiempo()>24) {
        			return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "El tiempo de ser menor a 12 meses")).build();
        		}
        	}else {
        		return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Datos erroneos")).build();
        	}
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
    public Response update(@HeaderParam("Authorization") String authHeader,Tarifa tarifa) {
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
        	
            gTarifas.eliminarTarifa(id);
            return Response.ok(new Respuesta(Respuesta.OK, "Tarifa eliminada con éxito")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(503).entity(new Respuesta(Respuesta.ERROR, "Error al eliminar la tarifa")).build();
        }
    }
    
    @GET
	@Produces("application/json")
	public Response listTarifas(@HeaderParam("Authorization") String authHeader){
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
			List<Tarifa> listatarifas = gTarifas.listarTarifas();
			return Response.ok(listatarifas).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
}