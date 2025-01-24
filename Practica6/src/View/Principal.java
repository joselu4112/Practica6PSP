package View;

import javax.swing.*;

public class Principal extends JFrame {

    public Principal() {
        setTitle("Carrera de Globos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel personalizado
        PanelCarrera panel = new PanelCarrera();
        add(panel); // Agregar el panel al JFrame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal frame = new Principal();
            frame.setVisible(true);
        });
    }
}