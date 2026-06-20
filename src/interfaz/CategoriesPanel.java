package interfaz;

import control.Control;

import logica.Category;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class CategoriesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	public CategoriesPanel(Control control) {
		
        this.control = control;
        
        setLayout(new BorderLayout(10, 10));
        
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        buildUI();
        
        refreshTable();
    }
	
	private void buildUI() {
		
	}
	
	private void refreshTable() {
		
	}

}
