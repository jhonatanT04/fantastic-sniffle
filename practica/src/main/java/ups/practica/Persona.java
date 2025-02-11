package ups.practica;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity


public class Persona {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona")
	private int id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="contratos")
	private List<Contrato> listaContratos;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="tickets")
	private List<Ticket> listaTickets;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="cedula")
    private String cedula;
	
	@Column(name="nombre")
    private String nombre;
	
	@Column(name="apellido")
    private String apellido;
	
	@Column(name="telefono")
    private String telefono;
	
	@Column(name="direccion")
    private String direccion;
	
	@Column(name="rol")
    private boolean rol;
    
    
    
    public List<Contrato> getListaContratos(){
    	return this.listaContratos;
    }
    public void setListaContratos(List<Contrato> listaContratos){
    	this.listaContratos = listaContratos;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean getRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }


    public String getEmail() { 
        return email;
    }

    public void setEmail(String email) { 
        this.email = email;
    }
    


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	
	public List<Ticket> getListaTickets() {
		return listaTickets;
	}


	public void setListaTickets(List<Ticket> listaTickets) {
		this.listaTickets = listaTickets;
	}


	@Override
	public String toString() {
		return "Persona [id=" + id + ", listaContratos=" + listaContratos + ", correo=" + email + ", password="
				+ password + ", cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono="
				+ telefono + ", direccion=" + direccion + ", rol=" + rol + "]";
	}

    
    
}
