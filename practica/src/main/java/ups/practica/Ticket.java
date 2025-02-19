	package ups.practica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_ticket")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Persona usuario;
	
	@Column(name="placa")
	private String placa;
	
	@Column(name="fechaIngreso")
	private String fechaIngreso;
	
	@Column(name="fechaSalida")
	private String fechaSalida;
	
	@Column(name="valorTotal")
	private double valorTotal;

	@ManyToOne
	@JoinColumn(name="id_espacio")
	private Espacio espacio;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Espacio getEspacio() {
		return espacio;
	}

	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
	}
	
	public Persona getUsuario() {
		return usuario;
	}

	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", placa=" + placa + ", fechaIngreso=" + fechaIngreso + ", fechaSalida="
				+ fechaSalida + ", valorTotal=" + valorTotal + ", espacio=" + espacio + "]";
	}

}