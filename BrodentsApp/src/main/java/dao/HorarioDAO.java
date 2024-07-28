package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Horario;

public class HorarioDAO {
	private final Connection connection;

	public HorarioDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Horario> getAllHorarios() throws SQLException {
		List<Horario> horarios = new ArrayList<>();
		String sql = "SELECT * FROM horarios";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				horarios.add(new Horario(rs.getInt("numero"), rs.getTime("horario").toLocalTime()));
			}
		}
		return horarios;
	}
}
