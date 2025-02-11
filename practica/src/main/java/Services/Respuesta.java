package Services;

public class Respuesta {
    public static final String OK = "OK";
    public static final String ERROR = "Error";

    private String estado;
    private String mensaje;

    public Respuesta(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "estado='" + estado + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
