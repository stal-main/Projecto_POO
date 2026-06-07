package logica;

import java.io.Serializable;
import java.util.Date;

public class Alert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private Date date;
	
	private int interval;
	
	private AlertType type;
	
	private Loan loan;
	
	public Alert(String message, Date date, int interval, AlertType type, Loan loan) {
		
		this.message = message;
		
		this.date = date;
		
		this.interval = interval;
		
		this.type = type;
		
		this.loan = loan;
	}
	
	public String getMessage() {
		
		return message;
	}
	
	public Date getDate() {
		
		return date;
	}
	
	public AlertType getType() {
		
		return type;
	}
	
	public Loan getLoan() {
		
		return loan;
	}
	
	public boolean mustActivate() {
		
		return !new Date().before(date);
	}
	
	public void activate() {
		
		if (type == AlertType.RECURRENT && interval > 0) {
			
			long intervalMsg = (long) interval * 24L * 60L * 1000L;
			
			date = new Date(date.getTime() + intervalMsg);
		}
	}
	
	@Override
	public String toString() {
		
		return "[" + type + "] " + message + " - " + date;
	}

}
