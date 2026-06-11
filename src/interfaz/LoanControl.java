package interfaz;

import control.Control;

public class LoanControl {
	
	private static Control control;

	public static void main(String[] args) {
		
		control = Control.loadData();

	}

}
