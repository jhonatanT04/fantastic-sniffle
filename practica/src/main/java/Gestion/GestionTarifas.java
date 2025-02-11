package Gestion;

import java.util.List;

import DAO.TarifaDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Tarifa;


@Stateless
public class GestionTarifas {
	 @EJB
	    private TarifaDAO tarifaDAO;

	    public void agregarTarifa(Tarifa tarifa) {
	        if (tarifa != null) {
	            tarifaDAO.agregarTarifa(tarifa);;
	        }
	    }

	    public Tarifa encontrarTarifa(int id) {
	        if (id > 0) {
	            return tarifaDAO.encontrarTarifa(id);
	        }
	        return null;
	    }

	    public Tarifa modificarTarifa(Tarifa tarifa) {
	        if (tarifa != null && tarifa.getId() > 0) {
	            return tarifaDAO.modificarTarifa(tarifa);
	        }
	        return null;
	    }

	    public void eliminarTarifa(int id) {
	        if (id > 0) {
	            tarifaDAO.eliminarTarifa(id);
	        }
	    }

	    public List<Tarifa> listarTarifas() {
	        return tarifaDAO.listarTarifa();
	    }

}
