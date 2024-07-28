package dao;

import model.Servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private final Connection connection;

    public ServicioDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Servicio> getAllServicios() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                servicios.add(new Servicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio")
                ));
            }
        }
        return servicios;
    }
}
