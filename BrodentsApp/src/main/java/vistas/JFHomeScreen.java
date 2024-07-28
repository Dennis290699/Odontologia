package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import decorators.ColorButtonDecorator;
import decorators.FontButtonDecorator;
import decorators.HoverEffectButtonDecorator;

public class JFHomeScreen extends JFrame {

    private JFrame frame;
    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color HOVER_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_HOVER_COLOR = new Color(0, 123, 255);
    private static final Font MAIN_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);
    private static final String LOGO_IMAGE_PATH = "/assets/icons/icon_logo.png";

    public JFHomeScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("BRODENT'S - Inicio");
        frame.setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(MAIN_COLOR);
        frame.add(topPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Añadir logo
        JLabel lblLogo = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(LOGO_IMAGE_PATH)).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        topPanel.add(lblLogo, gbc);

        // Añadir título
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblTitulo = new JLabel("BRODENT'S");
        lblTitulo.setFont(MAIN_FONT);
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo, gbc);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.add(createDecoratedButton("Pacientes", e -> openPacientesScreen()), gbc);
        gbc.gridy++;
        centerPanel.add(createDecoratedButton("Citas", e -> openCitasScreen()), gbc);
        gbc.gridy++;
        centerPanel.add(createDecoratedButton("Especialistas", e -> openMembersScreen()), gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        centerPanel.add(createDecoratedButton("Salir", e -> exitApplication()), gbc);
    }

    private JButton createDecoratedButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Apply decorators
        new ColorButtonDecorator(button, MAIN_COLOR, BUTTON_TEXT_COLOR);
        new FontButtonDecorator(button, BUTTON_FONT);
        new HoverEffectButtonDecorator(button, HOVER_COLOR, BUTTON_TEXT_HOVER_COLOR);

        button.addActionListener(actionListener);
        return button;
    }

    private void openPacientesScreen() {
        JFPacientesScreen pacientes = new JFPacientesScreen();
        pacientes.setVisible(true);
        frame.dispose();
    }

    private void openCitasScreen() {
        JFCitasScreen citas = new JFCitasScreen();
        citas.setVisible(true);
        frame.dispose();
    }

    private void openMembersScreen() {
        JFMembersScreen especialistas = new JFMembersScreen();
        especialistas.setVisible(true);
        frame.dispose();
    }

    private void exitApplication() {
        JFPrincipalScreen principal = new JFPrincipalScreen();
        principal.setVisible(true);
        frame.dispose();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
