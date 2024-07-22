package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Cita;

public class CitaDAO {
    private final Connection connection;

    public CitaDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCita(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (paciente_id, fecha_hora, tipo_servicio_id, observaciones) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cita.getPacienteId());
            stmt.setTimestamp(2, Timestamp.valueOf(cita.getFechaHora()));
            stmt.setInt(3, cita.getTipoServicioId());
            stmt.setString(4, cita.getObservaciones());
            stmt.executeUpdate();
        }
    }

    public Cita getCita(int id) throws SQLException {
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cita(
                            rs.getInt("id"),
                            rs.getInt("paciente_id"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getInt("tipo_servicio_id"),
                            rs.getString("observaciones")
                    );
                }
            }
        }
        return null;
    }

    public List<Cita> getAllCitas() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                citas.add(new Cita(
                        rs.getInt("id"),
                        rs.getInt("paciente_id"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getInt("tipo_servicio_id"),
                        rs.getString("observaciones")
                ));
            }
        }
        return citas;
    }

    public void updateCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET paciente_id = ?, fecha_hora = ?, tipo_servicio_id = ?, observaciones = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cita.getPacienteId());
            stmt.setTimestamp(2, Timestamp.valueOf(cita.getFechaHora()));
            stmt.setInt(3, cita.getTipoServicioId());
            stmt.setString(4, cita.getObservaciones());
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
}
