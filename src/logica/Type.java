package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Type implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String description;
	
	private ArrayList<Item> items;
	
	public Type(String name, String description) {
		
		this.name = name;
		
		this.description = description;
		
		this.items = new ArrayList<>();
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
    
    public void addItem(Item i) {
    	
    	if (!items.contains(i)) {
    		
    		items.add(i);
    	}
    }
    
    public void deleteItem(Item i) {
    	
    	items.remove(i);
    }
    
    public ArrayList<Item> getItems() {
    	
    	return items;
    }
    
    @Override
    public String toString() {
    	
        return name;
    }
    

}
