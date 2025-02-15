package DAO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import ups.practica.Registro;



@Stateless
public class RegistroDAO {
	
	@PersistenceContext
    EntityManager em;

    public void agregarResgistro(Registro registro) {
        em.persist(registro);
    }

    public Registro buscarRegistro(int id) {
        return em.find(Registro.class, id);
    }
    
    public Registro buscarRegistroPorPlaca(String placa) {
    	TypedQuery<Registro> query = em.createQuery(
                "SELECT t FROM Registro t WHERE t.placa = :placa", Registro.class);
            query.setParameter("placa", placa);    
        List<Registro> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    public Registro buscarRegistroPendientePorPlaca(String placa) {
    	TypedQuery<Registro> query = em.createQuery(
                "SELECT t FROM Registro t WHERE t.placa = :placa AND t.fechaSalida is null", Registro.class);
            query.setParameter("placa", placa);    
        List<Registro> resultados = query.getResultList();
        System.out.println(resultados);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public List<Registro> listarRegistros() {
        TypedQuery<Registro> query = em.createQuery("SELECT t FROM Registro t", Registro.class);
        return query.getResultList();
    }

    public void eliminarRegistro(int id) {
    	Registro registro = em.find(Registro.class, id);
        if (registro != null) {
            em.remove(registro);
        }
    }

    public Registro modificarTicket(Registro registro) {
        return em.merge(registro);
    }
    
    public List<Registro> obtenerHistorialPorPeriodo(String periodo) {
        Date hoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);

        String jpql = "SELECT r FROM Registro r WHERE ";
        TypedQuery<Registro> query;

        switch (periodo.toLowerCase()) {
            case "dia":
                jpql += "FUNCTION('DATE', r.fechaIngreso) = :hoy";
                query = em.createQuery(jpql, Registro.class);
                query.setParameter("hoy", hoy, TemporalType.DATE);
                break;

            case "semana":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date inicioSemana = cal.getTime();
                jpql += "FUNCTION('DATE', r.fechaIngreso) BETWEEN :inicioSemana AND :hoy";
                query = em.createQuery(jpql, Registro.class);
                query.setParameter("inicioSemana", inicioSemana, TemporalType.DATE);
                query.setParameter("hoy", hoy, TemporalType.DATE);
                break;

            case "mes":
                int mes = cal.get(Calendar.MONTH) + 1;
                int anio = cal.get(Calendar.YEAR);
                jpql += "MONTH(r.fechaIngreso) = :mes AND YEAR(r.fechaIngreso) = :anio";
                query = em.createQuery(jpql, Registro.class);
                query.setParameter("mes", mes);
                query.setParameter("anio", anio);
                break;

            default:
                throw new IllegalArgumentException("El período especificado no es válido. Debe ser 'dia', 'semana' o 'mes'.");
        }

        return query.getResultList();
    }

    
    public List<Registro> obtenerVehiculosEnParqueadero() {
        String jpql = "SELECT r FROM Registro r WHERE r.fechaSalida IS NULL";
        TypedQuery<Registro> query = em.createQuery(jpql, Registro.class);
        return query.getResultList();
    }

}
