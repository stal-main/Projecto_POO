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
	
	public Loan(Person person) {
		
		this.creationDate = new Date();
		
		this.person = person;
		
		this.items = new ArrayList<>();
		
		this.alert = null;
		
	}
	
	public Date getDate() {
		
		return creationDate;
	}
	
	public Person getPerson() {
		
		return person;
	}
	
	public void setPerson(Person p) {
		
		this.person = p;
	}
	
	public Alert getAlert() {
		
		return alert;
	}
	
	public void setAlert(Alert a) {
		
		this.alert = a;
	}
	
	public void addItem(Item i) {
		
		if (!items.contains(i)) {
			
			items.add(i);
			
			i.setLoan(this);
		}
	}
	
	public void deleteItem(Item i) {
		
		if (items.remove(i)) {
			
			i.setLoan(null);
		}
	}
	
	public ArrayList<Item> getItems() {
		
		return items;
	}
	
	public void endLoan() {
		
		for (Item i : new ArrayList<>(items)) {
			
			i.setLoan(null);
		}
		
		items.clear();
		
		alert = null;
	}
	
	@Override
	public String toString() {
		
		return "Loan to " + person.getName() + "(" + creationDate + ")";
	}

}
