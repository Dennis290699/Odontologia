// JFBuscarPacientes.java
package vistas;

import dao.PacienteDAO;
import model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class JFBuscarPacientesScreen extends JFrame{

    private JFrame frame;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTable table;
    private PacienteDAO pacienteDAO;

    public JFBuscarPacientesScreen() {
        pacienteDAO = new PacienteDAO();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("BRODENT'S - Buscar Pacientes");
        frame.setLayout(new BorderLayout());

        // Panel superior con título
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(topPanel, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("BRODENT'S - Buscar Pacientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);

        // Panel central con formulario y tabla
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblCedula, gbc);

        gbc.gridx = 1;
        txtCedula = new JTextField(20);
        txtCedula.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBackground(new Color(40, 167, 69));
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setPreferredSize(new Dimension(150, 40));
        btnBuscar.addActionListener(e -> buscarPacientes());
        formPanel.add(btnBuscar, gbc);

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
            JFPacientesScreen pacientesScreen = new JFPacientesScreen();
            pacientesScreen.setVisible(true);
            frame.dispose();
        });
        bottomPanel.add(btnRegresar);
    }

    private void buscarPacientes() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();

        List<Paciente> pacientes;

        try {
            if (!cedula.isEmpty()) {
                pacientes = pacienteDAO.buscarPacientesPorCedula(cedula);
            } else if (!nombre.isEmpty()) {
                pacientes = pacienteDAO.buscarPacientesPorNombre(nombre);
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese la cédula o el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Paciente paciente : pacientes) {
                model.addRow(new Object[]{paciente.getCedula(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al buscar pacientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
