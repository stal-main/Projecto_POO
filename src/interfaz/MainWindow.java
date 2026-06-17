package interfaz;

import control.Control;

import javax.swing.*;

import java.awt.*;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	public MainWindow(Control control) {
		
		this.control = control;
		
		setTitle("Loan Control");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setSize(600, 400);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
	}
	
	public void buildUI() {
		
		
	}
	
	public void checkAlerts() {
		
		
	}

}
