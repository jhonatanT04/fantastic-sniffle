package ups.practica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Tarifa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tarifa")
	private int id;
	
	@Column(name="tiempo")
    private int tiempo;
	
	@Column(name="tipo")
    private char tipo; //m: minutos H:horas D:Dias M:meses 
	
	@Column(name="costo")
    private double costo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

	@Override
	public String toString() {
		return "Tarifa [id=" + id + ", tiempo=" + tiempo + ", costo=" + costo + "]";
	}

    
}

