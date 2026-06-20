package interfaz;

import control.Control;

import logica.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class LoansWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	 
    private Control control;
    
    private DefaultTableModel loansModel;
    
    private JTable loansTable;
    
    private DefaultTableModel itemsModel;
    
    private JTable itemsTable;
    
    private JLabel lblLoanInfo;
    
    public LoansWindow(Control control) {
    	
    	this.control = control;
    	
    	setTitle("Loans");
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
        setSize(800, 560);
        
        setLocationRelativeTo(null);
    	
    	buildUI();
    	
    	refreshLoansTable();
    }
    
    private void buildUI() {
    	
    	setLayout(new BorderLayout(10, 10));
    	
    	JPanel main = new JPanel(new BorderLayout(10, 10));
    	
    	main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	//Panel izquierdo con lista de prestamos
    	
    	JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
    	
    	leftPanel.setBorder(BorderFactory.createTitledBorder("Active loans"));
    	
    	String[] loansCols = {"Person", "Date", "Items"};
    	
    	loansModel = new DefaultTableModel(loansCols, 0) {
    		
    		public boolean isCellEditable(int r, int c) {
    			
    			return false;
    		}
    	};
    	
    	loansTable = new JTable(loansModel);
    	
    	loansTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	loansTable.setRowHeight(24);
    	
    	loansTable.getSelectionModel().addListSelectionListener(e -> refreshItemsTable());
    	
    	leftPanel.add(new JScrollPane(loansTable), BorderLayout.CENTER);
    	
    	//Botones del panel izquierdo
    	
    	JButton btnNew = new JButton("New loan");
    	
    	JButton btnFinish = new JButton("End loan");
    	
    	btnNew.setBackground(new Color(40, 160, 90));
    	
        btnNew.setForeground(Color.WHITE);
        
        btnNew.setFocusPainted(false);
        
        btnFinish.setBackground(new Color(200, 60, 60));
        
        btnFinish.setForeground(Color.WHITE);
        
        btnFinish.setFocusPainted(false);
        
        JPanel loanBtns = new JPanel(new GridLayout(1, 2, 6, 0));
        
        loanBtns.add(btnNew);
        
        loanBtns.add(btnFinish);
        
        leftPanel.add(loanBtns, BorderLayout.SOUTH);
        
        //Panel derecho con items del prestamo
    	
    	JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
    	
    	rightPanel.setBorder(BorderFactory.createTitledBorder("Loan items"));
    	 
    	lblLoanInfo = new JLabel(" ");
    	
    	lblLoanInfo.setFont(new Font("SansSerif", Font.ITALIC, 12));
    	
    	rightPanel.add(lblLoanInfo, BorderLayout.NORTH);
    	
    	String[] itemCols = {"Code", "Name", "Type"};
    	
    	itemsModel = new DefaultTableModel(itemCols, 0) {
    		
    		public boolean isCellEditable(int r, int c) {
    			
    			return false;
    		}
    		
    	};
    	
    	itemsTable = new JTable(itemsModel);
    	
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        itemsTable.setRowHeight(24);
        
        rightPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        
        //Botones del panel derecho
        
        JButton btnReturn = new JButton("Return selected item");
        
        btnReturn.setBackground(new Color(200, 130, 30));
        
        btnReturn.setForeground(Color.WHITE);
        
        btnReturn.setFocusPainted(false);
        
        rightPanel.add(btnReturn, BorderLayout.SOUTH);
    	
    	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    	
        split.setDividerLocation(320);
        
        main.add(split, BorderLayout.CENTER);
    	
    	add(main, BorderLayout.CENTER);
    	
    	btnNew.addActionListener(e -> openNewLoanDialog());
    	
    	btnFinish.addActionListener(e -> finishLoan());
    	
    }
    
    private void refreshLoansTable() {
    	
    	loansModel.setRowCount(0);
    }
    
    private void refreshItemsTable() {
    	
    	loansModel.setRowCount(0);
    	
    }
    
    private Loan getSelectedLoan() {
    	
    	int row = loansTable.getSelectedRow();
    	
    	if (row < 0) {
    		
    		return null;
    	}
    	
    	ArrayList<Loan> loans = control.getLoans();
    	
    	if (row >= loans.size()) {
    		
    		return null;
    	}
    	
    	return loans.get(row);
    }
    
    private void finishLoan() {
    	
    	Loan loan = getSelectedLoan();
    	
    	if (loan == null) {
    		
    		JOptionPane.showMessageDialog(this, "Select a loan first", "Warning", JOptionPane.WARNING_MESSAGE);
    		
    		return;
    	}
    	
    	int confirm = JOptionPane.showConfirmDialog(this, "Finish " + loan.getPerson().getName() + "'s loan?\n All the items will now be available", "Confirm", JOptionPane.YES_NO_OPTION);
    	
    	if (confirm != JOptionPane.YES_OPTION) {
    		
    		return;
    	}
    	
    	control.endLoan(loan);
    	
    	refreshLoansTable();
    	
    	itemsModel.setRowCount(0);
    	
        lblLoanInfo.setText(" ");
    }
    
    //Interfaz para crear un prestamo nuevo
    private void openNewLoanDialog() {
    	
    	if (control.getPeople().isEmpty()) {
    		
    		JOptionPane.showMessageDialog(this, "No registered people to lend to", "Error", JOptionPane.ERROR_MESSAGE);
    	
    		return;
    	}
    	
    	ArrayList<Item> available = new ArrayList<>();
    	
    	for (Item i : control.getItems()) {
    		
    		if (!i.isLend()) {
    			
    			available.add(i);
    		}
    	}
    	
    	if (available.isEmpty()) {
    		
    		JOptionPane.showMessageDialog(this, "No available items to lend", "Error", JOptionPane.ERROR_MESSAGE);
        	
    		return;
    	}
    	
    	JDialog dialog = new JDialog(this, "New loan", true);
    	
    	dialog.setSize(550, 480);
    	
    	dialog.setLocationRelativeTo(this);
    	
    	dialog.setLayout(new BorderLayout(10, 10));
    	
    	JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	
    	top.add(new JLabel("Person:"));
    	
    	JComboBox<String> cmbPerson = new JComboBox<>();
    	
    	for (Person p : control.getPeople()) {
    		
    		cmbPerson.addItem(p.getName());
    	}

    	top.add(cmbPerson);
    	
    	dialog.add(top, BorderLayout.NORTH);
    	
    	
    }

}
