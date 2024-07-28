package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.PacienteDAO;
import model.Paciente;

public class JFRegistrarPacientesScreen extends JFrame {

    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color BUTTON_REGISTER_COLOR = new Color(40, 167, 69);
    private static final Color BUTTON_BACK_COLOR = new Color(220, 53, 69);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private PacienteDAO pacienteDAO;

    public JFRegistrarPacientesScreen() {
        pacienteDAO = new PacienteDAO();
        initialize();
    }

    private void initialize() {
        setTitle("BRODENT'S - Registrar Paciente");
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createBottomPanel();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(MAIN_COLOR);
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(topPanel, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("BRODENT'S - Registrar Paciente");
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Crear y añadir los campos de texto y sus etiquetas
        addField(centerPanel, gbc, 0, "Cédula:", txtCedula = new JTextField(20));
        addField(centerPanel, gbc, 1, "Nombre:", txtNombre = new JTextField(20));
        addField(centerPanel, gbc, 2, "Apellido:", txtApellido = new JTextField(20));
        addField(centerPanel, gbc, 3, "Teléfono:", txtTelefono = new JTextField(20));
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        textField.setFont(LABEL_FONT);
        panel.add(textField, gbc);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(bottomPanel, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        styleButton(btnRegistrar, BUTTON_REGISTER_COLOR, e -> registrarPaciente());
        bottomPanel.add(btnRegistrar);

        JButton btnRegresar = new JButton("Regresar");
        styleButton(btnRegresar, BUTTON_BACK_COLOR, e -> regresar());
        bottomPanel.add(btnRegresar);
    }

    private void styleButton(JButton button, Color color, ActionListener actionListener) {
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(actionListener);
    }

    private void registrarPaciente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
        try {
            pacienteDAO.addPaciente(paciente);
            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar paciente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
    }

    private void regresar() {
        JFPacientesScreen pacientesScreen = new JFPacientesScreen();
        pacientesScreen.setVisible(true);
        dispose();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
