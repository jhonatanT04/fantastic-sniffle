package DAO;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Espacio;


@Stateless
public class EspacioDAO {
	@PersistenceContext
	EntityManager em ;
	
	public void agregarEspacio(Espacio espacio) {
		em.persist(espacio);
    }


    public Espacio encontrarEspacio(int id) {
        return em.find(Espacio.class, id);
    }


    public Espacio modificarEspacio(Espacio espacio) {
        return em.merge(espacio);
    }


    public void eliminarEspacio(int id) {
        Espacio espacio = em.find(Espacio.class, id);
        if (espacio  != null) {
            em.remove(espacio );
        }
    }


    public List<Espacio> listarEspacio() {
        TypedQuery<Espacio> query = em.createQuery("SELECT e FROM Espacio e ORDER BY e.id ASC", Espacio.class);
        return query.getResultList();
    }
}
