package model;

import java.time.LocalTime;

public class Horario {
	private int numero;
	private LocalTime horario;

	public Horario(int numero, LocalTime horario) {
		this.numero = numero;
		this.horario = horario;
	}

	public int getNumero() {
		return numero;
	}

	public LocalTime getHorario() {
		return horario;
	}

	@Override
	public String toString() {
		return horario.toString();
	}
}
