package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dao.CitaDAO;
import model.Cita;

public class CitaService {
    private final CitaDAO citaDAO;

    public CitaService(Connection connection) {
        this.citaDAO = new CitaDAO(connection);
    }

    public void addCita(int pacienteId, LocalDateTime fechaHora, int tipoServicioId, String observaciones) throws SQLException {
        Cita cita = new Cita(0, pacienteId, fechaHora, tipoServicioId, observaciones);
        citaDAO.addCita(cita);
    }

    public Cita getCita(int id) throws SQLException {
        return citaDAO.getCita(id);
    }

    public List<Cita> getAllCitas() throws SQLException {
        return citaDAO.getAllCitas();
    }

    public void updateCita(int id, int pacienteId, LocalDateTime fechaHora, int tipoServicioId, String observaciones) throws SQLException {
        Cita cita = new Cita(id, pacienteId, fechaHora, tipoServicioId, observaciones);
        citaDAO.updateCita(cita);
    }

    public void deleteCita(int id) throws SQLException {
        citaDAO.deleteCita(id);
    }
}
