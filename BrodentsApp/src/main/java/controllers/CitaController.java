package controllers;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import dao.CitaDAO;
import model.CitaDetallada;
import util.DatabaseConnection;
import vistas.JFListarCitasScreen;

public class CitaController {
    private CitaDAO citaDAO;
    private JFListarCitasScreen view;

    public CitaController(JFListarCitasScreen view) {
        this.view = view;
        this.citaDAO = new CitaDAO(DatabaseConnection.getConnection()); // Asegúrate de que DatabaseConnection esté disponible
    }

    public void loadCitas() {
        List<CitaDetallada> citas = null;
        try {
            citas = citaDAO.getAllCitasDetalladas();
            view.updateTable(citas);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar citas: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

