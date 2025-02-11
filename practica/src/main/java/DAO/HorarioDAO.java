package DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Horario;



@Stateless

public class HorarioDAO {
	@PersistenceContext
	EntityManager em ;
	
	public void agregarHorario(Horario horario) {
		em.persist(horario);
    }


    public Horario encontrarHorario(int id) {
        return em.find(Horario.class, id);
    }


    public Horario modificarHorario(Horario horario) {
        return em.merge(horario);
    }


    public void eliminarHorario(int id) {
        Horario horario = em.find(Horario.class, id);
        if (horario != null) {
            em.remove(horario);
        }
    }


    public List<Horario> listarHorario() {
        TypedQuery<Horario> query = em.createQuery("SELECT h FROM Horario h", Horario.class);
        return query.getResultList();
    }
    
    public List<Horario> listarHorariosNormales() {
        TypedQuery<Horario> query = em.createQuery(
            "SELECT h FROM Horario h WHERE h.tipoHorario = 'Normal'", 
            Horario.class
        );
        return query.getResultList();
    }

    public List<Horario> listarHorariosEspeciales() {
        TypedQuery<Horario> query = em.createQuery(
            "SELECT h FROM Horario h WHERE h.tipoHorario = 'Especial'", 
            Horario.class
        );
        return query.getResultList();
    }

    public void agregarHorarioEspecial(Horario horario) {
        horario.setTipoHorario("Especial");
        em.persist(horario);
    }
    
    public Horario getHorarioDelDia() {
        try {
            Date fechaHoy = new Date(); 
            SimpleDateFormat formatoDia = new SimpleDateFormat("EEEE", new Locale("es", "EC"));
            String diaActual = formatoDia.format(fechaHoy); // Obtener el nombre del día en español

            TypedQuery<Horario> queryEspecial = em.createQuery(
                "SELECT h FROM Horario h WHERE h.fechaEspecial = :fecha", Horario.class);
            queryEspecial.setParameter("fecha", fechaHoy);
            List<Horario> horariosEspeciales = queryEspecial.getResultList();

            if (!horariosEspeciales.isEmpty()) {
                return horariosEspeciales.get(0);
            }

            TypedQuery<Horario> queryRegular = em.createQuery(
                "SELECT h FROM Horario h WHERE LOWER(h.dia) = LOWER(:dia)", Horario.class);
            queryRegular.setParameter("dia", diaActual);
            List<Horario> horariosRegulares = queryRegular.getResultList();

            if (!horariosRegulares.isEmpty()) {
                return horariosRegulares.get(0);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


	
}
