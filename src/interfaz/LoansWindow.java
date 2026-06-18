package interfaz;

import control.Control;

import logica.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

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
    	
    	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    	
        split.setDividerLocation(320);
        
        main.add(split, BorderLayout.CENTER);
    	
    	add(main, BorderLayout.CENTER);
    	
    }
    
    private void refreshLoansTable() {
    	
    }
    
    private void refreshItemsTable() {
    	
    }

}
