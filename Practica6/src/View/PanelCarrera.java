package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.Globo;
import Model.Techo;

class PanelCarrera extends JPanel {
    private final List<Globo> globos; // Lista de globos
    private final List<Globo> llegadas; // Lista para registrar el orden de llegada
    private boolean carreraTerminada = false; // Control de la carrera
    private BufferedImage buffer; // Imagen en memoria para el doble buffer
    private Techo techo; // El techo al que los globos deben llegar

    public PanelCarrera() {
        globos = new ArrayList<>();
        llegadas = new ArrayList<>();
        buffer = new BufferedImage(800, 450, BufferedImage.TYPE_INT_ARGB);
        techo = new Techo(); // Inicializamos el techo
        setLayout(null); // Layout nulo para colocar componentes manualmente
    }

    public void iniciarCarrera() {
        // Restablecemos las variables de la carrera
        globos.clear();
        llegadas.clear();
        carreraTerminada = false;

        // Crear varios globos con posiciones iniciales
        globos.add(new Globo(100, 400, "Rojo"));
        globos.add(new Globo(200, 400, "Azul"));
        globos.add(new Globo(300, 400, "Verde"));
        globos.add(new Globo(400, 400, "Naranja"));
        globos.add(new Globo(500, 400, "Morado"));

        // Iniciar cada globo como un hilo independiente
        for (Globo globo : globos) {
            globo.start();
        }

        // Crear un hilo que actualice la pantalla periódicamente
        new Thread(() -> {
            while (!carreraTerminada) {
                repaint(); // Redibujar el panel
                verificarLlegadas(); // Verificar si algún globo llegó al techo
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
            g2d.drawImage(globo.getImagen(), globo.getX(), globo.getY(), globo.getAncho(), globo.getAlto(), this);
        }

        // Dibujar el podio si la carrera terminó
        if (carreraTerminada) {
            dibujarPodio(g2d);
        }

        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);

        g2d.dispose(); // Liberar los recursos del Graphics2D
    }

    private void verificarLlegadas() {
        for (Globo globo : globos) {
            if (techo.colisionaCon(globo) && !llegadas.contains(globo)) { // Si el globo llegó al techo y no está registrado
                llegadas.add(globo);
                if (llegadas.size() == globos.size()) { // Todos los globos llegaron
                    carreraTerminada = true;
                    detenerCarrera();
                }
            }
        }
    }

    private void detenerCarrera() {
        for (Globo globo : globos) {
            globo.detener();
        }
    }

    private void dibujarPodio(Graphics2D g2d) {
        // Verificar si hay suficientes llegadas para el podio
        if (llegadas.size() < 3) return;

        // Dibujar el podio
        int baseX = 300;
        int baseY = 300;

        // Tercer lugar
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(baseX, baseY, 60, 50);
        g2d.drawImage(llegadas.get(llegadas.size() - 3).getImagen(), baseX, baseY - 50, 60, 60, this);

        // Segundo lugar
        g2d.setColor(Color.GRAY);
        g2d.fillRect(baseX + 80, baseY - 20, 60, 70);
        g2d.drawImage(llegadas.get(llegadas.size() - 2).getImagen(), baseX + 80, baseY - 70, 60, 60, this);

        // Primer lugar
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(baseX + 160, baseY - 40, 60, 90);
        g2d.drawImage(llegadas.get(llegadas.size() - 1).getImagen(), baseX + 160, baseY - 90, 60, 60, this);
    }
}

