package View;

import javax.swing.*;

public class Principal extends JFrame {

	    public Principal() {
	        setTitle("Carrera de Globos");
	        setSize(800, 450);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setResizable(false);
	
	        // Crear el panel personalizado
	        PanelCarrera panel = new PanelCarrera();
	
	        // Agregar el panel y el botón al JFrame
	        setLayout(null);
	        add(panel); // Agregar el panel al JFrame
	       
	        // Crear el botón para iniciar la carrera
	        JButton btnIniciar = new JButton("Iniciar Carrera");
	        btnIniciar.setBounds(350, 380, 120, 30); // Ubicación del botón
	        add(btnIniciar); // Agregar el botón al JFrame
	        
	        btnIniciar.addActionListener(e -> panel.iniciarCarrera()); // Acción del botón
	

	    }
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	        	Principal frame = new Principal();
	            frame.setVisible(true);
	        });
    	    }
    	
}