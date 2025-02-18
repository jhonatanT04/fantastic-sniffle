package Services;



import java.util.List;


import Gestion.GestionHorarios;
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
import ups.practica.Horario;

@Path("/horarios")
public class HorarioService {
	
	@Inject
	private GestionHorarios gestionHorario;
	
	@POST 
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createHorario(@HeaderParam("Authorization") String authHeader,Horario horario) { 
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
	public Response updateHorario(@HeaderParam("Authorization") String authHeader,Horario horario) {
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
			
			
			gestionHorario.modificarHorario(horario);
			return Response.ok(horario).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@GET
	@Produces("application/json")
	public Response listHorarios(@HeaderParam("Authorization") String authHeader){
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
	public Response getHorario(@HeaderParam("Authorization") String authHeader,@PathParam("id") int id) {
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
			
			
			Horario horario = gestionHorario.encontrarHorario(id);
			return Response.ok(horario).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(503).entity(new Respuesta(Respuesta.ERROR,"Error en la base de datos")).build();
		}
	}
	
	@DELETE
	@Path("/deletePersona/{id}")
	public Response deletePersona(@HeaderParam("Authorization") String authHeader,@PathParam("id") int id) {
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
    public Response listHorariosNormales(@HeaderParam("Authorization") String authHeader) {
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
    public Response listHorariosEspeciales(@HeaderParam("Authorization") String authHeader) {
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
    public Response createHorarioEspecial(@HeaderParam("Authorization") String authHeader,Horario horario) {
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
    public Response getHorarioDelDia(@HeaderParam("Authorization") String authHeader) {
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