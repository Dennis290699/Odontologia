package model;

import java.time.LocalDate;

public class Cita {
	private int id;
	private String cedulaPaciente;
	private int idServicio;
	private int idHorario;
	private LocalDate fecha;

	// Constructor
	public Cita(int id, String cedulaPaciente, int idServicio, int idHorario, LocalDate fecha) {
		this.id = id;
		this.cedulaPaciente = cedulaPaciente;
		this.idServicio = idServicio;
		this.idHorario = idHorario;
		this.fecha = fecha;
	}

	// Constructor sin parámetros (opcional)
	public Cita() {
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCedulaPaciente() {
		return cedulaPaciente;
	}

	public void setCedulaPaciente(String cedulaPaciente) {
		this.cedulaPaciente = cedulaPaciente;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public int getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return " Paciente: " + cedulaPaciente + ", Fecha: " + fecha;
	}
}
