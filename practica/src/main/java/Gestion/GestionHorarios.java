package Gestion;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.practica.Horario;
import DAO.HorarioDAO;

@Stateless
public class GestionHorarios {

    @EJB
    private HorarioDAO horarioDAO;

    public void agregarHorario(Horario horario) {
        if (horario != null) {
            horarioDAO.agregarHorario(horario);
        }
    }

    public void agregarHorarioEspecial(Horario horario) {
    	horarioDAO.agregarHorarioEspecial(horario);
    }
    
    public Horario encontrarHorario(int id) {
        if (id > 0) {
            return horarioDAO.encontrarHorario(id);
        }
        return null;
    }

    public Horario modificarHorario(Horario horario) {
        if (horario != null && horario.getId() > 0) {
            return horarioDAO.modificarHorario(horario);
        }
        return null;
    }

    public void eliminarHorario(int id) {
        if (id > 0) {
            horarioDAO.eliminarHorario(id);
        }
    }

    public List<Horario> listarHorarios() {
        return horarioDAO.listarHorario();
    }
    
    public List<Horario> listarHorariosNormales() {
        return horarioDAO.listarHorariosNormales();
    }
    
    public List<Horario> listarHorariosEspeciales() {
        return horarioDAO.listarHorariosEspeciales();
    }
    
    public Horario getHorarioDia() {
    	return horarioDAO.getHorarioDelDia();
    }
}
