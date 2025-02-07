package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Model.Globo;
import Model.Techo;

class PanelCarrera extends JPanel {
    private final List<Globo> globos; // Lista de globos
    private final List<Globo> llegadas; // Lista para registrar el orden de llegada
    private boolean carreraTerminada = false; // Control de la carrera
    private BufferedImage buffer; // Imagen en memoria para el doble buffer
    private Techo techo; // El techo al que los globos deben llegar
    private Image fondo; // Fondo del panel
    private Image techoImagen; // Imagen del techo

    public PanelCarrera() {
        globos = new ArrayList<>();
        llegadas = new ArrayList<>();
        buffer = new BufferedImage(800, 450, BufferedImage.TYPE_INT_ARGB);
        techo = new Techo(); // Inicializamos el techo

        // Cargar la imagen de fondo
        String rutaFondo = "/Images/fondo.jpg"; 
        fondo = new ImageIcon(getClass().getResource(rutaFondo)).getImage();
        if (fondo == null) {
            System.out.println("No se pudo cargar la imagen de fondo: " + rutaFondo);
        }

        // Cargar la imagen del techo
        String rutaTecho = "/Images/techo.png"; 
        techoImagen = new ImageIcon(getClass().getResource(rutaTecho)).getImage();
        if (techoImagen == null) {
            System.out.println("No se pudo cargar la imagen del techo: " + rutaTecho);
        }

        iniciarCarrera();

        // Añadir MouseListener para los eventos del ratón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Globo globo : globos) {
                    if (globo.getBounds().contains(e.getPoint())) {
                        globo.frenar(); // Frenamos el globo mientras se presiona el ratón
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (Globo globo : globos) {
                    if (globo.isDetenido()) {
                        globo.reanudar(); // Reanudar el globo
                    }
                }
            }
        });
    }

    public void iniciarCarrera() {
        globos.clear();
        llegadas.clear();
        carreraTerminada = false;
        
        globos.add(new Globo(100, 400, "Rojo"));
        globos.add(new Globo(200, 400, "Azul"));
        globos.add(new Globo(300, 400, "Verde"));
        globos.add(new Globo(400, 400, "Naranja"));
        globos.add(new Globo(500, 400, "Amarillo"));

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
                    Thread.sleep(16); // Actualización periódica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void iniciarAnimacion(Globo globo) {
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String rutaImagen = "/Images/globo" + globo.getColor() + i + ".png"; // Ruta relativa
                    Image nuevaImagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
                    if (nuevaImagen != null) {
                        globo.setImagen(nuevaImagen); // Cambiar la imagen del globo
                        repaint(); // Redibujar el panel
                        Thread.sleep(100); // Tiempo entre cada frame de la animación
                    } else {
                        System.out.println("No se pudo cargar la imagen: " + rutaImagen);
                    }
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Asegurarse de que el buffer está creado
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        // Dibujar en el buffer
        Graphics2D g2d = buffer.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight()); // Limpiar el fondo

        // Dibujar el fondo si está cargado
        if (fondo != null) {
            g2d.drawImage(fondo, 0, 0, getWidth(), getHeight(), this); // Escalar la imagen al tamaño del panel
        }

        // Suavizar los bordes de las figuras
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el techo como una imagen
        if (techoImagen != null) {
            g2d.drawImage(techoImagen, 0, 0, getWidth(), 50, this); 
        }

        // Dibujar todos los globos en el buffer
        for (Globo globo : globos) {
            g2d.drawImage(globo.getImagen(), globo.getX(), globo.getY(), globo.getAncho(), globo.getAlto(), this);
        }

        // Dibujar el podio si la carrera termina
        if (carreraTerminada) {
            dibujarPodio(g2d);
        }

        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, null);
        g2d.dispose(); // Liberar los recursos
    }

    private void verificarLlegadas() {
        for (Globo globo : globos) {
            if (techo.colisionaCon(globo) && !llegadas.contains(globo)) { // Si el globo llegó al techo y no está registrado
                llegadas.add(globo);
                iniciarAnimacion(globo); 
                if (llegadas.size() == globos.size()) { // Todos los globos llegaron
                    carreraTerminada = true;
                    detenerCarrera();
                }
            }
        }
    }

    private void detenerCarrera() {
        for (Globo globo : globos) {
            globo.detener(); // Detener todos los hilos de los globos
        }
    }

    private void dibujarPodio(Graphics2D g2d) {
        int baseX = 300;
        int baseY = 300;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        // Dibujar fondo del podio
        g2d.setColor(new Color(250, 250, 250)); 
        g2d.fillRect(baseX - 40, baseY - 100, 300, 160);

        // Tercer lugar
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(baseX, baseY, 60, 50);
        String rutaTercero = "/Images/globo" + llegadas.get(llegadas.size() - 3).getColor() + ".png";
        Image tercerlugar = new ImageIcon(getClass().getResource(rutaTercero)).getImage();
        g2d.drawImage(tercerlugar, baseX, baseY - 50, 60, 60, this);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("3", baseX + 25, baseY + 30);

        // Segundo lugar
        g2d.setColor(Color.GRAY);
        g2d.fillRect(baseX + 80, baseY - 20, 60, 70);
        String rutaSegundo = "/Images/globo" + llegadas.get(llegadas.size() - 2).getColor() + ".png";
        Image segundolugar = new ImageIcon(getClass().getResource(rutaSegundo)).getImage();
        g2d.drawImage(segundolugar, baseX + 80, baseY - 70, 60, 60, this);
        g2d.setColor(Color.BLACK);
        g2d.drawString("2", baseX + 105, baseY + 10);

        // Primer lugar
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(baseX + 160, baseY - 40, 60, 90);
        String rutaPrimero = "/Images/globo" + llegadas.get(llegadas.size() - 1).getColor() + ".png";
        Image primerlugar = new ImageIcon(getClass().getResource(rutaPrimero)).getImage();
        g2d.drawImage(primerlugar, baseX + 160, baseY - 90, 60, 60, this);
        g2d.setColor(Color.BLACK);
        g2d.drawString("1", baseX + 185, baseY - 10);
    }


}