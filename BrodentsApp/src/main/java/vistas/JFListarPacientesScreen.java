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

public class JFListarPacientesScreen extends JFrame {

    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color BUTTON_COLOR = new Color(220, 53, 69);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

    private JTable table;
    private PacienteDAO pacienteDAO;

    public JFListarPacientesScreen() throws SQLException {
        pacienteDAO = new PacienteDAO();
        initialize();
    }

    private void initialize() {
        setTitle("BRODENT'S - Listar Pacientes");
        setBounds(100, 100, 900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        loadPacientes();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(MAIN_COLOR);
        topPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(topPanel, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("BRODENT'S - Lista de Pacientes");
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Cédula", "Nombre", "Apellido", "Teléfono"}
        ));
        table.setRowHeight(25);
        table.getTableHeader().setFont(TABLE_HEADER_FONT);
        table.getTableHeader().setBackground(MAIN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(TABLE_FONT);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 80));
        add(bottomPanel, BorderLayout.SOUTH);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(BUTTON_FONT);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(BUTTON_COLOR);
        btnRegresar.setOpaque(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setPreferredSize(new Dimension(150, 40));
        btnRegresar.addActionListener(e -> regresar());

        bottomPanel.add(btnRegresar);
    }

    private void loadPacientes() {
        List<Paciente> pacientes = pacienteDAO.getAllPacientes();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Paciente paciente : pacientes) {
            model.addRow(new Object[] {paciente.getCedula(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono()});
        }
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
