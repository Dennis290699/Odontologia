package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.TipoServicio;

public class TipoServicioDAO {
    private final Connection connection;

    public TipoServicioDAO(Connection connection) {
        this.connection = connection;
    }

    public void addTipoServicio(TipoServicio tipoServicio) throws SQLException {
        String sql = "INSERT INTO tipos_servicio (nombre, precio) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoServicio.getNombre());
            stmt.setBigDecimal(2, tipoServicio.getPrecio());
            stmt.executeUpdate();
        }
    }

    public TipoServicio getTipoServicio(int id) throws SQLException {
        String sql = "SELECT * FROM tipos_servicio WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoServicio(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getBigDecimal("precio")
                    );
                }
            }
        }
        return null;
    }

    public List<TipoServicio> getAllTiposServicio() throws SQLException {
        List<TipoServicio> tiposServicio = new ArrayList<>();
        String sql = "SELECT * FROM tipos_servicio";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tiposServicio.add(new TipoServicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio")
                ));
            }
        }
        return tiposServicio;
    }

    public void updateTipoServicio(TipoServicio tipoServicio) throws SQLException {
        String sql = "UPDATE tipos_servicio SET nombre = ?, precio = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoServicio.getNombre());
            stmt.setBigDecimal(2, tipoServicio.getPrecio());
            stmt.setInt(3, tipoServicio.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteTipoServicio(int id) throws SQLException {
        String sql = "DELETE FROM tipos_servicio WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
