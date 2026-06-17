package interfaz;

import control.Control;

import javax.swing.*;

public class LoanControl {

	public static void main(String[] args) {
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		catch (Exception e) {
			
		}
		
		Control control = Control.loadData();
		
		SwingUtilities.invokeLater(() -> {
			
			MainWindow window = new MainWindow(control);
			
			window.setVisible(true);
			
			window.checkAlerts();
		});

	}

}
