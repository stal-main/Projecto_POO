package logica;

import java.io.Serializable;

public class Object implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String name;
	
	private String description;
	
	public Object(String code, String name, String description) {
		
		this.code = code;
		
		this.name = name;
		
		this.description = description;
	}
	
	public String getCode() {
		
		return code;
	}
	
	public String getName() {
		
		return name;
	}

}
