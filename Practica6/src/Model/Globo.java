package Model;


import java.awt.*;
import java.util.Random;

public class Globo extends Thread {
    private int x, y, velocidadX;
    private boolean exploto = false;
    private static final int VELOCIDAD_Y = 2; // Velocidad de ascenso
    private static final int TAMANO = 30; // Tamaño del globo
    private Color color;

    public Globo(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.velocidadX = new Random().nextInt(3) - 1; // Movimiento lateral aleatorio
    }

    @Override
    public void run() {
        while (!exploto) {
            // Movimiento vertical hacia arriba
            y -= VELOCIDAD_Y;
            x += velocidadX; // Oscilación lateral

            // Límite de movimiento lateral (evitar que se salga de la ventana)
            if (x < 0) x = 0;
            if (x > 770) x = 770;

            // Simulación de "gravedad" (limitación en el movimiento)
            try {
                Thread.sleep(10); // Control de FPS (simulado aquí)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void explotar() {
        this.exploto = true;
    }

    public boolean haExplotado() {
        return exploto;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return TAMANO; }
    public Color getColor() { return color; }

    public void detener() {
        this.exploto = true;
    }
}