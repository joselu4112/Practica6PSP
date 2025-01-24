package animacion4_carrera;

import java.awt.Color;
import java.awt.Graphics;

public class Bola extends Thread {
    private int x; // Posición X de la bola
    private int y; // Posición Y de la bola
    private final int tamaño; // Tamaño de la bola
    private final Color color; // Color de la bola
    private final PanelDeCarrera panel; // Referencia al panel para redibujar
    private boolean corriendo = true; // Control del movimiento

    public Bola(int x, int y, int tamaño, Color color, PanelDeCarrera panel) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
        this.panel = panel;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void run() {
        while (corriendo && x < 750) { // La bola se detendrá al llegar al borde derecho
            x += 5; // Mover la bola hacia la izquierda
            panel.repaint(); // Redibujar el panel
            if (x >= 750) { // Comprobar si llegó a la meta
                corriendo = false; // Detener la bola
                panel.notificarGanador(this); // Notificar al panel
            }
            try {
                Thread.sleep((int) (Math.random()*150+15)); // Pausa para simular el movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void detener() {
        corriendo = false;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, tamaño, tamaño);
    }
}

