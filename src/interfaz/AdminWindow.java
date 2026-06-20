package interfaz;

import control.Control;

import javax.swing.*;

import java.awt.*;

public class AdminWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	public AdminWindow(Control control) {
		
		this.control = control;
		
		setTitle("Administration");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
        setSize(750, 550);
        
        setLocationRelativeTo(null);
        
        buildUI();
	}
	
	private void buildUI() {
		
		JTabbedPane tabs = new JTabbedPane();
		
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        tabs.addTab("Items", new ItemsPanel(control));
        
        tabs.addTab("People", new PersonsPanel(control));
        
        tabs.addTab("Categories", new CategoriesPanel(control));
        
        tabs.addTab("Types", new TypesPanel(control));
        
        add(tabs);
	}

}
