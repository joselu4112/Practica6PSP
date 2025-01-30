package Model;


import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Globo extends Thread {
    private int x, y, Xinicial;
    private boolean exploto = false;
    private static final int VELOCIDAD_Y = 1; // Velocidad de ascenso
    private static final int ANCHO = 60; 
    private static final int ALTO = 80;
    private Image imagen;

    private int VELOCIDAD_X = 1; // Velocidad de oscilacion
    private static final int OSCILACION_MAX=20;
    private String color;
    private boolean moviendoDerecha; // Dirección del movimiento oscilatorio
	public boolean detenido;
    
    public Globo(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.Xinicial=x;
        this.moviendoDerecha=InicioOscilacion();
        
        // Cargar la imagen correspondiente al color
        String rutaImagen = "../Images/globo" + color + ".png";
        this.imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
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
        public Image getImagen() {
        return imagen;
    }
    
    @Override
    public void run() {
        while (!exploto) {
            if (!detenido) {
            	// Movimiento vertical hacia arriba
            	y -= VELOCIDAD_Y;
        	}
            // Movimiento oscilatorio dentro de un área de 40 unidades
            if (moviendoDerecha) {
                x += VELOCIDAD_X; // Mover hacia la derecha
            } else {
                x -= VELOCIDAD_X; // Mover hacia la izquierda
            }

            // Cambiar dirección si el globo alcanza los límites de la oscilación
            if (x >= Xinicial + OSCILACION_MAX || x <= Xinicial - OSCILACION_MAX) {
                moviendoDerecha = !moviendoDerecha; // Cambiar dirección
            }

            // Límite de movimiento lateral (evitar que se salga de la ventana)
            if (x < 0) x = 0;
            if (x > 770) x = 770;
            

            try {
                Thread.sleep(10); // Control de FPS (simulado aquí)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void frenar() {
        // Frenar el globo (detener el movimiento vertical)
        this.detenido = true; // Marcar el globo como detenido
    }

    public void reanudar() {
        // Reanudar el movimiento del globo
        this.detenido = false; // Marcar el globo como en movimiento

        // Si el hilo no está en ejecución, reiniciamos el hilo
        if (!this.isAlive()) {
            this.start(); // Iniciar el hilo nuevamente si se ha detenido
        }
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }
    public void explotar() {
        this.exploto = true;
    }

    public boolean haExplotado() {
        return exploto;
    }
    
    public void detener() {
        this.exploto = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }

	public int getAncho() { return ANCHO;}

	public int getAlto() {return ALTO;}

	public Object getColor() {
		// TODO Auto-generated method stub
		return null;
	}




}