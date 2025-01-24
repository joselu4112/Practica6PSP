package Model;

import java.awt.*;

public class Techo {
    private int y;

    public Techo() {
        y = 0; // El techo está en la parte superior
    }

    public boolean colisionaCon(Globo globo) {
        return globo.getY() <= y;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, y, 800, 10); // Techo representado por una línea roja
    }
}