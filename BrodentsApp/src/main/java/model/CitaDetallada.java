package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDetallada {
	private int id;
	private String cedulaPaciente;
	private String nombrePaciente;
	private String apellidoPaciente;
	private String nombreServicio;
	private LocalTime horario;
	private LocalDate fecha;

	public CitaDetallada(int id, String cedulaPaciente, String nombrePaciente, String apellidoPaciente,
			String nombreServicio, LocalTime horario, LocalDate fecha) {
		this.id = id;
		this.cedulaPaciente = cedulaPaciente;
		this.nombrePaciente = nombrePaciente;
		this.apellidoPaciente = apellidoPaciente;
		this.nombreServicio = nombreServicio;
		this.horario = horario;
		this.fecha = fecha;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public String getCedulaPaciente() {
		return cedulaPaciente;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public String getApellidoPaciente() {
		return apellidoPaciente;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public LocalDate getFecha() {
		return fecha;
	}
}
