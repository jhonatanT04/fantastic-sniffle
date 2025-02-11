package DAO;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Vehiculo;


@Stateless
public class VehiculoDAO {
	@PersistenceContext
	EntityManager em ;
	
	public void agregarVehiculo(Vehiculo vehiculo) {
		em.persist(vehiculo);
    }


    public Vehiculo encontrarVehiculo(int id) {
        return em.find(Vehiculo.class, id);
    }


    public Vehiculo modificarVehiculo(Vehiculo vehiculo) {
        return em.merge(vehiculo);
    }


    public void eliminarVehiculo(int id) {
    	Vehiculo vehiculo = em.find(Vehiculo.class, id);
        if (vehiculo  != null) {
            em.remove(vehiculo );
        }
    }


    public List<Vehiculo> listarVehiculo() {
        TypedQuery<Vehiculo> query = em.createQuery("SELECT e FROM Vehiculo e", Vehiculo.class);
        return query.getResultList();
    }
}
