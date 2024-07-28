package vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFPrincipal {

	private JFrame frame;
	private static final String BACKGROUND_IMAGE_PATH = "/assets/background/wallpaper1.jpg";
	private static final String LOGO_IMAGE_PATH = "/assets/icons/icon_logo.png";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFPrincipal window = new JFPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFPrincipal() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JLabel background = createBackgroundLabel();
		frame.setContentPane(background);
		background.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 0, 20, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		background.add(createLogoLabel(), gbc);

		gbc.gridy++;
		background.add(createTitleLabel(), gbc);

		gbc.gridy++;
		background.add(createWelcomeButton(), gbc);
	}

	private JLabel createBackgroundLabel() {
		ImageIcon backgroundImageIcon = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE_PATH));
		Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		return new JLabel(new ImageIcon(backgroundImage));
	}

	private JLabel createLogoLabel() {
		ImageIcon logoImageIcon = new ImageIcon(getClass().getResource(LOGO_IMAGE_PATH));
		Image logoImage = logoImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		return new JLabel(new ImageIcon(logoImage));
	}

	private JLabel createTitleLabel() {
		JLabel lblNombre = new JLabel("BRODENT'S");
		lblNombre.setFont(new Font("Arial", Font.BOLD, 36));
		lblNombre.setForeground(Color.WHITE);
		return lblNombre;
	}

	private JButton createWelcomeButton() {
		JButton btnBienvenido = new JButton("Bienvenido");
		btnBienvenido.setFont(new Font("Arial", Font.BOLD, 24));
		btnBienvenido.setForeground(Color.WHITE);
		btnBienvenido.setBackground(new Color(0, 123, 255));
		btnBienvenido.setOpaque(true);
		btnBienvenido.setBorderPainted(false);
		btnBienvenido.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnBienvenido.setFocusPainted(false);
		btnBienvenido.setContentAreaFilled(false);
		btnBienvenido.setOpaque(true);
		btnBienvenido.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true));
		btnBienvenido.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnBienvenido.setBackground(new Color(255, 255, 255));
				btnBienvenido.setForeground(new Color(0, 123, 255));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnBienvenido.setBackground(new Color(0, 123, 255));
				btnBienvenido.setForeground(Color.WHITE);
			}
		});
		btnBienvenido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFHomeScreen homeScreen = new JFHomeScreen();
				homeScreen.setVisible(true);
				frame.dispose();
			}
		});
		return btnBienvenido;
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
