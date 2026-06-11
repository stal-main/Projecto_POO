package control;

import logica.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Control implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String DATA_FILE = "data.dat";
	
	private ArrayList<Type> types;
	
	private ArrayList<Item> items;
	
	private ArrayList<Category> categories;
	
	private ArrayList<Loan> loans;
	
	private ArrayList<Person> people;
	
	private Type genericType;
	
	public Control() {
		
		types = new ArrayList<>();
		
		items = new ArrayList<>();
		
		categories = new ArrayList<>();
		
		loans = new ArrayList<>();
		
		people = new ArrayList<>();
		
		genericType = new Type("Generic", "System's generic type");
		
		types.add(genericType);		
		
	}
	
	//Cosas
	
	public void createItem(String code, String name, String desc, Type type, ArrayList<Category> cats) {
		
		Item i = new Item(code, name, desc, type);
		
		type.addItem(i);
		
		if (cats != null) {
			
			for (Category c : cats) {
				
				i.addCategory(c);
				
				c.addItem(i);
			}
		}
		
		items.add(i);
	}
	
	public void updateItem(Item i, String name, String desc, Type type, ArrayList<Category> cats) {
		
		i.getType().deleteItem(i);
		
		for (Category c : new ArrayList<>(i.getCategories())) {
			
			c.deleteItem(i);
			
			i.deleteCategory(c);
		}
		
		i.setName(name);
		
		i.setDescription(desc);
		
		i.setType(type);
		
		type.addItem(i);
		
		if (cats != null) {
		
			for (Category c : cats) {
				
				i.addCategory(c);
				
				c.addItem(i);
			}
		}
	}
	
	public void removeItem(Item i) throws Exception {
		
		if (i.isLend()) {
			
			throw new Exception("This item " + i.getName() + " can't be deleted because it's being lend");
		}
		
		i.getType().deleteItem(i);
		
		for (Category c : new ArrayList<>(i.getCategories())) {
			
			c.deleteItem(i);
		}
		
		items.remove(i);
	}
	
	public Item getItem(String code) {
		
		for (Item i : items) {
			
			if (i.getCode().equals(code)) {
				
				return i;
			}
		}
		
		return null;
	}
	
	public ArrayList<Item> getItems() {
		
		return items;
	}
	
	//Gente
	
	public void createPerson(String name, String phone, String email) {
		
		people.add(new Person(name, phone, email));
	}
	
	public void updatePerson(Person p, String name, String phone, String email) {
		
		p.setName(name);
		
		p.setPhone(phone);
		
		p.setEmail(email);
	}
	
	public void removePerson(Person p) throws Exception {
		
		if(p.hasLoans()) {
			
			throw new Exception(p.getName() + "can't be deleted because of active loans");
		}
		
		people.remove(p);
	}
	
	public ArrayList<Person> getPeople() {
		
		return people;
		
	}
	
	//Categorias
	
	public void createCategory(String name) {
		
		categories.add(new Category(name));
	}
	
	public void updateCategory(Category c, String name) {
		
		c.setName(name);
	}
	
	public void removeCategory(Category c) {
		
		for (Item i : new ArrayList<>(c.getItems())) {
			
			i.deleteCategory(c);
		}
		
		categories.remove(c);
	}
	
	
	public ArrayList<Category> getCategories() {
		
		return categories;
	}
	
	//Tipos
	
	public void createType(String name, String desc) {
		
		types.add(new Type(name, desc));
	}
	
	public void updateType(Type t, String name, String desc) {
		
		t.setName(name);
		
		t.setDescription(desc);
	}
	
	public void removeType(Type t) throws Exception {
	
		if (t == genericType) {
			
			throw new Exception("The generic type can't be deleted");
		}
		
		for (Item i : new ArrayList<>(t.getItems())) {
			
			i.setType(genericType);
			
			genericType.addItem(i);
		}
		
		types.remove(t);
	
	}
	
	public ArrayList<Type> getTypes() {
		
		return types;
	}
	
	public Type getGenericType() {
		
		return genericType;
	}
	
	//Prestamos
	
	public Loan doLoan(Person p, ArrayList<Item> itemLent) {
		
		Loan l = new Loan(p);
		
		for (Item i : itemLent) {
			
			l.addItem(i);
		}
		
		p.addLoan(l);
		
		loans.add(l);
		
		return l;
	}
	
	public void addItemToLoan(Loan l, Item i) {
		
		l.addItem(i);
	}
	
	public void removeItemFromLoan(Loan l, Item i) {
		
		l.deleteItem(i);
	}
	
	public void returnItem(Loan l, Item i) {
		
		l.deleteItem(i);
	}
	
	public void endLoan(Loan l) {
		
		l.endLoan();
		
		l.getPerson().deleteLoan(l);
		
		loans.remove(l);
	}
	
	public void addAlert(Loan l, String msg, Date date, int interval, AlertType type) {
		
		l.setAlert(new Alert(msg, date, interval, type, l));
	}
	
	public ArrayList<Loan> getLoans() {
		
		return loans;
	}
	
	//Alertas
	
	public ArrayList<Alert> verifyAlerts() {
		
		ArrayList<Alert> actives = new ArrayList<>();
		
		for (Loan l : loans) {
			
			Alert a = l.getAlert();
			
			if (a != null && a.mustActivate()) {
				
				actives.add(a);
				
				if (a.getType() == AlertType.UNIQUE) {
					
					l.setAlert(null);
				}
			}
		}
		
		return actives;
	}
	
	//Reportes
	
	//Guardado y cargado de datos
	
	public void saveData() {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
			
			oos.writeObject(this);
		}
		
		catch (IOException e) {
			
			System.err.println("Error while saving data: " + e.getMessage());
		}
	}
	
	public static Control loadData() {
		
		File file = new File(DATA_FILE);
		
		if (!file.exists()) {
			
			return new Control();
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			
			return (Control) ois.readObject();
		}
		
		catch (IOException | ClassNotFoundException e) {
			
			System.err.println("Error while loading data: " + e.getMessage());
			
			return new Control();
		}
	}
	
	

}
