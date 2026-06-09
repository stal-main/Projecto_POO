package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private ArrayList<Item> items;
	
	public Category(String name) {
		
		this.name = name;
		
		this.items = new ArrayList<>();
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
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
