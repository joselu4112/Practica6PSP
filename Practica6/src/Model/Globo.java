package Model;


import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Globo extends Thread {
    private int x, y, velocidadX;
    private boolean exploto = false;
    private static final int VELOCIDAD_Y = 2; // Velocidad de ascenso
    private static final int ANCHO = 60; 
    private static final int ALTO = 80;
    private Image imagen;

    public Globo(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.velocidadX = new Random().nextInt(3) - 1; // Movimiento lateral aleatorio

        // Cargar la imagen correspondiente al color
        String rutaImagen = "../Images/globo" + color + ".png";
        this.imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
    }

    public Image getImagen() {
        return imagen;
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