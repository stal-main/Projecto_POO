package control;

import logica.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Control implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String DATA_ARCHIVE = "data.dat";
	
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

}
