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
    }
    
    public void refreshTable() {
    	
    }
    
    public void refreshTypesCombo() {
    	
    }
    
    public void refreshCategoriesList() {
    	
    }

}
