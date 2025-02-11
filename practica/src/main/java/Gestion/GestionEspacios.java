package Gestion;

import java.util.List;

import DAO.EspacioDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Espacio;


@Stateless
public class GestionEspacios {

	@EJB
	private EspacioDAO espacioDAO;
	
	public void agregarEspacio(Espacio espacio) {
		if(espacio !=null) {
			espacioDAO.agregarEspacio(espacio);
			
		}
	}
	
	public Espacio encontrarEspacio(int id) {
		return espacioDAO.encontrarEspacio(id);
	}
	
	public Espacio modificarEspacio(Espacio espacio) {
		return espacioDAO.modificarEspacio(espacio);
	}
	
	public void eliminarEspacio(int id) {
		espacioDAO.eliminarEspacio(id);
	}
	
	public List<Espacio> listarEspacios(){
		return espacioDAO.listarEspacio();
	}
}
