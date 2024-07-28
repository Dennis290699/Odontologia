package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;
import util.DatabaseConnection;

public class PacienteDAO {
	private Connection connection;

	public PacienteDAO() {
		this.connection = DatabaseConnection.getConnection();
	}

	// Verificar si el paciente ya existe
	public boolean pacienteExists(String cedula) throws SQLException {
		String query = "SELECT COUNT(*) FROM pacientes WHERE cedula = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cedula);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
	}

	public void addPaciente(Paciente paciente) throws SQLException {
		if (pacienteExists(paciente.getCedula())) {
			throw new SQLException("Paciente con cédula " + paciente.getCedula() + " ya existe.");
		}

		String query = "INSERT INTO pacientes (cedula, nombre, apellido, telefono) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, paciente.getCedula());
			stmt.setString(2, paciente.getNombre());
			stmt.setString(3, paciente.getApellido());
			stmt.setString(4, paciente.getTelefono());
			stmt.executeUpdate();
		}
	}

	public Paciente getPaciente(int id) throws SQLException {
		String sql = "SELECT * FROM pacientes WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Paciente(rs.getString("cedula"), rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("telefono"));
				}
			}
		}
		return null;
	}

	public List<Paciente> getAllPacientes() {
		List<Paciente> pacientes = new ArrayList<>();
		String query = "SELECT cedula, nombre, apellido, telefono FROM pacientes";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String cedula = resultSet.getString("cedula");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				String telefono = resultSet.getString("telefono");

				Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
				pacientes.add(paciente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pacientes;
	}

	public void updatePaciente(Paciente paciente) throws SQLException {
		String sql = "UPDATE pacientes SET nombre = ?, apellido = ?, telefono = ? WHERE cedula = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, paciente.getNombre());
			stmt.setString(2, paciente.getApellido());
			stmt.setString(3, paciente.getTelefono());
			stmt.setString(4, paciente.getCedula());
			stmt.executeUpdate();
		}
	}

	public void deletePaciente(int id) throws SQLException {
		String sql = "DELETE FROM pacientes WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
	
	public List<Paciente> buscarPacientesPorCedula(String cedula) throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes WHERE cedula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente(
                            rs.getString("cedula"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono")
                    );
                    pacientes.add(paciente);
                }
            }
        }
        return pacientes;
    }

    public List<Paciente> buscarPacientesPorNombre(String nombre) throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes WHERE nombre ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente(
                            rs.getString("cedula"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono")
                    );
                    pacientes.add(paciente);
                }
            }
        }
        return pacientes;
    }
}