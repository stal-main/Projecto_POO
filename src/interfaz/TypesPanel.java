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
        
        gbc.gridx = 0; 
        
        gbc.gridy = 0; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Name:"), gbc);
        
        txtName = new JTextField(20);
        
        gbc.gridx = 1; gbc.gridwidth = 3;
        
        form.add(txtName, gbc);
        
        add(form, BorderLayout.SOUTH);
        
        gbc.gridx = 0; 
        
        gbc.gridy = 1; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Description:"), gbc);
        
        txtDesc = new JTextField(20);
        
        gbc.gridx = 1; 
        
        gbc.gridwidth = 3;
        
        form.add(txtDesc, gbc);
        
        JButton btnAdd    = new JButton("Add");
        
        JButton btnUpdate = new JButton("Update");
        
        JButton btnDelete = new JButton("Delete");
        
        JButton btnClear  = new JButton("Clear");
        
        gbc.gridy = 2; 
        
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
        
        btnAdd.addActionListener(e -> addType());
        
        btnUpdate.addActionListener(e -> updateType());
        
        btnDelete.addActionListener(e -> deleteType());
        
        btnClear.addActionListener(e -> clearForm());
		
	}
	
	private void refreshTable() {
		
		tableModel.setRowCount(0);
		
		for (Type t : control.getTypes()) {
			
			String name = t.getName();
			
			if (t == control.getGenericType()) {
				
				name += " (generic)";
			}
			
			tableModel.addRow(new Object[]{name, t.getDescription(), t.getItems().size()});
		}
		
	}
	
	private void fillForm() {
		
		int row = table.getSelectedRow();
		
		if (row < 0) {
			
			return;
		}
		
		String name = (String) tableModel.getValueAt(row, 0);
		
		name = name.replace(" (generic)", "");
		
		txtName.setText(name);
		
        txtDesc.setText((String) tableModel.getValueAt(row, 1));
		
	
	}
	
	private void clearForm() {
		
		txtName.setText("");
		
        txtDesc.setText("");
        
        table.clearSelection();
		
	}
	
	private void addType() {
		
		String name = txtName.getText().trim();
		
        String desc = txtDesc.getText().trim();
        
        if (name.isEmpty()) {
        	
        	JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
        	
        	return;
        }
        
        try {
        	
        	control.createType(name, desc);
        	
            refreshTable();
            
            clearForm();
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
		
	}
	
	private void updateType() {
		
		int row = table.getSelectedRow();
		
		if (row <0) {
			
			JOptionPane.showMessageDialog(this, "Select a type first.", "Warning", JOptionPane.WARNING_MESSAGE);
            
			return;
		}
		
		String oldName = ((String) tableModel.getValueAt(row, 0)).replace(" (generic)", "");
		
        String newName = txtName.getText().trim();
        
        String desc    = txtDesc.getText().trim();
        
        if (newName.isEmpty()) {
        	
        	JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
        	
        	return;
        }
        
        try {
        	
        	control.updateType(oldName, newName, desc);
        	
            refreshTable();
            
            clearForm();         	
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
        }
	}
	
	private void deleteType() {
		
		int row = table.getSelectedRow();
		
		if (row <0) {
			
			JOptionPane.showMessageDialog(this, "Select a type first.", "Warning", JOptionPane.WARNING_MESSAGE);
            
			return;
		}
		
		String name = ((String) tableModel.getValueAt(row, 0)).replace(" (generic)", "");
		
		int confirm = JOptionPane.showConfirmDialog(this,"Delete type " + name + "?\nItems of this type will switch to the generic type", "Confirm", JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) {
			
			return;
		}
		
		try {
			
			control.removeType(name);
			
            refreshTable();
            
            clearForm();
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
        }
	}
}