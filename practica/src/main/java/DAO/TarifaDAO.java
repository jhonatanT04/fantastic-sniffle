package DAO;

import java.time.Duration;
import java.time.LocalDateTime;
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
    	TypedQuery<Tarifa> query = em.createQuery(
    	        "SELECT t FROM Tarifa t ORDER BY " +
    	        "CASE WHEN t.tipo = 'm' THEN 1 " +  
    	        "     WHEN t.tipo = 'H' THEN 2 " +  
    	        "     WHEN t.tipo = 'D' THEN 3 " +  
    	        "     WHEN t.tipo = 'M' THEN 4 END, " +  
    	        " t.tiempo ASC", Tarifa.class
    	    );
        return query.getResultList();
    }
    
    public double consultaValorApagar(String horaEntrada, String horaSalida) {
        List<Tarifa> tarifas = this.listarTarifa();

        
        LocalDateTime fechaInicio = LocalDateTime.parse(horaEntrada, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime fechaFin = LocalDateTime.parse(horaSalida, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Duration duracion = Duration.between(fechaInicio, fechaFin);

        long dias = duracion.toDays();
        long horas = duracion.toHours() % 24;
        long minutos = duracion.toMinutes() % 60;

        double costoTotal = 0.0;
        if(dias==0 && horas==0 && minutos==0) {
        	return 0.1;
        }
        
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
        if(dias==0) {
        	
        }

        return costoTotal;
    }

    
}
