package animacion7_neko;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    private NekoThread nekoThread;  // Hilo que maneja la lógica de Neko

    public Principal() {
        setTitle("Neko JFrame");
        setSize(600, 400); // Tamaño del JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Inicializar el hilo y pasar el JFrame
        nekoThread = new NekoThread(this);
        setVisible(true);
        nekoThread.start(); // Iniciar el hilo
    }

    // Sobrescribir el método paint para que el JFrame sea responsable de dibujar
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Llamada al método paint de JFrame
        setBackground(Color.WHITE); // Fondo blanco

        // Obtener la imagen y la posición de Neko desde el hilo y dibujarla
        g.drawImage(nekoThread.getImgActual(), nekoThread.getPosX(), nekoThread.getPosY(), null);
        //nekoThread.drawNeko(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal()); // Crear e iniciar el JFrame
    }
}


