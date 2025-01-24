package animacion4_carrera;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class PanelDeCarrera extends JPanel {
    private final List<Bola> bolas; // Lista de bolas
    private boolean carreraTerminada = false; // Control de la carrera

    public PanelDeCarrera() {
        bolas = new ArrayList<>();
        iniciarBolas();
    }

    private void iniciarBolas() {
        // Crear varias bolas con posiciones y colores diferentes
        bolas.add(new Bola(50, 50, 30, Color.RED, this));
        bolas.add(new Bola(50, 150, 30, Color.BLUE, this));
        bolas.add(new Bola(50, 250, 30, Color.GREEN, this));

        // Iniciar cada bola como un hilo independiente
        for (Bola bola : bolas) {
            bola.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Dibujar todas las bolas
        for (Bola bola : bolas) {
            bola.dibujar(g);
            //System.out.print(".");
        }
    }



    public synchronized void notificarGanador(Bola ganadora) {
        if (!carreraTerminada) { // Asegurarse de que solo haya un ganador
            carreraTerminada = true;

            // Detener todas las bolas
            for (Bola bola : bolas) {
                bola.detener();
            }

            // Mostrar el ganador en un cuadro de diálogo
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                        "¡La bola de color " + obtenerNombreColor(ganadora.getColor()) + " ganó la carrera!",
                        "Ganador", JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }

    // Método para convertir el color en un nombre (opcional)
    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        return "Desconocido";
    }


}

