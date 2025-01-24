package View;

import javax.swing.*;

import Model.Globo;
import Model.Techo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class PanelCarrera extends JPanel {
    private final List<Globo> globos; // Lista de globos
    private boolean carreraTerminada = false; // Control de la carrera
    private BufferedImage buffer; // Imagen en memoria para el doble buffer
    private Techo techo; // El techo al que los globos deben llegar

    public PanelCarrera() {
        globos = new ArrayList<>();
        buffer = new BufferedImage(800, 450, BufferedImage.TYPE_INT_ARGB);
        techo = new Techo(); // Inicializamos el techo
        iniciarGlobos();
    }

    private void iniciarGlobos() {
        // Crear varios globos con posiciones iniciales
        globos.add(new Globo(100, 400, Color.RED));
        globos.add(new Globo(200, 400, Color.BLUE));
        globos.add(new Globo(300, 400, Color.GREEN));
        globos.add(new Globo(400, 400, Color.YELLOW));

        // Iniciar cada globo como un hilo independiente
        for (Globo globo : globos) {
            globo.start();
        }

        // Crear un hilo que actualice la pantalla periódicamente
        new Thread(() -> {
            while (!carreraTerminada) {
                repaint(); // Redibujar el panel
                verificarGanador(); // Verificar si algún globo llegó al techo
                try {
                    Thread.sleep(1); // Actualización periódica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Asegurarse de que el buffer está creado
        if (buffer == null) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        // Dibujar en el buffer
        Graphics2D g2d = buffer.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight()); // Limpiar el fondo

        // Suavizamos los bordes (opcional)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el techo
        techo.dibujar(g2d);

        // Dibujar todos los globos en el buffer
        for (Globo globo : globos) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval(globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño());
            g2d.setColor(globo.getColor());
            g2d.fillOval(globo.getX() + 3, globo.getY() + 3, globo.getTamaño() - 7, globo.getTamaño() - 7);
        }

        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose(); // Liberar los recursos del Graphics2D
    }

    private void verificarGanador() {
        for (Globo globo : globos) {
            if (techo.colisionaCon(globo)) { // Verificamos si el globo ha llegado al techo
                carreraTerminada = true;
                detenerCarrera();
                notificarGanador(globo);
                break;
            }
        }
    }

    private void detenerCarrera() {
        for (Globo globo : globos) {
            globo.detener();
        }
    }

    private void notificarGanador(Globo ganador) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                    "¡El globo de color " + obtenerNombreColor(ganador.getColor()) + " ganó la carrera!",
                    "Ganador", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.YELLOW)) return "Amarillo";
        return "Desconocido";
    }
}