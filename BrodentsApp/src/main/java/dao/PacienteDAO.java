package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteDAO {
    private final Connection connection;

    public PacienteDAO(Connection connection) {
        this.connection = connection;
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

        String query = "INSERT INTO pacientes (cedula, nombre, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, paciente.getCedula());
            stmt.setString(2, paciente.getNombre());
            stmt.setString(3, paciente.getTelefono());
            stmt.setString(4, paciente.getEmail());
            stmt.executeUpdate();
        }
    }

    public Paciente getPaciente(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                            rs.getInt("id"),
                            rs.getString("cedula"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public List<Paciente> getAllPacientes() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pacientes.add(new Paciente(
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }
        }
        return pacientes;
    }

    public void updatePaciente(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET cedula = ?, nombre = ?, telefono = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paciente.getCedula());
            stmt.setString(2, paciente.getNombre());
            stmt.setString(3, paciente.getTelefono());
            stmt.setString(4, paciente.getEmail());
            stmt.setInt(5, paciente.getId());
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
}
