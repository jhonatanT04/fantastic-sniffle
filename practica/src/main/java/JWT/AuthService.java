
package JWT;

import java.util.Optional;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.practica.Persona;
import DAO.*;

@Path("/auth")
public class AuthService {
	@Inject
	private PersonaDAO PersonaDAO;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Persona persona) {
		
        Optional<Persona> user = PersonaDAO.validarCredenciales(persona.getEmail(), persona.getPassword());
        if (user.isPresent()) {
            String token = JwtUtil.generateToken(user.get().getEmail(), user.get().getRol(),user.get().getId());
            return Response.ok(new AuthResponse(token)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales incorrectas").build();
        }
    }
	
	
	
	class AuthResponse {
	    private String token;

	    public AuthResponse(String token) {
	        this.token = token;
	    }

	    public String getToken() {
	        return token;
	    }
	}
}
