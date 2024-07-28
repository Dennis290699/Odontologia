package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.CitaDAO;
import model.CitaDetallada;
import util.DatabaseConnection;

public class JFListarCitasScreen extends JFrame {

	private static final Color MAIN_COLOR = new Color(0, 123, 255);
	private static final Color BUTTON_BACK_COLOR = new Color(220, 53, 69);
	private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
	private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
	private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14);
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

	private JTable table;
	private CitaDAO citaDAO;

	public JFListarCitasScreen() {
		Connection connection = DatabaseConnection.getConnection();
		citaDAO = new CitaDAO(connection);
		initialize();
	}

	private void initialize() {
		setTitle("BRODENT'S - Listar Citas");
		setBounds(100, 100, 900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		createTopPanel();
		createCenterPanel();
		createBottomPanel();

		loadCitas();
	}

	private void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(MAIN_COLOR);
		topPanel.setPreferredSize(new Dimension(getWidth(), 80));
		add(topPanel, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("BRODENT'S - Lista de Citas");
		lblTitulo.setFont(TITLE_FONT);
		lblTitulo.setForeground(Color.WHITE);
		topPanel.add(lblTitulo);
	}

	private void createCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Cédula Paciente",
				"Nombre Paciente", "Apellido Paciente", "Servicio", "Horario", "Fecha" }));
		table.setRowHeight(25);
		table.getTableHeader().setFont(HEADER_FONT);
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

	private void loadCitas() {
		List<CitaDetallada> citas = null;
		try {
			citas = citaDAO.getAllCitasDetalladas();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar citas: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		if (citas != null) {
			for (CitaDetallada cita : citas) {
				model.addRow(new Object[] { cita.getId(), cita.getCedulaPaciente(), cita.getNombrePaciente(),
						cita.getApellidoPaciente(), cita.getNombreServicio(), cita.getHorario(), cita.getFecha() });
			}
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
