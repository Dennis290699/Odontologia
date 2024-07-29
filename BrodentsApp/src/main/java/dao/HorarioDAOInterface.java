package dao;

import model.Horario;
import java.sql.SQLException;
import java.util.List;

public interface HorarioDAOInterface {
    List<Horario> getAllHorarios() throws SQLException;
}
