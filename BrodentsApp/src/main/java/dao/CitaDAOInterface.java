package dao;

import model.Cita;
import model.CitaDetallada;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface CitaDAOInterface {
	void addCita(Cita cita) throws SQLException;

	Cita getCita(int id) throws SQLException;

	List<Cita> getAllCitas() throws SQLException;

	List<Cita> getCitasByFecha(LocalDate fecha) throws SQLException;

	void updateCita(Cita cita) throws SQLException;

	void deleteCita(int id) throws SQLException;

	List<CitaDetallada> getAllCitasDetalladas() throws SQLException;

	List<CitaDetallada> getCitasDetalladasByCedula(String cedula) throws SQLException;

	void updateFechaCita(int id, LocalDate nuevaFecha) throws SQLException;
}
