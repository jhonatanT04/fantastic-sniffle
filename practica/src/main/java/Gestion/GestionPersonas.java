package Gestion;

import java.util.List;

import javax.naming.AuthenticationException;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Persona;
import DAO.PersonaDAO;

@Stateless
public class GestionPersonas {

    @EJB
    private PersonaDAO personaDAO;

    public void agregarPersona(Persona persona) {
        if (persona != null) {
            personaDAO.agregarPersona(persona);
        }
    }

    public Persona buscarPersona(int id) {
    	return personaDAO.encontrarPersona(id);
    }
    
    public Persona buscarPersonaEmail(String email) throws AuthenticationException {
    	return personaDAO.encontrarPersonaEmail(email);
    }
    
    public Persona actualizarPersona(Persona persona) {
    	return personaDAO.modificarPersona(persona);
    }
    
    public void eliminarPersona(int id) {
    	personaDAO.eliminarPersona(id); 
    }
    public List<Persona> listarPersonas() {
        return personaDAO.listarPersona();
    }
}
