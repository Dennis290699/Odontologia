package model;

import java.time.LocalDateTime;

public class Cita {
    private int id;
    private int pacienteId;
    private LocalDateTime fechaHora;
    private int tipoServicioId;
    private String observaciones;

    public Cita(int id, int pacienteId, LocalDateTime fechaHora, int tipoServicioId, String observaciones) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.fechaHora = fechaHora;
        this.tipoServicioId = tipoServicioId;
        this.observaciones = observaciones;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(int pacienteId) {
		this.pacienteId = pacienteId;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getTipoServicioId() {
		return tipoServicioId;
	}

	public void setTipoServicioId(int tipoServicioId) {
		this.tipoServicioId = tipoServicioId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
    @Override
    public String toString() {
        return "Cita{id=" + id + ", pacienteId=" + pacienteId + ", fechaHora=" + fechaHora + ", tipoServicioId=" + tipoServicioId + ", observaciones='" + observaciones + "'}";
    }
}
