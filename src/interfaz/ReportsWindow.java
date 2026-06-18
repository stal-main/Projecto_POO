package interfaz;

import control.Control;

import javax.swing.*;

import java.awt.*;

public class ReportsWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	 
    private Control control;
 
    public ReportsWindow(Control control) {
    	
        this.control = control;
        
        setTitle("Reports");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setSize(650, 500);
        
        setLocationRelativeTo(null);
        
        buildUI();
    }
    
    private void buildUI() {
    	
    	JTabbedPane tabs = new JTabbedPane();
    	
    	tabs.setFont(new Font("SansSerif", Font.PLAIN, 13));
    	
    	tabs.addTab("By user", makeReportTab(control.reportByUser()));
    	
    	tabs.addTab("By item", makeReportTab(control.reportePorItem()));
    	
    	tabs.addTab("By category", makeReportTab(control.reportePorCategoria()));
    	
    	tabs.addTab("By type", makeReportTab(control.reportePorTipo()));
    	
    	add(tabs);
    }
    
    private JPanel makeReportTab(String content) {
    	
    	JPanel panel = new JPanel(new BorderLayout(6, 6));
    	
    	panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    	
    	JTextArea area = new JTextArea(content);
    	
    	area.setFont(new Font("Monospaced", Font.PLAIN, 13));
    	
    	area.setEditable(false);
    	
    	area.setBackground(new Color(252, 252, 252));
    	
    	panel.add(new JScrollPane(area), BorderLayout.CENTER);
    	
    	return panel;
    }

}
