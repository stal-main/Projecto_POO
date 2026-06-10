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
	
	public void deletePerson(Person p) throws Exception {
		
		if(p.hasLoans()) {
			
			throw new Exception("");
		}
		
		people.remove(p);
	}
	
	public ArrayList<Person> getPeople() {
		
		return people;
		
	}
	
	//Categorias
	
	//Tipos
	
	//Prestamos
	
	//Alertas
	
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
