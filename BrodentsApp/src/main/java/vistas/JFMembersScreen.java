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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFMembersScreen {

    private JFrame frame;
    private static final Color MAIN_COLOR = new Color(0, 123, 255);
    private static final Color HOVER_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_HOVER_COLOR = new Color(0, 123, 255);
    private static final Font MAIN_FONT = new Font("Arial", Font.BOLD, 24);
    private static final String LOGO_IMAGE_PATH = "/assets/icons/icon_logo.png";

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    JFMembersScreen window = new JFMembersScreen();
//                    window.frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public JFMembersScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("BRODENT'S - Miembros");
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

        List<Miembro> miembros = getMiembros();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        int columnas = 3;

        for (int i = 0; i < miembros.size(); i++) {
            Miembro miembro = miembros.get(i);
            JPanel perfilPanel = createPerfilPanel(miembro);
            centerPanel.add(perfilPanel, gbc);

            gbc.gridx++;
            if (gbc.gridx >= columnas) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        centerPanel.add(createButton("Volver", e -> volverAlMenu()), gbc);
    }

    private JPanel createPerfilPanel(Miembro miembro) {
        JPanel perfilPanel = new JPanel();
        perfilPanel.setLayout(new BorderLayout());
        perfilPanel.setPreferredSize(new Dimension(150, 200));
        perfilPanel.setBackground(Color.WHITE);
//        perfilPanel.setBorder(BorderFactory.createLineBorder(MAIN_COLOR, 2));

        JLabel lblImagen = new JLabel(createRoundImageIcon(miembro.getImagePath(), 100));
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        perfilPanel.add(lblImagen, BorderLayout.CENTER);

        JLabel lblNombre = new JLabel(miembro.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setHorizontalAlignment(JLabel.CENTER);
        perfilPanel.add(lblNombre, BorderLayout.SOUTH);

        return perfilPanel;
    }

    private ImageIcon createRoundImageIcon(String path, int diameter) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource(path));
            BufferedImage roundedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2 = roundedImage.createGraphics();
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
            g2.drawImage(originalImage, 0, 0, diameter, diameter, null);
            g2.dispose();
            return new ImageIcon(roundedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Miembro> getMiembros() {
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(new Miembro("B4LB3R1TH", "/assets/members/2.png"));
        miembros.add(new Miembro("Miembro 2", "/assets/members/2.png"));
        miembros.add(new Miembro("Miembro 3", "/assets/members/2.png"));
        miembros.add(new Miembro("Miembro 4", "/assets/members/2.png"));
        miembros.add(new Miembro("Miembro 5", "/assets/members/2.png"));
        miembros.add(new Miembro("Miembro 6", "/assets/members/2.png"));
        return miembros;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(MAIN_COLOR);
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
                button.setBackground(MAIN_COLOR);
                button.setForeground(BUTTON_TEXT_COLOR);
            }
        });
        button.addActionListener(actionListener);
        return button;
    }

    private void volverAlMenu() {
        JFHomeScreen homeScreen = new JFHomeScreen();
        homeScreen.setVisible(true);
        frame.dispose();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}

class Miembro {
    private String nombre;
    private String imagePath;

    public Miembro(String nombre, String imagePath) {
        this.nombre = nombre;
        this.imagePath = imagePath;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagePath() {
        return imagePath;
    }
}
