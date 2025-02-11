package ups.practica;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Horario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_horario")
	private int id;
	
	@Column(name="dia",	 nullable = true)
    private String dia;
	
	@Column(name="horaApertura")
    private String horaApertura;
	
	@Column(name="horaCierre")
    private String horaCierre;
    
	@Column(name="tipoHorario")
    private String tipoHorario; 

    @Column(name="fechaEspecial", nullable = true)
    private Date fechaEspecial;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

	public String getTipoHorario() {
		return tipoHorario;
	}

	public void setTipoHorario(String tipoHorario) {
		this.tipoHorario = tipoHorario;
	}

	public Date getFechaEspecial() {
		return fechaEspecial;
	}

	public void setFechaEspecial(Date fechaEspecial) {
		this.fechaEspecial = fechaEspecial;
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", dia=" + dia + ", horaApertura=" + horaApertura + ", horaCierre=" + horaCierre
				+ ", tipoHorario=" + tipoHorario + ", fechaEspecial=" + fechaEspecial + "]";
	}

}
