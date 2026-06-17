package interfaz;

import control.Control;

import logica.Person;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.ArrayList;

public class PersonsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	 
    private Control control;
    
    public PersonsPanel(Control control) {
    	
    	this.control = control;
    }

}
