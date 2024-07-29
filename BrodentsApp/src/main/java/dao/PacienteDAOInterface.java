package dao;

import java.sql.SQLException;
import java.util.List;

import model.Paciente;

public interface PacienteDAOInterface {
	boolean pacienteExists(String cedula) throws SQLException;

	void addPaciente(Paciente paciente) throws SQLException;

	Paciente getPaciente(int id) throws SQLException;

	List<Paciente> getAllPacientes() throws SQLException;

	void updatePaciente(Paciente paciente) throws SQLException;

	void deletePaciente(int id) throws SQLException;

	List<Paciente> buscarPacientesPorCedula(String cedula) throws SQLException;

	Paciente getPacienteByCedula(String cedula) throws SQLException;

	List<Paciente> buscarPacientesPorNombre(String nombre) throws SQLException;
}
