package vistas;

import dao.CitaDAO;
import model.CitaDetallada;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JFReagendarCitaScreen extends JFrame {

    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color BUTTON_CONFIRM_COLOR = new Color(40, 167, 69);
    private static final Color BUTTON_BACK_COLOR = new Color(220, 53, 69);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    private JTextField txtCedula;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNuevaFecha;
    private CitaDAO citaDAO;

    public JFReagendarCitaScreen() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        citaDAO = new CitaDAO(connection);
        initialize();
    }

    private void initialize() {
        setTitle("Reagendar Cita");
        setBounds(100, 100, 900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        createTopPanel();
        createSearchPanel();
        createBottomPanel();

        setVisible(true);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(MAIN_COLOR);
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(topPanel, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("Reagendar Cita");
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        add(searchPanel, BorderLayout.CENTER);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(LABEL_FONT);
        searchPanel.add(lblCedula);

        txtCedula = new JTextField(20);
        txtCedula.setFont(FIELD_FONT);
        searchPanel.add(txtCedula);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(BUTTON_FONT);
        btnBuscar.addActionListener(e -> buscarCitas(null));
        searchPanel.add(btnBuscar);

        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Cédula Paciente", "Nombre Paciente", "Apellido Paciente", "Servicio", "Horario", "Fecha"}
        );
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(LABEL_FONT);
        table.getTableHeader().setBackground(MAIN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(FIELD_FONT);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 400));
        searchPanel.add(scrollPane);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        add(bottomPanel, BorderLayout.SOUTH);

        JLabel lblNuevaFecha = new JLabel("Nueva Fecha (YYYY-MM-DD):");
        lblNuevaFecha.setFont(LABEL_FONT);
        bottomPanel.add(lblNuevaFecha);

        txtNuevaFecha = new JTextField(10);
        txtNuevaFecha.setFont(FIELD_FONT);
        bottomPanel.add(txtNuevaFecha);

        JButton btnReagendar = new JButton("Reagendar");
        styleButton(btnReagendar, BUTTON_CONFIRM_COLOR, this::reagendarCita);
        bottomPanel.add(btnReagendar);

        JButton btnRegresar = new JButton("Regresar");
        styleButton(btnRegresar, BUTTON_BACK_COLOR, e -> regresar());
        bottomPanel.add(btnRegresar);
    }

    private void styleButton(JButton button, Color color, java.awt.event.ActionListener actionListener) {
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(actionListener);
    }

    private void buscarCitas(String cedula) {
        if (cedula == null) {
            cedula = txtCedula.getText().trim();
        }
        try {
            List<CitaDetallada> citas = citaDAO.getCitasDetalladasByCedula(cedula);
            tableModel.setRowCount(0); // Clear existing rows
            for (CitaDetallada cita : citas) {
                tableModel.addRow(new Object[]{
                    cita.getId(),
                    cita.getCedulaPaciente(),
                    cita.getNombrePaciente(),
                    cita.getApellidoPaciente(),
                    cita.getNombreServicio(),
                    cita.getHorario(),
                    cita.getFecha()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar citas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void reagendarCita(ActionEvent event) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para reagendar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (int) tableModel.getValueAt(selectedRow, 0);
        String nuevaFechaStr = txtNuevaFecha.getText().trim();
        try {
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);
            citaDAO.updateFechaCita(idCita, nuevaFecha);
            JOptionPane.showMessageDialog(this, "Cita reagendada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtNuevaFecha.setText(""); // Clear the new date field
            buscarCitas(null); // Refresh the table
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al reagendar cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void regresar() {
        JFCitasScreen citasScreen = new JFCitasScreen();
        citasScreen.setVisible(true);
        dispose();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
