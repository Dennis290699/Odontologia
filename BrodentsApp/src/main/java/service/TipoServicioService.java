package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.TipoServicioDAO;
import model.TipoServicio;

public class TipoServicioService {
    private final TipoServicioDAO tipoServicioDAO;

    public TipoServicioService(Connection connection) {
        this.tipoServicioDAO = new TipoServicioDAO(connection);
    }

    public void addTipoServicio(String nombre, BigDecimal precio) throws SQLException {
        TipoServicio tipoServicio = new TipoServicio(0, nombre, precio);
        tipoServicioDAO.addTipoServicio(tipoServicio);
    }

    public TipoServicio getTipoServicio(int id) throws SQLException {
        return tipoServicioDAO.getTipoServicio(id);
    }

    public List<TipoServicio> getAllTiposServicio() throws SQLException {
        return tipoServicioDAO.getAllTiposServicio();
    }

    public void updateTipoServicio(int id, String nombre, BigDecimal precio) throws SQLException {
        TipoServicio tipoServicio = new TipoServicio(id, nombre, precio);
        tipoServicioDAO.updateTipoServicio(tipoServicio);
    }

    public void deleteTipoServicio(int id) throws SQLException {
        tipoServicioDAO.deleteTipoServicio(id);
    }
}
