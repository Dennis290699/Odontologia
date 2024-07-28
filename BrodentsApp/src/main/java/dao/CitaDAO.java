package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cita;
import model.CitaDetallada;

public class CitaDAO {
	private final Connection connection;

	public CitaDAO(Connection connection) {
		this.connection = connection;
	}

	public void addCita(Cita cita) throws SQLException {
		String sql = "INSERT INTO citas (cedula_paciente, id_servicio, id_horario, fecha) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cita.getCedulaPaciente());
			stmt.setInt(2, cita.getIdServicio());
			stmt.setInt(3, cita.getIdHorario());
			stmt.setDate(4, java.sql.Date.valueOf(cita.getFecha()));
			stmt.executeUpdate();
		}
	}

	public Cita getCita(int id) throws SQLException {
		String sql = "SELECT * FROM citas WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Cita(rs.getInt("id"), rs.getString("cedula_paciente"), rs.getInt("id_servicio"),
							rs.getInt("id_horario"), rs.getDate("fecha").toLocalDate());
				}
			}
		}
		return null;
	}

	public List<Cita> getAllCitas() throws SQLException {
		List<Cita> citas = new ArrayList<>();
		String sql = "SELECT * FROM citas";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				citas.add(new Cita(rs.getInt("id"), rs.getString("cedula_paciente"), rs.getInt("id_servicio"),
						rs.getInt("id_horario"), rs.getDate("fecha").toLocalDate()));
			}
		}
		return citas;
	}

	public List<Cita> getCitasByFecha(LocalDate fecha) throws SQLException {
		List<Cita> citas = new ArrayList<>();
		String sql = "SELECT * FROM citas WHERE fecha = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, Date.valueOf(fecha));
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					citas.add(new Cita(rs.getInt("id"), rs.getString("cedula_paciente"), rs.getInt("id_servicio"),
							rs.getInt("id_horario"), rs.getDate("fecha").toLocalDate()));
				}
			}
		}
		return citas;
	}

	public void updateCita(Cita cita) throws SQLException {
		String sql = "UPDATE citas SET cedula_paciente = ?, id_servicio = ?, id_horario = ?, fecha = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cita.getCedulaPaciente());
			stmt.setInt(2, cita.getIdServicio());
			stmt.setInt(3, cita.getIdHorario());
			stmt.setDate(4, Date.valueOf(cita.getFecha()));
			stmt.setInt(5, cita.getId());
			stmt.executeUpdate();
		}
	}

	public void deleteCita(int id) throws SQLException {
		String sql = "DELETE FROM citas WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	public List<CitaDetallada> getAllCitasDetalladas() throws SQLException {
		List<CitaDetallada> citas = new ArrayList<>();
		String sql = "SELECT c.id, c.fecha, p.cedula, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido, "
				+ "s.nombre AS servicio_nombre, h.horario " + "FROM citas c "
				+ "JOIN pacientes p ON c.cedula_paciente = p.cedula " + "JOIN servicios s ON c.id_servicio = s.id "
				+ "JOIN horarios h ON c.id_horario = h.numero";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				citas.add(new CitaDetallada(rs.getInt("id"), rs.getString("cedula"), rs.getString("paciente_nombre"),
						rs.getString("paciente_apellido"), rs.getString("servicio_nombre"),
						rs.getTime("horario").toLocalTime(), rs.getDate("fecha").toLocalDate()));
			}
		}
		return citas;
	}

	public List<CitaDetallada> getCitasDetalladasByCedula(String cedula) throws SQLException {
		String sql = "SELECT c.id, p.cedula, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente, "
				+ "s.nombre AS nombre_servicio, h.horario, c.fecha " + "FROM citas c "
				+ "JOIN pacientes p ON c.cedula_paciente = p.cedula " + "JOIN servicios s ON c.id_servicio = s.id "
				+ "JOIN horarios h ON c.id_horario = h.numero " + "WHERE p.cedula = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cedula);
			try (ResultSet rs = stmt.executeQuery()) {
				List<CitaDetallada> citas = new ArrayList<>();
				while (rs.next()) {
					citas.add(
							new CitaDetallada(rs.getInt("id"), rs.getString("cedula"), rs.getString("nombre_paciente"),
									rs.getString("apellido_paciente"), rs.getString("nombre_servicio"),
									rs.getTime("horario").toLocalTime(), rs.getDate("fecha").toLocalDate()));
				}
				return citas;
			}
		}
	}

	public void updateFechaCita(int id, LocalDate nuevaFecha) throws SQLException {
		String sql = "UPDATE citas SET fecha = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, Date.valueOf(nuevaFecha));
			stmt.setInt(2, id);
			stmt.executeUpdate();
		}
	}
}
