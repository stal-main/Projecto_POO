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
		
		this.name = name;
		
		this.phone = phone;
		
		this.email = email;
		
		this.loans = new ArrayList<>();
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getPhone() {
		
		return phone;
	}
	
	public void setPhone(String phone) {
		
		this.phone = phone;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
		
        this.email = email;
    }
	
	public void addLoan(Loan l) {
		
		if (!loans.contains(l)) {
			
			loans.add(l);
		}
	}
	
	public void deleteLoan(Loan l) {
		
		loans.remove(l);
	}

	
}
