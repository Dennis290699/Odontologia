package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.PacienteDAO;
import model.Paciente;

public class JFListarPacientes {

    private JFrame frame;
    private JTable table;
    private PacienteDAO pacienteDAO;

    public JFListarPacientes() throws SQLException {
        pacienteDAO = new PacienteDAO();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("BRODENT'S - Listar Pacientes");
        frame.setLayout(new BorderLayout());

        // Panel superior con título
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(topPanel, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("BRODENT'S - Lista de Pacientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);

        // Panel central con tabla
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Cédula", "Nombre", "Apellido", "Teléfono"}
        ));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 123, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón de regreso
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(bottomPanel, BorderLayout.SOUTH);
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 18));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(new Color(220, 53, 69));
        btnRegresar.setOpaque(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setPreferredSize(new Dimension(150, 40));
        btnRegresar.addActionListener(e -> {
            // Acción del botón regresar
            JFPacientesScreen pacientesScreen = new JFPacientesScreen();
            pacientesScreen.setVisible(true);
            frame.dispose();
        });
        bottomPanel.add(btnRegresar);

        loadPacientes();
    }

    private void loadPacientes() {
        List<Paciente> pacientes = pacienteDAO.getAllPacientes();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Paciente paciente : pacientes) {
            model.addRow(new Object[]{paciente.getCedula(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono()});
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}