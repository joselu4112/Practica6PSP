package animacion7_neko;

import javax.swing.*;
import java.awt.*;

public class NekoThread extends Thread {
    private Image[] neko_img = new Image[3]; // Array de imágenes
    private Image imgActual;                 // Imagen actual de Neko
    private int pos_x, pos_y;                // Posición de Neko
    private JFrame frame;                    // Referencia al JFrame para repintar

    public NekoThread(JFrame frame) {
        this.frame = frame;
        cargarImagenes();
        pos_x = frame.getWidth() / 2; // Posición inicial (centro del JFrame)
        pos_y = frame.getHeight() / 2;
    }

    // Cargar las imágenes de los gifs
    private void cargarImagenes() {
        String[] neko_gif = {"src/animacion7_neko/Recursos/pararse.gif",
                "src/animacion7_neko/Recursos/correr1.gif",
                "src/animacion7_neko/Recursos/correr2.gif"};

        for (int i = 0; i < neko_img.length; i++) {
            neko_img[i] = Toolkit.getDefaultToolkit().getImage(neko_gif[i]);
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            // Neko se para
            imgActual = neko_img[0];
            frame.repaint();  // Solicita repintar el JFrame
            pausa(1000);       // Neko está parado por medio segundo
            imgActual = neko_img[1];

            // Luego empieza a correr
            nekoCorre();
        }
    }

    private void nekoCorre() {
        for (int i = 0; i < 18; i++) {
            // Control de posición (se mueve de izquierda a derecha)
            if (pos_x + 10 > frame.getWidth()) {
                pos_x = 0; // Si llega al final de la pantalla, vuelve al principio
            } else {
                pos_x += 10; // Mueve a la derecha
            }

            // Alterna entre las imágenes de correr
            imgActual = (imgActual == neko_img[1]) ? neko_img[2] : neko_img[1];

            // Redibuja el JFrame
            frame.repaint();
            pausa(500); // MS de espera entre cada actualización de imagen.
        }
    }

    // Pausa en el hilo (simula la espera entre cada actualización)
    private void pausa(int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            interrupt(); // Detiene el hilo si se interrumpe
        }
    }

    // Método para obtener la imagen y la posición de Neko para el JFrame
    public Image getImgActual() {
        return imgActual;
    }

    public int getPosX() {
        return pos_x;
    }

    public int getPosY() {
        return pos_y;
    }
}
