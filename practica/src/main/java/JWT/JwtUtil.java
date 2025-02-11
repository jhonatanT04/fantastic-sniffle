package JWT;



import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public class JwtUtil {
	 
	 private static final long EXPIRATION_TIME = 86400000; 
	 private static final String SECRET_KEY = "mi_clave_secreta_que_tiene_256_bits!!!!!"; 

	
	 private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	 
	 
	 public static String generateToken(String email, boolean rol,int id) {
	        return Jwts.builder()
	        		.claim("id", id)
	                .setSubject(email)
	                .claim("rol", rol ) 
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(key)
	                .compact();
	    }

	    public static Claims validateToken(String token) {
	        return Jwts.parser()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
}
