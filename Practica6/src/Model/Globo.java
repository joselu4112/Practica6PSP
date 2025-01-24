package Model;


import java.awt.*;
import java.util.Random;

public class Globo extends Thread {
    private int x, y, Xinicial;
    private boolean exploto = false;
    private static final int VELOCIDAD_Y = 2; // Velocidad de ascenso
    private int VELOCIDAD_X = 1; // Velocidad de oscilacion
    private static final int OSCILACION_MAX=20;
    private Color color;
    private boolean moviendoDerecha; // Dirección del movimiento oscilatorio
    
    public Globo(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.Xinicial=x;
        this.moviendoDerecha=InicioOscilacion();
    }
    
    public boolean InicioOscilacion() {
    	//Metodo para generar el movimiento oscilatorio inicial de forma aleatoria:
    	Random random=new Random();
    	int valor=random.nextInt();
    	if(valor<0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    @Override
    public void run() {
        while (!exploto) {
            // Movimiento vertical hacia arriba
            y -= VELOCIDAD_Y;
            
            // Movimiento oscilatorio dentro de un área de 40 unidades
            if (moviendoDerecha) {
                x += VELOCIDAD_X; // Mover hacia la derecha
            } else {
                x -= VELOCIDAD_X; // Mover hacia la izquierda
            }

            // Cambiar dirección si el globo alcanza los límites de la oscilación
            if (x >= Xinicial + 20 || x <= Xinicial - 20) {
                moviendoDerecha = !moviendoDerecha; // Cambiar dirección
            }
            
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
    public Color getColor() { return color; }

    public void detener() {
        this.exploto = true;
    }
}