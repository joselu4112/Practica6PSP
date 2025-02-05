package Model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Globo extends Thread {
    private int x, y, Xinicial; // Posición inicial y actual del globo
    private boolean exploto = false; // Indica si el globo ha explotado
    private static final int VELOCIDAD_Y = 1; // Velocidad vertical hacia arriba
    private static final int ANCHO = 100; // Ancho del globo
    private static final int ALTO = 100; // Alto del globo
    public Image imagen; // Imagen del globo

    private int VELOCIDAD_X = 1; // Velocidad de oscilación lateral
    private static final int OSCILACION_MAX = 20; // Rango máximo de oscilación lateral
    private String color; // Color del globo
    private boolean moviendoDerecha; // Dirección del movimiento oscilatorio
    private boolean detenido = false; // Indica si el globo está detenido

 
    public Globo(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.Xinicial = x;
        this.moviendoDerecha = InicioOscilacion();

        // Cargar la imagen correspondiente al color
        String rutaImagen = "/Images/globo" + color + ".png"; // Asegúrate de que esta ruta sea correcta
        this.imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
    }

    
    private boolean InicioOscilacion() {
        Random random = new Random();
        return random.nextBoolean(); // 50% de probabilidad de moverse a la derecha o izquierda
    }

    
    @Override
    public void run() {
        while (!exploto) {
            if (!detenido) {
                // Movimiento vertical hacia arriba
                y -= VELOCIDAD_Y;
            }

            // Movimiento oscilatorio dentro de un área de OSCILACION_MAX unidades
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
                Thread.sleep(16); // Control de FPS (aproximadamente 60 FPS)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

  
    public void frenar() {
        this.detenido = true; // Marcar el globo como detenido
    }


    public void reanudar() {
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
        this.interrupt(); // Interrumpir el hilo
    }

  
    public int getX() {
        return x;
    }

   
    public int getY() {
        return y;
    }

  
    public int getAncho() {
        return ANCHO;
    }

  
    public int getAlto() {
        return ALTO;
    }

  
    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

  
    public Image getImagen() {
        return imagen;
    }

   
    public String getColor() {
        return color;
    }

  
    public boolean isDetenido() {
        return detenido;
    }
}