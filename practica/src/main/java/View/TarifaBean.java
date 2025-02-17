package View;

import java.io.Serializable;
import java.util.List;

import DAO.TarifaDAO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ups.practica.Tarifa;

@Named
@ViewScoped
public class TarifaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Tarifa> tarifas;
    private Tarifa tarifa;
    private boolean mostrarAgregar;
    private boolean mostrarEditar;

    @Inject
    private TarifaDAO tarifaDAO;

    @PostConstruct
    public void init() {
        tarifas = tarifaDAO.listarTarifa();
        tarifa = new Tarifa(); 
        mostrarAgregar = false;
        mostrarEditar = false;
    }

    
    public void mostrarFormularioAgregar() {
        tarifa = new Tarifa();
        mostrarAgregar = true;
        mostrarEditar = false;
    }

    public void mostrarFormularioEditar(Tarifa t) {
        this.tarifa = t;
        mostrarAgregar = false;
        mostrarEditar = true;
    }

    public void guardarTarifa() {
        tarifaDAO.agregarTarifa(tarifa);
        tarifas = tarifaDAO.listarTarifa(); 
        tarifa = new Tarifa();
        mostrarAgregar = false; 
    }

    public void actualizarTarifa() {
        tarifaDAO.modificarTarifa(tarifa);
        tarifas = tarifaDAO.listarTarifa(); 
        tarifa = new Tarifa(); 
        mostrarEditar = false; 
    }

    public void eliminarTarifa(int id) {
        tarifaDAO.eliminarTarifa(id);
        tarifas = tarifaDAO.listarTarifa(); 
    }

    public void cancelar() {
        tarifa = new Tarifa(); 
        mostrarAgregar = false;
        mostrarEditar = false;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public boolean isMostrarAgregar() {
        return mostrarAgregar;
    }

    public void setMostrarAgregar(boolean mostrarAgregar) {
        this.mostrarAgregar = mostrarAgregar;
    }

    public boolean isMostrarEditar() {
        return mostrarEditar;
    }

    public void setMostrarEditar(boolean mostrarEditar) {
        this.mostrarEditar = mostrarEditar;
    }
}
