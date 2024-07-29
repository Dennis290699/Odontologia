package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import dao.CitaDAO;
import model.Cita;
import util.DatabaseConnection;

public class JFEliminarCitaSreen extends JFrame {

	private JComboBox<Cita> cmbCitas;
	private CitaDAO citaDAO;
	private static final Color MAIN_COLOR = new Color(0, 123, 255);
	private static final Color HOVER_COLOR = Color.WHITE;
	private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
	private static final Color BUTTON_TEXT_HOVER_COLOR = new Color(0, 123, 255);
	private static final Font MAIN_FONT = new Font("Arial", Font.BOLD, 18);
	private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);

	public JFEliminarCitaSreen() {
		Connection connection = DatabaseConnection.getConnection();
		citaDAO = new CitaDAO(connection);
		initialize();
	}

	private void initialize() {
		setTitle("Eliminar Cita");
		setBounds(100, 100, 600, 400); // Aumentar el tamaño de la pantalla
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		// Panel superior
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(MAIN_COLOR);
		add(topPanel, BorderLayout.NORTH);

		GridBagConstraints gbcTop = new GridBagConstraints();
		gbcTop.insets = new Insets(10, 10, 10, 10);
		gbcTop.gridx = 0;
		gbcTop.gridy = 0;
		gbcTop.anchor = GridBagConstraints.CENTER;

		JLabel lblTitulo = new JLabel("Eliminar Cita");
		lblTitulo.setFont(TITLE_FONT);
		lblTitulo.setForeground(Color.WHITE);
		topPanel.add(lblTitulo, gbcTop);

		// Panel central
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setBackground(Color.WHITE);
		add(centerPanel, BorderLayout.CENTER);

		GridBagConstraints gbcCenter = new GridBagConstraints();
		gbcCenter.insets = new Insets(10, 10, 10, 10);
		gbcCenter.fill = GridBagConstraints.HORIZONTAL;

		gbcCenter.gridx = 0;
		gbcCenter.gridy = 0;
		JLabel lblCita = new JLabel("Seleccionar Cita:");
		lblCita.setFont(MAIN_FONT);
		centerPanel.add(lblCita, gbcCenter);

		gbcCenter.gridx = 1;
		cmbCitas = new JComboBox<>();
		cmbCitas.setPreferredSize(new Dimension(300, 30));
		centerPanel.add(cmbCitas, gbcCenter);

		loadCitas();

		// Panel inferior
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centrar los botones
		bottomPanel.setBackground(Color.WHITE);
		add(bottomPanel, BorderLayout.SOUTH);

		JButton btnEliminar = createButton("Eliminar Cita", e -> eliminarCita(), Color.RED); // Botón rojo
		bottomPanel.add(btnEliminar);

		JButton btnRegresar = createButton("Regresar", e -> regresar(), MAIN_COLOR); // Botón con color principal
		bottomPanel.add(btnRegresar);
	}

	private JButton createButton(String text, ActionListener actionListener, Color backgroundColor) {
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(150, 40));
		button.setFont(MAIN_FONT);
		button.setForeground(BUTTON_TEXT_COLOR);
		button.setBackground(backgroundColor);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(HOVER_COLOR);
				button.setForeground(BUTTON_TEXT_HOVER_COLOR);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(backgroundColor);
				button.setForeground(BUTTON_TEXT_COLOR);
			}
		});
		button.addActionListener(actionListener);
		return button;
	}

	private void loadCitas() {
		try {
			List<Cita> citas = citaDAO.getAllCitas();
			for (Cita cita : citas) {
				cmbCitas.addItem(cita);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void eliminarCita() {
		Cita cita = (Cita) cmbCitas.getSelectedItem();
		if (cita == null) {
			JOptionPane.showMessageDialog(this, "Seleccione una cita válida", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			if (citaDAO.deleteCita(cita.getId())) {
				JOptionPane.showMessageDialog(this, "Cita eliminada exitosamente", "Eliminación Exitosa",
						JOptionPane.INFORMATION_MESSAGE);
				cmbCitas.removeItem(cita);
			} else {
				JOptionPane.showMessageDialog(this, "No se pudo eliminar la cita", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al eliminar la cita", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void regresar() {
		JFCitasScreen citasScreen = new JFCitasScreen();
		citasScreen.setVisible(true);
		dispose();
	}
}
