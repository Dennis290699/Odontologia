package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.CitaDAO;
import model.Cita;
import util.DatabaseConnection;

public class JFGestionarCitas {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtPacienteId, txtFechaHora, txtTipoServicioId, txtObservaciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				JFGestionarCitas window = new JFGestionarCitas();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JFGestionarCitas() {
		initialize();
		cargarCitas(); // Cargar citas al iniciar
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Gestionar Citas");
		frame.setLayout(new BorderLayout());

		// Panel superior con título
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 123, 255));
		topPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
		topPanel.setLayout(new GridBagLayout());
		frame.add(topPanel, BorderLayout.NORTH);
		JLabel lblTitulo = new JLabel("Gestionar Citas");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setForeground(Color.WHITE);
		topPanel.add(lblTitulo);

		// Panel central con tabla de citas
		model = new DefaultTableModel(
				new String[] { "ID", "Paciente ID", "Fecha y Hora", "Tipo Servicio ID", "Observaciones" }, 0);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);

		// Panel inferior con campos y botones
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(frame.getWidth(), 200));
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.setLayout(new GridBagLayout());
		frame.add(bottomPanel, BorderLayout.SOUTH);

		// Campos de entrada
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		txtPacienteId = new JTextField();
		txtPacienteId.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(new JLabel("Paciente ID:"), gbc);
		gbc.gridx = 1;
		bottomPanel.add(txtPacienteId, gbc);

		txtFechaHora = new JTextField();
		txtFechaHora.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		bottomPanel.add(new JLabel("Fecha y Hora (YYYY-MM-DD HH:MM:SS):"), gbc);
		gbc.gridx = 1;
		bottomPanel.add(txtFechaHora, gbc);

		txtTipoServicioId = new JTextField();
		txtTipoServicioId.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 2;
		bottomPanel.add(new JLabel("Tipo Servicio ID:"), gbc);
		gbc.gridx = 1;
		bottomPanel.add(txtTipoServicioId, gbc);

		txtObservaciones = new JTextField();
		txtObservaciones.setPreferredSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 3;
		bottomPanel.add(new JLabel("Observaciones:"), gbc);
		gbc.gridx = 1;
		bottomPanel.add(txtObservaciones, gbc);

		// Botones
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setPreferredSize(new Dimension(100, 30));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		bottomPanel.add(btnAgregar, gbc);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setPreferredSize(new Dimension(100, 30));
		gbc.gridx = 1;
		bottomPanel.add(btnActualizar, gbc);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setPreferredSize(new Dimension(100, 30));
		gbc.gridx = 2;
		bottomPanel.add(btnEliminar, gbc);

		// Acción de botones
		btnAgregar.addActionListener(e -> agregarCita());

		btnActualizar.addActionListener(e -> actualizarCita());

		btnEliminar.addActionListener(e -> eliminarCita());

		// Acción de selección en la tabla
		table.getSelectionModel().addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				int selectedRow = table.getSelectedRow();
				txtPacienteId.setText(model.getValueAt(selectedRow, 1).toString());
				txtFechaHora.setText(model.getValueAt(selectedRow, 2).toString());
				txtTipoServicioId.setText(model.getValueAt(selectedRow, 3).toString());
				txtObservaciones.setText(model.getValueAt(selectedRow, 4).toString());
			}
		});
	}

	private void cargarCitas() {
		try (Connection con = DatabaseConnection.getConnection()) {
			CitaDAO citaDAO = new CitaDAO(con);
			List<Cita> citas = citaDAO.getAllCitas();
			model.setRowCount(0); // Limpiar la tabla antes de cargar nuevas citas
			for (Cita cita : citas) {
				model.addRow(new Object[] { cita.getId(), cita.getPacienteId(), cita.getFechaHora(),
						cita.getTipoServicioId(), cita.getObservaciones() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Error al cargar citas.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void agregarCita() {
		try {
			// Obtener los datos de los campos de entrada
			int pacienteId = Integer.parseInt(txtPacienteId.getText().trim());
			LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim());
			int tipoServicioId = Integer.parseInt(txtTipoServicioId.getText().trim());
			String observaciones = txtObservaciones.getText().trim();

			// Crear una nueva instancia de Cita
			Cita nuevaCita = new Cita(0, pacienteId, fechaHora, tipoServicioId, observaciones);

			// Agregar la cita a la base de datos
			try (Connection con = DatabaseConnection.getConnection()) {
				CitaDAO citaDAO = new CitaDAO(con);
				citaDAO.addCita(nuevaCita);
				// Volver a cargar las citas en la tabla
				cargarCitas();
				limpiarCampos();
				JOptionPane.showMessageDialog(frame, "Cita agregada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error al agregar cita.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Ingrese datos válidos.", "Error de entrada",
					JOptionPane.WARNING_MESSAGE);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(frame, "Formato de fecha y hora inválido.", "Error de entrada",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void actualizarCita() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(frame, "Selecciona una cita para actualizar.", "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			// Obtener el ID de la cita seleccionada
			int selectedRow = table.getSelectedRow();
			int id = (int) model.getValueAt(selectedRow, 0);

			// Obtener los datos de los campos de entrada
			int pacienteId = Integer.parseInt(txtPacienteId.getText().trim());
			LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim());
			int tipoServicioId = Integer.parseInt(txtTipoServicioId.getText().trim());
			String observaciones = txtObservaciones.getText().trim();

			// Crear una instancia de Cita con los datos actualizados
			Cita citaActualizada = new Cita(id, pacienteId, fechaHora, tipoServicioId, observaciones);

			// Actualizar la cita en la base de datos
			try (Connection con = DatabaseConnection.getConnection()) {
				CitaDAO citaDAO = new CitaDAO(con);
				citaDAO.updateCita(citaActualizada);
				// Volver a cargar las citas en la tabla
				cargarCitas();
				limpiarCampos();
				JOptionPane.showMessageDialog(frame, "Cita actualizada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error al actualizar cita.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Ingrese datos válidos.", "Error de entrada",
					JOptionPane.WARNING_MESSAGE);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(frame, "Formato de fecha y hora inválido.", "Error de entrada",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void limpiarCampos() {
		txtPacienteId.setText("");
		txtFechaHora.setText("");
		txtTipoServicioId.setText("");
		txtObservaciones.setText("");
	}

	private void eliminarCita() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(frame, "Selecciona una cita para eliminar.", "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int selectedRow = table.getSelectedRow();
		int id = (int) model.getValueAt(selectedRow, 0);

		int confirm = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que deseas eliminar esta cita?",
				"Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try (Connection con = DatabaseConnection.getConnection()) {
				CitaDAO citaDAO = new CitaDAO(con);
				citaDAO.deleteCita(id);
				model.removeRow(selectedRow);
				limpiarCampos();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error al eliminar cita.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
