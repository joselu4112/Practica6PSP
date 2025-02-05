package Start;

import javax.swing.SwingUtilities;

import View.PanelInicial;

public class Start {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            PanelInicial frame = new PanelInicial();
	            frame.setVisible(true);
	        });
	    }

}
