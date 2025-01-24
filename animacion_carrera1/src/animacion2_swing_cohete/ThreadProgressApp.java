package animacion2_swing_cohete;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThreadProgressApp extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private Rocket[] rockets; // Array para almacenar los cohetes

    public ThreadProgressApp() {
        setTitle("Lanzamiento de Cohetes");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración de la tabla
        String[] columnNames = {"Cohete", "Altura (m)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Botón para iniciar los cohetes
        JButton startButton = new JButton("Lanzar Cohetes");
        startButton.addActionListener(e -> startRockets());
        add(startButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startRockets() {
        int numRockets = 5; // Número de cohetes
        rockets = new Rocket[numRockets];

        // Inicializa los cohetes y añade a la tabla
        tableModel.setRowCount(0); // Limpia la tabla
        for (int i = 0; i < numRockets; i++) {
            rockets[i] = new Rocket("Cohete-" + (i + 1));
            tableModel.addRow(new Object[]{rockets[i].getName(), 0});
            new Thread(rockets[i]).start(); // Crea e inicia el hilo directamente
        }

        // Bucle para actualizar la tabla con la altura de los cohetes
        new Timer(100, e -> updateTable()).start();
    }

    private void updateTable() {
        for (int i = 0; i < rockets.length; i++) {
            Rocket rocket = rockets[i];
            tableModel.setValueAt(rocket.getHeight(), i, 1); // Actualiza la altura
            if (!rocket.isRunning()) {
                tableModel.setValueAt("¡Terminado!", i, 1);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThreadProgressApp::new);
    }
}
