package Main;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DAO.EspacioDAO;
import DAO.HorarioDAO;
import DAO.PersonaDAO;
import DAO.TarifaDAO;
import Services.NotificacionService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import ups.practica.*;


@Singleton
@Startup
public class Inicio {
	@Inject
	private HorarioDAO daoHoraio;
	
	@Inject
	private PersonaDAO daoPersona ;
	
	@Inject
    private TarifaDAO daoTarifa;  

    @Inject
    private EspacioDAO daoEspacio;
    
    @Inject
    private HorarioDAO daoHorario;
    
	@PostConstruct
	public void init() {
		System.out.println("Hola mundo EJB");
		Horario hor = new Horario();
		
		/*hor.setDia("Jueves");
		hor.setHoraApertura("12:00");
		hor.setHoraCierre("20:00");
		daoHoraio.agregarHorario(hor);*/
		
		daoHoraio.listarHorario();
		
		for (Horario horario: daoHoraio.listarHorario()) {
			System.out.println(horario);
		} 
		
		
		
		Persona per = new Persona();
		per.setEmail("admin@example.com");
		per.setPassword("admin123");
		per.setApellido("Lucero");
		per.setCedula("012312312");
		per.setDireccion("Casa");
		per.setNombre("Justin");
		per.setRol(true);
		per.setTelefono("0123");
		daoPersona.agregarPersona(per);
		
		Persona per2 = new Persona();
		per2.setPassword("QPhHc8rknpW1HjGfD3CYpPYt9jr2");
		per2.setEmail("venotacu@gmail.com");
		per2.setApellido("JH");
		per2.setCedula("0921312323");
		per2.setDireccion("Casa");
		per2.setNombre("Insano");
		per2.setRol(false);
		per2.setTelefono("0921312323");
		daoPersona.agregarPersona(per2);
		
		/*Persona per3 = new Persona();
		per3.setPassword("justin12");
		per3.setEmail("justinmateolucero@gmail.com");
		per3.setApellido("Mateo");
		per3.setCedula("0921312323");
		per3.setDireccion("Casa");
		per3.setNombre("Justin");
		per3.setRol(false);
		per3.setTelefono("0921312323");
		daoPersona.agregarPersona(per3);*/
		
		//NotificacionService servicio = new NotificacionService();
	    //servicio.enviarCorreoApertura("justinmateolucero@gmail.com", "Justin", "20:20");
	    //servicio.enviarCorreoCierre("correo_destino@gmail.com", "Juan", "20:00");
		
		List<Tarifa> listaTarifas = new ArrayList<>();

		Tarifa tarifa1 = new Tarifa();
		tarifa1.setTiempo("30 minutos");
		tarifa1.setCosto(0.50);

		Tarifa tarifa2 = new Tarifa();
		tarifa2.setTiempo("1 hora");
		tarifa2.setCosto(1.00);

		Tarifa tarifa5 = new Tarifa();
		tarifa5.setTiempo("8 horas");
		tarifa5.setCosto(6.00);

		Tarifa tarifa6 = new Tarifa();
		tarifa6.setTiempo("12 horas");
		tarifa6.setCosto(8.00);

		Tarifa tarifa7 = new Tarifa();
		tarifa7.setTiempo("24 horas");
		tarifa7.setCosto(12.00);

		Tarifa tarifa9 = new Tarifa();
		tarifa9.setTiempo("Mensual");
		tarifa9.setCosto(40.00);

		listaTarifas.add(tarifa1);
		listaTarifas.add(tarifa2);
		listaTarifas.add(tarifa5);
		listaTarifas.add(tarifa6);
		listaTarifas.add(tarifa7);
		listaTarifas.add(tarifa9);

		for (Tarifa tarifa : listaTarifas) {
		    daoTarifa.agregarTarifa(tarifa);
		}


        List<Espacio> listaEspacios = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Espacio espacio = new Espacio();
            espacio.setNombreEspacio("Espacio " + i);
            espacio.setEstado("D");
            
            listaEspacios.add(espacio);
            daoEspacio.agregarEspacio(espacio);
        }
		
		daoPersona.agregarPersona(per);
		for (Persona persona: daoPersona.listarPersona()) {
			System.out.println(persona);
		}
		
		List<String> diasSemana = List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        for (String dia : diasSemana) {
            Horario horario = new Horario();
            horario.setDia(dia);
            horario.setHoraApertura("08:00");
            horario.setHoraCierre("18:00");
            horario.setTipoHorario("Normal");
            daoHorario.agregarHorario(horario);
        }

        List<Horario> horariosEspeciales = new ArrayList<>();
        Horario especial1 = new Horario();
        especial1.setFechaEspecial(new Date(2025, 2, 14)); 
        especial1.setHoraApertura("10:00");
        especial1.setHoraCierre("20:00");
        especial1.setTipoHorario("Especial");

        Horario especial2 = new Horario();
        especial2.setFechaEspecial(new Date(2025, 12, 25));
        especial2.setHoraApertura("09:00");
        especial2.setHoraCierre("22:00");
        especial2.setTipoHorario("Especial");

        horariosEspeciales.add(especial1);
        horariosEspeciales.add(especial2);

        for (Horario h : horariosEspeciales) {
            daoHorario.agregarHorario(h);
        }
		
	}
}

