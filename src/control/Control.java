package control;

import logica.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
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
	
	//Buscadores (para buscar)
	
	private Item findItem(String code) throws Exception {
		 
		for (Item i : items) {
 
			if (i.getCode().equals(code)) {
 
				return i;
			}
		}
 
		throw new Exception("Item with code '" + code + "' not found");
	}
 
	private Person findPerson(String name) throws Exception {
 
		for (Person p : people) {
 
			if (p.getName().equals(name)) {
 
				return p;
			}
		}
 
		throw new Exception("Person '" + name + "' not found");
	}
 
	private Category findCategory(String name) throws Exception {
 
		for (Category c : categories) {
 
			if (c.getName().equals(name)) {
 
				return c;
			}
		}
 
		throw new Exception("Category '" + name + "' not found");
	}
 
	private Type findType(String name) throws Exception {
 
		for (Type t : types) {
 
			if (t.getName().equals(name)) {
 
				return t;
			}
		}
 
		throw new Exception("Type '" + name + "' not found");
	}
	
	//Cosas
	
	public void createItem(String code, String name, String desc, String typeName) throws Exception {
		 
		if (getItem(code) != null) {
 
			throw new Exception("An item with code '" + code + "' already exists");
		}
 
		Type type = findType(typeName);
 
		Item i = new Item(code, name, desc, type);
 
		type.addItem(i);
 
		items.add(i);
	}
	
	public void addCategoryToItem(String itemCode, String categoryName) throws Exception {
		 
		Item i = findItem(itemCode);
 
		Category c = findCategory(categoryName);
 
		i.addCategory(c);
 
		c.addItem(i);
	}
	
	public void removeCategoryFromItem(String itemCode, String categoryName) throws Exception {
		 
		Item i = findItem(itemCode);
 
		Category c = findCategory(categoryName);
 
		i.deleteCategory(c);
 
		c.deleteItem(i);
	}
	
	public void updateItem(String itemCode, String name, String desc, String typeName) throws Exception {
		 
		Item i = findItem(itemCode);
 
		Type newType = findType(typeName);
 
		i.getType().deleteItem(i);
 
		i.setName(name);
 
		i.setDescription(desc);
 
		i.setType(newType);
 
		newType.addItem(i);
	}
	
	public void removeItem(String code) throws Exception {
		 
		Item i = findItem(code);
 
		if (i.isLend()) {
 
			throw new Exception("Item " + i.getName() + " can't be deleted because it's currently lent out");
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
	
	public void createPerson(String name, String phone, String email) throws Exception {
		 
		for (Person p : people) {
 
			if (p.getName().equals(name)) {
 
				throw new Exception("A person named " + name + " already exists");
			}
		}
 
		people.add(new Person(name, phone, email));
	}
	
	public void updatePerson(String personName, String newName, String phone, String email) throws Exception {
		 
		Person p = findPerson(personName);
 
		p.setName(newName);
 
		p.setPhone(phone);
 
		p.setEmail(email);
	}
	
	public void removePerson(String name) throws Exception {
		 
		Person p = findPerson(name);
 
		if (p.hasLoans()) {
 
			throw new Exception(" " + p.getName() + " can't be deleted because of active loans");
		}
 
		people.remove(p);
	}
	
	public ArrayList<Person> getPeople() {
		
		return people;
		
	}
	
	//Categorias
	
	public void createCategory(String name) throws Exception {
		 
		for (Category c : categories) {
 
			if (c.getName().equals(name)) {
 
				throw new Exception("A category named " + name + " already exists");
			}
		}
 
		categories.add(new Category(name));
	}
	
	public void updateCategory(String categoryName, String newName) throws Exception {
		 
		Category c = findCategory(categoryName);
 
		c.setName(newName);
	}
	
	public void removeCategory(String name) throws Exception {
		 
		Category c = findCategory(name);
 
		for (Item i : new ArrayList<>(c.getItems())) {
 
			i.deleteCategory(c);
		}
 
		categories.remove(c);
	}
	
	
	public ArrayList<Category> getCategories() {
		
		return categories;
	}
	
	//Tipos
	
	public void createType(String name, String desc) throws Exception {
		 
		for (Type t : types) {
 
			if (t.getName().equals(name)) {
 
				throw new Exception("A type named " + name + " already exists");
			}
		}
 
		types.add(new Type(name, desc));
	}
	
	public void updateType(String typeName, String newName, String desc) throws Exception {
		 
		Type t = findType(typeName);
 
		t.setName(newName);
 
		t.setDescription(desc);
	}
	
	public void removeType(String name) throws Exception {
		 
		Type t = findType(name);
 
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
	
	public Loan doLoan(String personName, ArrayList<String> itemCodes) throws Exception {
		 
		Person p = findPerson(personName);
 
		Loan l = new Loan(p);
 
		for (String code : itemCodes) {
 
			Item i = findItem(code);
 
			if (i.isLend()) {
 
				throw new Exception("Item '" + i.getName() + "' is already lent out");
			}
 
			l.addItem(i);
		}
 
		p.addLoan(l);
 
		loans.add(l);
 
		return l;
	}
	
	public void addItemToLoan(Loan l, String itemCode) throws Exception {
		 
		Item i = findItem(itemCode);
 
		if (i.isLend()) {
 
			throw new Exception("Item '" + i.getName() + "' is already lent out");
		}
 
		l.addItem(i);
	}
	
	public void removeItemFromLoan(Loan l, String itemCode) throws Exception {
		 
		Item i = findItem(itemCode);
 
		l.deleteItem(i);
	}
	
	public void returnItem(Loan l, String itemCode) throws Exception {
		 
		Item i = findItem(itemCode);
 
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
				
				else {
					a.activate();
				}
			}
		}
		
		return actives;
	}
	
	//Reportes
	
	public String reportByUser() {
		
		StringBuilder sb = new StringBuilder("Report by user\n");
		
		ArrayList<Person> ordered = new ArrayList<>(people);
		
		ordered.sort(Comparator.comparing(Person::getName));
		
		for (Person p : ordered) {
			
			sb.append("Person: ").append(p.getName()).append("\n");
			
			sb.append("Phone: ").append(p.getPhone()).append("\n");
			
			sb.append("Email: ").append(p.getEmail()).append("\n");
			
			if (p.getLoans().isEmpty()) {
				
				sb.append("No active loans\n");
			}
			
			else {
				
				for (Loan l : p.getLoans()) {
					
					sb.append(" Loan (").append(l.getDate()).append("):\n");
					
					for (Item i : l.getItems()) {
						
						sb.append(" - ").append(i).append("\n");
					}
				}
			}
			
			sb.append(" \n");
		}
		
		return sb.toString();
	}
	
	public String reportePorItem() {
		
        StringBuilder sb = new StringBuilder("Report by item\n");
 
        ArrayList<Item> ordered = new ArrayList<>(items);
        
        ordered.sort(Comparator.comparing(Item::getName));
 
        for (Item i : ordered) {
        	
            sb.append("item: ").append(i.getName()).append(" [").append(i.getCode()).append("]\n");
            
            sb.append("Description: ").append(i.getDescription()).append("\n");
            
            sb.append("Type: ").append(i.getType().getName()).append("\n");
            
 
            if (i.isLend()) {
            	
                sb.append("Status: Lend to ").append(i.getLoan().getPerson().getName()).append("\n");
            } 
            
            else {
            	
                sb.append("Status: Available\n");
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
 
    public String reportePorCategoria() {
    	
        StringBuilder sb = new StringBuilder("Report by category\n");
 
        ArrayList<Category> ordered = new ArrayList<>(categories);
        
        ordered.sort(Comparator.comparing(Category::getName));
 
        for (Category c : ordered) {
        	
            sb.append("Category: ").append(c.getName()).append("\n");
 
            if (c.getItems().isEmpty()) {
            	
                sb.append("No items in this category\n");
            } 
            
            else {
            	
                for (Item i : c.getItems()) {
                	
                    sb.append(" - ").append(i).append("\n");
                }
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
 
    public String reportePorTipo() {
    	
        StringBuilder sb = new StringBuilder("Report by type\n");
 
        ArrayList<Type> ordered = new ArrayList<>(types);
        
        ordered.sort(Comparator.comparing(Type::getName));
 
        for (Type t : ordered) {
        	
            sb.append("Type: ").append(t.getName()).append("\n");
 
            if (t.getItems().isEmpty()) {
            	
                sb.append("No items of this type\n");
            } 
            
            else {
            	
                for (Item i : t.getItems()) {
                	
                    sb.append(" - ").append(i).append("\n");
                }
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
	
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
