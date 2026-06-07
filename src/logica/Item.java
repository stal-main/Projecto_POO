package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private Type type;
	
	private ArrayList<Category> categories;
	
	private Loan loan;
	
	public Item(String code, String name, String description, Type type) {
		
		this.code = code;
		
		this.name = name;
		
		this.description = description;
		
		this.type = type;
		
		this.categories = new ArrayList<>();
		
		this.loan = null;
	}
	
	public String getCode() {
		
		return code;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Type getType() {
    	
    	return type;
    }
    
    public void setType(Type type) {
    	
    	this.type = type;
    }
    
    public void addCategory(Category c) {
    	
    	if (!categories.contains(c)) {
    		
    		categories.add(c);
    	}
    }
    
    public void deleteCategory(Category c) {
    	
    	categories.remove(c);
    }
    
    public ArrayList<Category> getCategories() {
    	
    	return categories;
    }
    
    public Loan getLoan() {
    	
    	return loan;
    }
    
    public void setLoan(Loan l) {
    	
    	this.loan = l;
    }
    
    @Override
    public String toString() {
    	
    	return name + " [" + code + "]";
    }
}
