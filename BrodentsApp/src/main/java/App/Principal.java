package App;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import model.Cita;
import model.Paciente;
import model.TipoServicio;
import service.CitaService;
import service.PacienteService;
import service.TipoServicioService;
import util.DatabaseConnection;

public class Principal {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            TipoServicioService tipoServicioService = new TipoServicioService(connection);
            PacienteService pacienteService = new PacienteService(connection);
            CitaService citaService = new CitaService(connection);

            // Ejemplo de uso del servicio TipoServicio
            TipoServicio tipoServicio = new TipoServicio(0, "Consulta General", new BigDecimal("50.00"));
            tipoServicioService.addTipoServicio(tipoServicio.getNombre(), tipoServicio.getPrecio());
            TipoServicio retrievedTipoServicio = tipoServicioService.getTipoServicio(1);
            System.out.println("Tipo de Servicio: " + retrievedTipoServicio);

            // Ejemplo de uso del servicio Paciente
            Paciente paciente = new Paciente(0, "123456789", "Juan Pérez", "555-1234", "juan.perez@example.com");
            try {
                pacienteService.addPaciente(paciente.getCedula(), paciente.getNombre(), paciente.getTelefono(), paciente.getEmail());
                Paciente retrievedPaciente = pacienteService.getPaciente(1);
                System.out.println("Paciente: " + retrievedPaciente);
            } catch (SQLException e) {
                System.err.println("Error al agregar paciente: " + e.getMessage());
            }

            // Ejemplo de uso del servicio Cita
            Cita cita = new Cita(0, 1, LocalDateTime.now(), 1, "Primera cita");
            citaService.addCita(cita.getPacienteId(), cita.getFechaHora(), cita.getTipoServicioId(), cita.getObservaciones());
            Cita retrievedCita = citaService.getCita(1);
            System.out.println("Cita: " + retrievedCita);

            // Listar todos los tipos de servicio, pacientes y citas
            List<TipoServicio> allTiposServicio = tipoServicioService.getAllTiposServicio();
            System.out.println("Todos los tipos de servicio: " + allTiposServicio);

            List<Paciente> allPacientes = pacienteService.getAllPacientes();
            System.out.println("Todos los pacientes: " + allPacientes);

            List<Cita> allCitas = citaService.getAllCitas();
            System.out.println("Todas las citas: " + allCitas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
