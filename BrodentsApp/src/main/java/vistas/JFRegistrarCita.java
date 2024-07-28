package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.CitaDAO;
import dao.HorarioDAO;
import dao.PacienteDAO;
import dao.ServicioDAO;
import model.Cita;
import model.Horario;
import model.Paciente;
import model.Servicio;
import util.DatabaseConnection;

public class JFRegistrarCita extends JFrame {

    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color BUTTON_REGISTER_COLOR = new Color(40, 167, 69);
    private static final Color BUTTON_BACK_COLOR = new Color(220, 53, 69);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

    private JTextField txtCedula;
    private JComboBox<Servicio> cmbServicios;
    private JComboBox<Horario> cmbHorarios;
    private JDatePickerImpl datePicker;
    private PacienteDAO pacienteDAO;
    private CitaDAO citaDAO;
    private ServicioDAO servicioDAO;
    private HorarioDAO horarioDAO;

    public JFRegistrarCita() {
        Connection connection = DatabaseConnection.getConnection();
        pacienteDAO = new PacienteDAO(connection);
        citaDAO = new CitaDAO(connection);
        servicioDAO = new ServicioDAO(connection);
        horarioDAO = new HorarioDAO(connection);
        initialize();
    }

    private void initialize() {
        setTitle("BRODENT'S - Crear Cita");
        setBounds(100, 100, 600, 450);
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

        JLabel lblTitulo = new JLabel("BRODENT'S - Crear Cita");
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblCedula = new JLabel("Cédula del Paciente:");
        lblCedula.setFont(LABEL_FONT);
        centerPanel.add(lblCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        txtCedula = new JTextField(20);
        txtCedula.setFont(LABEL_FONT);
        centerPanel.add(txtCedula, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton btnValidar = new JButton("Validar Paciente");
        btnValidar.setFont(BUTTON_FONT);
        btnValidar.setForeground(Color.WHITE);
        btnValidar.setBackground(new Color(23, 162, 184));
        btnValidar.setOpaque(true);
        btnValidar.setBorderPainted(false);
        btnValidar.addActionListener(e -> validarPaciente());
        centerPanel.add(btnValidar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel lblServicio = new JLabel("Servicio:");
        lblServicio.setFont(LABEL_FONT);
        centerPanel.add(lblServicio, gbc);

        gbc.gridx = 1;
        cmbServicios = new JComboBox<>();
        cmbServicios.setFont(LABEL_FONT);
        centerPanel.add(cmbServicios, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setFont(LABEL_FONT);
        centerPanel.add(lblHorario, gbc);

        gbc.gridx = 1;
        cmbHorarios = new JComboBox<>();
        cmbHorarios.setFont(LABEL_FONT);
        centerPanel.add(cmbHorarios, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(LABEL_FONT);
        centerPanel.add(lblFecha, gbc);

        gbc.gridx = 1;
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        centerPanel.add(datePicker, gbc);

        loadServicios();
        loadHorarios();
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(bottomPanel, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar Cita");
        styleButton(btnRegistrar, BUTTON_REGISTER_COLOR, e -> registrarCita());
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

    private void validarPaciente() {
        String cedula = txtCedula.getText();
        try {
            Paciente paciente = pacienteDAO.getPacienteByCedula(cedula);
            if (paciente != null) {
                JOptionPane.showMessageDialog(this,
                        "Paciente válido: " + paciente.getNombre() + " " + paciente.getApellido(), "Validación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado", "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al validar paciente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadServicios() {
        try {
            List<Servicio> servicios = servicioDAO.getAllServicios();
            for (Servicio servicio : servicios) {
                cmbServicios.addItem(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadHorarios() {
        try {
            List<Horario> horarios = horarioDAO.getAllHorarios();
            for (Horario horario : horarios) {
                cmbHorarios.addItem(horario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registrarCita() {
        String cedula = txtCedula.getText();
        try {
            Paciente paciente = pacienteDAO.getPacienteByCedula(cedula);
            if (paciente == null) {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado. No se puede registrar la cita.",
                        "Error de Registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Servicio servicio = (Servicio) cmbServicios.getSelectedItem();
            Horario horario = (Horario) cmbHorarios.getSelectedItem();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Error de Registro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate fecha = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Cita nuevaCita = new Cita();
            nuevaCita.setCedulaPaciente(cedula);
            nuevaCita.setIdServicio(servicio.getId());
            nuevaCita.setIdHorario(horario.getNumero());
            nuevaCita.setFecha(fecha);

            citaDAO.addCita(nuevaCita);
            JOptionPane.showMessageDialog(this, "Cita registrada exitosamente", "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario
            txtCedula.setText("");
            cmbServicios.setSelectedIndex(0);
            cmbHorarios.setSelectedIndex(0);
            datePicker.getModel().setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar cita", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresar() {
        JFCitasScreen citasScreen = new JFCitasScreen();
        citasScreen.setVisible(true);
        dispose();
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    // Formatter for JDatePicker
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
