package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFHomeScreen {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFHomeScreen window = new JFHomeScreen();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public JFHomeScreen() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Deshabilitar la opción de maximizar la ventana
        frame.setTitle("HeladosUCE - Home");
        frame.setLayout(new BorderLayout());

        // Panel superior con título
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255));
        frame.add(topPanel, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("Bienvenido a HeladosUCE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo);

        // Panel central con botones de opciones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón "Hacer un Pedido"
        JButton btnHacerPedido = crearBoton("Hacer un Pedido");
        centerPanel.add(btnHacerPedido, gbc);

        // Botón "Catálogo de Productos"
        gbc.gridy++;
        JButton btnCatalogo = crearBoton("Catálogo de Productos");
        centerPanel.add(btnCatalogo, gbc);

        // Botón "Ver Carrito de Compras"
        gbc.gridy++;
        JButton btnCarrito = crearBoton("Ver Carrito de Compras");
        centerPanel.add(btnCarrito, gbc);

        // Botón "Ofertas y Promociones"
        gbc.gridy++;
        JButton btnOfertas = crearBoton("Ofertas y Promociones");
        centerPanel.add(btnOfertas, gbc);

        // Botón "Contactar con Nosotros"
        gbc.gridy++;
        JButton btnContacto = crearBoton("Contactar con Nosotros");
        centerPanel.add(btnContacto, gbc);

        // Botón "Sobre Nosotros"
        gbc.gridy++;
        JButton btnSobreNosotros = crearBoton("Sobre Nosotros");
        centerPanel.add(btnSobreNosotros, gbc);

        // Botón "Salir"
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        JButton btnSalir = crearBoton("Salir");
        centerPanel.add(btnSalir, gbc);

        // Acción del botón "Salir"
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana principal y cerrar la ventana actual
                JFPrincipal principal = new JFPrincipal();
                principal.setVisible(true);
                frame.dispose();
            }
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(200, 40));
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0, 123, 255));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(255, 255, 255));
                boton.setForeground(new Color(0, 123, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(0, 123, 255));
                boton.setForeground(Color.WHITE);
            }
        });
        return boton;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
