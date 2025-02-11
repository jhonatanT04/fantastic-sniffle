package Gestion;

import java.util.List;

import DAO.RegistroDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Registro;


@Stateless
public class GestionRegistros {

    @EJB
    private RegistroDAO registroDAO;

    public void agregarRegistro(Registro registro) {
        registroDAO.agregarResgistro(registro);
    }

    public List<Registro> listarRegistros() {
        return registroDAO.listarRegistros();
    }
    
    public List<Registro> listarEnParqueadero() {
        return registroDAO.obtenerVehiculosEnParqueadero();
    }

    public Registro buscarRegistro(int id) {
        return registroDAO.buscarRegistro(id);
    }

    public Registro modificarRegistro(Registro registro) {
        return registroDAO.modificarTicket(registro);
    }

    public void eliminarRegistro(int id) {
        registroDAO.eliminarRegistro(id);
    }
    
    public List<Registro> obtenerHistorial(String periodo){
    	return registroDAO.obtenerHistorialPorPeriodo(periodo);
    }
}
