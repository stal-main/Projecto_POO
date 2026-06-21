package interfaz;

import control.Control;

import logica.Category;

import logica.Item;

import logica.Type;
 
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.ArrayList;

public class ItemsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	 
    private Control control;
 
    private DefaultTableModel tableModel;
    
    private JTable table;
    
    private JTextField txtCode;
    
    private JTextField txtName;
    
    private JTextField txtDesc;
    
    private JComboBox<String> cmbType;
    
    private JList<String> lstCategories;
    
    private DefaultListModel<String> catListModel;
    
    public ItemsPanel(Control control) {
    	
    	this.control = control;
    	
    	setLayout(new BorderLayout(10, 10));
    	
    	buildUI();
    	
    	refreshTable();
    	
        refreshTypesCombo();
        
        refreshCategoriesList();
    }
    
    private void buildUI() {
    	
    	String[] columns = {"Code" , "Name", "Type", "Status"};
    	
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
    	
    	form.setBorder(BorderFactory.createTitledBorder("Item data"));
    	
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	gbc.insets = new Insets(4, 5, 4, 5);
    	
        gbc.anchor = GridBagConstraints.WEST;
        
        //Codigo
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        
        form.add(new JLabel("Code:"), gbc);
        
        txtCode = new JTextField(10);
        
        gbc.gridx = 1;
        
        gbc.gridwidth = 1;
        
        form.add(txtCode, gbc);
        
        //Nombre
        
        gbc.gridx = 2; 
        
        gbc.gridy = 0; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Name:"), gbc);
        
        txtName = new JTextField(15);
        
        gbc.gridx = 3; 
        
        gbc.gridwidth = 1;
        
        form.add(txtName, gbc);
        
        //Descripcion
        
        gbc.gridx = 0; 
        
        gbc.gridy = 1; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Description:"), gbc);
        
        txtDesc = new JTextField(28);
        
        gbc.gridx = 1; 
        
        gbc.gridwidth = 3;
        
        form.add(txtDesc, gbc);
        
        //Tipo
        
        gbc.gridx = 0;
        
        gbc.gridy = 2; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Type:"), gbc);
        
        cmbType = new JComboBox<>();
        
        gbc.gridx = 1; 
        
        gbc.gridwidth = 1;
        
        form.add(cmbType, gbc);
        
        //Categorias
        
        gbc.gridx = 2; 
        
        gbc.gridy = 2; 
        
        gbc.gridwidth = 1;
        
        form.add(new JLabel("Categories:"), gbc);
        
        catListModel = new DefaultListModel<>();
        
        lstCategories = new JList<>(catListModel);
        
        lstCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JScrollPane catScroll = new JScrollPane(lstCategories);
        
        catScroll.setPreferredSize(new Dimension(150, 60));
        
        gbc.gridx = 3; 
        
        gbc.gridwidth = 1;
        
        form.add(catScroll, gbc);
        
        //Botones
        
        JButton btnAdd = new JButton("Add");
        
        JButton btnUpdate = new JButton("Modify");
        
        JButton btnDelete = new JButton("Delete");
        		
        JButton btnClear  = new JButton("Clear");
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        
        btnPanel.add(btnAdd);
        
        btnPanel.add(btnUpdate);
        
        btnPanel.add(btnDelete);
        
        btnPanel.add(btnClear);
        
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.gridwidth = 4;
        
        form.add(btnPanel, gbc);
        
        add(form, BorderLayout.SOUTH);
        
        btnAdd.addActionListener(e -> addItem());
        
        btnUpdate.addActionListener(e -> updateItem());
        
        btnDelete.addActionListener(e -> deleteItem());
        
        btnClear.addActionListener(e -> clearForm());
    }
    
    public void refreshTable() {
    	
    	tableModel.setRowCount(0);
    	
    	for (Item i : control.getItems()) {
    		
    		String status = i.isLend() ? "Lend to " + i.getLoan().getPerson().getName() : "Available";
    		
    		tableModel.addRow(new Object[] {i.getCode(), i.getName(), i.getType().getName(), status});
    	}
    	
    }
    
    public void refreshTypesCombo() {
    	
    	cmbType.removeAllItems();
    	
    	for (Type t : control.getTypes()) {
    		
    		cmbType.addItem(t.getName());
    	}
    	
    }
    
    public void refreshCategoriesList() {
    	
    	catListModel.clear();
    	
    	for (Category c : control.getCategories()) {
    		
    		catListModel.addElement(c.getName());
    	}
    	
    }
    
    private void fillForm() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		return;
    	}
    	
    	String code = (String) tableModel.getValueAt(row, 0);
    	
    	Item item = control.getItem(code);
    	
    	if (item == null) {
    		
    		return;
    	}
    	
    	txtCode.setText(item.getCode());
    	
    	txtCode.setEditable(false);
    	
    	txtName.setText(item.getName());
    	
    	txtDesc.setText(item.getDescription());
    	
        cmbType.setSelectedItem(item.getType().getName());
        
        lstCategories.clearSelection();
        
        ArrayList<Integer> index = new ArrayList<>();
        
        for (int i = 0; i < catListModel.size(); i++) {
        	
        	String catName = catListModel.getElementAt(i);
        	
        	for (Category c : item.getCategories()) {
        		
        		if (c.getName().equals(catName)) {
        			
        			index.add(i);
        		}
        	}
        }
        
        int[] array = index.stream().mapToInt(Integer::intValue).toArray();
        
        lstCategories.setSelectedIndices(array);
    }
    
    public void clearForm() {
    	
    	txtCode.setText("");
    	
    	txtCode.setEditable(true);
    	
    	txtName.setText("");
    	
        txtDesc.setText("");
        
        if (cmbType.getItemCount() > 0) {
        	
        	cmbType.setSelectedIndex(0);
        }
        
        lstCategories.clearSelection();
        
        table.clearSelection();
    }
    
    public void addItem() {
    	
    	String code = txtCode.getText().trim();
    	
        String name = txtName.getText().trim();
        
        String desc = txtDesc.getText().trim();
        
        String type = (String) cmbType.getSelectedItem();
        
        if (code.isEmpty() || name.isEmpty()) {
        	
        	JOptionPane.showMessageDialog(this, "Code and name are needed", "Error", JOptionPane.ERROR_MESSAGE);
        	
        	return;
        }
        
        if (type == null) {
        	
        	JOptionPane.showMessageDialog(this, "There must be at least one type", "Error", JOptionPane.ERROR_MESSAGE);
        
        	return;
        }
        
        try {
        	
        	control.createItem(code, name, desc, type);
        	
        	for (String cat : lstCategories.getSelectedValuesList()) {
        		
        		control.addCategoryToItem(code, cat);
        	}
        	
        	refreshTable();
        	
        	clearForm();
        }
        
        catch (Exception ex) {
        	
        	JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateItem() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		JOptionPane.showMessageDialog(this, "Select an item first", "Warning", JOptionPane.WARNING_MESSAGE);
    		
    		return;
    	}
    	
    	String code = (String) tableModel.getValueAt(row, 0);
    	
    	String name = txtName.getText().trim();
    	
    	String desc = txtDesc.getText().trim();
    	
        String type = (String) cmbType.getSelectedItem();
    	
    	if (name.isEmpty()) {
    		
    		JOptionPane.showMessageDialog(this, "The name is needed", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		return;
    	}
    	
    	try {
    		
    		control.updateItem(code, name, desc, type);
    		
    		Item item = control.getItem(code);
    		
    		for (Category c: new ArrayList<>(item.getCategories())) {
    			
    			control.removeCategoryFromItem(code, c.getName());
    		}
    		
    		for (String cat : lstCategories.getSelectedValuesList()) {
    			 control.addCategoryToItem(code, cat);
    		}
    		
    		refreshTable();
    		
    		clearForm();
    	}
    	
    	catch (Exception e) {
    		JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	
    }
    
    public void deleteItem() {
    	
    	int row = table.getSelectedRow();
    	
    	if (row < 0) {
    		
    		JOptionPane.showMessageDialog(this, "Select an item first", "Warning", JOptionPane.WARNING_MESSAGE);
    		
    		return;
    	}
    	
    	String code = (String) tableModel.getValueAt(row, 0);
    	
        String name = (String) tableModel.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Delete item " + name + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
        	
        	return;
        }
        
        try {
        	
        	control.removeItem(code);
        	
        	refreshTable();
        	
        	clearForm();
        }
        
        catch (Exception e) {
        	
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
