package animacion6_doblebuffer;

import java.awt.*;

public class Bola extends Thread {
    private int x; // Posición X de la bola
    private final int y; // Posición Y de la bola
    private final int tamaño; // Tamaño de la bola
    private final Color color; // Color de la bola
    private boolean corriendo = true; // Control del movimiento

    public Bola(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    @Override
    public void run() {
        while (corriendo && x < 800) { // Se mueve mientras no alcance el borde derecho
            x += 3; // Mover hacia la derecha
            try {
                Thread.sleep((int) (Math.random()*25+25)); // Pausa para simular el movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        corriendo = false; // Detener la bola al llegar al borde
    }

    public void detener() {
        corriendo = false;
    }

    // Métodos para obtener los datos de la bola
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamaño() {
        return tamaño;
    }

    public Color getColor() {
        return color;
    }
}

