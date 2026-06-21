package interfaz;

import control.Control;

import logica.Alert;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	public MainWindow(Control control) {
		
		this.control = control;
		
		setTitle("Loan Control");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setSize(800, 600);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		//escuchador de ventanas
		addWindowListener(new java.awt.event.WindowAdapter() {
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				
				control.saveData();

				dispose();
				
				System.exit(0);
			}
			
		});
			
		buildUI();
		
	}
	
	public void buildUI() {
		
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setBackground(new Color(245, 245, 245));
		
		JLabel title = new JLabel("Loan Control", SwingConstants.CENTER);
		
		title.setFont(new Font("SansSerif", Font.BOLD, 22));
		
		title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
		
		panel.add(title, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridBagLayout());
		  
	    center.setBackground(new Color(245, 245, 245));
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    gbc.insets = new Insets(10, 20, 10, 20);
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    
	    gbc.weightx = 1;
	    
	    JButton btnAdmin = makeNavButton("Administration" , new Color(52, 120, 200));
	    
	    JButton btnLoans = makeNavButton("Loans", new Color(40, 160, 90));
	    
	    JButton btnReports = makeNavButton("Reports", new Color(160, 80, 180));
	    
	    gbc.gridx = 0;
	    
	    gbc.gridy = 0;
	    
	    center.add(btnAdmin, gbc);
	    
	    gbc.gridy = 1;
	    
	    center.add(btnLoans, gbc);
	    
	    gbc.gridy = 2;
	    
	    center.add(btnReports, gbc);
	    
	    panel.add(center, BorderLayout.CENTER);
		
		add(panel);
		
		btnAdmin.addActionListener(e -> openAdminWindow());
		
		btnLoans.addActionListener(e -> openLoansWindow());
		
		btnReports.addActionListener(e -> openReportsWindow());
		
	}
	
	private JButton makeNavButton(String text, Color color) {
		
		JButton btn = new JButton(text);
		
		btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		btn.setBackground(color);
		
		btn.setForeground(Color.WHITE);
		
		btn.setFocusPainted(false);
		
		btn.setOpaque(false);
		
		btn.setPreferredSize(new Dimension(300, 55));
		
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		return btn;
	}
	
	private void openAdminWindow() {
		
		new AdminWindow(control).setVisible(true);
	}
	
	private void openLoansWindow() {
		
		new LoansWindow(control).setVisible(true);
	}
	
	private void openReportsWindow() {
		
		new ReportsWindow(control).setVisible(true);
	}
	
	public void checkAlerts() {
		
		ArrayList<Alert> actives = control.verifyAlerts();
		
		if (actives.isEmpty()) {
			
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("The following alerts have been activated: \n\n");
		
		for (Alert a : actives) {
			
			sb.append(" ").append(a.getMessage()).append("\n");
			
			sb.append(" Loans: ").append(a.getLoan()).append("\n");
			
			sb.append(" Type: ").append(a.getType()).append("\n\n");
		}
		
		JOptionPane.showMessageDialog(this, sb.toString(), "Pending Alerts (" + actives.size() + ")", JOptionPane.WARNING_MESSAGE);
		
		control.saveData();
	}

}
