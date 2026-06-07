package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Loan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date creationDate;
	
	private Person person;
	
	private ArrayList<Item> items;
	
	private Alert alert;

}
