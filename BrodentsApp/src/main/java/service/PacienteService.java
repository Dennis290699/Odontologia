package service;

import dao.PacienteDAO;
import model.Paciente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PacienteService {
    private final PacienteDAO pacienteDAO;

    public PacienteService(Connection connection) {
        this.pacienteDAO = new PacienteDAO(connection);
    }

    public void addPaciente(String nombre, String apellido, String cedula, String telefono) throws SQLException {
        Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
        pacienteDAO.addPaciente(paciente);
    }

    public Paciente getPaciente(int id) throws SQLException {
        return pacienteDAO.getPaciente(id);
    }

    public List<Paciente> getAllPacientes() throws SQLException {
        return pacienteDAO.getAllPacientes();
    }

    public void updatePaciente(int id, String cedula, String nombre, String telefono, String email) throws SQLException {
        Paciente paciente = new Paciente( cedula, nombre, telefono, email);
        pacienteDAO.updatePaciente(paciente);
    }

    public void deletePaciente(int id) throws SQLException {
        pacienteDAO.deletePaciente(id);
    }
}
