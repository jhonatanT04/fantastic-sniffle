package ups.practica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Espacio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_espacio")
	private int id;
	
	@Column(name="nombreEspacio")
    private String nombreEspacio;
	
	@Column(name="estadoEspacio")
    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	@Override
	public String toString() {
		return "Espacio [id=" + id + ", nombreEspacio=" + nombreEspacio + ", estado=" + estado + "]";
	}
    
    
}

