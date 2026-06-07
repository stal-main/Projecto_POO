package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String phone; //ring ring
	
	private String email;
	
	private ArrayList<Loan> loans;
	
	public Person(String name, String phone, String email) {
		
		
	}

	
}
