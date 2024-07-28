package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.Paciente;
import service.PacienteService;
import util.DatabaseConnection;

public class Test {
	static Connection connection = DatabaseConnection.getConnection();

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
			Paciente paciente = new Paciente();
			PacienteService pacienteService = new PacienteService(connection);

			System.out.println("Bienvenido a --Tu medico virtual-- para agendar tu cita online.");

			// VERIFICAR Y CREAR UN USUARIO
			boolean pacienteEncontrado = false;

			while (!pacienteEncontrado) {
			    // Solicitar la cédula del paciente
			    System.out.println("Por favor, ingrese su número de cedula:");
			    String cedula = scanner.nextLine();
			    paciente.setCedula(cedula);

			    // Verificar si el paciente ya existe
			    try {
			        String queryPacienteExistente = "SELECT * FROM pacientes WHERE cedula = ?";
			        PreparedStatement pstmtPaciente = connection.prepareStatement(queryPacienteExistente);
			        pstmtPaciente.setString(1, cedula); // Aquí se establece el parámetro

			        ResultSet rsPacienteExistente = pstmtPaciente.executeQuery();

			        if (rsPacienteExistente.next()) {
			            System.out.println("La cédula ya está registrada. Por favor, ingrese una cédula diferente.");
			        } else {
			            // Paciente no encontrado, solicitar más datos
			            System.out.println("Por favor, ingrese su nombre:");
			            String nombre = scanner.nextLine();
			            paciente.setNombre(nombre);

			            System.out.println("Por favor, ingrese su apellido:");
			            String apellido = scanner.nextLine();
			            paciente.setApellido(apellido);

			            System.out.println("Por favor, ingrese su número de telefono:");
			            String telefono = scanner.nextLine();
			            paciente.setTelefono(telefono);

			            // AGREGAR USUARIO
			            Paciente pacienteAdd = new Paciente(cedula, nombre, apellido, telefono);
			            try {
			                pacienteService.addPaciente(pacienteAdd.getNombre(), pacienteAdd.getApellido(),
			                        pacienteAdd.getCedula(), pacienteAdd.getTelefono());
			                System.out.println("Paciente " + nombre + " registrado");
			                pacienteEncontrado = true;
			            } catch (SQLException e) {
			                System.err.println("Error al agregar paciente: " + e.getMessage());
			            }
			        }
			    } catch (SQLException e) {
			        System.err.println("Error al verificar paciente: " + e.getMessage());
			    }
			}

			// MOSTRAR SERVICIOS
			int idServicioSeleccionado = -1;
			try {
			    String queryServicios = "SELECT * FROM servicios";
			    Statement stmtServicios = connection.createStatement();
			    ResultSet rsServicios = stmtServicios.executeQuery(queryServicios);
			    System.out.println("######### Selecciona un servicio: #########");
			    while (rsServicios.next()) {
			        System.out.println("|" + rsServicios.getInt("id") + ". " + rsServicios.getString("nombre")
			                + " - Precio: $" + rsServicios.getDouble("precio") + "|");
			    }

			    System.out.println("Por favor, ingrese el número del servicio deseado:");
			    idServicioSeleccionado = scanner.nextInt();
			    scanner.nextLine(); // Limpiar el buffer del scanner
			} catch (SQLException e) {
			    System.err.println("Error al seleccionar servicio " + e.getMessage());
			}

			// MOSTRAR HORARIOS
			int idHorarioSeleccionado = -1;
			try {
			    String queryHorarios = "SELECT * FROM horarios";
			    Statement stmtHorarios = connection.createStatement();
			    ResultSet rsHorarios = stmtHorarios.executeQuery(queryHorarios);

			    System.out.println("Selecciona un horario:");
			    while (rsHorarios.next()) {
			        System.out.println(rsHorarios.getInt("numero") + ". " + rsHorarios.getString("horario"));
			    }

			    System.out.println("Por favor, ingrese el número del horario deseado:");
			    idHorarioSeleccionado = scanner.nextInt();
			    scanner.nextLine(); // Limpiar el buffer del scanner
			} catch (SQLException e) {
			    System.err.println("Error al seleccionar horario " + e.getMessage());
			}

			// REGISTRAR CITA
			try {
			    String queryRegistrarCita = "INSERT INTO citas (cedula_paciente, id_servicio, id_horario, fecha) VALUES (?, ?, ?, ?)";
			    PreparedStatement pstmtRegistrarCita = connection.prepareStatement(queryRegistrarCita, Statement.RETURN_GENERATED_KEYS);
			    pstmtRegistrarCita.setString(1, paciente.getCedula());
			    pstmtRegistrarCita.setInt(2, idServicioSeleccionado);
			    pstmtRegistrarCita.setInt(3, idHorarioSeleccionado);
			    pstmtRegistrarCita.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Fecha actual

			    pstmtRegistrarCita.executeUpdate();
			    System.out.println("Cita registrada exitosamente.");

			    // Obtener el ID de la cita recién registrada
			    ResultSet rsCitaGenerada = pstmtRegistrarCita.getGeneratedKeys();
			    if (rsCitaGenerada.next()) {
			        int idCitaGenerada = rsCitaGenerada.getInt(1);

			        // Mostrar detalles de la cita registrada
			        String queryCitaDetalles = "SELECT c.cedula_paciente, p.nombre AS nombre_paciente, h.horario, s.nombre AS nombre_servicio " +
			                                   "FROM citas c " +
			                                   "JOIN pacientes p ON c.cedula_paciente = p.cedula " +
			                                   "JOIN horarios h ON c.id_horario = h.numero " +
			                                   "JOIN servicios s ON c.id_servicio = s.id " +
			                                   "WHERE c.id = ?";
			        PreparedStatement pstmtCitaDetalles = connection.prepareStatement(queryCitaDetalles);
			        pstmtCitaDetalles.setInt(1, idCitaGenerada);

			        ResultSet rsCitaDetalles = pstmtCitaDetalles.executeQuery();
			        if (rsCitaDetalles.next()) {
			            System.out.println("Detalles de la cita registrada:");
			            System.out.println("Cédula del paciente: " + rsCitaDetalles.getString("cedula_paciente"));
			            System.out.println("Nombre del paciente: " + rsCitaDetalles.getString("nombre_paciente"));
			            System.out.println("Horario: " + rsCitaDetalles.getString("horario"));
			            System.out.println("Servicio: " + rsCitaDetalles.getString("nombre_servicio"));
			        }
			    }
			} catch (SQLException e) {
			    System.err.println("Error al registrar la cita: " + e.getMessage());
			}
		}
    }
}
