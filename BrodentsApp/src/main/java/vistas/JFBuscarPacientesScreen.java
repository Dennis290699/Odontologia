package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controllers.PacienteController;
import model.Paciente;

public class JFBuscarPacientesScreen extends JFrame {
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTable table;
    private PacienteController controller;

    public JFBuscarPacientesScreen() {
        controller = new PacienteController(this);
        initialize();
    }

    private void initialize() {
        setTitle("BRODENT'S - Buscar Pacientes");
        setBounds(100, 100, 900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createBottomPanel();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255));
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(topPanel, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("BRODENT'S - Buscar Pacientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        centerPanel.add(formPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
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
        styleButton(btnBuscar, new Color(40, 167, 69), e -> buscarPacientes());
        formPanel.add(btnBuscar, gbc);

        table = new JTable();
        table.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] { "Cédula", "Nombre", "Apellido", "Teléfono" }));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 123, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(bottomPanel, BorderLayout.SOUTH);

        JButton btnRegresar = new JButton("Regresar");
        styleButton(btnRegresar, new Color(220, 53, 69), e -> {
            JFPacientesScreen pacientesScreen = new JFPacientesScreen();
            pacientesScreen.setVisible(true);
            dispose();
        });
        bottomPanel.add(btnRegresar);
    }

    private void styleButton(JButton button, Color color, ActionListener actionListener) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(actionListener);
    }

    private void buscarPacientes() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        controller.buscarPacientes(cedula, nombre);
    }

    public void actualizarTabla(List<Paciente> pacientes) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Paciente paciente : pacientes) {
            model.addRow(new Object[] { paciente.getCedula(), paciente.getNombre(), paciente.getApellido(),
                    paciente.getTelefono() });
        }
    }
}
