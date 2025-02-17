package ups.practica;

import java.util.Date;

import jakarta.persistence.*;

@Entity

public class Contrato{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contrato")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_tarifa")
	private Tarifa tarifa; 
	
	@OneToOne
	@JoinColumn(name="id_espacio")
	private Espacio espacio ;
	
	@Column(name="placa")
    private String placa ;
    
	@Column(name="fechaInicio")
	private Date fechaInicio;
    
	@Column(name="fechaFin")
	private Date fechaFin; 

	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Persona usuario;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

	public Persona getUsuario() {
		return usuario;
	}

	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Contrato [id=" + id + ", tarifa=" + tarifa + ", espacio=" + espacio + ", placa=" + placa
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", usuario=" + usuario + "]";
	}

}

