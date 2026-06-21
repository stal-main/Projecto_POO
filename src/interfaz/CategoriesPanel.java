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
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; 
        
        gbc.gridy = 0;
        
        form.add(new JLabel("Name:"), gbc);
        
        txtName = new JTextField(20);
        
        gbc.gridx = 1; gbc.gridwidth = 3;
        
        form.add(txtName, gbc);
        
        JButton btnAdd    = new JButton("Add");
        
        JButton btnUpdate = new JButton("Update");
        
        JButton btnDelete = new JButton("Delete");
        
        JButton btnClear  = new JButton("Clear");
        
        gbc.gridy = 1; 
        
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; 
        
        form.add(btnAdd, gbc);
        
        gbc.gridx = 1; 
        
        form.add(btnUpdate, gbc);
        
        gbc.gridx = 2; 
        
        form.add(btnDelete, gbc);
        
        gbc.gridx = 3; 
        
        form.add(btnClear, gbc);
        
        add(form, BorderLayout.SOUTH);
        
        btnAdd.addActionListener(e -> addCategory());
        
        btnUpdate.addActionListener(e -> updateCategory());
        
        btnDelete.addActionListener(e -> deleteCategory());
        
        btnClear.addActionListener(e -> clearForm());
		
	}
	
	private void refreshTable() {
		
		tableModel.setRowCount(0);
		
		for (Category c : control.getCategories()) {
			
			tableModel.addRow(new Object[] {c.getName(), c.getItems().size()});
		}
	}
	
	private void fillForm() {
		
		int row = table.getSelectedRow();
		
		if (row < 0) {
			
			return;
		}
		
		 txtName.setText((String) tableModel.getValueAt(row, 0));
		
	}
	
	private void clearForm() {
		
        txtName.setText("");
        
        table.clearSelection();
    }
	
	private void addCategory() {
		
		String name = txtName.getText().trim();
		
		if (name.isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		try {
			
			control.createCategory(name);
			
            refreshTable();
            
            clearForm();		
		}
		
		catch (Exception e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void updateCategory() {
		
		int row = table.getSelectedRow();
		
		if (row < 0) {
			
			JOptionPane.showMessageDialog(this, "Select a category first", "Warning", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		String oldName = (String) tableModel.getValueAt(row, 0);
		
        String newName = txtName.getText().trim();
        
        if (newName.isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
        
        try {
        	
        	control.updateCategory(oldName, newName);
        	
            refreshTable();
            
            clearForm();
        }
        
        catch (Exception e) {
			
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void deleteCategory() {
		
		int row = table.getSelectedRow();
		
		if (row < 0) {
			
			JOptionPane.showMessageDialog(this, "Select a category first", "Warning", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		String name = (String) tableModel.getValueAt(row, 0);
		
		int confirm = JOptionPane.showConfirmDialog(this,"Delete category " + name + "?", "Confirm", JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) {
			
			return;
		}
		
		try {
			
			control.removeCategory(name);
			
            refreshTable();
            
            clearForm();
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
        }
		
		
	}

}
