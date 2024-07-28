package controllers;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import dao.PacienteDAO;
import model.Paciente;
import vistas.JFBuscarPacientesScreen;

public class PacienteController {
    private PacienteDAO pacienteDAO;
    private JFBuscarPacientesScreen view;

    public PacienteController(JFBuscarPacientesScreen view) {
        this.view = view;
        this.pacienteDAO = new PacienteDAO();
    }

    public void buscarPacientes(String cedula, String nombre) {
        List<Paciente> pacientesEncontrados;
        try {
            if (!cedula.isEmpty()) {
                pacientesEncontrados = pacienteDAO.buscarPacientesPorCedula(cedula);
            } else if (!nombre.isEmpty()) {
                pacientesEncontrados = pacienteDAO.buscarPacientesPorNombre(nombre);
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, ingrese la cédula o el nombre.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            view.actualizarTabla(pacientesEncontrados);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error al buscar pacientes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
