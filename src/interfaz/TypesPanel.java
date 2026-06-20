package interfaz;

import control.Control;

import logica.Type;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class TypesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Control control;
	
	private DefaultTableModel tableModel;
	
    private JTable table;
    
    private JTextField txtName;
    
    private JTextField txtDesc;
	
	public TypesPanel(Control control) {
		
        this.control = control;
        
        setLayout(new BorderLayout(10, 10));
        
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        buildUI();
        
        refreshTable();
    }
	
	private void buildUI() {
		
		String[] columns = {"Name", "Description", "Items"};
		
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
        
        form.setBorder(BorderFactory.createTitledBorder("Type data"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        
        add(form, BorderLayout.SOUTH);
		
	}
	
	private void refreshTable() {
		
	}
	
	private void fillForm() {
		
	
	}
	
	private void addType() {
		
	}
}