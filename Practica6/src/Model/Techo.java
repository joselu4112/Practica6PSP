package Model;

import java.awt.*;
import javax.swing.*;

public class Techo {
    private int y; // Posición vertical del techo
    private Image imagen; // Variable para almacenar la imagen del techo


    public Techo() {
        y = 0; // El techo está en la parte superior
        String rutaImagen = "/Images/techo.png"; // Ruta relativa a la carpeta "resources"
        imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();

        if (imagen == null) {
            System.out.println("No se pudo cargar la imagen del techo: " + rutaImagen);
        }
    }

 
    public Techo(String rutaImagen) {
        y = 0; // El techo está en la parte superior
        imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();

        if (imagen == null) {
            System.out.println("No se pudo cargar la imagen del techo: " + rutaImagen);
        }
    }


    public boolean colisionaCon(Globo globo) {
        if (imagen != null) {
            return globo.getY() <= y + imagen.getHeight(null); // Ajustamos la altura del techo según la imagen
        }
        return false;
    }


    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, y, null); // Dibujamos la imagen en la posición (0, y)
        }
    }


    public int getAltura() {
        if (imagen != null) {
            return imagen.getHeight(null);
        }
        return 0;
    }
}