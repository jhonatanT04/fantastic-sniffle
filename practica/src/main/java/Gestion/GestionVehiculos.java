package Gestion;

import java.util.List;

import DAO.VehiculoDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Vehiculo;


@Stateless
public class GestionVehiculos {
	@EJB
	private VehiculoDAO vehiculoDAO;
	
	public void agregarVehiculo(Vehiculo vehiculo) {
		vehiculoDAO.agregarVehiculo(vehiculo);
	}
	
	public List<Vehiculo> listarVehiculo(){
		return vehiculoDAO.listarVehiculo();
	}
	
	public Vehiculo encontrarVehiculo(int id) {
		return vehiculoDAO.encontrarVehiculo(id);
	}
	
	public Vehiculo modificarVehiculo(Vehiculo vehiculo) {
		return vehiculoDAO.modificarVehiculo(vehiculo);
	}
	
	public void eliminarVehiculo(int id) {
		vehiculoDAO.eliminarVehiculo(id); 
	}

}
