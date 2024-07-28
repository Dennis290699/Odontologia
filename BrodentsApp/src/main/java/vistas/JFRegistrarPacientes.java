package vistas;

import dao.PacienteDAO;
import model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class JFRegistrarPacientes {

    private JFrame frame;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private PacienteDAO pacienteDAO;

    public JFRegistrarPacientes() {
        pacienteDAO = new PacienteDAO();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("BRODENT'S - Registrar Paciente");
        frame.setLayout(new BorderLayout());

        // Panel superior con título
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(topPanel, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("BRODENT'S - Registrar Paciente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);

        // Panel central con formulario
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(lblCedula, gbc);

        gbc.gridx = 1;
        txtCedula = new JTextField(20);
        txtCedula.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(lblApellido, gbc);

        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        txtApellido.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(lblTelefono, gbc);

        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(txtTelefono, gbc);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(bottomPanel, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(new Color(40, 167, 69));
        btnRegistrar.setOpaque(true);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setPreferredSize(new Dimension(150, 40));
        btnRegistrar.addActionListener(e -> registrarPaciente());
        bottomPanel.add(btnRegistrar);

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

    private void registrarPaciente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
        try {
            pacienteDAO.addPaciente(paciente);
            JOptionPane.showMessageDialog(frame, "Paciente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al registrar paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
