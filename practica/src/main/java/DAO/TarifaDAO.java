package DAO;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.practica.Tarifa;


@Stateless
public class TarifaDAO {
	@PersistenceContext
	EntityManager em ;
	
	public void agregarTarifa(Tarifa tarifa) {
		em.persist(tarifa);
    }


    public Tarifa encontrarTarifa(int id) {
        return em.find(Tarifa.class, id);
    }


    public Tarifa modificarTarifa(Tarifa tarifa) {
        return em.merge(tarifa);
    }


    public void eliminarTarifa(int id) {
        Tarifa tarifa = em.find(Tarifa.class, id);
        if (tarifa != null) {
            em.remove(tarifa);
        }
    }


    public List<Tarifa> listarTarifa() {
        TypedQuery<Tarifa> query = em.createQuery("SELECT t FROM Tarifa t", Tarifa.class);
        return query.getResultList();
    }
    
    public double consultaValorApagar(String horaEntrada, String horaSalida) {
        List<Tarifa> tarifas = this.listarTarifa();

        LocalTime fechaInicio = LocalTime.parse(horaEntrada, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime fechaFin = LocalTime.parse(horaSalida, DateTimeFormatter.ofPattern("HH:mm"));
        Duration duracion = Duration.between(fechaInicio, fechaFin);

        long dias = duracion.toDays();
        long horas = duracion.toHours() % 24;
        long minutos = duracion.toMinutes() % 60;

        double costoTotal = 0.0;

        
        if (dias > 0) {
            Tarifa tarifaDia = tarifas.stream()
                    .filter(t -> t.getTipo() == 'D')
                    .findFirst()
                    .orElse(null);
            if (tarifaDia != null) {
                costoTotal += dias * tarifaDia.getCosto();
            }
        }


        if (horas > 0) {
            Tarifa tarifaHora = tarifas.stream()
                    .filter(t -> t.getTipo() == 'H' && t.getTiempo() == 1)
                    .findFirst()
                    .orElse(null);
            if (tarifaHora != null) {
                costoTotal += horas * tarifaHora.getCosto();
            }
        }


        if (minutos > 0) {
            Tarifa tarifaMinuto = tarifas.stream()
                    .filter(t -> t.getTipo() == 'm' && t.getTiempo() == 30)
                    .findFirst()
                    .orElse(null);
            if (tarifaMinuto != null) {
                costoTotal += (minutos / 30.0) * tarifaMinuto.getCosto();
            }
        }

        return costoTotal;
    }
    
}
