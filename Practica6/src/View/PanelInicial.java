package View;

import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PanelInicial extends JFrame {

    private JPanel panelInicio; // Panel de bienvenida
    private PanelCarrera panelCarrera; // Panel para la carrera

    public PanelInicial() {
        setTitle("Carrera de Globos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new CardLayout()); // Usar CardLayout para intercambiar paneles

        // Crear el panel de inicio
        crearPanelInicio();

        // Crear el panel de la carrera
        panelCarrera = new PanelCarrera();

        // Agregar ambos paneles al JFrame con nombres únicos
        add(panelInicio, "Inicio");
        add(panelCarrera, "Carrera");
    }

    private void crearPanelInicio() {
        panelInicio = new JPanel();
        panelInicio.setLayout(null);

        // Título o mensaje de bienvenida
        JLabel lblTitulo = new JLabel("¡Bienvenido a la Carrera de Globos!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(200, 100, 400, 40);
        panelInicio.add(lblTitulo);

        // Botón para iniciar la carrera
        JButton btnIniciar = new JButton("Iniciar Carrera");
        btnIniciar.setBounds(350, 200, 120, 30);
        btnIniciar.addActionListener(e -> {
            // Cambiar al panel de la carrera
            CardLayout cl = (CardLayout) getContentPane().getLayout();
            cl.show(getContentPane(), "Carrera");

            // Iniciar la carrera
            panelCarrera.iniciarCarrera();
        });
        panelInicio.add(btnIniciar);
    }

   
}