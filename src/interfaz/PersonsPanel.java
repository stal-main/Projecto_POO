package interfaz;

import control.Control;

import logica.Person;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PersonsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	 
    private Control control;
    
    private DefaultTableModel tableModel;
    
    private JTable table;
    
    private JTextField txtName;
    
    private JTextField txtPhone;
    
    private JTextField txtEmail;
    
    public PersonsPanel(Control control) {
    	
    	this.control = control;
    	
    	setLayout(new BorderLayout(10, 10));
    	
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        buildUI();
        
        refreshTable();
    }
    
    private void buildUI() {
    	
    	String[] columns = {"Name", "Phone", "Email", "Active loans"};
    	
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
    	
        form.setBorder(BorderFactory.createTitledBorder("Person's data"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.WEST;
        
        txtName  = addField(form, gbc, "Name:",  0);
        
        txtPhone = addField(form, gbc, "Phone:", 1);
        
        txtEmail = addField(form, gbc, "Email:",  2);
        
        //Botones
        JButton btnAdd = new JButton("Add");
        
        JButton btnUpdate = new JButton("Update");
        
        JButton btnDelete = new JButton("Delete");
        
        JButton btnClear = new JButton("Clear");
        
        gbc.gridx = 0; 
        
        gbc.gridy = 3; 
        
        gbc.gridwidth = 1;
        
        form.add(btnAdd, gbc);
        
        gbc.gridx = 1;
        
        form.add(btnUpdate, gbc);
        
        gbc.gridx = 2;
        
        form.add(btnDelete, gbc);
        
        gbc.gridx = 3;
        
        form.add(btnClear, gbc);
        
        add(form, BorderLayout.SOUTH);
        
        btnAdd.addActionListener(e -> addPerson());
        
        btnUpdate.addActionListener(e -> updatePerson());
        
        btnDelete.addActionListener(e -> deletePerson());
        
        btnClear.addActionListener(e -> clearForm());
    }
    
    private JTextField addField(JPanel panel, GridBagConstraints gbc, String label, int row) {
    	
    	gbc.gridx = 0;
    	
    	gbc.gridy = row;
    	
    	gbc.gridwidth = 1;
    	
    	panel.add(new JLabel(label), gbc);
    	
    	JTextField field = new JTextField(20);
    	
    	gbc.gridx = 1; 
    	
    	gbc.gridwidth = 3;
    	
    	panel.add(field, gbc);
    	
    	return field;
    }
    
    private void refreshTable() {
    	
    	tableModel.setRowCount(0);
    	
    	for (Person p : control.getPeople()) {
    		
    		tableModel.addRow(new Object[]{
    			p.getName(), p.getPhone(), p.getEmail(), p.getLoans().size()
    		});
    	}
    	
    }
    
    private void fillForm() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		return;
    	}
    	
    	txtName.setText((String) tableModel.getValueAt(row, 0));
    	
        txtPhone.setText((String) tableModel.getValueAt(row, 1));
        
        txtEmail.setText((String) tableModel.getValueAt(row, 2));
    }
    
    private void clearForm() {
    	
    	txtName.setText("");
    	
        txtPhone.setText("");
        
        txtEmail.setText("");
        
        table.clearSelection();
    	
    }
    
    private void addPerson() {
    	
    	String name  = txtName.getText().trim();
    	
        String phone = txtPhone.getText().trim();
        
        String email = txtEmail.getText().trim();
        
        if (name.isEmpty()) {
        	
            JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        try {
        	
        	control.createPerson(name, phone, email);
        	
            refreshTable();
            
            clearForm();
        	
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updatePerson() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		JOptionPane.showMessageDialog(this, "Select a person first", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		return;
    	}
    	
    	String oldName = (String) tableModel.getValueAt(row, 0);
    	
        String name    = txtName.getText().trim();
        
        String phone   = txtPhone.getText().trim();
        
        String email   = txtEmail.getText().trim();
        
        if (name.isEmpty()) {
        	
        	JOptionPane.showMessageDialog(this, "The name is needed.", "Error", JOptionPane.ERROR_MESSAGE);
        	
        	return;
        }
        
        try {
        	
        	control.updatePerson(oldName, name, phone, email);
        	
            refreshTable();
            
            clearForm();
        	
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deletePerson() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		JOptionPane.showMessageDialog(this, "Select a person first", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		return;
    	}
    	
    	String name = (String) tableModel.getValueAt(row, 0);
    	
    	int confirm = JOptionPane.showConfirmDialog(this, "Delete " + name + "from existence???", "Comfirm" , JOptionPane.YES_NO_OPTION);
    
    	if (confirm != JOptionPane.YES_OPTION) {
    		
    		return;
    	}
    	
    	try {
    		
    		
    		control.removePerson(name);
    		
    		refreshTable();
            
            clearForm();
    	}
    	
    	catch (Exception e) {
    		
    		JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }

}
