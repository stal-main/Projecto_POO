package interfaz;

import control.Control;

import logica.Category;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class CategoriesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	private DefaultTableModel tableModel;
	
    private JTable table;
    
    private JTextField txtName;
	
	public CategoriesPanel(Control control) {
		
        this.control = control;
        
        setLayout(new BorderLayout(10, 10));
        
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        buildUI();
        
        refreshTable();
    }
	
	private void buildUI() {
		
		String[] columns = {"Name", "Items"};
		
		tableModel = new DefaultTableModel(columns, 0) {
			
			public boolean isCellEditable(int r, int c) {
				
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
        table.setRowHeight(24);
        
        table.getSelectionModel().addListSelectionListener(e -> fillForm());
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel form = new JPanel(new GridBagLayout());
        
        form.setBorder(BorderFactory.createTitledBorder("Category data"));
		
	}
	
	private void refreshTable() {
		
	}
	
	private void fillForm() {
		
	}

}
