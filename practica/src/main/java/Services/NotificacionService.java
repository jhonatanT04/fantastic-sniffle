package Services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Properties;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import ups.practica.*;
import DAO.HorarioDAO;
import DAO.PersonaDAO;
import DAO.TicketDAO;
import DAO.ContratoDAO;

@Singleton
@Startup
public class NotificacionService {
    
    @Inject
    private HorarioDAO horarioDAO;

    @Inject
    private PersonaDAO personaDAO;

    @Inject
    private ContratoDAO contratoDAO;
    
    @Inject
    private TicketDAO ticketDAO;

    private Timer timer = new Timer();

    private String username = "upsparqueadero@gmail.com";
    private String password = "jqry hjht dnha itpz";

    @PostConstruct
    public void iniciarNotificaciones() {
        System.out.println("⏰ Iniciando servicio de notificaciones...");
        
        verificarHorariosYNotificar();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                verificarHorariosYNotificar();
            }
        }, 0, 5 * 60 * 1000);
    }

    public void verificarHorariosYNotificar() {
        System.out.println("Verificando horarios para notificaciones...");

        Horario horarioHoy = horarioDAO.getHorarioDelDia();
        if (horarioHoy == null) {
            System.out.println("No hay horario definido para hoy.");
            return;
        }

        String horaApertura = horarioHoy.getHoraApertura();
        String horaCierre = horarioHoy.getHoraCierre();

        List<Persona> clientes = personaDAO.listarPersona();

        List<Contrato> contratosActivos = contratoDAO.listarContrato();
        
        List<Ticket> ticketsActivos = ticketDAO.listarTicketsActivos(); 


        java.time.LocalTime ahora = java.time.LocalTime.now();
        String horaActual = ahora.toString().substring(0, 5);

        if (horaApertura != null && ahora.plusMinutes(30).toString().substring(0, 5).equals(horaApertura)) {
            for (Persona cliente : clientes) {
                enviarCorreoApertura(cliente.getEmail(), cliente.getNombre(), horaApertura);
            }
        }

        if (horaCierre != null && ahora.plusMinutes(15).toString().substring(0, 5).equals(horaCierre)) {
            for (Contrato contrato : contratosActivos) {
                Persona cliente = contrato.getUsuario();
                enviarCorreoCierre(cliente.getEmail(), cliente.getNombre(), horaCierre);
            }
            
            for (Ticket ticket : ticketsActivos) {
                Persona cliente = ticket.getUsuario();
                enviarCorreoCierre(cliente.getEmail(), cliente.getNombre(), horaCierre);
            }
        }
    }

    public void enviarCorreoApertura(String destinatario, String nombreCliente, String horaApertura) {
        String asunto = "🔔 Aviso de Apertura del Parqueadero";
        String mensajeHtml = "<h2 style='color: #28a745;'>¡Buenos días " + nombreCliente + "!</h2>"
                + "<p>Te recordamos que el parqueadero abrirá a las <strong>" + horaApertura + "</strong>.</p>"
                + "<p>Te esperamos.</p>"
                + "<p style='color: #777;'>Este es un mensaje automático, por favor no respondas.</p>";

        enviarCorreo(destinatario, asunto, mensajeHtml);
    }

    public void enviarCorreoCierre(String destinatario, String nombreCliente, String horaCierre) {
        String asunto = "⏳ Recordatorio de Cierre del Parqueadero";
        String mensajeHtml = "<h2 style='color: #dc3545;'>¡Atención " + nombreCliente + "!</h2>"
                + "<p>El parqueadero cerrará en <strong>15 minutos</strong> a las <strong>" + horaCierre + "</strong>.</p>"
                + "<p>Si tu vehículo sigue estacionado, por favor retíralo a tiempo.</p>"
                + "<p style='color: #777;'>Este es un mensaje automático, por favor no respondas.</p>";

        enviarCorreo(destinatario, asunto, mensajeHtml);
    }

    public void enviarCorreo(String destinatario, String asunto, String mensajeHtml) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable",  "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress("noreply@parqueadero.com")); 
            correo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            correo.setSubject(asunto);
            correo.setContent(mensajeHtml, "text/html; charset=utf-8");
            Transport.send(correo);
            System.out.println("✅ Correo enviado a: " + destinatario);
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar correo a " + destinatario + ": " + e.getMessage());
        }
    }


}
